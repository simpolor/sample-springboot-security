package io.simpolor.custom.security;

import io.simpolor.custom.repository.entity.Access;
import io.simpolor.custom.service.AccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;

/**
 * 권한 관리에 따른 처리를 위한 필터
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private final AccessService accessService;

    public Map<RequestMatcher, List<ConfigAttribute>> getResources(){

		log.info("CustomFilterInvocationSecurityMetadataSource.getResources");

		Map<RequestMatcher, List<ConfigAttribute>> resources = new HashMap<>();

		// URL 및 권한 정보를 DB에서 호출
		List<Access> accesses = accessService.getAll();

		if(!CollectionUtils.isEmpty(accesses)) {
			List<ConfigAttribute> configList;

			// URL 및 권한에 따른 매핑 정보를 저장
			// (하나의 URL에 따른 여러 권한이 있을 경우에 대한 처리 과정)
			for(Access access : accesses) {
				AntPathRequestMatcher matcher = new AntPathRequestMatcher(access.getTargetUrl());
				configList = new LinkedList<>();
				for(String role : access.getRoles()){
					configList.add(new SecurityConfig(role));
				}
				resources.put(matcher, configList);
			}
		}

		log.info("-- resources : {}", resources);

		return resources;
	}
	
	/***
	 * 매핑정보와 요청한 URL이 일치하는지 확인하는 함수
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		
		log.info("CustomFilterInvocationSecurityMetadataSource.getAttributes");
		
		// resourceMap :  {Ant [pattern='/admin/home']=[ADMIN], Ant [pattern .. }
		Collection<ConfigAttribute> result = null;
		Map<RequestMatcher, List<ConfigAttribute>> resources = getResources();

		HttpServletRequest request = ( ( FilterInvocation ) object ).getRequest();
		log.info("-- request.getRequestURI : {}", request.getRequestURI());

		for( Entry<RequestMatcher, List<ConfigAttribute>> entry : resources.entrySet() ) {
			// entry.getKey() : Ant [pattern='/admin/home']
			if(entry.getKey().matches(request)){
				result = entry.getValue();
				System.out.println("entry.getValue() : "+entry.getValue());
				break;
			}
		}

		return result;
	}

	/***
	 * 최초 매핑정보를 메모리에 읽어 올리는 함수
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {

		log.info("CustomFilterInvocationSecurityMetadataSource.getAllConfigAttributes");

		Map<RequestMatcher, List<ConfigAttribute>> resources = getResources();
		Set<ConfigAttribute> allAttributes = new HashSet<>();
		for (Entry<RequestMatcher, List<ConfigAttribute>> entry : resources.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}
		return allAttributes;
		
	}

	@Override
	public boolean supports(Class<?> clazz) {

		log.info("CustomFilterInvocationSecurityMetadataSource.supports");

		//return FilterInvocation.class.isAssignableFrom(clazz);
		return true;
	}

	

}

