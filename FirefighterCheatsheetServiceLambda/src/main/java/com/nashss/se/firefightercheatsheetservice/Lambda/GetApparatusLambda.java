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

        // createPlaylist version

        log.info("HIT!");
        return super.runActivity(
            () -> {
//                GetApparatusRequest unauthenticatedRequest = input.fromBody(GetApparatusRequest.class);       //public T fromUserClaims(Function<Map<String, String>, T> converter) {
                return input.fromUserClaims(claims ->
                        GetApparatusRequest.builder()
                                .withUserName(claims.get("email"))
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideGetApparatusActivity().handleRequest(request)
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
