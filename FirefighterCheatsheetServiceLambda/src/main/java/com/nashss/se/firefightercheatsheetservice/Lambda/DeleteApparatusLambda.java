package com.nashss.se.firefightercheatsheetservice.Lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.firefightercheatsheetservice.Activity.Requests.DeleteApparatusRequest;
import com.nashss.se.firefightercheatsheetservice.Activity.Results.DeleteApparatusResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteApparatusLambda extends LambdaActivityRunner<DeleteApparatusRequest, DeleteApparatusResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteApparatusRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteApparatusRequest> input, Context context) {

        // createPlaylist version

        log.info("DeleteApparatusLambda: handleRequest method accessed.");
        return super.runActivity(
                () -> {
                DeleteApparatusRequest unauthenticatedRequest = input.fromBody(DeleteApparatusRequest.class);
                    return input.fromUserClaims(claims ->
                            DeleteApparatusRequest.builder()
                                    .withUserName(claims.get("email"))
                                    .withApparatusTypeAndNumber(unauthenticatedRequest.getApparatusTypeAndNumber())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteApparatusActivity().handleRequest(request)
        );




// htt://foo.bar.com/some-path/user/1234?q=hello

//original path version

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
