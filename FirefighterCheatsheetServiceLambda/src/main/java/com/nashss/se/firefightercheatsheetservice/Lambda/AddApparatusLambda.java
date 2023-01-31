package com.nashss.se.firefightercheatsheetservice.Lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.AddApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.AddApparatusResult;

public class AddApparatusLambda extends LambdaActivityRunner<AddApparatusRequest, AddApparatusResult>
            implements RequestHandler<AuthenticatedLambdaRequest<AddApparatusRequest>, LambdaResponse> {
        @Override
        public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddApparatusRequest> input, Context context) {
            return super.runActivity(
                    () -> {
                        AddApparatusRequest unauthenticatedRequest = input.fromBody(AddApparatusRequest.class);
                        return input.fromUserClaims(claims ->
                                AddApparatusRequest.builder()
                                        .withUserName(unauthenticatedRequest.getUserName())
                                        .withApparatusTypeAndNumber(unauthenticatedRequest.getApparatusTypeAndNumber())
                                        .withFireDept(unauthenticatedRequest.getFireDept())
                                        .build());
                    },
                    (request, serviceComponent) ->
                            serviceComponent.provideAddApparatusActivity().handleRequest(request)
            );
        }
    }
