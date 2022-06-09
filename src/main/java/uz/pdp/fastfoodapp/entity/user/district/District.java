package uz.pdp.fastfoodapp.entity.user.district;


import lombok.*;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class District extends AbsEntity {
    private String name;
}
