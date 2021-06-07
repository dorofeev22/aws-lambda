package com.novardis.train;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

abstract class BaseRequestHandler<Rq, Rs> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected APIGatewayV2HTTPResponse createResponse(final Rs body) throws JsonProcessingException {
        var response = new APIGatewayV2HTTPResponse();
        response.setBody(objectMapper.writeValueAsString(body));
        response.setStatusCode(200);
        return response;
    }

    protected APIGatewayV2HTTPResponse createResponse() {
        var response = new APIGatewayV2HTTPResponse();
        response.setStatusCode(200);
        return response;
    }

    protected Rq readRequest(APIGatewayV2HTTPEvent apiGatewayProxyRequestEvent) {
        try {
            return objectMapper.readValue(apiGatewayProxyRequestEvent.getBody(), getRequestClass());
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    private Class<Rq> getRequestClass() throws ClassNotFoundException {
        Type type = getClass().getGenericSuperclass();
        String className = ((ParameterizedType) type).getActualTypeArguments()[0].getTypeName();
        return (Class<Rq>) Class.forName(className);
    }

}