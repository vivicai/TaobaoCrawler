package com.whjt.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class JSONParserUtils {

    /**
     * 日志记录
     */
    private static Logger log = Logger.getLogger(JSONParserUtils.class);

    /**
     * 根据json格式字符串得到json对象
     * 
     * @param jsonStr
     * @return
     */
    public static JSONObject getJsonObjectByStr(String jsonStr) {

        JSONObject jsonObj = new JSONObject();

        log.info("JSONParserUtils-getJsonObjectByStr-jsonStr: " + jsonStr);

        if (jsonStr.startsWith("{") && jsonStr.endsWith("}")) {

            jsonObj = JSONObject.fromObject(jsonStr);
        }

        return jsonObj;
    }

    /**
     * 根据key值获取value
     * 
     * @param jsonStr
     * @param key
     * @return
     */
    public static String getValueByKey(JSONObject jsonObj, String key) {

        String value = "";

        log.info("JSONParserUtils-getJsonObjectByStr-jsonObj: " + jsonObj
                + ", key: " + key);

        if (jsonObj.containsKey(key)) {

            value = jsonObj.getString(key);
        }

        return value;
    }

    /**
     * 根据key值获取jsonobject
     * 
     * @param jsonStr
     * @param key
     * @return
     */
    public static JSONObject getJSONObjectByKey(JSONObject jsonObj, String key) {

        JSONObject jsonObject = new JSONObject();

        log.info("JSONParserUtils-getJSONObjectByKey-jsonObj: " + jsonObj
                + ", key: " + key);

        if (jsonObj.containsKey(key)) {

            jsonObject = jsonObj.getJSONObject(key);
        }

        return jsonObject;
    }

    /**
     * 根据key值获取jsonarray
     * 
     * @param jsonStr
     * @param key
     * @return
     */
    public static JSONArray getJSONArrayByKey(JSONObject jsonObj, String key) {

        JSONArray jsonArr = new JSONArray();

        log.info("JSONParserUtils-getJSONArrayByKey-jsonObj: " + jsonObj
                + ", key: " + key);

        if (jsonObj.containsKey(key)) {

            jsonArr = jsonObj.getJSONArray(key);
        }

        return jsonArr;
    }
}
