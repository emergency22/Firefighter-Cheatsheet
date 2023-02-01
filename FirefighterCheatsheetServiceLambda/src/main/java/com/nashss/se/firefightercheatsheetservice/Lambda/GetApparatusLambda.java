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

        log.info("handleRequest");

        return super.runActivity(
            () -> {
                GetApparatusRequest unauthenticatedRequest = input.fromBody(GetApparatusRequest.class);
                return input.fromUserClaims(claims ->
                        GetApparatusRequest.builder()
                                .withUserName(unauthenticatedRequest.getUserName())
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideGetApparatusActivity().handleRequest(request)
        );




        // log.info("handleRequest");
        // return super.runActivity(
        //         () -> input.fromPath(path ->
        //                 GetApparatusRequest.builder()
        //                         .withUserName(path.get("userName"))
        //                         .build()),
        //         (request, serviceComponent) ->
        //                 serviceComponent.provideGetApparatusActivity().handleRequest(request)
        // );
    }


}
