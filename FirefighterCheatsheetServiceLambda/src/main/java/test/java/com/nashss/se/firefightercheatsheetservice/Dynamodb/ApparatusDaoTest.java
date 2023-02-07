package test.java.com.nashss.se.firefightercheatsheetservice.Dynamodb;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.DynamoDbClientProvider;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Hose;
import com.nashss.se.firefightercheatsheetservice.Exceptions.ApparatusListNotFoundException;
import com.nashss.se.firefightercheatsheetservice.Metrics.MetricsPublisher;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ApparatusDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;
    @Mock
    private PaginatedQueryList<Apparatus> queryList;
    private ApparatusDao apparatusDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        apparatusDao = new ApparatusDao(dynamoDBMapper, metricsPublisher);
    }

    @Test
    void getApparatus_givenUserName_returnsListOfApparatus() {
        // GIVEN a valid User Name
        String userName = "userName";

        when(dynamoDBMapper.query(eq(Apparatus.class), any())).thenReturn(queryList);

        ArgumentCaptor<DynamoDBQueryExpression> queryExpressionArgumentCaptor = ArgumentCaptor.forClass(DynamoDBQueryExpression.class);
        //WHEN calling getApparatus
        List<Apparatus> finalResult = apparatusDao.getApparatus(userName);

        //THEN
        verify(dynamoDBMapper).query(eq(Apparatus.class), queryExpressionArgumentCaptor.capture());

        DynamoDBQueryExpression queryExpression = queryExpressionArgumentCaptor.getValue();
        verify(dynamoDBMapper).query(Apparatus.class, queryExpression);

        assertEquals(queryList, finalResult, "Expected method to return the results of the query");
    }

    @Test
    void getApparatus_givenANullList_throwsApparatusNotFoundException() {
        //GIVEN a null list returned by the query

        PaginatedQueryList<Apparatus> nullList = null;
        when(dynamoDBMapper.query(eq(Apparatus.class), any())).thenReturn(nullList);

        //WHEN & THEN an ApparatusListNotFoundException should be thrown
        assertThrows(ApparatusListNotFoundException.class, () -> apparatusDao.getApparatus("whatever"));
    }

    @Test
    void makeSomeJson() throws JsonProcessingException {

        String name = "Preconnect 1";
        String color = "Red";
        int length = 200;
        Double hoseDiameter = 1.5;
        int waterQuantityInGallons = 200;
        Hose hose1 = new Hose(name, color, length, hoseDiameter, waterQuantityInGallons);
        Hose hose2 = new Hose("Preconnect 2", "Red", 200, 1.75, 150);
        List<Hose> hoseList = new ArrayList<>();
        hoseList.add(hose1);
        hoseList.add(hose2);

        Apparatus apparatus = new Apparatus();
        apparatus.setHoseList(hoseList);

        ObjectMapper objectMapper = new ObjectMapper();
        String serializedApparatus = objectMapper.writeValueAsString(apparatus);
        System.out.println(serializedApparatus);
    }

}
