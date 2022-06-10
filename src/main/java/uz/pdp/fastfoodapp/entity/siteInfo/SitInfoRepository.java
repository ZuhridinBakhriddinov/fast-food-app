package uz.pdp.fastfoodapp.entity.siteInfo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SitInfoRepository extends JpaRepository<SiteInfo, UUID> {
}
