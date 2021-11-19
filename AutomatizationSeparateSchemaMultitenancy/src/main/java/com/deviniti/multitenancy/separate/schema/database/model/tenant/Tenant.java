package com.deviniti.multitenancy.separate.schema.database.model.tenant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema="public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {

	@Id
	@Column(name = "tenant_id")
	private String tenantId;
	@Column(name = "schema_name")
	private String schemaName;
	
}
