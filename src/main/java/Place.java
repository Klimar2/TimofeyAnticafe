public class Place {
    private final int id;
    private boolean isFree;
    public int getId() {
        return id;
    }
    public boolean isFree() {
        return isFree;
    }
    public void setFree(boolean free) {
        isFree = free;
    }
    public Place(int id) {
        this.id = id;
        this.isFree = true;
    }
    public String toString(){
        return String.format("Стол № %d", id);
    }
}