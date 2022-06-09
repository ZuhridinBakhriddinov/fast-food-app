package uz.pdp.fastfoodapp.entity.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "permissions")
public class Permissions extends AbsEntity {
    private String name;
}
