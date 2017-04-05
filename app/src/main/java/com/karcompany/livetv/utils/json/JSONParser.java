package com.karcompany.livetv.utils.json;

import com.google.gson.Gson;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * JSON parser (Utility class which parses JSON string to any object using GSON annotations)
 */

public class JSONParser {

	private static Gson mGson = new Gson();

	public static String toJSON(Object o) {
		return mGson.toJson(o);
	}

	public static <T> T parse(String jsonInString, Class<T> clazz) {
		return mGson.fromJson(jsonInString, clazz);
	}

}
