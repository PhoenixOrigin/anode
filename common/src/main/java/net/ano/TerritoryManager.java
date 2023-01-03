package net.ano;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TerritoryManager {

    public void depthFirstSearch(Territory t, Set<Territory> visited) {
        visited.add(t);

        for (Territory connection : t.connections) {
            if (!visited.contains(connection)) {
                depthFirstSearch(connection, visited);
            }
        }
    }

    public Set<Territory> findConnectedTerritories(Guild guild) {
        Set<Territory> visited = new HashSet<>();

        depthFirstSearch(guild.headquarters, visited);

        return visited;
    }

    public class Territory {
        String name;
        Guild guild;
        List<Territory> connections;

        public Territory(String name, Guild guild) {
            this.name = name;
            this.guild = guild;
            this.connections = new ArrayList<>();
        }

        public void addConnection(Territory t) {
            this.connections.add(t);
        }
    }

    public class Guild {
        String name;
        Territory headquarters;

        public Guild(String name, Territory headquarters) {
            this.name = name;
            this.headquarters = headquarters;
        }
    }
}
