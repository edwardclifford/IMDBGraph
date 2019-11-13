import java.util.*;

public class ActorNode implements Node {
    public String _name;
    public Collection<MovieNode> _neighbors = new HashSet<MovieNode>();

    public ActorNode (String name) {
        String _name = name;
    }

    public String getName () {
       return _name;
    }

    public Collection<MovieNode> getNeighbors () {
        return _neighbors;
    }

    /*
    @Override
    public boolean equals (Object o) {
        final ActorNode other = (ActorNode) o;
        return _name == other._name && _neighbors.equals(other._neighbors);
    }

    @Override 
    public int hashCode () {
        //final String hashString = "" + _name.hashCode() + _neighbors.hashCode();
        //return hashString.hashCode();
        return _name.hashCode();
    }
    */
}

