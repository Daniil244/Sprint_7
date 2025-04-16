package api_test.models;

public class Order {

    private int track;
    private int id;

    public Order(int track, int id) {
        this.track = track;
        this.id = id;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
