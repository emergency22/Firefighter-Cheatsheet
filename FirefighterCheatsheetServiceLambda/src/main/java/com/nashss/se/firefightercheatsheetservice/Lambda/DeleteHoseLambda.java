package com.nashss.se.firefightercheatsheetservice.Lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.DeleteHoseRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.DeleteHoseResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteHoseLambda extends LambdaActivityRunner<DeleteHoseRequest, DeleteHoseResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteHoseRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteHoseRequest> input, Context context) {

        log.info("DeleteHoseLambda: handleRequest method accessed.");

        DeleteHoseRequest email = input.fromUserClaims(claims ->
                DeleteHoseRequest.builder()
                        .withUserName(claims.get("email"))
                        .build());

        String actualEmail = email.getUserName();
        log.info("DeleteHoseLambda: email: " + actualEmail);

        return super.runActivity(
                () -> input.fromQuery(query ->
                        DeleteHoseRequest.builder()
                                .withUserName(actualEmail)
                                .withFireDept(query.get("fireDept"))
                                .withApparatusTypeAndNumber(query.get("apparatusTypeAndNumber"))
                                .withHoseIndexNumber(Integer.valueOf(query.get("hoseIndexNumber")))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteHoseActivity().handleRequest(request)
        );
    }
}
