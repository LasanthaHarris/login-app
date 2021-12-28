package edu.home.and.company.apps.login.gateway.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	private Environment environment;
	
	public AuthorizationFilter(AuthenticationManager authenticationManager, Environment environment) {
		super(authenticationManager);
		this.environment = environment;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String authoriztionHeader = request.getHeader(environment.getProperty("authorization.token.header.name"));
		
		if(authoriztionHeader == null || !authoriztionHeader.startsWith(environment.getProperty("authorization.token.header.name.prefix")))
		{
			chain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}
	

	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
		
		String authoriztionHeader = request.getHeader(environment.getProperty("authorization.token.header.name"));
		
		if(authoriztionHeader == null)
		{
			return null;
		}
		
		String token = authoriztionHeader.replace(environment.getProperty("authorization.token.header.name.prefix"), "");
		String userId = Jwts.parser()
								.setSigningKey(environment.getProperty("token.secret"))
								.parseClaimsJws(token)
								.getBody()
								.getSubject();
		
		if(userId == null)
		{
			return null;
		}
		
		return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
	}
}
