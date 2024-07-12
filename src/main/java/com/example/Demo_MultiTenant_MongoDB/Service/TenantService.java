package com.example.Demo_MultiTenant_MongoDB.Service;

import com.example.Demo_MultiTenant_MongoDB.Model.Tenant;
import com.example.Demo_MultiTenant_MongoDB.Repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantService {
    @Autowired
    TenantRepository tenantRepository;

    public Tenant saveTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public Tenant getTenantById(String id) {
        return tenantRepository.getTenantById(id);
    }

    public Tenant getTenantByName(String name) {
        return tenantRepository.getTenantByName(name);
    }

    public void deleteTenantById(String id) {
        tenantRepository.deleteTenantById(id);
    }
}
