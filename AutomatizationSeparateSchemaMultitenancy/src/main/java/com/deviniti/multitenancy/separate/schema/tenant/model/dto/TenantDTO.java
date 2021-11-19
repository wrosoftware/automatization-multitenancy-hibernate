package com.deviniti.multitenancy.separate.schema.tenant.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TenantDTO {

	private String tenantId;
	private DataSourceDTO dataSource;
	
}
