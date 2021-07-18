package com.deviniti.multitenancy.separate.schema.tenant;

public interface TenantDomain {

	public boolean setTenantInContext(String tenantId);

}
