public class Comer {
    private static int lastId = 0;
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Comer() {
        this.id = lastId++;
    }
}