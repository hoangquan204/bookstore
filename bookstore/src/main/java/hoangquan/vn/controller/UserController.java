package hoangquan.vn.controller;

import hoangquan.vn.dto.response.ApiResponse;
import hoangquan.vn.entity.User;
import hoangquan.vn.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ApiResponse<List<User>> getUsers(){
        return ApiResponse.<List<User>>builder()
                .code(200)
                .message("Get users successfully!")
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{id}")
    @PostAuthorize("returnObject.result.username == authentication.name")
    public ApiResponse<User> getUserById(@PathVariable int id){
        return ApiResponse.<User>builder()
                .code(200)
                .message("Get user successfully!")
                .result(userService.getUserById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<User> addUser(@RequestBody @Valid User user){
        return ApiResponse.<User>builder()
                .code(200)
                .message("Add user successfully!")
                .result(userService.addUser(user))
                .build();
    }

    @PutMapping("{id}")
    public ApiResponse<User> updateUser(@PathVariable int id, @RequestBody User user){
        user.setId(id);
        return ApiResponse.<User>builder()
                .code(200)
                .message("Add user successfully!")
                .result(userService.updateUser(user))
                .build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ApiResponse<String> deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Delete user successfully!")
                .build();
    }
}
