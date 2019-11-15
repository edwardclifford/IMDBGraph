/*
 * Marie Tessier and Edward Clifford 
 * 
 * This class uses Breath First Search Algorithm the search through a graph 
 * and find the shortest path.
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
		if (!(bfs(s, t))) {
			return null;
		}
		List<Node> finalList = searchBackward(s, t);
		Collections.reverse(finalList);
		return finalList;
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
			final Node temp = queue.poll();

			//creating an iterator to look through the nodes
			Iterator<? extends Node> i = temp.getNeighbors().iterator();
			while (i.hasNext()) {

				//dequeue
				final Node neighborNode = i.next();
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
		
		//adding target node to queue and setting value to 0
		queue.add(t);
		finalPath.add(t);
		
		//iterate through the queue
		while (queue.size() != 0) {

			//dequeue node
			final Node temp = queue.poll();

			//creating an iterator to look through the nodes
			Iterator<? extends Node> i = temp.getNeighbors().iterator();
			while (i.hasNext()) {
				//dequeue
				final Node neighborNode = i.next();
				if (_graphDist.get(neighborNode) != null) {
					if(_graphDist.get(temp) > _graphDist.get(neighborNode)) {
						finalPath.add(neighborNode);

						queue.add(neighborNode);	

						//if the final node is found
						if(neighborNode.equals(s)) {
							System.out.println("You found the final path, congratulations!!!");
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
	

