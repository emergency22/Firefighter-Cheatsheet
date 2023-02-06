package com.nashss.se.firefightercheatsheetservice.Lambda;

import com.nashss.se.firefightercheatsheetservice.Activity.Requests.GetApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.GetApparatusResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetApparatusLambda 
        extends LambdaActivityRunner<GetApparatusRequest, GetApparatusResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetApparatusRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetApparatusRequest> input, Context context) {

        log.info("GetApparatusLambda: handleRequest method accessed.");
        return super.runActivity(
            () -> {
                return input.fromUserClaims(claims ->
                        GetApparatusRequest.builder()
                                .withUserName(claims.get("email"))
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideGetApparatusActivity().handleRequest(request)
        );

    }


}
