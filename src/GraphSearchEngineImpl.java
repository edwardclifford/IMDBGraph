import java.util.HashMap;

import LRUCache.Element;

/*
 * Marie Tessier and Edward Clifford 
 * 
 * This class uses Breath First Search Algorithm the search through a graph 
 * and find the shortest path.
 */

public class GraphSearchEngineImpl implements GraphSearchEngine {
	
	//creat a hash map that holds a value and a node
	private HashMap<int, Node> _graphDist; 
	
	public List<Node> findShortestPath (Node s, Node t) {
		
	}
	
	private void bfs(Node s, Node t) {
		//creating the queue 
		LinkedList<Node> queue = new LinkedList<Node>();
		
		//counter for values
		value = 0;
		
		//adding initial node to queue and setting value to 0
		queue.add(s);
		_graphDist.put(value, s);
		
		//iterate throught the queue
		while (queue.size() != 0) {
			
			//dequeue node
			temp = queue.poll();
			
			//creating an itorator to look through the nodes
			Itorator<Node> i = temp.getNeighbors.listItorator();
			
			
			
		}
		
		
	}
	
}
