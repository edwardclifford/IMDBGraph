import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
/*
 * Marie Tessier and Edward Clifford 
 * 
 * This class uses Breath First Search Algorithm the search through a graph 
 * and find the shortest path.
 */

public class GraphSearchEngineImpl implements GraphSearchEngine {

	//create a hash map that holds a value and a node
	private HashMap<Integer, Node> _graphDist; 
	
	public List<Node> findShortestPath (Node s, Node t) {
		return null;
		
	}
	
	private void bfs(Node s, Node t) {
		//creating the queue 
		LinkedList<Node> queue = new LinkedList<Node>();
		
		//counter for values
		int value = 0;
		
		//adding initial node to queue and setting value to 0
		queue.add(s);
		_graphDist.put(value, s);
		
		//iterate throught the queue
		while (queue.size() != 0) {
			
			//dequeue node
			Node temp = queue.poll();
						
			//creating an iterator to look through the nodes
			Iterator<? extends Node> i = temp.getNeighbors().iterator();
					
			
			
		}
		
		
	}
	
}
