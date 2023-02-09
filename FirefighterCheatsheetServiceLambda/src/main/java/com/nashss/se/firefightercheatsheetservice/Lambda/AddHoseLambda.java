package com.nashss.se.firefightercheatsheetservice.Lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.firefightercheatsheetservice.Activity.Requests.AddHoseRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddHoseLambda extends LambdaActivityRunner<AddHoseRequest, AddHoseResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddHoseRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddHoseRequest> input, Context context) {

        log.info("AddHoseLambda: handleRequest method accessed.");

//        DeleteHoseRequest email = input.fromUserClaims(claims ->
//                DeleteHoseRequest.builder()
//                        .withUserName(claims.get("email"))
//                        .build());
//
//        String actualEmail = email.getUserName();
//        log.info("DeleteHoseLambda: email: " + actualEmail);
//
//        return super.runActivity(
//                () -> input.fromQuery(query ->
//                        DeleteHoseRequest.builder()
//                                .withUserName(actualEmail)
//                                .withFireDept(query.get("fireDept"))
//                                .withApparatusTypeAndNumber(query.get("apparatusTypeAndNumber"))
//                                .withHoseIndexNumber(Integer.valueOf(query.get("hoseIndexNumber")))
//                                .build()),
//                (request, serviceComponent) ->
//                        serviceComponent.provideDeleteHoseActivity().handleRequest(request)
//        );
        return super.runActivity(
                () -> {
                    AddHoseRequest queryRequest = input.fromQuery(query ->
                            AddHoseRequest.builder()
                                    .withFireDept(query.get("fireDept"))
                                    .withApparatusTypeAndNumber(query.get("apparatusTypeAndNumber"))
                                    .withName(query.get("name"))
                                    .withColor(query.get("color"))
                                    .withLength(Integer.valueOf(query.get("length")))
                                    .withHoseDiameter(Double.valueOf(query.get("diameter")))
                                    .withWaterQuantityInGallons(Integer.valueOf(query.get("gallons")))
                                    .build());
                    return input.fromUserClaims(claims ->
                            AddHoseRequest.builder()
                                    .withUserName(claims.get("email"))
                                    .withFireDept(queryRequest.getFireDept())
                                    .withApparatusTypeAndNumber(queryRequest.getApparatusTypeAndNumber())
                                    .withName(queryRequest.getName())
                                    .withColor(queryRequest.getColor())
                                    .withLength(queryRequest.getLength())
                                    .withHoseDiameter(queryRequest.getHoseDiameter())
                                    .withWaterQuantityInGallons(queryRequest.getWaterQuantityInGallons())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideAddHoseActivity().handleRequest(request)
        );

    }
}