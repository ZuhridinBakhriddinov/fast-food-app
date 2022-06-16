package uz.pdp.fastfoodapp.entity.user.permission;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PermissionService {
    @Autowired
    PermissionRepository permissionRepository;

    public ApiResponse getAllPermission() {
        List<Permission> all = permissionRepository.findAll();
        if (all.isEmpty()) {
            return new ApiResponse("Not found", false);
        }
        return new ApiResponse("Successfully", true, all);
    }

    public ApiResponse getPermissionById(UUID uuid) {
        Optional<Permission> byId = permissionRepository.findById(uuid);
        if (byId.isEmpty()) {
            return new ApiResponse("Not found", false);
        }
        return new ApiResponse("Successfully", true, byId);
    }

    public ApiResponse addPermission(Permission permission) {
        try {
            Permission save = permissionRepository.save(permission);
            return new ApiResponse("Successfully saved", true, save);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse editPermission(UUID uuid, Permission permission) {
        Optional<Permission> byId = permissionRepository.findById(uuid);
        if (byId.isEmpty()) {
            return new ApiResponse("Not found", false);
        }
        Permission edit = byId.get();
        edit.setName(permission.getName());
        Permission save = permissionRepository.save(edit);
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
