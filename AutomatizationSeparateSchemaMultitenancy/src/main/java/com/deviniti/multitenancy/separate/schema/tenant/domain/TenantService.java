package com.deviniti.multitenancy.separate.schema.tenant.domain;


import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.deviniti.multitenancy.separate.schema._configuration.flayway.FlywayBuilder;
import com.deviniti.multitenancy.separate.schema.database.model.tenant.Tenant;
import com.deviniti.multitenancy.separate.schema.security.domain.CredentialsException;
import com.deviniti.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
class TenantService implements TenantMapper {
	
	private final TenantRepository repository;
	private final FlywayBuilder flywayBuilder;
	
	public List<TenantDTO> findAllTenants() {
		return repository.findAll()
				.stream()
				.map(this::mapToDto)
				.collect(toList());
	}

	public TenantDTO findByTenantId(String tenantId) {
		return Optional.ofNullable(tenantId)
				.map(repository::findByTenantId)
				.map(this::mapToDto)
				.orElseThrow(() -> new CredentialsException(String.format("Uknown tenant")));
	}
	
	public Tenant createNewTenant(TenantDTO dto) {
		return Optional.ofNullable(dto)
			.map(this::buildDatabaseSchema)
			.map(this::mapToEntity)
			.map(repository::save)
			.orElseThrow(() -> new RuntimeException("Cannot add new tenant"));
	}

	private TenantDTO buildDatabaseSchema(TenantDTO dto) {
		flywayBuilder.createFlyway(dto)
			.migrate();
		return dto;
	}

}
