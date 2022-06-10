package uz.pdp.fastfoodapp.entity.user.permission;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.security.Permission;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PermissionService {
    @Autowired
    PermissionRepository permissionRepository;

    public ApiResponse getAllPermission() {
        List<Permissions> all = permissionRepository.findAll();
        if (all.isEmpty()) {
            return new ApiResponse("Not found", false);
        }
        return new ApiResponse("Successfully", true, all);
    }

    public ApiResponse getPermissionById(UUID uuid) {
        Optional<Permissions> byId = permissionRepository.findById(uuid);
        if (byId.isEmpty()) {
            return new ApiResponse("Not found", false);
        }
        return new ApiResponse("Successfully", true, byId);
    }

    public ApiResponse addPermission(Permissions permission) {
        try {
            Permissions save = permissionRepository.save(permission);
            return new ApiResponse("Successfully saved", true, save);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse editPermission(UUID uuid, Permissions permission) {
        Optional<Permissions> byId = permissionRepository.findById(uuid);
        if (byId.isEmpty()) {
            return new ApiResponse("Not found", false);
        }
        Permissions edit = byId.get();
        edit.setName(permission.getName());
        Permissions save = permissionRepository.save(edit);
        return new ApiResponse("Successfully edited", true, save);
    }

    public ApiResponse delete(UUID uuid) {
        try {
            permissionRepository.deleteById(uuid);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Not found", false);
        }
    }
}
