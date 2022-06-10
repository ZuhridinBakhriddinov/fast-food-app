package uz.pdp.fastfoodapp.entity.user.district;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DistrictService {
    @Autowired
    DistrictRepository districtRepository;

    public ApiResponse getAllDistrict() {
        List<District> all = districtRepository.findAll();
        if (all.isEmpty()) {
            return new ApiResponse("Not found", false);
        }
        return new ApiResponse("Successfully", true, all);
    }

    public ApiResponse getDistrictById(UUID uuid) {
        Optional<District> byId = districtRepository.findById(uuid);
        if (byId.isEmpty()) {
            return new ApiResponse("Not found", false);
        }
        return new ApiResponse("Successfully", true, byId);
    }

    public ApiResponse addDistrict(District district) {
        try {
            District save = districtRepository.save(district);
            return new ApiResponse("Successfully saved", true, save);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse editDistrict(UUID uuid, District district) {
        Optional<District> byId = districtRepository.findById(uuid);
        if (byId.isEmpty()) {
            return new ApiResponse("Not found", false);
        }
        District edit = byId.get();
        edit.setName(district.getName());
        District save = districtRepository.save(edit);
        return new ApiResponse("Successfully edited", true, save);
    }

    public ApiResponse delete(UUID uuid) {
        try {
            districtRepository.deleteById(uuid);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Not found", false);
        }
    }

}
