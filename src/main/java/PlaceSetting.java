import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
public class PlaceSetting {
    public static Map<Integer, Place> tables = Map.of(
            1, new Place(1),
            2, new Place(2),
            3, new Place(3),
            4, new Place(4),
            5, new Place(5),
            6, new Place(6),
            7, new Place(7),
            8, new Place(8),
            9, new Place(9),
            10, new Place(10));
    public static List<Place> getFreeTables(){
//        List<Table> freeTables = new ArrayList<>();
//        for (Table table : tables.values()) {
//            if(table.isFree())
//                freeTables.add(table);
//        }
//        return freeTables;
        return tables.values().stream()
                .filter(Place::isFree)
                .collect(Collectors.toList());
    }
    public static List<Place> getReservedTables(){
        return tables.values().stream()
                .filter(t -> !t.isFree())
                .collect(Collectors.toList());
    }
}