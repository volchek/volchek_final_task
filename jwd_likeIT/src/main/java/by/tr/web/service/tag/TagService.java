package by.tr.web.service.tag;

import java.util.Set;

import by.tr.web.service.exception.common.ServiceException;

public interface TagService {

	boolean addTag(String tag) throws ServiceException;
	
	Set<String> updateTagList() throws ServiceException;

}
