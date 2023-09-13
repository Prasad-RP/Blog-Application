package com.bloggingapp.exception;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
//@Slf4j
public class AccessDenideHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException  {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// log.info("AccessDenideHandler...");
		if (authentication != null)
			// log.info("User " + authentication.getName() + " attempted to access the URL:
			// " + request.getRequestURI());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Sorry' You don't have enough permissions to access this end-point");
	}

}
