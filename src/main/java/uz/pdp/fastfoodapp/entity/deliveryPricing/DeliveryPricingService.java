package uz.pdp.fastfoodapp.entity.deliveryPricing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.exception.RestException;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryPricingService {
    @Autowired
    DeliveryPricingRepository deliveryPricingRepository;

    public ApiResponse getDeliveryPricingById(UUID uuid) {
        Optional<DeliveryPricing> optionalDP = deliveryPricingRepository.findById(uuid);
        if (!optionalDP.isPresent()) {
            return new ApiResponse("Not found", false);
        }
        return new ApiResponse("Success", true, optionalDP);
    }

    public ApiResponse addDeliveryPricing(DeliveryPricing deliveryPricing) {
        try {
            DeliveryPricing save = deliveryPricingRepository.save(deliveryPricing);

            return new ApiResponse("Successfully added", true, save);
        } catch (Exception e) {
            return new ApiResponse("Maybe this already exist", false);
        }
    }

    public ApiResponse editDeliveryPricing(DeliveryPricing deliveryPricing, UUID uuid) {
//        Optional<SiteInfo> optionalAgent = deliveryPricingRepository.findById(uuid);
        DeliveryPricing editDeliveryPricing = deliveryPricingRepository.findById(uuid).orElseThrow(() -> RestException.notFound("Not found"));

        try {
            editDeliveryPricing.setDeliveryPricePerKm(deliveryPricing.getDeliveryPricePerKm());
            editDeliveryPricing.setInitialDeliveryPrice(deliveryPricing.getInitialDeliveryPrice());
            DeliveryPricing save = deliveryPricingRepository.save(editDeliveryPricing);
            return new ApiResponse("Successfully edited", true, save);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }
    public ApiResponse deleteDeliveryPricing(DeliveryPricingService deliveryPricingService, UUID uuid) {
        try {
            deliveryPricingRepository.deleteById(uuid);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("SitInfo not found", false);
        }
    }

}
