package com.example.Demo_MultiTenant_MongoDB.Controller;


import com.example.Demo_MultiTenant_MongoDB.Model.Tenant;
import com.example.Demo_MultiTenant_MongoDB.Service.TenantDatabaseService;
import com.example.Demo_MultiTenant_MongoDB.Service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tenant")
public class TenantController {
    
    @Autowired
    TenantService tenantService;

    @Autowired
    TenantDatabaseService tenantDatabaseService;

    @PostMapping("/add")
    public ResponseEntity<Tenant> getTenant(@RequestBody Tenant tenant) {
        Tenant tenant1 = tenantService.saveTenant(tenant);
        tenantDatabaseService.createDatabaseForTenant(tenant1.getId());
        return ResponseEntity.ok(tenant1);
    }

    @GetMapping("/getById")
    public ResponseEntity<Tenant> getTenantById(@RequestParam("id") String tenantId) {
        Tenant tenant = tenantService.getTenantById(tenantId);

        if(tenant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(tenant);
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteTenantById(@RequestParam("id") String tenantId) {
        tenantService.deleteTenantById(tenantId);
        tenantDatabaseService.dropDatabaseForTenant(tenantId);
        return ResponseEntity.ok("Delete Successfully");
    }
}
