package test.java.com.nashss.se.firefightercheatsheetservice.Dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;

import com.nashss.se.firefightercheatsheetservice.Dynamodb.ApparatusDao;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Apparatus;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Coefficient;
import com.nashss.se.firefightercheatsheetservice.Dynamodb.models.Hose;
import com.nashss.se.firefightercheatsheetservice.Exceptions.ApparatusListNotFoundException;
import com.nashss.se.firefightercheatsheetservice.Metrics.MetricsPublisher;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    void deleteApparatus_validRequest_deletesTheApparatus() {

    }

    @Test
    void addApparatus_validRequest_returnsListWithApparatusAdded() {
//        String userName = "userName";
//        String fireDept = "fireDept";
//        String apparatusTypeAndNumber = "apparatusTypeAndNumber";
//
//        List<Apparatus> returnedListOfApparatus = new ArrayList<>();
//        Apparatus apparatus = new Apparatus();
//        apparatus.setUserName(userName);
//        apparatus.setApparatusTypeAndNumber(apparatusTypeAndNumber);
//        apparatus.setFireDept(fireDept);
//        returnedListOfApparatus.add(apparatus);
//
//        when(dynamoDBMapper.query(eq(Apparatus.class), any())).thenReturn(queryList);
//        //just mock the query.
//
//        List<Apparatus> result = apparatusDao.addApparatus(userName, apparatusTypeAndNumber, fireDept);
//
//        verify(dynamoDBMapper).save(eq(Apparatus.class));
    }

    @Test
    void getIndividualApparatus_validRequest_returnsListWithSingleRequestedApparatus() {
        String fireDept = "fireDept";
        String apparatusTypeAndNumber = "apparatusTypeAndNumber";

        when(dynamoDBMapper.query(eq(Apparatus.class), any())).thenReturn(queryList);

        List<Apparatus> result = apparatusDao.getIndividualApparatus(fireDept, apparatusTypeAndNumber);

        verify(dynamoDBMapper).query(eq(Apparatus.class), any());
    }

    @Test
    void deleteHose_validRequest_returnsApparatusWithHoseDeleted() {
        String fireDept = "fireDept";
        String apparatusTypeAndNumber = "apparatusTypeAndNumber";
        int hoseIndexNumber = 1;

        Apparatus apparatus = new Apparatus();
        apparatus.setUserName("userName");
        apparatus.setApparatusTypeAndNumber(apparatusTypeAndNumber);
        apparatus.setFireDept(fireDept);
        List<Hose> hoseList = new ArrayList<>();
        Hose zeroIndexHose = new Hose();
        Hose oneIndexHose = new Hose();
        hoseList.add(zeroIndexHose);
        hoseList.add(oneIndexHose);
        apparatus.setHoseList(hoseList);

        when(dynamoDBMapper.query(eq(Apparatus.class), any())).thenReturn(queryList);
        when(queryList.get(0)).thenReturn(apparatus);

        List<Apparatus> result = apparatusDao.deleteHose(fireDept, apparatusTypeAndNumber, hoseIndexNumber);

        assertEquals(fireDept, result.get(0).getFireDept());
        assertEquals(apparatusTypeAndNumber, result.get(0).getApparatusTypeAndNumber());
        assertEquals(1, result.get(0).getHoseList().size());
    }

    @Test
    void addHose_validRequestButNullHoseList_returnsListWithApparatusWithHoseAdded() {
        //GIVEN
        String fireDept = "fireDept";
        String apparatusTypeAndNumber = "apparatusTypeAndNumber";
        String name = "name";
        String color = "blue";
        int length = 150;
        Double diameter = 1.5;
        int gallons = 250;

        Apparatus apparatus = new Apparatus();
        apparatus.setUserName("userName");
        apparatus.setApparatusTypeAndNumber(apparatusTypeAndNumber);
        apparatus.setFireDept(fireDept);

        when(dynamoDBMapper.query(eq(Apparatus.class), any())).thenReturn(queryList);
        when(queryList.get(0)).thenReturn(apparatus);

        //WHEN
        List<Apparatus> result = apparatusDao.addHose(fireDept, apparatusTypeAndNumber, name, color,
                length, diameter, gallons);

        //THEN
        assertEquals(fireDept, result.get(0).getFireDept());
        assertEquals(color, result.get(0).getHoseList().get(0).getColor());
        assertEquals(1, result.get(0).getHoseList().size());
    }

    @Test
    void calculatePSI_validRequestWithOneHoseInHoseList_returnsUpdatedHose() {
        //GIVEN
        String fireDept = "fireDept";
        String apparatusTypeAndNumber = "apparatusTypeAndNumber";
        String name = "name";
        String color = "blue";
        int length = 100;
        Double diameter = 2.5;
        int gallons = 150;

        Apparatus apparatus = new Apparatus();
        apparatus.setUserName("userName");
        apparatus.setApparatusTypeAndNumber(apparatusTypeAndNumber);
        apparatus.setFireDept(fireDept);

        List<Hose> hoseList = new ArrayList<>();
        Hose hose = new Hose(name, color, length, diameter, gallons);
        hoseList.add(hose);
        apparatus.setHoseList(hoseList);
        when(dynamoDBMapper.query(eq(Apparatus.class), any())).thenReturn(queryList);
        when(queryList.get(0)).thenReturn(apparatus);
        Coefficient coefficient = new Coefficient();
        coefficient.setHoseDiameter(diameter);
        Coefficient returnCoefficient = new Coefficient();
        Double two = 2.0;
        returnCoefficient.setCoefficient(two);
        when(dynamoDBMapper.load(coefficient)).thenReturn(returnCoefficient);

        //WHEN
        List<Apparatus> result = apparatusDao.calculatePSI(fireDept, apparatusTypeAndNumber, 0);

        //THEN
        assertEquals(fireDept, result.get(0).getFireDept());
        assertEquals(color, result.get(0).getHoseList().get(0).getColor());
        assertEquals(1, result.get(0).getHoseList().size());
        assertEquals(103, result.get(0).getHoseList().get(0).getPumpDischargePressure());

    }


}
