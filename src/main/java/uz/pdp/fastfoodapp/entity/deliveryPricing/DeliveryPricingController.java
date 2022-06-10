package uz.pdp.fastfoodapp.entity.deliveryPricing;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.fastfoodapp.entity.siteInfo.SiteInfo;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery-pricing")
public class DeliveryPricingController {

    private final DeliveryPricingService deliveryPricingService;

    @GetMapping("/{uuid}")
    public HttpEntity<?> getDeliveryPricingById(@PathVariable UUID uuid) {
        ApiResponse allDeliveryPricingById = deliveryPricingService.getDeliveryPricingById(uuid);
        return ResponseEntity.status(allDeliveryPricingById.isSuccess() ? 200 : 400).body(allDeliveryPricingById);
    }

    @PostMapping("/{uuid}")
    public HttpEntity<?> addDeliveryPricing(@RequestParam DeliveryPricing deliveryPricing) {
        ApiResponse apiResponse = deliveryPricingService.addDeliveryPricing(deliveryPricing);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @PutMapping("/{uuid}")
    public HttpEntity<?> editDeliveryPricing(@PathVariable UUID uuid, @RequestPart DeliveryPricing deliveryPricing) {
        ApiResponse apiResponse = deliveryPricingService.editDeliveryPricing(deliveryPricing, uuid);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @DeleteMapping("/uuid")
    public HttpEntity<?> delete(@PathVariable UUID uuid) {
        ApiResponse delete = deliveryPricingService.deleteDeliveryPricing(deliveryPricingService, uuid);
        return ResponseEntity.status(delete.isSuccess() ? 200 : 400).body(delete);
    }


}
