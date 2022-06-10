package uz.pdp.fastfoodapp.entity.user.permission;


import lombok.*;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "permissions")
public class Permissions extends AbsEntity {
    private String name;
}
