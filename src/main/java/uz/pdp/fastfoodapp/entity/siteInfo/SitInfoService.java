package uz.pdp.fastfoodapp.entity.siteInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SitInfoService {

    @Autowired
    SitInfoRepository sitInfoRepository;

    public ApiResponse getAllSiteInfo() {

        List<SiteInfo> all = sitInfoRepository.findAll();
        if (all.size() == 0) {
            return new ApiResponse("List empty", false);
        }
        return new ApiResponse("Success", true, all);
    }

    public ApiResponse getSitInfoById(UUID uuid) {
        Optional<SiteInfo> optionalSiteInfo = sitInfoRepository.findById(uuid);
        if (optionalSiteInfo.isEmpty()) {
            return new ApiResponse("Not found", false);
        }
        return new ApiResponse("Success", true, optionalSiteInfo);
    }

    public ApiResponse addSiteInfo(SiteInfo siteInfo) {
        try {
            SiteInfo save = sitInfoRepository.save(siteInfo);

            return new ApiResponse("Successfully added", true, save);
        } catch (Exception e) {
            return new ApiResponse("Maybe this already exist", false);
        }
    }

    public ApiResponse editSitInfo(SiteInfo siteInfo, UUID uuid) {
        Optional<SiteInfo> optionalSiteInfo = sitInfoRepository.findById(uuid);
        if (optionalSiteInfo.isEmpty()) {
            return new ApiResponse("SitInfo not found", false);
        }
        try {
            SiteInfo editSitInfo = optionalSiteInfo.get();

            editSitInfo.setAddress(siteInfo.getAddress());
            editSitInfo.setPhoneNumber(siteInfo.getPhoneNumber());
            editSitInfo.setLatitude(siteInfo.getLatitude());
            editSitInfo.setLongitude(siteInfo.getLongitude());

            SiteInfo save = sitInfoRepository.save(editSitInfo);
            return new ApiResponse("Successfully edited", true, save);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse deleteSitInfo(UUID uuid) {
        try {
            sitInfoRepository.deleteById(uuid);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("SitInfo not found", false);
        }
    }


}

