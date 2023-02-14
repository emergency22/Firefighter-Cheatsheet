package com.nashss.se.firefightercheatsheetservice.Lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.firefightercheatsheetservice.Activity.Results.DeleteHoseResult;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.DeleteHoseRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteHoseLambda extends LambdaActivityRunner<DeleteHoseRequest, DeleteHoseResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteHoseRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteHoseRequest> input, Context context) {

        log.info("DeleteHoseLambda: handleRequest method accessed.");

        return super.runActivity(
            () -> {
                DeleteHoseRequest queryRequest = input.fromQuery(query ->
                    DeleteHoseRequest.builder()
                        .withFireDept(query.get("fireDept"))
                        .withApparatusTypeAndNumber(query.get("apparatusTypeAndNumber"))
                        .withHoseIndexNumber(Integer.valueOf(query.get("hoseIndexNumber")))
                        .build());
                return input.fromUserClaims(claims ->
                DeleteHoseRequest.builder()
                        .withUserName(claims.get("email"))
                        .withFireDept(queryRequest.getFireDept())
                        .withApparatusTypeAndNumber(queryRequest.getApparatusTypeAndNumber())
                        .withHoseIndexNumber(queryRequest.getHoseIndexNumber())
                        .build());
            },
            (request, serviceComponent) ->
            serviceComponent.provideDeleteHoseActivity().handleRequest(request)
        );

    }
}
