package by.tr.web.controller.listener;

import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.tr.web.controller.command.util.attribute.TextAttribute;
import by.tr.web.controller.command.util.json.JsonConverter;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.entity.language.LanguageSet;
import by.tr.web.entity.language.LanguageSetSingleton;
import by.tr.web.entity.tag.TagSet;
import by.tr.web.entity.tag.TagSetSingleton;
import by.tr.web.service.InitializeService;
import by.tr.web.service.exception.common.FatalServiceException;
import by.tr.web.service.factory.ServiceFactory;

public class ControllerCreationListener implements ServletContextListener {

	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private static InitializeService initializeService = serviceFactory.getInitializeService();

	@Override
	public void contextInitialized(ServletContextEvent event) {

		LanguageSet languageSet = LanguageSetSingleton.getInstance().getLanguageSet();
		TagSet tagSet = TagSetSingleton.getInstance().getTagSet();

		try {
			initializeService.initializeApplication();

			Set<String> languages = languageSet.getLanguageSet();
			String jsonLanguages = JsonConverter.getJson(languages);

			Set<String> tags = tagSet.getTagSet();
			String jsonTags = JsonConverter.getJson(tags);

			event.getServletContext().setAttribute(TextAttribute.LANGUAGE_LIST, jsonLanguages);
			event.getServletContext().setAttribute(TextAttribute.TAG_LIST, jsonTags);

		} catch (FatalServiceException | DaoException ex) {
			System.err.println("Stop application");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		initializeService.stopApplication();
	}

}
