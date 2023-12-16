import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.Scanner;
public class AntiCafeRun {
    private static final Logger logger = LoggerFactory.getLogger(AntiCafeRun.class);
    private static String menu = """
            1)Выбрать стол
            2)Покинуть стол
            3)Показать занятые столы
            4)Показать свободные столы
            5)Узнать время за каждый стол
            6)Узнать сколько гостям нужно заплатить
            7)Узнать сколько нужно заплатить всем гостям если они все покинут кафе
            8)Узнать общую прибыль
            9)Узнать среднее время за столом
            10)Узнать какой стол чаще всего используется
            11)Узнать какой стол самый прибыльный
            12)Список визитов
            """;
    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        logger.info("Старт");
        while (true) {
            System.out.println("\nАнтикафе");
            System.out.println(menu);
            System.out.println("Укажите действие: ");
            String optionLine = in.nextLine();
            String tableIdLine;
            int option = 0;
            int tableId;
            option = toDigit(optionLine);
            switch (option) {
                case 1 -> {
                    System.out.println("Свободные столы: ");
                    for (Place place : ComerSetting.getFreeTables()) {
                        System.out.println(place);
                    }
                    System.out.println("Выберите стол: ");
                    tableIdLine = in.nextLine();
                    tableId = toDigit(tableIdLine);
                    try {
                        Input input = ComerSetting.createVisit(new Comer(), tableId);
                        logger.info("Стол занят успешно");
                        System.out.printf("Стол занят успешно.%n номер визита: %d%nСтарт визита: %s", input.getId(), input.getFormattedTime());
                    } catch (RuntimeException ex) {
                        logger.error(ex.getMessage());
                        System.out.println(ex.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("Занятые столы");
                    System.out.println(ComerSetting.getReservedTables());
                    System.out.println("Выберите стол: ");
                    tableIdLine = in.nextLine();
                    tableId = toDigit(tableIdLine);
                    try {
                        ComerSetting.finishVisit(tableId);
                    }
                    catch (RuntimeException ex){
                        logger.error(ex.getMessage());
                        System.out.println(ex.getMessage());
                    }
                    logger.info("Стол освобожден");
                }
                case 3 -> {
                    System.out.println("Список занятых столов: ");
                    for (Place place : ComerSetting.getReservedTables()){
                        System.out.println(place);
                        logger.info("Список занятых столов");
                    }
                }
                case 4 -> {
                    System.out.println("Список свободных столов");
                    for (Place place : ComerSetting.getFreeTables()){
                        System.out.println(place);
                        logger.info("Список свободных столов");
                    }
                }
                case 5 ->{
                    System.out.println("Сколько всего минут сидят за каждым столом");
                    System.out.println(ComerSetting.getTotalCurrentDuration());
                    logger.info("Сколько всего минут сидят за каждым столом");
                }
                case 6 -> {
                    System.out.println("Выберите стол: ");
                    tableIdLine = in.nextLine();
                    tableId = toDigit(tableIdLine);
                    logger.info("Сколько гостям нужно заплатить");
                    try {
                        System.out.println(ComerSetting.getCurrentCost(tableId));
                    } catch (RuntimeException ex) {
                        logger.error(ex.getMessage());
                        System.out.println(ex.getMessage());
                    }
                }
                case 7 -> {
                    System.out.println("Сколько нужно заплатить всем гостям если они все покинут кафе");
                    System.out.println(ComerSetting.getTotalCurrentDuration());
                    System.out.println(ComerSetting.getTotalCurrentCost());
                    logger.info("Сколько нужно заплатить всем гостям, если они все покинут кафе");
                }
                case 8 ->{
                    System.out.println("Общая прибыль");
                    System.out.println(ComerSetting.getTotalCostOfAllTime());
                    logger.info("Просмотрена общая прибыль");
                }
                case 9 -> {
                    System.out.println("Cредняя занятость стола");
                    Map<Place, DoubleSummaryStatistics> map = ComerSetting.getAverageDurationOfAllTables();
                    for (Place place :map.keySet()
                    ) {
                        System.out.println(place + ": " + map.get(place).getAverage());
                    }
                    logger.info("Сколько в среднем занят стол");
                }
                case 10 -> {
                    System.out.println("Стол который чаще всего выбирается");
                    logger.info("Какой стол чаще всего выбирается");
                    try {
                        System.out.println(ComerSetting.getTheMostPopularTable());
                    }
                    catch (RuntimeException ex){
                        System.out.println(ex.getMessage());
                        logger.error(ex.getMessage());
                    }
                }
                case 11 -> {
                    System.out.println("Самый прибыльный стол");
                    logger.info("Самый прибыльный стол");
                    try {
                        System.out.println(ComerSetting.getTheMostEarnedTable());
                    }
                    catch (RuntimeException ex){
                        System.out.println(ex.getMessage());
                        logger.error(ex.getMessage());
                    }
                }
                case 12 -> {
                    System.out.println("Cписок визитов:");
                    for (Input input : ComerSetting.getVisits()) {
                        System.out.printf("    Стол: %s%n    Длительность: %d минут%n   Закончен: %s%n", input.getTable(), input.getDuration(), input.isFinished()?"Да":"Нет");
                        if (input.isFinished())
                            System.out.printf("    Стоимость: %f %n", input.getCost());
                        System.out.println();
                    }
                    logger.info("Список всех визитов");
                }
                case 13 -> {
                    System.out.println("Список завершенных визитов");
                    for (Input input : ComerSetting.getFinishedVisits()) {
                        System.out.printf("    Стол: %s%n    Длительность: %d минут", input.getTable(), input.getDuration());
                        if (input.isFinished())
                            System.out.printf("    Стоимость: %f ", input.getCost());
                    }
                    logger.info("Список всех завершенных визитов");
                }
                default -> System.out.println("Неправильный символ");
            }
        }
    }
    public static int toDigit(String line) {
        return StringUtils.isNumeric(line)? Integer.parseInt(line):0;
    }
}