import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.*;
public class ComerSetting {
    private static double pricePerMinute = 5;
    private static List<Input> inputs = new ArrayList<>();
    public static double getPricePerMinute() {
        return pricePerMinute;
    }
    public static void setPricePerMinute(double pricePerMinute) {
        ComerSetting.pricePerMinute = pricePerMinute;
    }
    public static List<Input> getVisits() {
        return inputs;
    }
    public static void setVisits(List<Input> inputs) {
        ComerSetting.inputs = inputs;
    }
    public static Input createVisit(Comer comer, int tableId) {
        if (tableId < 1 || tableId > 10)
            throw new IllegalArgumentException("Всего 10 столов, выберите из них");
        Place place = PlaceSetting.tables.get(tableId);
        if (!place.isFree())
            throw new RuntimeException("Этот стол уже занят");
        Input input = new Input(comer, place, LocalDateTime.now());
        place.setFree(false);
        inputs.add(input);
        return input;
    }
    public static Input finishVisit(int tableId) {
        if (tableId < 1 || tableId > 10)
            throw new IllegalArgumentException("Всего 10 столов выберите");
        Place place = PlaceSetting.tables.get(tableId);
        if (place.isFree()){
            throw new RuntimeException("Выбранный стол свободен");
        }
        Input input = inputs.stream()
                .filter(v -> v.getTable().getId() == tableId && !v.isFinished())
                .findFirst().orElseThrow();
        input.setDuration(input.getCurrentDuration());
        input.setCost(input.calculateCost(pricePerMinute));
        input.setFinished(true);
        place.setFree(true);
        return input;
    }
    public static List<Place> getFreeTables() {
        return PlaceSetting.getFreeTables();
    }
    public static List<Place> getReservedTables() {
        return PlaceSetting.getReservedTables();
    }
    public static List<Input> getFinishedVisits() {
        return inputs.stream()
                .filter(Input::isFinished).collect(Collectors.toList());
    }
    public static long getCurrentDuration(int tableId) {
        Input input = inputs.stream()
                .filter(v -> v.getTable().getId() == tableId && !v.isFinished())
                .findFirst().orElseThrow();
        return input.calculateDuration();
    }
    public static Map<Place, Long> getTotalCurrentDuration() {
        Map<Place, Long> durations = new HashMap<>();
        for (Place place : PlaceSetting.getReservedTables()) {
            durations.put(place, getCurrentDuration(place.getId()));
        }
        return durations;
    }
    public static double getCurrentCost(int tableId) {
        if (tableId < 1 || tableId > 10)
            throw new IllegalArgumentException("Всего 10 столов выберите");
        Input input = inputs.stream()
                .filter(v -> v.getTable().getId() == tableId && !v.isFinished())
                .findFirst().orElseThrow(() -> new RuntimeException("Стол свободен"));
        return input.calculateCost(pricePerMinute);
    }
    public static Map<Place, Double> getTotalCurrentCost() {
        Map<Place, Double> costs = new HashMap<>();
        for (Place place : PlaceSetting.getReservedTables()) {
            costs.put(place, getCurrentCost(place.getId()));
        }
        return costs;
    }
    public static double getTotalCostOfAllTime() {
        return inputs.stream()
                .filter(Input::isFinished).mapToDouble(Input::getCost).sum();
    }
    public static Map.Entry<Place, Long> getTheMostPopularTable() {
        Map<Place, Long> map = inputs.stream()
                .filter(Input::isFinished).collect(Collectors.groupingBy(Input::getTable, Collectors.counting()));
        return map.entrySet().stream()
                .max(Map.Entry.comparingByValue()).orElseThrow(() ->new RuntimeException("Посещений нет"));
    }
    public static Map<Place, DoubleSummaryStatistics> getAverageDurationOfAllTables() {
        return inputs.stream()
                .filter(Input::isFinished)
                .collect(Collectors.groupingBy(Input::getTable, Collectors.summarizingDouble(Input::getDuration)));
    }
    public static Map.Entry<Place, DoubleSummaryStatistics> getTheMostEarnedTable() {
        Map<Place, DoubleSummaryStatistics> map = inputs.stream()
                .filter(Input::isFinished)
                .collect(Collectors.groupingBy(Input::getTable, Collectors.summarizingDouble(Input::getCost)));
        return map.entrySet().stream().max(Comparator.comparing(entry -> entry.getValue().getSum())).orElseThrow(() -> new RuntimeException("Нет завершенных визитов"));
    }
}