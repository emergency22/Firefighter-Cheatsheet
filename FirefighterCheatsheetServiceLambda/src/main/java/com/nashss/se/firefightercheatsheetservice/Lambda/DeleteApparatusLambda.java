package com.nashss.se.firefightercheatsheetservice.Lambda;

import com.nashss.se.firefightercheatsheetservice.Activity.Requests.DeleteApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.DeleteApparatusResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;

public class DeleteApparatusLambda extends LambdaActivityRunner<DeleteApparatusRequest, DeleteApparatusResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteApparatusRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteApparatusRequest> input, Context context) {

        log.info("DeleteApparatusLambda: handleRequest method accessed.");

        DeleteApparatusRequest email = input.fromUserClaims(claims ->
                        DeleteApparatusRequest.builder()
                                .withUserName(claims.get("email"))
                                .build());

        String actualEmail = email.getUserName();

        return super.runActivity(
            () -> input.fromPath(path -> {
                try {
                    return DeleteApparatusRequest.builder()
                            .withUserName(actualEmail)
                            .withApparatusTypeAndNumber(path.get("apparatusTypeAndNumber"))
                            .build();
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }),
            (request, serviceComponent) ->
                         serviceComponent.provideDeleteApparatusActivity().handleRequest(request)
         );

    }
}



