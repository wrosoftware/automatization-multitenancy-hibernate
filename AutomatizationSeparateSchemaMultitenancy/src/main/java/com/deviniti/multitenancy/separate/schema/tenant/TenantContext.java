package com.deviniti.multitenancy.separate.schema.tenant;

import com.deviniti.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

public abstract class TenantContext {

	public static final String DEFAULT_TENANT_ID = "public";
	private static ThreadLocal<TenantDTO> currentTenant = new ThreadLocal<TenantDTO>();

    public static void setCurrentTenant(TenantDTO tenant) {
        currentTenant.set(tenant);
    }

    public static TenantDTO getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }
	
}
