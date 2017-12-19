package by.tr.web.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharsetFilter implements Filter {

	private static String encoding;
	private static final String CHARSET_PARAM_NAME = "targetCharset";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) {

		encoding = config.getInitParameter(CHARSET_PARAM_NAME);

	}

}
