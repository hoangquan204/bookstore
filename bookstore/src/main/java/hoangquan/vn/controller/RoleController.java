package hoangquan.vn.controller;

import hoangquan.vn.dto.response.ApiResponse;
import hoangquan.vn.entity.Role;
import hoangquan.vn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ApiResponse<List<Role>> getRoles(){
        return ApiResponse.<List<Role>>builder()
                .code(200)
                .message("Get roles successfully!")
                .result(roleService.getRoles())
                .build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ApiResponse<Role> addRole(@RequestBody Role role){
        return ApiResponse.<Role>builder()
                .code(200)
                .message("Add role successfully!")
                .result(roleService.addRole(role))
                .build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ApiResponse<Void> deleteRole(@PathVariable int id){
        roleService.deleteRole(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Delete role successfully!")
                .build();
    }

}
