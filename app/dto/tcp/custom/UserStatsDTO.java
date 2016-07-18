package dto.tcp.custom;

import dto.tcp.clientpackets.WebSocketSendableImpl;

import java.util.List;

/**
 * @author iRevThis
 */
public final class UserStatsDTO extends WebSocketSendableImpl {

    private List<Stat> stats;

    public UserStatsDTO(List<Stat> stats) {
        super();
        this.stats = stats;
    }

    public List<Stat> getStats() {
        return stats;
    }

    public static class Stat {
        String name;
        Integer value;

        public Stat(String name, Integer value) {
            this.name = name;
            this.value = value;
        }
    }
}
