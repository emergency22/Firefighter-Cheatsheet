package test.java.com.nashss.se.firefightercheatsheetservice.Dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
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
import static org.mockito.Mockito.*;

public class ApparatusDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;
    @Mock
    private PaginatedQueryList<Apparatus> queryList;
    private ApparatusDao apparatusDao;
//    @Mock
//    private PaginatedScanList<Category> scanResult;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        apparatusDao = new ApparatusDao(dynamoDBMapper, metricsPublisher);
    }

    @Test
    void getApparatus_givenUserName_returnsListOfApparatus() {
        // GIVEN a valid User Name
        String userName = "userName";

        Apparatus apparatus = new Apparatus();
        apparatus.setUserName(userName);
        queryList.add(apparatus);
        System.out.println(queryList.get(0));
        when(dynamoDBMapper.query(eq(Apparatus.class), any())).thenReturn(queryList);

        System.out.println(queryList.get(0));


        ArgumentCaptor<DynamoDBQueryExpression> queryExpressionArgumentCaptor = ArgumentCaptor.forClass(DynamoDBQueryExpression.class);
        //WHEN calling getApparatus
        List<Apparatus> finalResult = apparatusDao.getApparatus(userName);
        System.out.println(finalResult.get(0));


        //THEN
        verify(dynamoDBMapper).query(eq(Apparatus.class), queryExpressionArgumentCaptor.capture());

        DynamoDBQueryExpression queryExpression = queryExpressionArgumentCaptor.getValue();
        verify(dynamoDBMapper).query(Apparatus.class, queryExpression);

        assertEquals(userName, finalResult.get(0).getUserName(), "Expected method to return the results of the query");

    }
}
