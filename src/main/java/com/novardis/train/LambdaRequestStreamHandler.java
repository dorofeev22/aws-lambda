package com.novardis.train;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

    public class LambdaRequestStreamHandler implements RequestStreamHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        RequestDto requestDto = objectMapper.readValue(inputStream, RequestDto.class);
        context.getLogger().log("my data: " + requestDto.getId());
        outputStream.write(objectMapper.writeValueAsBytes(requestDto));
    }

}
