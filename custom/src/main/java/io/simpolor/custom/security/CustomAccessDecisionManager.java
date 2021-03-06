package io.simpolor.custom.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

@Slf4j
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {

	/***
	 * 인가 : 사용자의 권한과 매핑정보를 비교하는 함수
	 *
	 * 인가 단계에서는 여러개의 voter(권한 체계)를 가질 수 있음
	 * - affirmativeBased : 여러 Voter 중에 한명이라도 허용하면 허용 ( 기본 전략 )
	 * - ConsensusBased : 다수결
	 * - UnanimousBased : 만장 일치
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {

		log.info("CustomAccessDecisionManager.decide");
		
		if (configAttributes == null || configAttributes.size() <= 0) {
            return;
        }
		
		// 로그인 된 사용자의 정보를 가져옴
		Iterator<ConfigAttribute> cas = configAttributes.iterator();

		// 반복문을 실행하는 이유는 한 사용자가 여러 권한을 소유할 수 있기 때문
		while(cas.hasNext()) {
			ConfigAttribute ca = cas.next();
			String role = ((SecurityConfig) ca).getAttribute(); // 사용자 권한을 가져옴

			System.out.println("** authentication.getAuthorities() : "+authentication.getAuthorities());
			System.out.println("** role: "+role);

			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (role.trim().equals(ga.getAuthority().trim())) { // 사용자 권한과 매핑정보를 비교
					
					log.info("> role : {}", role.trim());
					log.info("> ga.getAuthority() {}: ", ga.getAuthority().trim());
					
					return;
				}
			}
		}

		System.out.println("Access Denied!!");

		throw new AccessDeniedException("Access Denied!!!");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {

		log.info("CustomAccessDecisionManager.supports");

		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {

		log.info("CustomAccessDecisionManager.supports");
		
		return true;
	}



}
