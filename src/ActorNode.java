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

