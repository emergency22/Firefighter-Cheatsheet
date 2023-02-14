package com.nashss.se.firefightercheatsheetservice.Lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.firefightercheatsheetservice.Activity.Requests.AddApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.AddApparatusResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddApparatusLambda extends LambdaActivityRunner<AddApparatusRequest, AddApparatusResult>
            implements RequestHandler<AuthenticatedLambdaRequest<AddApparatusRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
        public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddApparatusRequest> input, Context context) {

        log.info("AddApparatusLambda: handleRequest method accessed.");

       return super.runActivity(
               () -> {
                   AddApparatusRequest unauthenticatedRequest = input.fromBody(AddApparatusRequest.class);
                   return input.fromUserClaims(claims ->
                           AddApparatusRequest.builder()
                                   .withUserName(claims.get("email"))
                                   .withFireDept(unauthenticatedRequest.getFireDept())
                                   .withApparatusTypeAndNumber(unauthenticatedRequest.getApparatusTypeAndNumber())
                                   .build());
               },
               (request, serviceComponent) ->
                       serviceComponent.provideAddApparatusActivity().handleRequest(request)
       );

        }
    }
