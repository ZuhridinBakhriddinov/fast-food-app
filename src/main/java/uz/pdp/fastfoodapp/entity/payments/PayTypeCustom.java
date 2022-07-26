package uz.pdp.fastfoodapp.entity.payments;

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
public class PayTypeCustom {
    UUID id;
}
