package com.deviniti.multitenancy.separate.schema.configuration.spring.multitenancy;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.deviniti.multitenancy.separate.schema.security.domain.SecurityDomain;
import com.deviniti.multitenancy.separate.schema.tenant.TenantContext;
import com.deviniti.multitenancy.separate.schema.tenant.TenantDomain;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TenantRequestInterceptor implements AsyncHandlerInterceptor{
	
	private final SecurityDomain securityDomain;
	private final TenantDomain tenantDomain;
	

	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		 return Optional.ofNullable(request)
				 .map(req -> securityDomain.getTenantIdFromJwt(req))
				 .map(tenant -> setTenantContext(tenant))
				 .orElse(false);
	    }

	    @Override
	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
	        TenantContext.clear();
	    }
	    
	    private boolean setTenantContext(String tenant) {
	    	return tenantDomain.setTenantInContext(tenant);
	    }
}
