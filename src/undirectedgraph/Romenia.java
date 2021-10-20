package undirectedgraph;

import searchalgorithm.Algorithms;
import searchalgorithm.Node;

import java.lang.invoke.StringConcatFactory;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Romenia {

    public static Graph defineGraph() {
        
        Graph g = new Graph();
        // Define cities:
        g.addVertice("Arad", 46.18333, 21.31667);
        g.addVertice("Bucharest", 44.43225, 26.10626);
        g.addVertice("Craiova", 44.33018, 23.79488);
        g.addVertice("Dobreta", 44.63692, 22.65973);
        g.addVertice("Eforie", 44.05842, 28.63361);
        g.addVertice("Fagaras", 45.84164, 24.97310);
        g.addVertice("Giurgiu", 43.90371, 25.96993);
        g.addVertice("Hirsova", 44.68935, 27.94566);
        g.addVertice("Iasi", 47.15845, 27.60144);
        g.addVertice("Lugoj", 45.69099, 21.90346);
        g.addVertice("Mehadia", 44.90411, 22.36452);
        g.addVertice("Neamt", 46.97587, 26.38188);
        g.addVertice("Oradea", 47.04650, 21.91894);
        g.addVertice("Pitesti", 44.85648, 24.86918);
        g.addVertice("R. Vilcea", 45.09968, 24.36932);
        g.addVertice("Sibiu", 45.79833, 24.12558);
        g.addVertice("Timisoara", 45.74887, 21.20868);
        g.addVertice("Urziceni", 44.71653, 26.64112);
        g.addVertice("Vaslui", 46.64069, 27.72765);
        g.addVertice("Zerind", 46.62251, 21.51742);
        // Define routes:
        g.addEdge("Arad","Sibiu");
        g.addEdge("Arad","Timisoara");
        g.addEdge("Arad","Zerind");
        g.addEdge("Bucharest","Fagaras");
        g.addEdge("Bucharest","Giurgiu");
        g.addEdge("Bucharest","Pitesti");
        g.addEdge("Bucharest","Urziceni");
        g.addEdge("Craiova","Dobreta");
        g.addEdge("Craiova","Pitesti");
        g.addEdge("Craiova","R. Vilcea");
        g.addEdge("Dobreta","Mehadia");
        g.addEdge("Eforie","Hirsova");
        g.addEdge("Fagaras","Sibiu");
        g.addEdge("Hirsova","Urziceni");
        g.addEdge("Iasi","Neamt");
        g.addEdge("Iasi","Vaslui");
        g.addEdge("Lugoj","Mehadia");
        g.addEdge("Lugoj","Timisoara");
        g.addEdge("Oradea","Sibiu");
        g.addEdge("Oradea","Zerind");
        g.addEdge("Pitesti","R. Vilcea");
        g.addEdge("R. Vilcea","Sibiu");
        g.addEdge("Urziceni","Vaslui");
        // Define regions:
        g.addVerticeSet("Banat");
        g.addVerticeToSet("Banat","Lugoj");
        g.addVerticeToSet("Banat","Mehadia");
        g.addVerticeToSet("Banat","Timisoara");
        g.addVerticeSet("Crisana");
        g.addVerticeToSet("Crisana","Arad");
        g.addVerticeToSet("Crisana","Oradea");
        g.addVerticeToSet("Crisana","Zerind");
        g.addVerticeSet("Dobrogea");
        g.addVerticeToSet("Dobrogea","Eforie");
        g.addVerticeToSet("Dobrogea","Hirsova");
        g.addVerticeSet("Moldova");
        g.addVerticeToSet("Moldova","Iasi");
        g.addVerticeToSet("Moldova","Neamt");
        g.addVerticeToSet("Moldova","Vaslui");
        g.addVerticeSet("Muntenia");
        g.addVerticeToSet("Muntenia","Bucharest");
        g.addVerticeToSet("Muntenia","Giurgiu");
        g.addVerticeToSet("Muntenia","Pitesti");
        g.addVerticeToSet("Muntenia","Urziceni");
        g.addVerticeSet("Oltenia");
        g.addVerticeToSet("Oltenia","Craiova");
        g.addVerticeToSet("Oltenia","Dobreta");
        g.addVerticeToSet("Oltenia","R. Vilcea");
        g.addVerticeSet("Transilvania");
        g.addVerticeToSet("Transilvania","Fagaras");
        g.addVerticeToSet("Transilvania","Sibiu");

        return g;
    }

    public static Graph defineGraph2() {
        Graph g = new Graph();
        g.addVertice("Arad", 46.18333, 21.31667);
        g.addVertice("Bucharest", 44.43225, 26.10626);
        g.addVertice("Eforie", 44.05842, 28.63361);
        g.addVertice("Pitesti", 44.85648, 24.86918);
        g.addVertice("R. Vilcea", 45.09968, 24.36932);
        g.addVertice("Sibiu", 45.79833, 24.12558);
        g.addVertice("Urziceni", 44.71653, 26.64112);
        g.addVertice("Hirsova", 44.68935, 27.94566);

        g.addEdge("Arad","Sibiu");
        g.addEdge("Sibiu","R. Vilcea");
        g.addEdge("R. Vilcea","Pitesti");
        g.addEdge("Pitesti","Bucharest");
        g.addEdge("Bucharest","Urziceni");
        g.addEdge("Urziceni","Hirsova");
        g.addEdge("Hirsova","Eforie");

        return g;
    }

    public static Graph defineNewGraph(String initLabel, String goalLabel, String province, Algorithms algID, Graph g ) {
        //State init = new State(this.getVertice(initLabel));
        //State goal = new State(this.getVertice(goalLabel));
        Node n=null;
        Vertex initV = g.getVertice(initLabel);
        Vertex goalV = g.getVertice(goalLabel);

        HashSet<Vertex> cities_province = g.getCitesProvince(province);
        Iterator<Vertex> iterator_province = cities_province.iterator();
        List<List<Object>> solutions= new LinkedList<List<Object>>();
        List<List<Object>> solutions2= new LinkedList<List<Object>>();
        //Novo grafo
        Graph new_graph = new Graph();
        new_graph.addVertice(initLabel, initV.getLatitude(), initV.getLongitude());
        new_graph.addVertice(goalLabel, goalV.getLatitude(), goalV.getLongitude());
        new_graph.initVSet(g.getVSets());
        while (iterator_province.hasNext()){
            String city = iterator_province.next().getLabel();
            List<Object> solution_go= g.searchSolution(initLabel,city, algID).getPath();
            List<Object> solution_back= g.searchSolution(city,goalLabel, algID).getPath();
            solutions.add(solution_go);
            solutions2.add(solution_back);
            System.out.println(solution_go);
            System.out.println(solution_back);
            Vertex city_next_v = g.getVertice(city);
            new_graph.addVertice(city,city_next_v.getLatitude(), city_next_v.getLongitude());
            new_graph.addEdge(initLabel,city);
            new_graph.addEdge(goalLabel,city);

            //createVertexAndEdge(solution_go,new_graph,g,goalLabel);
            //createVertexAndEdge(solution_back,new_graph,g,city);

        }

        return new_graph;
    }


    private static void createVertexAndEdge(List<Object> solution_go, Graph new_graph, Graph g, String goalLabel){
        for (int i = 0; i<solution_go.size()-1;i++) {
            String cityGo = solution_go.get(i).toString();
            if(i!=solution_go.size()-1) {
                String cityGo_next = solution_go.get(i + 1).toString();
                Vertex cityGoVertex_next = g.getVertice(cityGo_next);
                new_graph.addVertice(cityGo_next,cityGoVertex_next.getLatitude(), cityGoVertex_next.getLongitude());
                if(cityGo != goalLabel){
                    new_graph.addEdge(cityGo,cityGo_next);
                }
            }
        }
    }


}
