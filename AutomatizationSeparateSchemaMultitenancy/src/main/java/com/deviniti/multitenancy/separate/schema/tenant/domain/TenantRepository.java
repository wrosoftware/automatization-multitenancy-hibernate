package com.deviniti.multitenancy.separate.schema.tenant.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deviniti.multitenancy.separate.schema.database.model.tenant.Tenant;

@Repository
interface TenantRepository  extends JpaRepository<Tenant, String>{

	
	Tenant findByTenantId(String tenantId);
	
}
