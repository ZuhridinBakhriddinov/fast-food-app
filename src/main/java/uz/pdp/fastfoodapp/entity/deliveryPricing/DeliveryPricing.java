package uz.pdp.fastfoodapp.entity.deliveryPricing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "delivery_pricing")
@PackagePrivate
public class DeliveryPricing extends AbsEntity {

    double initialDeliveryPrice;
    double deliveryPricePerKm;

}
