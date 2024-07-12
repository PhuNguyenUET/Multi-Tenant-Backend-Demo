package com.example.Demo_MultiTenant_MongoDB.Service;

import com.example.Demo_MultiTenant_MongoDB.Model.Tenant;
import com.example.Demo_MultiTenant_MongoDB.Repository.TenantAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeaderAuthService {
    @Autowired
    TenantAuth tenantAuth;

    public boolean checkTenantExist(String id) {
        Tenant tenant = tenantAuth.getTenantById(id);
        return tenant != null;
    }
}
