package com.webjournal.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Yuliana
 * @version 1.0.0
 * @group 343(1)
 * @department PZKS
 * @project WebJournal
 * @class JsonUtil
 * @since 4/5/2023 - 11.33
 **/
public class JsonUtil {
    private final static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
