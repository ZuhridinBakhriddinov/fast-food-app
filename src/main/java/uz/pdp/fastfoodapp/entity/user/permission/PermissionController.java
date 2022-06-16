package uz.pdp.fastfoodapp.entity.user.permission;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.UUID;

@RestController
@RequestMapping("api/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @GetMapping
    public HttpEntity<?> getAllPermission() {
        ApiResponse allPermission = permissionService.getAllPermission();
        return ResponseEntity.status(allPermission.isSuccess() ? 200 : 400).body(allPermission);
    }

    @GetMapping("/{uuid}")
    public HttpEntity<?> getPermissionById(@PathVariable UUID uuid) {
        ApiResponse allPermissionById = permissionService.getPermissionById(uuid);
        return ResponseEntity.status(allPermissionById.isSuccess() ? 200 : 400).body(allPermissionById);
    }

    @PostMapping
    public HttpEntity<?> addPermission(@RequestBody Permission permission) {
        ApiResponse apiResponse = permissionService.addPermission(permission);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @PutMapping("/{uuid}")
    public HttpEntity<?> editPermission(@PathVariable UUID uuid, @RequestBody Permission permission) {
        ApiResponse apiResponse = permissionService.editPermission(uuid, permission);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {
        ApiResponse delete = permissionService.delete(uuid);
        return ResponseEntity.status(delete.isSuccess() ? 200 : 400).body(delete);
    }
}
