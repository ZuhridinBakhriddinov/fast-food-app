package uz.pdp.fastfoodapp.entity.order;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fastfoodapp.entity.feedback.Feedback;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
