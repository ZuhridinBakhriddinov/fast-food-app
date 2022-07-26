package uz.pdp.fastfoodapp.entity.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "payments")
@PackagePrivate
public class Payments extends AbsEntity {


   String name;


}
