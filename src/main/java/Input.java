import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.LocalDateTime;
public class Input {
    private static int lastId = 1;
    private int id;
    private Comer comer;
    private Place place;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long duration;
    private double cost;
    private boolean finished;
    public static int getLastId() {
        return lastId;
    }
    public  long getCurrentDuration(){
        return calculateDuration();
    }
    public static void setLastId(int lastId) {
        Input.lastId = lastId;
    }
    public String getFormattedTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("mm:HH");
        return dateTimeFormatter.format(startTime);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Input(Comer comer, Place place, LocalDateTime startTime) {
        this.id = lastId++;
        this.comer = comer;
        this.place = place;
        this.startTime = startTime;
        this.finished = false;
    }
    public Comer getClient() {
        return comer;
    }
    public void setClient(Comer comer) {
        this.comer = comer;
    }
    public Place getTable() {
        return place;
    }
    public void setTable(Place place) {
        this.place = place;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public long getDuration() {
        return duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public boolean isFinished() {
        return finished;
    }
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    public long calculateDuration(){
        return Duration.between(startTime,LocalDateTime.now()).toMinutes();
    }
    public double calculateCost(double pricePerMinute ){
        return calculateDuration() * pricePerMinute;
    }
}