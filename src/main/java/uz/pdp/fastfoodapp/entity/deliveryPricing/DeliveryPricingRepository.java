package uz.pdp.fastfoodapp.entity.deliveryPricing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryPricingRepository extends JpaRepository<DeliveryPricing, UUID> {
}
