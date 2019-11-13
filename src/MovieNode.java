import java.util.HashSet;

public class MovieNode implements Node {
    
    public MovieNode (String name) {
        public String _name = name;
        public Collection<ActorNode> _neighbors = new HashSet<ActorNode>;
    }

    public String getName () {
       return _name;
    }

    public Collection<ActorNode> getNeighbors () {
        return _neighbors;
    }

}
