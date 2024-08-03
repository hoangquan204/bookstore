package hoangquan.vn.controller;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.nimbusds.jose.JOSEException;
import hoangquan.vn.dto.request.IntroRequest;
import hoangquan.vn.dto.response.ApiResponse;
import hoangquan.vn.dto.response.AuthResponse;
import hoangquan.vn.entity.User;
import hoangquan.vn.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/log-in")
    public ApiResponse<AuthResponse> authenticate(@RequestBody User user){
        return ApiResponse.<AuthResponse>builder()
                .code(200)
                .result(authService.authenticate(user))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<Boolean> introspect(@RequestBody IntroRequest request)
            throws JsonEOFException, ParseException, JOSEException {
        return ApiResponse.<Boolean>builder()
                .code(200)
                .result(authService.introspect(request))
                .build();
    }

    @PostMapping("/log-out")
    public ApiResponse<Void> logOut(@RequestBody IntroRequest request)
            throws JsonEOFException, ParseException, JOSEException {
        return ApiResponse.<Void>builder()
                .code(200)
                .message(authService.logOut(request))
                .build();
    }
}
