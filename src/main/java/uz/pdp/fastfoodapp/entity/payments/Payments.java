package uz.pdp.fastfoodapp.entity.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import uz.pdp.fastfoodapp.entity.enums.PayType;
import uz.pdp.fastfoodapp.entity.order.Order;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "payments")
@PackagePrivate
public class Payments extends AbsEntity {

    @OneToOne
    Order orderId;
    @Enumerated(EnumType.STRING)
    PayType payType;
    double totalAmount;


}
