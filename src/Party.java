
import java.io.Serializable;

public class Party implements Serializable {

    /**
     * last update : 10 / 11 / 2015
     */
    private static final long serialVersionUID = 20151110L;

    private final String name;
    private int players;
    private int capacity;
    private final String creator;

    public Party(String name, String creator) {
        this.name = name;
        this.creator = creator;
        this.players = 1;
        this.capacity = Constants.PARTY_MAX_CAPACITY;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        if (players >= 1 && players <= capacity) {
            this.players = players;
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity >= Constants.PARTY_MIN_CAPACITY
                && capacity <= Constants.PARTY_MAX_CAPACITY) {
            this.capacity = capacity;
        }
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

}
