package uz.pdp.fastfoodapp.entity.user.role;


import lombok.*;
import uz.pdp.fastfoodapp.entity.user.role.Role;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "roles")
public class Roles extends AbsEntity {
    private String name;
    private Role role;

}
