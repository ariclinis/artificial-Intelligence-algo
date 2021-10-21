import undirectedgraph.*;
import searchproblem.*;
import searchalgorithm.*;

import java.util.ArrayList;
import java.util.List;


public class main {
    public static void main(String[] args) {
        Graph graph1 = Romenia.defineGraph();
        Node n;
        graph1.showSets();

        List<String> lProvinces= new ArrayList<>();
        lProvinces.add("Dobrogea");
        lProvinces.add("Banat");
        System.out.println("De uma provincia");
        n = graph1.searchSolution("Arad", "Bucharest", Algorithms.AStarSearch, "Dobrogea");


        graph1.showSolution(n);
        System.out.println("De Varias provincia");
        n = graph1.searchSolution("Arad", "Bucharest", Algorithms.AStarSearch, lProvinces);


        graph1.showSolution(n);
    }
}