package uz.pdp.fastfoodapp.entity.payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentsService {

    @Autowired
    PaymentsRepository paymentsRepository;
}
