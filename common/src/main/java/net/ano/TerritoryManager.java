package net.ano;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TerritoryManager {

    public void depthFirstSearch(Territory t, Set<Territory> visited) {
        visited.add(t);

        for (Territory connection : t.connections) {
            if (visited.contains(connection) || !connection.guild.name.equals(t.guild.name)) continue;
            depthFirstSearch(connection, visited);
        }
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
        List<Territory> territories;

        public Guild(String name) {
            this.name = name;
        }

        public void setTerritories(List<Territory> territories){
            this.territories = territories;
        }

        public void setHeadquarters(Territory headquarters){
            this.headquarters = headquarters;
        }

        public Set<Territory> findConnectedTerritories() {
            Set<Territory> visited = new HashSet<>();

            depthFirstSearch(headquarters, visited);

            return visited;
        }

        public Set<Territory> findUnconnectedTerritories(){
            Set<Territory> visited = new HashSet<>();
            Set<Territory> unvisited = new HashSet<>();
            depthFirstSearch(headquarters, visited);
            for(Territory t : territories){
                if(!visited.contains(t)) unvisited.add(t);
            }

            return unvisited;
        }
    }
}
