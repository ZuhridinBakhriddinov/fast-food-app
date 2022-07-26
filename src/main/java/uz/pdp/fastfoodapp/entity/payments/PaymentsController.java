package uz.pdp.fastfoodapp.entity.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    private final PaymentsService paymentsService;

    @GetMapping("/getPayType")
    public ResponseEntity<?> getPaymentTypes() {
        return paymentsService.getAllFoods();

    }
}
