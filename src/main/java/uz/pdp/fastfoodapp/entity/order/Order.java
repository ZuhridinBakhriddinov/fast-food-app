package uz.pdp.fastfoodapp.entity.order;

import lombok.*;
import lombok.experimental.PackagePrivate;
import uz.pdp.fastfoodapp.entity.order.enums.OrderStatus;
import uz.pdp.fastfoodapp.entity.payments.Payments;
import uz.pdp.fastfoodapp.entity.user.User;
import uz.pdp.fastfoodapp.entity.user.address.Address;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@PackagePrivate
@Entity(name = "my_orders")
public class Order extends AbsEntity {
    @ManyToOne
    User user;

    @ManyToOne
    User deliverer;

    Integer orderNumber;

    LocalDateTime deliveredAt;

    Integer estimatedTime;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @OneToOne
    @JoinColumn(name = "address_id")
    Address address;

    Double totalSum;

    Double deliveryPrice;

    @ManyToOne
    Payments payments;

}
