package uz.pdp.fastfoodapp.entity.orderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fastfoodapp.entity.order.Order;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
