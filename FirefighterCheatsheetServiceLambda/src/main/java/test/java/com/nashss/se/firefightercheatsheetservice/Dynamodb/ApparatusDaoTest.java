package test.java.com.nashss.se.firefightercheatsheetservice.Dynamodb;

import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Metrics.MetricsPublisher;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ApparatusDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;
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





        when(dynamoDBMapper.scan(eq(Category.class), any())).thenReturn(scanResult);

        ArgumentCaptor<DynamoDBScanExpression> scanExpressionArgumentCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        // WHEN
        List<Category> categories = categoryDao.getCategories();

        // THEN
        verify(dynamoDBMapper).scan(eq(Category.class), scanExpressionArgumentCaptor.capture());
        DynamoDBScanExpression scanExpression = scanExpressionArgumentCaptor.getValue();

        verify(dynamoDBMapper).scan(Category.class, scanExpression);

        assertEquals(scanResult, categories, "Expected method to return the results of the scan");
    }
}
