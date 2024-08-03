package hoangquan.vn.service;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import hoangquan.vn.dto.request.IntroRequest;
import hoangquan.vn.dto.response.AuthResponse;
import hoangquan.vn.entity.InvalidatedToken;
import hoangquan.vn.entity.Role;
import hoangquan.vn.entity.User;
import hoangquan.vn.exception.AppException;
import hoangquan.vn.exception.ErrorCode;
import hoangquan.vn.repository.InvalidatedTokenRepository;
import hoangquan.vn.repository.UserRepository;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    protected static final String SIGNER_KEY = "1TjXchw5FloESb63Kc+DFhTARvpWL4jUGCwfGWxuG5SIf/1y/LgJxHnMqaF6A/ij";
    public AuthResponse authenticate(User user){
        User temp = userRepository.findUserByUsername(user.getUsername())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(user.getPassword(), temp.getPassword());

        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return AuthResponse.builder()
                .authenticated(authenticated)
                .token(generateToken(user))
                .build();
    }

    public String generateToken(User user){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("hoangquan.vn")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",builderScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean introspect(IntroRequest request)
            throws JsonEOFException, ParseException, JOSEException {

        var token = request.getToken();

        return verifyToken(token);
    }

    public boolean verifyToken(String token)
            throws JsonEOFException, ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();

        return !invalidatedTokenRepository.existsById(jwtId)
                && signedJWT.verify(verifier)
                && expiryTime.after(new Date());
    }

    public String builderScope(User user){
        User temp = userRepository.findUserByUsername(user.getUsername())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));

        StringJoiner stringJoiner = new StringJoiner("_");

        if(!CollectionUtils.isEmpty(temp.getRoles())){
            temp.getRoles().forEach(s -> stringJoiner.add(s.getName()));
        }

        return stringJoiner.toString();
    }

    public String logOut(IntroRequest request)
            throws JsonEOFException, ParseException, JOSEException {
        String token = request.getToken();

        SignedJWT signedJWT = SignedJWT.parse(token);

        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();

        invalidatedTokenRepository.save(InvalidatedToken.builder()
                .id(jwtId)
                .expiryDate(new java.sql.Date(System.currentTimeMillis()))
                .build());

        return "Log out successfully!";
    }
}

