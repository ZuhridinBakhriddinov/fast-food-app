package uz.pdp.fastfoodapp.entity.payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentsService {

    @Autowired
    PaymentsRepository paymentsRepository;

    public ResponseEntity<?> getAllFoods() {
        List<CustomPayment> allPayType = paymentsRepository.getAllPayType();
        return ResponseEntity.ok(allPayType);
    }
}
