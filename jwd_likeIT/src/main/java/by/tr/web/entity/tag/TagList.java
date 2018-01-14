package by.tr.web.entity.tag;

import java.util.HashMap;
import java.util.Map;

public class TagList {

	private static Map<String, Tag> tagList = new HashMap<String, Tag>();
	private static TagList instance = new TagList();

	private TagList() {

	}

	public static TagList getInstance() {
		return instance;
	}

	public static Map<String, Tag> getTagList() {
		return tagList;
	}

	public static void updateTagList(Map<String, Tag> newList) {
		tagList = newList;
	}

	public static void addTag(String tag) {
		tagList.put(tag, new Tag(tag));
	}

	public static boolean removeTag(String tag) {
		if (tagList.containsKey(tag)) {
			tagList.remove(tag);
			return true;
		}
		return false;
	}

	public static Tag getTag(String tag) {
		if (tagList.containsKey(tag)) {
			return tagList.get(tag);
		}
		return null;
	}

}
