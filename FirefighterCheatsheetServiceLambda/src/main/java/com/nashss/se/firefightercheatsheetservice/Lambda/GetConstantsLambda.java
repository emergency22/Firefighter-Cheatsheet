package com.nashss.se.firefightercheatsheetservice.Lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


import com.nashss.se.firefightercheatsheetservice.Activity.Requests.GetConstantsRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.GetConstantsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetConstantsLambda
        extends LambdaActivityRunner<GetConstantsRequest, GetConstantsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetConstantsRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetConstantsRequest> input, Context context) {

        log.info("GetConstantsLambda: handleRequest method accessed.");
        return super.runActivity(
            () -> {
                return input.fromUserClaims(claims ->
                        GetConstantsRequest.builder()
                                .withUserName(claims.get("email"))
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideGetConstantsActivity().handleRequest(request)
        );

    }


}
