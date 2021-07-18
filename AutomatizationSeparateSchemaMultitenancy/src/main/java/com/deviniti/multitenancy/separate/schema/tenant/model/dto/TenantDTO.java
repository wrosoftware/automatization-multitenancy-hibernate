package com.deviniti.multitenancy.separate.schema.tenant.model.dto;

import lombok.Data;

@Data
public class TenantDTO {

	private String tenantId;
	private DataSourceDTO dataSource;
	
}
