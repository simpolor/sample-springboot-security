package io.simpolor.custom.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 접근 권한을 체크하기 위한 핸들러
 */
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		log.info("CustomAccessDeniedHandler.handle");
		
		// 현재 페이지가 정상 응답되는 페이지임을 지정하는 의미
    	response.setStatus(HttpServletResponse.SC_OK);
    	
    	// 비정상 로그인일 경우 로그인 페이지로 이동 (CsrfToken으로 유무 확인)
		if(accessDeniedException instanceof InvalidCsrfTokenException) {
            DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            redirectStrategy.sendRedirect(request, response, "/member/login");
            return;
        }
		
		// 권한에 따른 접근권한 처리
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("> auth.getName() : {}", authentication.getName());
    	for (GrantedAuthority auth : authentication.getAuthorities()) {
    		log.info("> auth.getAuthority() : {}", auth.getAuthority());
    		if (auth.getAuthority().equalsIgnoreCase("USER")) {
	        	// 권한에 따라 로그인 이후 이동할 페이지를 지정할 수 있음
	        }
        }
    	
    	String accessDeniedUrl = "/error/accessDenied";
    	redirectStrategy.sendRedirect(request, response, accessDeniedUrl);
	}

}
