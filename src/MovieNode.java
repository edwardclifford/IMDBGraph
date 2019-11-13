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

    @Override
    public boolean equals (Object o) {
        final ActorNode other = (ActorNode) o;
        return _name == other._name && _neighbors.equals(other._neighbors);
    }

    @Override int hashCode () {
        final String hashString = "" + _name.hashCode() + _neighbors.hashCode();
        return hashString.hashCode();
    }
}
