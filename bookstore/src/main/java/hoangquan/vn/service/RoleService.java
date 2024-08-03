package hoangquan.vn.service;

import hoangquan.vn.entity.Role;
import hoangquan.vn.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    public Role addRole(Role role){
        return roleRepository.save(role);
    }

    public void deleteRole(int id){
        roleRepository.deleteById(id);
    }
}
