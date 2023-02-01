package io.simpolor.custom.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증 실패를 다루기 위한 핸들러
 */
@Slf4j
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		log.info("CustomAuthenticationFailureHandler.onAuthenticationFailure");
		 
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String failUrl = "/user/login?error=true";
		
		log.info("email : {}", email);
		log.info("password : {}", password);
		log.info("failUrl : {}", failUrl);
		log.info("exception : {}", exception.getMessage());
		
		// 에러에 따른 처리
		/*
		if(exception instanceof AuthenticationServiceException){
			response.sendRedirect(request.getContextPath() + "/admin.glo?error=1");
		}
		if(exception instanceof BadCredentialsException){
			response.sendRedirect(request.getContextPath() + "/admin.glo?error=2");
		}
		if(exception instanceof LockedException){
			response.sendRedirect(request.getContextPath() + "/admin.glo?error=3");
		}
		*/
	
		// exception에 대한 정보 전달
		request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
		
		// 로그인 실패시 이동할 페이지 지정
		redirectStrategy.sendRedirect(request, response, failUrl);
	}

}
