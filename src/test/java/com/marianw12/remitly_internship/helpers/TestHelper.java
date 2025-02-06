package com.marianw12.remitly_internship.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marianw12.remitly_internship.request.SwiftCodeBranchResponse;

public class TestHelper {
    public static boolean extractIsHeadquarter(SwiftCodeBranchResponse response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(response);
        JsonNode jsonNode = objectMapper.readTree(json);
        return jsonNode.get("isHeadquarter").asBoolean();
    }

}
