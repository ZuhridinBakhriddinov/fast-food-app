package uz.pdp.fastfoodapp.entity.user.address;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@PackagePrivate
@Data
public class AddressDto {
    UUID userId;
    String name;
    UUID districtId;
    String landmark;
    Integer houseNumber;
    Integer entrance;
    Integer flat;
    Integer floor;
    float latitude;
    float longitude;
}
