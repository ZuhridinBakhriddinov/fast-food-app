package uz.pdp.fastfoodapp.entity.user.district;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DistrictRepository extends JpaRepository<District, UUID> {
}
