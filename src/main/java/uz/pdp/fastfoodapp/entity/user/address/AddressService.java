package uz.pdp.fastfoodapp.entity.user.address;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.entity.user.User;
import uz.pdp.fastfoodapp.entity.user.UserRepository;
import uz.pdp.fastfoodapp.entity.user.district.District;
import uz.pdp.fastfoodapp.entity.user.district.DistrictRepository;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    UserRepository userRepository;

    public ApiResponse getAllAddress() {
        List<Address> all = addressRepository.findAll();
        if (all.isEmpty()) {
            return new ApiResponse("Address not found", false);
        }
        return new ApiResponse("Success", true, all);
    }

    public ApiResponse getAddressById(UUID uuid) {
        Optional<Address> byId = addressRepository.findById(uuid);
        if (!byId.isPresent()) {
            return new ApiResponse("Address not found", false);
        }
        return new ApiResponse("Successfully", true, byId);
    }

    public ApiResponse addAddress(AddressDto addressDto) {
        Optional<User> userId = userRepository.findById(addressDto.getUserId());
        if (!userId.isPresent()) {
            return new ApiResponse("User not found", false);
        }
        Optional<District> districtId = districtRepository.findById(addressDto.getDistrictId());
        if (!districtId.isPresent()) {
            return new ApiResponse("District not found", false);
        }
        try {
            Address save = addressRepository.save(new Address(userId.get(), addressDto.getName(), districtId.get(), addressDto.getLandmark(),
                    addressDto.getHouseNumber(), addressDto.getEntrance(), addressDto.getFlat(),
                    addressDto.getFloor(), addressDto.getLatitude(), addressDto.getLongitude()));
            return new ApiResponse("Successfully saved", true, save);
        } catch (Exception e) {
            return new ApiResponse("Error! Maybe address already exists!!", false);
        }
    }

    public ApiResponse editAddress(UUID addressId, AddressDto addressDto) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (!optionalAddress.isPresent()) {
            return new ApiResponse("Address not found", false);
        }
        Address address = optionalAddress.get();
        Optional<User> optionalUser = userRepository.findById(addressDto.getUserId());
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found", false);
        }
        address.setUser(optionalUser.get());
        address.setName(addressDto.getName());
        Optional<District> districtOptional = districtRepository.findById(addressDto.getDistrictId());
        if (!districtOptional.isPresent()) {
            return new ApiResponse("District not found", false);
        }
        address.setDistrict(districtOptional.get());
        address.setLandmark(addressDto.landmark);
        address.setHouseNumber(addressDto.getHouseNumber());
        address.setEntrance(addressDto.getEntrance());
        address.setFlat(addressDto.getFlat());
        address.setFloor(addressDto.getFloor());
        address.setLatitude(addressDto.getLatitude());
        address.setLongitude(addressDto.getLongitude());
        Address save = addressRepository.save(address);
        return new ApiResponse("Successfully edited", true, save);
    }

    public ApiResponse deleteAddreesById(UUID uuid) {
        try {
            addressRepository.deleteById(uuid);
            return new ApiResponse("Deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse getAllAddressByUserId(UUID userId) {
        List<getAllAddressByUserId> byUserId = addressRepository.findByUserId(userId);
        if (byUserId.isEmpty()) {
            return new ApiResponse("Address not found", false);
        }
        return new ApiResponse("Success", true, byUserId);
    }

}
