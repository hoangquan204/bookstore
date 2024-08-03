package hoangquan.vn.service;

import hoangquan.vn.entity.Role;
import hoangquan.vn.entity.User;
import hoangquan.vn.exception.AppException;
import hoangquan.vn.exception.ErrorCode;
import hoangquan.vn.repository.RoleRepository;
import hoangquan.vn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User addUser(User user){
        User temp = userRepository.findUserByUsername(user.getUsername())
                .orElse(null);

        if(temp!=null)
            throw new AppException(ErrorCode.USER_EXISTED);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role_user = roleRepository.findRoleByName("USER")
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        Set<Role> roles = new HashSet<Role>();
        roles.add(role_user);
        user.setRoles(roles);


        return userRepository.save(user);
    }

    public User getUserById(int id){
        return userRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public User updateUser(User user){
        User temp = userRepository.findById(user.getId())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));

        temp.mapContent(user);

        return userRepository.save(temp);
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

}
