package by.tr.web.controller.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import by.tr.web.controller.command.util.attribute.TextAttribute;
import by.tr.web.service.LanguageService;
import by.tr.web.service.exception.common.ServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class FrequentLanguagesFilter implements Filter {

	private ServiceFactory serviceFactory;
	private LanguageService languageService;
	private List<String> reserveLanguages;

	private final String LANG_CPP = "C++";
	private final String LANG_JAVA = "Java";
	private final String LANG_C_SHARP = "C#";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			List<String> languages = languageService.findFrequentLanguages();
			request.setAttribute(TextAttribute.LANGUAGE_LIST, languages);
		} catch (ServiceException e) {
			request.setAttribute(TextAttribute.LANGUAGE_LIST, reserveLanguages);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

		serviceFactory = ServiceFactory.getInstance();
		languageService = serviceFactory.getLanguageService();

		reserveLanguages = new ArrayList<String>();
		reserveLanguages.add(LANG_CPP);
		reserveLanguages.add(LANG_JAVA);
		reserveLanguages.add(LANG_C_SHARP);
	}

	public void destroy() {

	}

}
