package by.tr.web.controller.command.util.json;

import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

public class JsonConverter {

	private JsonConverter() {
	}

	public static String getJson(List<String> list) {
		String json = new Gson().toJson(list);
		return json;
	}

	public static String getJson(Set<String> list) {
		String json = new Gson().toJson(list);
		return json;
	}

}
