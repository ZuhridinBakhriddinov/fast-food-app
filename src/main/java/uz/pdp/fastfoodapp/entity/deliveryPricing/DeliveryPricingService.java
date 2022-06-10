package uz.pdp.fastfoodapp.entity.deliveryPricing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.template.ApiResponse;

@Service
public class DeliveryPricingService {
    @Autowired
    DeliveryPricingRepository deliveryPricingRepository;

}
