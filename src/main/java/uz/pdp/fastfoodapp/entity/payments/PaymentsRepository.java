package uz.pdp.fastfoodapp.entity.payments;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PaymentsRepository extends JpaRepository<Payments, UUID> {


    @Query(nativeQuery = true,value = "select cast(id as varchar) as id,\n" +
            "       name                as name\n" +
            "from payments")
    List<CustomPayment> getAllPayType();

}
