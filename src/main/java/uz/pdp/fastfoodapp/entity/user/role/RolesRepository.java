package uz.pdp.fastfoodapp.entity.user.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RolesRepository extends JpaRepository<Roles, UUID> {


}
