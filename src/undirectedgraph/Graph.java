package undirectedgraph;

import java.util.*;

import searchalgorithm.*;
import searchproblem.SearchProblem;
import searchproblem.State;

public class Graph {
	private HashMap<String,Vertex> vertices;
	private HashMap<Integer,Edge> edges;	
	private ArrayList<VertexSet> vSets;
	private Graph new_graph;
	private long expansions;
	private long generated;
	private long repeated;
	private double time;
	
	public Graph() {
		this.vertices = new HashMap<String,Vertex>();
		this.edges = new HashMap<Integer,Edge>();
		this.vSets = new ArrayList<VertexSet>();
		this.expansions = 0;
		this.generated = 0;
		this.repeated = 0;
		this.time = 0;
	}
	
	public void addVertice(String label, double lat, double lng) {
		Vertex v =  new Vertex(label);
		this.vertices.put(label, v);
		v.setCoordinates(lat, lng);
	}
	
	public Vertex getVertice(String label) {
		return this.vertices.get(label);
	}
	
	public void addVerticeSet(String label) {
		VertexSet vSet =  new VertexSet(label);
		this.vSets.add(vSet);
	}
	
	public VertexSet getVerticeSet(String setLabel) {
		for (VertexSet vSet : vSets) {
			if (vSet.getLabel()==setLabel)
				return vSet;
		}
		return null;
	}
	
	public void addVerticeToSet(String labelSet,String labelVertex) {
		Vertex v = this.vertices.get(labelVertex);
		for (VertexSet vSet : vSets) {
			if (vSet.getLabel()==labelSet) {
				vSet.addVertice(v);
				break;
			}
		}
	}
	
	public boolean addEdge(Vertex one, Vertex two, double weight) {
		if (one.equals(two)) return false;
		Edge e = new Edge(one,two,weight);
		if (edges.containsKey(e.hashcode())) return false;
		if (one.containsNeighbor(e) || two.containsNeighbor(e))  return false;	
		edges.put(e.hashcode(), e);
		one.addNeighbor(e);
		two.addNeighbor(e);
		return true;
	} 
	
	public boolean addEdge(String oneLabel, String twoLabel, double weight) {
		Vertex one = getVertice(oneLabel);
		Vertex two = getVertice(twoLabel);
		return addEdge(one,two,weight);
	}
	
	public boolean addEdge(String oneLabel, String twoLabel) {
		Vertex one = getVertice(oneLabel);
		Vertex two = getVertice(twoLabel);
		return addEdge(one,two,one.straightLineDistance(two));
	}

	public void showLinks() {
		System.out.println("********************* LINKS *********************");
		for (Vertex current: vertices.values()) {		
			System.out.print(current + ":" + " ");
			for (Edge e: current.getNeighbors()) {
				System.out.print(e.getNeighbor(current) + " (" + e.getWeight() + "); ");
			}
			System.out.println();
		}
		System.out.println("*************************************************");
	}
	
	public void showSets() {
		System.out.println("********************* SETS *********************");
		for (VertexSet vSet: vSets) {
			System.out.println(vSet);
		}
		System.out.println("*************************************************");
	}

	public Node searchSolution(String initLabel, String goalLabel, Algorithms algID, String province) {
		new_graph = new Graph();
		new_graph = getNew_graph(initLabel, goalLabel,this.getCitesProvince(province),algID);
		Node n = new_graph.searchSolution( initLabel, goalLabel, algID);

		return n;
	}


	public Node searchSolution(String initLabel, String goalLabel, Algorithms algID, List<String> provinces) {

		int count_l = 0;
		new_graph = new Graph();
		Vertex initV = this.getVertice(initLabel);
		Vertex goalV = this.getVertice(goalLabel);
		new_graph.addVertice(initLabel, initV.getLatitude(), initV.getLongitude());
		new_graph.addVertice(goalLabel, goalV.getLatitude(), goalV.getLongitude());

		while (count_l < provinces.size()) {
			HashSet<Vertex> cites = getCitesProvince(provinces.get(count_l));

			//Novo grafo
			if (count_l == 0 ) {
				for (Vertex v : cites) {
					String city = v.getLabel();
					Vertex city_next_v = this.getVertice(city);
					new_graph.addVertice(city, city_next_v.getLatitude(), city_next_v.getLongitude());
					new_graph.addEdge(initLabel, city_next_v.getLabel(), searchSolution(initLabel, city_next_v.getLabel(), algID).getPathCost());
				}
			}else if(count_l == provinces.size()-1){
				for (Vertex v : cites) {
					String city = v.getLabel();
					Vertex city_next_v = this.getVertice(city);
					HashSet<Vertex> cites_ant = getCitesProvince(provinces.get(count_l-1));

					new_graph.addVertice(city, city_next_v.getLatitude(), city_next_v.getLongitude());
					new_graph.addEdge(goalLabel, city_next_v.getLabel(), searchSolution(goalLabel, city_next_v.getLabel(), algID).getPathCost());
					for (Vertex v_ant:cites_ant) {
						new_graph.addEdge(v_ant.getLabel(), city_next_v.getLabel(), searchSolution(v_ant.getLabel(), city_next_v.getLabel(), algID).getPathCost());
					}

				}
			}else{
				HashSet<Vertex> cites_ant = getCitesProvince(provinces.get(count_l-1));
				for (Vertex v: cites) {
					for (Vertex v_ant:cites_ant) {
						String city = v.getLabel();
						Vertex city_next_v = this.getVertice(city);
						new_graph.addVertice(city, city_next_v.getLatitude(), city_next_v.getLongitude());
						new_graph.addEdge(v_ant.getLabel(), city_next_v.getLabel(), searchSolution(v_ant.getLabel(), city_next_v.getLabel(), algID).getPathCost());
					}
				}
			}

			count_l++;
		}

		Node n = new_graph.searchSolution(initLabel, goalLabel, algID);

		return n;
	}

	public Node searchSolution(String initLabel, String goalLabel, Algorithms algID) {
		State init = new State(this.getVertice(initLabel));
		State goal = new State(this.getVertice(goalLabel));
		SearchProblem prob = new SearchProblem(init,goal);
		SearchAlgorithm alg = null;
		switch(algID) {
			case BreadthFirstSearch :
				alg = new BreadthFirstSearch(prob);
				break;
			case DepthFirstSearch :
				alg = new DepthFirstSearch(prob);
				break; 
			case UniformCostSearch :
				alg = new UniformCostSearch(prob);
				break; 
			case GreedySearch :
				alg = new GreedySearch(prob);
				break; 
			case AStarSearch :
				alg = new AStarSearch(prob);
				break; 
		   default : 
			  System.out.println("This algorithm is currently not implemented!");
		}
		Node n = alg.searchSolution();	
		Map<String,Number> m = alg.getMetrics();
		this.expansions += (long)m.get("Node Expansions");
		this.generated += (long)m.get("Nodes Generated");
		this.repeated += (long)m.get("State repetitions");
		this.time += (double)m.get("Runtime (ms)");
		return n;
	}



	public void showSolution(Node n) {
		System.out.println("******************* SOLUTION ********************");
		System.out.println("Node Expansions: " + this.expansions);
		System.out.println("Nodes Generated: " + this.generated);
		System.out.println("State Repetitions: " + this.repeated);
		System.out.printf("Runtime (ms): %6.3f \n",this.time);
		Node ni = null;
		List<Object> solution = n.getPath();
		double dist = 0;
		for (int i = 0; i<solution.size()-1;i++) {
			System.out.printf("| %-9s | %4.0f | ",solution.get(i), dist);
			ni = searchSolution(solution.get(i).toString(), solution.get(i+1).toString(), Algorithms.AStarSearch);
			System.out.print(ni.getPath());	
			System.out.println(" -> " + (int)ni.getPathCost());
			dist += ni.getPathCost();
		}
		System.out.printf("| %-9s | %4.0f | \n",solution.get(solution.size()-1), dist);
		System.out.println("*************************************************");
	}

	public HashSet<Vertex> getCitesProvince(String province){
			boolean found = false;
			int count = this.vSets.size();
			ArrayList<VertexSet> ite = this.vSets;
			HashSet<Vertex> cities=null;
			int i =0;
			while (i<count && !found){
				if(this.vSets.get(i).getLabel() == province){
					found=true;
					cities = this.vSets.get(i).getVertices();
				}
				i++;
			}
			return cities;
	}

	private Graph getNew_graph(String initLabel, String goalLabel, HashSet<Vertex> cities_province, Algorithms algID){
		Graph g = this;
		Vertex initV = g.getVertice(initLabel);
		Vertex goalV = g.getVertice(goalLabel);
		Iterator<Vertex> iterator_province = cities_province.iterator();
		//Novo grafo
		new_graph = new Graph();
		new_graph.addVertice(initLabel, initV.getLatitude(), initV.getLongitude());
		new_graph.addVertice(goalLabel, goalV.getLatitude(), goalV.getLongitude());

		while (iterator_province.hasNext()){
			String city = iterator_province.next().getLabel();
			Vertex city_next_v = g.getVertice(city);
			new_graph.addVertice(city,city_next_v.getLatitude(), city_next_v.getLongitude());
			new_graph.addEdge(initLabel, city_next_v.getLabel(), searchSolution(initLabel, city_next_v.getLabel(), algID).getPathCost());
			new_graph.addEdge(goalLabel, city_next_v.getLabel(), searchSolution(goalLabel, city_next_v.getLabel(), algID).getPathCost());

		}
		return new_graph;
	}


}
