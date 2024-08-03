package hoangquan.vn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Iterator;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String username;
    @Size(min=8, message = "Password phải có tối thiểu 8 kí tự!")
    String password;

    @ManyToMany
    Set<Role> roles;

    public void mapContent(User user){
        this.password = user.getPassword();
    }

}
