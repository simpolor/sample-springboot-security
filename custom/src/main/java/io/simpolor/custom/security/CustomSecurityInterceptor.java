package io.simpolor.custom.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomSecurityInterceptor extends FilterSecurityInterceptor{
	
	public CustomSecurityInterceptor(CustomAccessDecisionManager accessDecisionManager,
                                     CustomFilterInvocationSecurityMetadataSource securityMetadataSource) {

		log.info("CustomSecurityInterceptor.init");
		
		super.setAccessDecisionManager(accessDecisionManager);
		super.setSecurityMetadataSource(securityMetadataSource);
	}
	
}
