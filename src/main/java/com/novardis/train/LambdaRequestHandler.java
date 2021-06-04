package com.novardis.train;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaRequestHandler implements RequestHandler<String, String> {

    @Override
    public String handleRequest(String s, Context context) {
        context.getLogger().log("lambda request");
        return "Hi everyone";
    }
}
