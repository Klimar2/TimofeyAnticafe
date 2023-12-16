import org.junit.jupiter.api.BeforeEach;
import java.util.concurrent.TimeUnit;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class InputTest {
    @BeforeEach
    public void setUp() {
        ComerSetting.createVisit(new Comer(), 1);
        ComerSetting.createVisit(new Comer(), 2);
        ComerSetting.createVisit(new Comer(), 3);
        ComerSetting.createVisit(new Comer(), 4);
        ComerSetting.createVisit(new Comer(), 5);
    }
    @org.junit.jupiter.api.Test
    void createVisitPositive() {
        assertEquals(1, ComerSetting.getVisits().size());
        assertEquals(1, ComerSetting.getVisits().get(0).getTable().getId());
    }
    @org.junit.jupiter.api.Test
    void createVisitNegative() {
        assertThrows(RuntimeException.class, () -> ComerSetting.createVisit(new Comer(), 1));
    }
    @org.junit.jupiter.api.Test
    void finishVisitPositive() {
        ComerSetting.finishVisit(1);
        assertEquals(true, ComerSetting.getVisits().get(0).isFinished());
        assertEquals(false, ComerSetting.getVisits().get(1).isFinished());
    }
    @org.junit.jupiter.api.Test
    void finishVisitNegative() {
        assertThrows(RuntimeException.class, () -> ComerSetting.finishVisit(7));
    }
    @org.junit.jupiter.api.Test
    void getFreeTables() {
        assertEquals(5, ComerSetting.getFreeTables().size());
        Set<Place> places = new HashSet<>();
        places.add(PlaceSetting.tables.get(6));
        places.add(PlaceSetting.tables.get(7));
        places.add(PlaceSetting.tables.get(8));
        places.add(PlaceSetting.tables.get(9));
        places.add(PlaceSetting.tables.get(10));
        assertEquals(5, ComerSetting.getFreeTables().size());
        assertEquals(places, new HashSet<>(ComerSetting.getFreeTables()));
    }
    @org.junit.jupiter.api.Test
    void getReservedTables() {
        assertEquals(5, ComerSetting.getFreeTables().size());
        Set<Place> places = new HashSet<>();
        places.add(PlaceSetting.tables.get(1));
        places.add(PlaceSetting.tables.get(2));
        places.add(PlaceSetting.tables.get(3));
        places.add(PlaceSetting.tables.get(4));
        places.add(PlaceSetting.tables.get(5));
        assertEquals(5, ComerSetting.getFreeTables().size());
        assertEquals(places, new HashSet<>(ComerSetting.getReservedTables()));
    }
    @org.junit.jupiter.api.Test
    void getFinishedVisits() {
        ComerSetting.finishVisit(1);
        ComerSetting.finishVisit(2);
        assertEquals(1, ComerSetting.getFinishedVisits().get(0).getTable().getId());
        assertEquals(2, ComerSetting.getFinishedVisits().get(1).getTable().getId());
        assertEquals(2, ComerSetting.getFinishedVisits().size());
    }
    @org.junit.jupiter.api.Test
    void getCurrentDuration() throws InterruptedException {
        ComerSetting.getCurrentDuration(5);
        TimeUnit.MINUTES.sleep(1);
        assertEquals(1, ComerSetting.getCurrentDuration(5));
    }
    @org.junit.jupiter.api.Test
    void getTotalCurrentDuration() throws InterruptedException {
        ComerSetting.finishVisit(1);
        ComerSetting.finishVisit(2);
        Map<Place, Long> map = Map.of(
                PlaceSetting.tables.get(3), 1L,
                PlaceSetting.tables.get(4), 1L,
                PlaceSetting.tables.get(5), 1L
        );
        TimeUnit.MINUTES.sleep(1);
        assertEquals(map, ComerSetting.getTotalCurrentDuration());
    }
    @org.junit.jupiter.api.Test
    void getCurrentCost() throws InterruptedException {
        TimeUnit.MINUTES.sleep(1);
        assertEquals(5, ComerSetting.getCurrentCost(1));
    }
    @org.junit.jupiter.api.Test
    void getTotalCurrentCost() throws InterruptedException {
        TimeUnit.MINUTES.sleep(1);
        ComerSetting.finishVisit(3);
        ComerSetting.finishVisit(4);
        ComerSetting.finishVisit(5);
        Map<Place, Double> expected = Map.of(
                PlaceSetting.tables.get(1), 5.0,
                PlaceSetting.tables.get(2), 5.0
        );
        assertEquals(expected, ComerSetting.getTotalCurrentCost());
    }
    @org.junit.jupiter.api.Test
    void getTotalCostOfAllTime() throws InterruptedException {
        TimeUnit.MINUTES.sleep(1);
        ComerSetting.finishVisit(1);
        ComerSetting.createVisit(new Comer(), 1);
        TimeUnit.MINUTES.sleep(1);
        ComerSetting.finishVisit(1);
        assertEquals(10.0, ComerSetting.getTotalCostOfAllTime());

    }
    @org.junit.jupiter.api.Test
    void getTheMostPopularTable() throws InterruptedException {
        TimeUnit.MINUTES.sleep(1);
        ComerSetting.finishVisit(1);
        ComerSetting.createVisit(new Comer(), 1);
        TimeUnit.MINUTES.sleep(1);
        ComerSetting.finishVisit(1);
        assertEquals(PlaceSetting.tables.get(1), ComerSetting.getTheMostPopularTable().getKey());
        assertEquals(2, ComerSetting.getTheMostPopularTable().getValue());
    }
    @org.junit.jupiter.api.Test
    void getAverageDurationOfAllTables() throws InterruptedException {
        TimeUnit.MINUTES.sleep(1);
        ComerSetting.finishVisit(1);
        ComerSetting.finishVisit(2);

        assertEquals(1, ComerSetting.getAverageDurationOfAllTables().get(PlaceSetting.tables.get(1)).getAverage());
        assertEquals(1, ComerSetting.getAverageDurationOfAllTables().get(PlaceSetting.tables.get(2)).getAverage());
    }
    @org.junit.jupiter.api.Test
    void getTheMostEarnedTable() throws InterruptedException {
        TimeUnit.MINUTES.sleep(1);
        ComerSetting.finishVisit(2);
        TimeUnit.MINUTES.sleep(1);
        ComerSetting.finishVisit(1);
        assertEquals(PlaceSetting.tables.get(1), ComerSetting.getTheMostEarnedTable().getKey());
        assertEquals(10.0, ComerSetting.getTheMostEarnedTable().getValue().getSum());
    }
}