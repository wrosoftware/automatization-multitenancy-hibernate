package com.deviniti.multitenancy.separate.schema.tenant.domain;

import java.util.Optional;

import com.deviniti.multitenancy.separate.schema.database.model.tenant.Tenant;
import com.deviniti.multitenancy.separate.schema.tenant.model.dto.DataSourceDTO;
import com.deviniti.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

interface TenantMapper {

	default TenantDTO mapToDto(Tenant tenant) {
		return TenantDTO.builder()
				.tenantId(tenant.getTenantId())
				.dataSource(DataSourceDTO.builder()
						.schemaName(tenant.getSchemaName())
						.build())
				.build();
	}
	
	default Tenant mapToEntity(TenantDTO dto) {
		return Tenant.builder()
				.tenantId(dto.getTenantId())
				.schemaName(Optional.ofNullable(dto)
								.map(TenantDTO::getDataSource)
								.map(DataSourceDTO::getSchemaName)
								.orElse(null))
				.build();
	}

}
