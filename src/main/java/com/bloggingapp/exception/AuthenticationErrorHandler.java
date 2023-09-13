package com.bloggingapp.exception;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.Objects;

import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.bloggingapp.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Ganesh Popde
 *
 */

@Configuration
public class AuthenticationErrorHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// log.error("Unauthorized user. Error {}", authException);
		handleFilterError(request, response, authException);
	}

	/**
	 * @param request
	 * @param response
	 * @param authException
	 * @throws Exception
	 */
	public static void handleFilterError(HttpServletRequest request, HttpServletResponse response,
			Exception authException) throws UsernameNotFoundException, IOException {
		// log.error("handleFilterError. Error {}", authException.getMessage());
		authException.printStackTrace();
		if (Objects.nonNull(request.getAttribute("err"))) {
			ObjectMapper op = new ObjectMapper();
			ApiResponse err = (ApiResponse) request.getAttribute("err");
			// log.error("Error details found. ErrorDetails {}", err);
			response.getOutputStream().println(op.convertValue(err, JSONObject.class).toJSONString());
			response.setContentType(APPLICATION_JSON_VALUE);
			response.setStatus(SC_UNAUTHORIZED);
		} else {
			response.sendError(SC_UNAUTHORIZED, "Unauthorized. Error: " + authException.getMessage());
		}
	}

}
