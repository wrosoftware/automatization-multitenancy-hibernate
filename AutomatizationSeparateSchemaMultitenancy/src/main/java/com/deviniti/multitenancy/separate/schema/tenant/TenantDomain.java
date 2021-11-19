package com.deviniti.multitenancy.separate.schema.tenant;

import java.util.List;

import com.deviniti.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

public interface TenantDomain {

	boolean setTenantInContext(String tenantId);
	void createNewTenant(TenantDTO dto);
	List<TenantDTO> getAllTenants();
}
