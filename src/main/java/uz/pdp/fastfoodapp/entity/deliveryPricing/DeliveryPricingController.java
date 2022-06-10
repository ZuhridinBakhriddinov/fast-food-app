package uz.pdp.fastfoodapp.entity.deliveryPricing;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.fastfoodapp.template.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery-pricing")
public class DeliveryPricingController {

    private final DeliveryPricingService deliveryPricingService;



}
