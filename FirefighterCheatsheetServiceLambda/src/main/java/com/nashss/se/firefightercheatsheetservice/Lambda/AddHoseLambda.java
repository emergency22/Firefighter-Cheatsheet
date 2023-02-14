package com.nashss.se.firefightercheatsheetservice.Lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.firefightercheatsheetservice.Activity.Results.AddHoseResult;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.AddHoseRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddHoseLambda extends LambdaActivityRunner<AddHoseRequest, AddHoseResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddHoseRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddHoseRequest> input, Context context) {

        log.info("AddHoseLambda: handleRequest method accessed.");

        return super.runActivity(
            () -> {
                AddHoseRequest unauthenticatedRequest = input.fromBody(AddHoseRequest.class);
                return input.fromUserClaims(claims ->
                            AddHoseRequest.builder()
                                    .withUserName(claims.get("email"))
                                    .withFireDept(unauthenticatedRequest.getFireDept())
                                    .withApparatusTypeAndNumber(unauthenticatedRequest.getApparatusTypeAndNumber())
                                    .withName(unauthenticatedRequest.getName())
                                    .withColor(unauthenticatedRequest.getColor())
                                    .withLength(Integer.valueOf(unauthenticatedRequest.getLength()))
                                    .withDiameter(Double.valueOf(unauthenticatedRequest.getDiameter()))
                                    .withGallons(Integer.valueOf(unauthenticatedRequest.getGallons()))
                                    .build());
            },
                (request, serviceComponent) ->
                        serviceComponent.provideAddHoseActivity().handleRequest(request)
        );

    }
}
