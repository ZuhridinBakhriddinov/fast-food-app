package uz.pdp.fastfoodapp.entity.user.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.UUID;

@RestController
@RequestMapping("api/address")
public class AddressController {
    @Autowired
    AddressService addressService;


    @GetMapping
    public HttpEntity<?> getAllAddress() {
        ApiResponse allAddress = addressService.getAllAddress();
        return ResponseEntity.ok(allAddress);
    }

//    @GetMapping("/{uuid}")
//    public HttpEntity<?> getAddressById(@PathVariable UUID uuid) {
//        ApiResponse addressById = addressService.getAddressById(uuid);
//        return ResponseEntity.ok(addressById);
//    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping
    public HttpEntity<?> addAddress(@RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.addAddress(addressDto);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PutMapping("/{addressId}")
    public HttpEntity<?> editAddress(@PathVariable UUID addressId,@RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.editAddress(addressId, addressDto);
        return ResponseEntity.ok(apiResponse);
    }


    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/{uuid}")
    public HttpEntity<?> delete(@PathVariable UUID uuid) {
        ApiResponse apiResponse = addressService.deleteAddreesById(uuid);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/{userId}")
    public HttpEntity<?> getAddressByUserId(@PathVariable UUID userId) {
        ApiResponse allAddressByUserId = addressService.getAllAddressByUserId(userId);
        return ResponseEntity.ok(allAddressByUserId);
    }
}
