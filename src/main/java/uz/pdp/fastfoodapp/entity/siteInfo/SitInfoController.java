package uz.pdp.fastfoodapp.entity.siteInfo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/set-info-controller")
public class SitInfoController {
    private final SitInfoService sitInfoService;


    @GetMapping
    public HttpEntity<?> getAllSiteInfo() {
        ApiResponse allSiteInfo = sitInfoService.getAllSiteInfo();
        return ResponseEntity.status(allSiteInfo.isSuccess() ? 200 : 400).body(allSiteInfo);
    }

    @GetMapping("/{uuid}")
    public HttpEntity<?> getSiteInfoById(@PathVariable UUID uuid) {
        ApiResponse allSiteInfoById = sitInfoService.getSitInfoById(uuid);
        return ResponseEntity.status(allSiteInfoById.isSuccess() ? 200 : 400).body(allSiteInfoById);
    }

    @PostMapping
    public HttpEntity<?> addSiteInfo(@RequestPart SiteInfo siteInfo) {
        ApiResponse apiResponse = sitInfoService.addSiteInfo(siteInfo);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @PutMapping("/{uuid}")
    public HttpEntity<?> editSitInfo(@PathVariable UUID uuid, @RequestPart SiteInfo siteInfo) {
        ApiResponse apiResponse = sitInfoService.editSitInfo(siteInfo, uuid);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {
        ApiResponse delete = sitInfoService.deleteSitInfo(uuid);
        return ResponseEntity.status(delete.isSuccess() ? 200 : 400).body(delete);
    }


}


