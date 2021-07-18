package com.deviniti.multitenancy.separate.schema.tenant.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.deviniti.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

@Component
public class TenantRestClient extends RestTemplate {

	private static final String TENANT_URL = "/tenant/";
	
	@Value("${tenant.api.base.url}")
	private String tenantBaseUrl;
	
	
	public TenantDTO getTenant(String tenantId) {
		return getForObject(tenantBaseUrl+TENANT_URL+tenantId, TenantDTO.class);
	}
	
}
