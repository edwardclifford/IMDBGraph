import java.util.HashSet;

public class ActorNode implements Node {

    public ActorNode (String name) {
        public String _name = name;
        public Collection<MovieNode> _neighbors = new HashSet<MovieNode>;
    }

    public String getName () {
       return _name;
    }

    public Collection<MovieNode> getNeighbors () {
        return _neighbors;
    }
}

