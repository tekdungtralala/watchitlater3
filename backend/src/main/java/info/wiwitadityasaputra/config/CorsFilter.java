package info.wiwitadityasaputra.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.SignatureException;

public class CorsFilter extends GenericFilterBean {

	private static String AUTH_QUERY_PARAM = "query-param-auth";

	private Logger logger = LogManager.getLogger(CorsFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		// logger.info("full URL = " + getFullURL(req));
		Map<String, String> queryMap = getQueryMap(req.getQueryString());
		if (queryMap != null)
			loadSecurityContext(queryMap.get(AUTH_QUERY_PARAM));

		chain.doFilter(request, response);
	}

	private void loadSecurityContext(String authValue) {
		if (StringUtils.isEmpty(authValue))
			return;
		logger.info("loadSecurityContext()");
		logger.info("authValue = " + authValue);

		try {
			AuthModel am = AppJWTManager.fromQueryParamAuth(authValue);
			logger.info("userId = " + am.getUserId());
			logger.info("emailAddress = " + am.getEmailAddress());
			logger.info("createdDate = " + am.getCreatedDate());
			am.checkAuthModel();

			List<GrantedAuthority> grantedAuth = new ArrayList<GrantedAuthority>();
			grantedAuth.add(new SimpleGrantedAuthority("ROLE_USER"));
			Authentication auth = new UsernamePasswordAuthenticationToken(am, "password", grantedAuth);
			logger.info("create auth");

			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(auth);
			SecurityContextHolder.setContext(context);
			logger.info("save auth");
		} catch (SignatureException se) {
			logger.info("Invalid JWT: " + se.getMessage());
		} catch (AuthExpiredException ae) {
			logger.info("AuthModel is expired: " + ae.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getFullURL(HttpServletRequest req) {
		StringBuffer requestURL = req.getRequestURL();
		String queryString = req.getQueryString();
		String fullUrl = null;
		if (queryString == null) {
			fullUrl = requestURL.toString();
		} else {
			fullUrl = requestURL.append('?').append(queryString).toString();
		}
		return fullUrl;
	}

	private Map<String, String> getQueryMap(String query) {
		if (StringUtils.isEmpty(query))
			return null;
		String[] params = query.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for (String param : params) {
			if (!StringUtils.isEmpty(param)) {
				String name = param.split("=")[0];
				String value = param.split("=")[1];
				map.put(name, value);
			}
		}
		return map;
	}
}
