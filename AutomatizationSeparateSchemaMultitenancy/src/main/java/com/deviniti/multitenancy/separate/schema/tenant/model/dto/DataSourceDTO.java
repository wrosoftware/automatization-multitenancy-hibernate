package com.deviniti.multitenancy.separate.schema.tenant.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataSourceDTO {

	private String schemaName;
	private String dataBaseUrl;
	private String user;
	private String password;
	
}
