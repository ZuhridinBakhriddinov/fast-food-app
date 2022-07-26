package uz.pdp.fastfoodapp.entity.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import uz.pdp.fastfoodapp.entity.orderItem.OrderItemDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@PackagePrivate
public class OrderListDto {
    List<OrderItemDto> orderItem;
    OrderInfo orderInfo;

}
