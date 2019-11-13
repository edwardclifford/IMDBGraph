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
	private HashMap<Node, Integer> _graphDist; 

	public List<Node> findShortestPath (Node s, Node t) {
		//finds if there is a path to the node
		if (!bfs(s, t)) {
			return null;
		}

		return searchBackward(s, t);
	}



	private boolean bfs(Node s, Node t) {
		//creating the queue 
		Queue<Node> queue = new LinkedList<Node>();

		//counter for values
		int value = 0;
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
	
	private List<Node> searchBackward(Node s, Node t) {
		// TODO Auto-generated method stub
		//creating the queue 
		Queue<Node> queue = new LinkedList<Node>();
		
		//Shortest pat
		LinkedList<Node> finalPath = new LinkedList<Node>();
		
		//adding target node to queue and setting value to 0
		queue.add(t);
		
		//iterate through the queue
		while (queue.size() != 0) {

			//dequeue node
			Node temp = queue.poll();
			finalPath.add(temp);

			//creating an iterator to look through the nodes
			Iterator<? extends Node> i = temp.getNeighbors().iterator();
			while (i.hasNext()) {

				//dequeue
				Node neighborNode = i.next();
				if(_graphDist.get(temp) > _graphDist.get(neighborNode)) {
					
					finalPath.add(neighborNode);
					
					queue.add(neighborNode);	

					//if the final node is found
					if(neighborNode.equals(s)) {
						return finalPath;
					}
				}
			}			
		}
		//if the final node path is not found
		return null;
		
		
	}
}
	

