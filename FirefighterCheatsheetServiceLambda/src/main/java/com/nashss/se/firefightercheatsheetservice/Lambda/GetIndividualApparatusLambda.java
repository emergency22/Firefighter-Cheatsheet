package com.nashss.se.firefightercheatsheetservice.Lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.GetIndividualApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.GetIndividualApparatusResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetIndividualApparatusLambda
        extends LambdaActivityRunner<GetIndividualApparatusRequest, GetIndividualApparatusResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetIndividualApparatusRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetIndividualApparatusRequest> input, Context context) {

        log.info("GetIndividualApparatusLambda: handleRequest method accessed.");

//        GetIndividualApparatusRequest email = input.fromUserClaims(claims ->
//                GetIndividualApparatusRequest.builder()
//                         .withUserName(claims.get("email"))
//                         .build());
//
//         String actualEmail = email.getUserName();

         return super.runActivity(
                 () -> {
                     GetIndividualApparatusRequest unauthenticatedRequest = input.fromBody(GetIndividualApparatusRequest.class);
                     return input.fromUserClaims(claims ->
                             GetIndividualApparatusRequest.builder()
                                     .withUserName(claims.get("email"))
                                     .withFireDept(unauthenticatedRequest.getFireDept())
                                     .withApparatusTypeAndNumber(unauthenticatedRequest.getApparatusTypeAndNumber())
                                     .build());
                 },
                 (request, serviceComponent) ->
                         serviceComponent.provideGetIndividualApparatusActivity().handleRequest(request)
         );
    }


}
