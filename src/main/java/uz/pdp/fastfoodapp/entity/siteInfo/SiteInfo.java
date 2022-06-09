package uz.pdp.fastfoodapp.entity.siteInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "site_info")
@PackagePrivate
public class SiteInfo extends AbsEntity {

    String address;

    @Column(nullable = false, unique = true)
    String phoneNumber;
    Double longitude;
    Double latitude;
}
