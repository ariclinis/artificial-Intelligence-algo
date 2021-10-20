import undirectedgraph.*;
import searchproblem.*;
import searchalgorithm.*;


public class main {
    public static void main(String[] args) {
        Graph graph1 = Romenia.defineGraph();
        Graph graph = Romenia.defineNewGraph("Arad", "Bucharest", "Dobrogea", Algorithms.AStarSearch, graph1);
        graph.showLinks();
        graph.showSets();
        Node n;
        n = graph1.searchSolution2("Arad", "Bucharest", "Dobrogea", Algorithms.AStarSearch );
        graph.showSolution(n);
    }
}