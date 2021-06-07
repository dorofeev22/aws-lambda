package com.novardis.train;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;

public class NewDocumentHandler extends BaseRequestHandler<TestDocument, TestDocument> implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    private final TestDocumentDao mongoDao = new TestDocumentDao();

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent apiGatewayProxyRequestEvent, Context context) {
        mongoDao.add(readRequest(apiGatewayProxyRequestEvent));
        return createResponse();
    }

}