package com.nashss.se.firefightercheatsheetservice.Lambda;

import com.nashss.se.firefightercheatsheetservice.Activity.Requests.CalculatePSIRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.CalculatePSIResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CalculatePSILambda extends LambdaActivityRunner<CalculatePSIRequest, CalculatePSIResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CalculatePSIRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CalculatePSIRequest> input, Context context) {

        log.info("CalculatePSILambda: handleRequest method accessed.");

        return super.runActivity(
            () -> {
                CalculatePSIRequest unauthenticatedRequest = input.fromBody(CalculatePSIRequest.class);
                return input.fromUserClaims(claims ->
                            CalculatePSIRequest.builder()
                                    .withUserName(claims.get("email"))
                                    .withFireDept(unauthenticatedRequest.getFireDept())
                                    .withApparatusTypeAndNumber(unauthenticatedRequest.getApparatusTypeAndNumber())
                                    .withHoseIndexNumber(Integer.valueOf(unauthenticatedRequest.getHoseIndexNumber()))
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideCalculatePSIActivity().handleRequest(request)
        );

    }
}
