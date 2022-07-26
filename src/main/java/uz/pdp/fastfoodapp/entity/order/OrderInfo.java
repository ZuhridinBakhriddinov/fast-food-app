package uz.pdp.fastfoodapp.entity.order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import java.util.UUID;




@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@PackagePrivate
public class OrderInfo {
    UUID payTypeId;
    UUID userId;
    UUID addressId;
}
