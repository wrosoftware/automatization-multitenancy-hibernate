package com.deviniti.multitenancy.separate.schema.tenant;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deviniti.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tenant")
@AllArgsConstructor
public class TenantController {

	private TenantDomain tenantDomain;
	
	@PutMapping
	public void createTenant(@RequestBody TenantDTO dto) {
		tenantDomain.createNewTenant(dto);
	}
}
