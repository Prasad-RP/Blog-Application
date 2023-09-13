package com.bloggingapp.utils;

import static java.util.Arrays.stream;
import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AuthenticationUtil {

	private AuthenticationUtil() {
	}

	public static final Function<String, String[]> apis = AuthenticationUtil::getApis;

	/**
	 * This Predicate return true if passed API URL is public.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<String> ALLOW_URL = s -> {
		if (isNull(s))
			return false;
		if (AuthenticationUtil.PUBLIC_APIS.get().stream().anyMatch(s::startsWith))
			return true;
		return stream(getApis("role")).anyMatch(s::equals);
	};

	// @formatter:off
	public static String[] getApis(String role) {
		List<String> api = new ArrayList<>();
		api.add("/api/"+"**");
		// Public APIS
		api.addAll(PERMIT_APIS.get());
		
		return api.toArray(new String[api.size()]);
	}

	/**
	 * Return API'S set public in module
	 * 
	 * @since 30-Dec-2021
	 * @version 1.3
	 *
	 */
	private static final Supplier<List<String>> PUBLIC_APIS = () -> {
		List<String> api = new ArrayList<>();
		api.add("/api/login/" + "/**");
		return api;
	};

	/**
	 * Returns allowed API Urls
	 * 
	 * @since 30-Dec-2021
	 * @version 1.3
	 *
	 */
	private static final Supplier<List<String>> PERMIT_APIS = () -> {
		List<String> api = new ArrayList<>();
		api.add("/api/login" + "/**");
		PUBLIC_APIS.get().stream().map(e -> e.concat("/**")).forEach(api::add);
		return api;
	};


}