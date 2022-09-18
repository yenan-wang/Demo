package com.example.demo.common.utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Iterator;

public class JsonUtil {

    private static final String TAG = "JsonUtil";

    public static boolean isJsonObject(String jsonString) {
        try {
            Object object = new JSONTokener(jsonString).nextValue();
            return object instanceof JSONObject;
        } catch (JSONException e) {
            LogUtil.e(TAG, "isJsonObject, error:" + e.getMessage());
        }
        return false;
    }

    public static boolean isJsonArray(String jsonString) {
        try {
            Object object = new JSONTokener(jsonString).nextValue();
            return object instanceof JSONArray;
        } catch (JSONException e) {
            LogUtil.e(TAG, "isJsonArray, error:" + e.getMessage());
        }
        return false;
    }

    public static boolean isValidValue(String valueString) {
        try {
            if (valueString.equals("")) {
                return true;
            }
            Object object = new JSONTokener(valueString).nextValue();
            return !(object instanceof JSONArray) && !(object instanceof JSONObject);
        } catch (JSONException e) {
            LogUtil.e(TAG, "isJsonArray, error:" + e.getMessage());
        }
        return false;
    }

    public static String formatJson(String text, int indentSpaces) throws JSONException {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        if (indentSpaces < 0) {
            indentSpaces = 4;
        }
        char[] spaces = new char[indentSpaces];
        Arrays.fill(spaces, ' ');
        StringBuilder builder = new StringBuilder();
        expandJson(builder, text, 0, spaces);
        return builder.toString();
    }

    public static String compressJson(String jsonString, boolean isEncode) throws JSONException {
        if (isJsonObject(jsonString)) {
            JSONObject jsonObject = new JSONObject(jsonString);
            return compressJsonObject(jsonObject, isEncode);
        } else if (isJsonArray(jsonString)) {
            JSONArray jsonArray = new JSONArray(jsonString);
            return compressJsonArray(jsonArray, isEncode);
        }
        throw new JSONException("Unsupported json type, only for JSONArray or JSONObject.");
    }

    public static String compressJsonArray(JSONArray jsonArray, boolean isEncode) throws JSONException {
        return compressJsonArray(jsonArray, null, isEncode).toString();
    }

    public static String compressJsonObject(JSONObject jsonObject, boolean isEncode) throws JSONException {
        return compressJsonObject(jsonObject, null, isEncode).toString();
    }

    private static JSONStringer compressJson(String jsonString, JSONStringer jsonStringer, boolean isEncode) throws JSONException {
        if (isJsonObject(jsonString)) {
            JSONObject jsonObject = new JSONObject(jsonString);
            return compressJsonObject(jsonObject, jsonStringer, isEncode);
        } else if (isJsonArray(jsonString)) {
            JSONArray jsonArray = new JSONArray(jsonString);
            return compressJsonArray(jsonArray, jsonStringer, isEncode);
        }
        throw new JSONException("Unsupported json type, only for JSONArray or JSONObject.");
    }

    private static JSONStringer compressJsonArray(JSONArray jsonArray, JSONStringer jsonStringer, boolean isEncode) throws JSONException {
        if (jsonStringer == null) {
            jsonStringer = new JSONStringer();
        }
        jsonStringer.array();
        for (int i = 0; i < jsonArray.length(); i++) {
            String value = jsonArray.get(i).toString();
            if (isValidValue(value)) {
                if (isEncode) {
                    try {
                        value = URLEncoder.encode(value, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        LogUtil.e(TAG, "compressJsonArray, url encode error:" + e.getMessage());
                    }
                }
                jsonStringer.value(value);
            } else {
                compressJson(jsonArray.get(i).toString(), jsonStringer, isEncode);
            }
        }
        jsonStringer.endArray();
        return jsonStringer;
    }

    private static JSONStringer compressJsonObject(JSONObject jsonObject, JSONStringer jsonStringer, boolean isEncode) throws JSONException {
        Iterator<String> iterable = jsonObject.keys();
        if (jsonStringer == null) {
            jsonStringer = new JSONStringer();
        }
        jsonStringer.object();
        while (iterable.hasNext()) {
            String key = iterable.next();
            String value = jsonObject.getString(key);
            jsonStringer.key(key);
            if (isValidValue(value)) {
                if (isEncode) {
                    try {
                        value = URLEncoder.encode(value, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        LogUtil.e(TAG, "compressJsonArray, url encode error:" + e.getMessage());
                    }
                }
                jsonStringer.value(value);
            } else {
                compressJson(value, jsonStringer, isEncode);
            }
        }
        jsonStringer.endObject();
        return jsonStringer;
    }

    private static StringBuilder expandJson(StringBuilder builder, String object, int level, char[] spaces) throws JSONException {
        if (isJsonObject(object)) {
            builder.append("{\n");
            expandJsonObject(builder, object, level + 1, spaces);
            appendSpaces(builder, level, spaces).append("}");
        } else if (isJsonArray(object)) {
            builder.append("[\n");
            expandJsonArray(builder, object, level + 1, spaces);
            appendSpaces(builder, level, spaces).append("]");
        } else {
            throw new JSONException("Unsupported json type, only for JSONArray or JSONObject.");
        }
        return builder;
    }

    private static StringBuilder expandJsonObject(StringBuilder builder, String object, int level, char[] spaces) throws JSONException {
        JSONObject jsonObject = new JSONObject(object);
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            appendSpaces(builder, level, spaces).append("\"").append(key).append("\"").append(": ");
            Object value = jsonObject.get(key);
            if (isValidValue(value.toString())) {
                appendValue(builder, value).append(",\n");
            } else if (isJsonObject(value.toString())) {
                builder.append("{\n");
                expandJsonObject(builder, value.toString(), level + 1, spaces);
                appendSpaces(builder, level, spaces).append("},\n");
            } else if (isJsonArray(value.toString())) {
                builder.append("[\n");
                expandJsonArray(builder, value.toString(), level + 1, spaces);
                appendSpaces(builder, level, spaces).append("],\n");
            } else {
                throw new JSONException("Unsupported json type, only for JSONArray or JSONObject.");
            }
        }
        builder.deleteCharAt(builder.lastIndexOf(","));
        return builder;
    }

    private static StringBuilder expandJsonArray(StringBuilder builder, String value, int level, char[] spaces) throws JSONException {
        JSONArray jsonArray = new JSONArray(value);
        for (int i = 0; i < jsonArray.length(); i++) {
            Object object = jsonArray.get(i);
            if (isValidValue(object.toString())) {
                appendSpaces(builder, level, spaces);
                appendValue(builder, object).append(",\n");
            } else if (isJsonArray(object.toString())) {
                appendSpaces(builder, level, spaces).append("[\n");
                expandJsonArray(builder, object.toString(), level + 1, spaces);
                appendSpaces(builder, level, spaces).append("],\n");
            } else if (isJsonObject(object.toString())) {
                appendSpaces(builder, level, spaces).append("{\n");
                expandJsonObject(builder, object.toString(), level + 1, spaces);
                appendSpaces(builder, level, spaces).append("},\n");
            } else {
                throw new JSONException("Unsupported json type, only for JSONArray or JSONObject.");
            }
        }
        builder.deleteCharAt(builder.lastIndexOf(","));
        return builder;
    }

    private static StringBuilder appendValue(StringBuilder builder, Object object) {
        if (object == null) {
            builder.append("\"\"");
        } else if (object instanceof String) {
            builder.append("\"").append(object).append("\"");
        } else {
            builder.append(object);
        }
        return builder;
    }

    private static StringBuilder appendSpaces(StringBuilder builder, int level, char[] spaces) {
        for (int i = 0; i < level; i++) {
            builder.append(spaces);
        }
        return builder;
    }
}
