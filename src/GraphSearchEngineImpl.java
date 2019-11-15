import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/*
 * Marie Tessier and Edward Clifford 
 * 
 * This class uses Breath First Search Algorithm the search through a graph 
 * and find the shortest path.
 */

public class GraphSearchEngineImpl implements GraphSearchEngine {

	//create a hash map that holds a value and a node
	private HashMap<Node, Integer> _graphDist = new HashMap<Node, Integer>(); 

	/**
	 * finds the shortest path from one node to another
	 * @param s is the starting node
	 * @param t is the target node
	 * @return returns a list of all the nodes that are in the shortest path
	 */
	public List<Node> findShortestPath (Node s, Node t) {

		//finds if there is a path to the node
        System.out.println(s.getName());
        System.out.println(t.getName());

		if (!(bfs(s, t))) {
			return null;
		}

		//only call searchBackward if there is a path
		System.out.println("checking list!!:  " + searchBackward(s, t));
		return searchBackward(s, t);
	}

	/**
	 * Uses Breath First Search to search through the graph for a path to the target node
	 * @param s is the starting node
	 * @param t is the target node
	 * @return true is a path was found, false if no path was found
	 */
	private boolean bfs(Node s, Node t) {
		//creating the queue 
		Queue<Node> queue = new LinkedList<Node>();
        _graphDist = new HashMap<Node, Integer>();
         
		//counter for values
		Integer value = 0;
		boolean finalFound = false;

		//adding initial node to queue and setting value to 0
		queue.add(s);
		_graphDist.put(s, value);

		//iterate through the queue
		while (queue.size() != 0) {

			//dequeue node
			Node temp = queue.poll();

			//creating an iterator to look through the nodes
			Iterator<? extends Node> i = temp.getNeighbors().iterator();
			while (i.hasNext()) {

				//dequeue
				Node neighborNode = i.next();
				if(!(_graphDist.containsKey(neighborNode))) {
					//set value to count
					_graphDist.put(neighborNode, _graphDist.get(temp) + 1);
                    
					queue.add(neighborNode);	

					//if the final node is found
					if(neighborNode.equals(t)) {
						finalFound = true;
						return finalFound;
					}
				}		
			}			
		}
		//if the final node is not found
		return finalFound;
	}
	
	/**
	 * Searches backwards from the target node to the start node
	 *   to find the shortest path from one node to another
	 * @param s is the starting node
	 * @param t is the target node
	 * @return returns a list of all the nodes that are in the shortest path
	 */
	private List<Node> searchBackward(Node s, Node t) {
		//creating the queue 
		Queue<Node> queue = new LinkedList<Node>();
		
		//Shortest path list
		LinkedList<Node> finalPath = new LinkedList<Node>();
		
		System.out.println("original finalPath: "+ finalPath);
		
		//adding target node to queue and setting value to 0
		queue.add(t);
		
		//iterate through the queue
		while (queue.size() != 0) {
			
			System.out.println("FIRST TIME WE REACH A NEW NODE");

			//dequeue node
			Node temp = queue.poll();
			finalPath.add(temp);
			System.out.println("Add a new node to final path: "+ finalPath);

			//creating an iterator to look through the nodes
			Iterator<? extends Node> i = temp.getNeighbors().iterator();
			while (i.hasNext()) {
				//dequeue
				Node neighborNode = i.next();
				if (_graphDist.get(neighborNode) != null) {
					if(_graphDist.get(temp) > _graphDist.get(neighborNode)) {
						finalPath.add(neighborNode);
						System.out.println("naighbore nodes finalPath: "+ finalPath);

						queue.add(neighborNode);	

						//if the final node is found
						if(neighborNode.equals(s)) {
							System.out.println("You found the final path, congratulations!!!");
							System.out.println("look at final path: "+ finalPath);
							return finalPath;
						}
						break;

					}
                }
			}			
		}
		//if the final node path is not found
		return null;	
	}
}
	

