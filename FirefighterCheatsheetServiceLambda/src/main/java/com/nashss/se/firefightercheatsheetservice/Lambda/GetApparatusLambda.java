package com.nashss.se.firefightercheatsheetservice.Lambda;
package com.nashss.se.musicplaylistservice.lambda;

import com.nashss.se.musicplaylistservice.activity.requests.GetPlaylistRequest;
import com.nashss.se.musicplaylistservice.activity.results.GetPlaylistResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetApparatusLambda extends LambdaActivityRunner<GetApparatusRequest, GetApparatusResult>
        implements RequestHandler<LambdaRequest<GetApparatusRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetApparatusRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetApparatusRequest.builder()
                                .withId(path.get("id"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetApparatusActivity().handleRequest(request)
        );
    }


}
