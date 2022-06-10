package uz.pdp.fastfoodapp.entity.user.district;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.UUID;

@RestController
@RequestMapping("api/district")
public class DistrictController {
    @Autowired
    DistrictService districtServise;

    @GetMapping
    public HttpEntity<?> getAllDistrict() {
        ApiResponse allDistrict = districtServise.getAllDistrict();
        return ResponseEntity.status(allDistrict.isSuccess() ? 200 : 400).body(allDistrict);
    }

    @GetMapping("/{uuid}")
    public HttpEntity<?> getDistrictById(@PathVariable UUID uuid) {
        ApiResponse allDistrictById = districtServise.getDistrictById(uuid);
        return ResponseEntity.status(allDistrictById.isSuccess() ? 200 : 400).body(allDistrictById);
    }

    @PostMapping
    public HttpEntity<?> addDistrict(@RequestBody District district) {
        ApiResponse apiResponse = districtServise.addDistrict(district);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @PutMapping("/{uuid}")
    public HttpEntity<?> editDistrict(@PathVariable UUID uuid, @RequestBody District district) {
        ApiResponse apiResponse = districtServise.editDistrict(uuid, district);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {
        ApiResponse delete = districtServise.delete(uuid);
        return ResponseEntity.status(delete.isSuccess() ? 200 : 400).body(delete);
    }
}
