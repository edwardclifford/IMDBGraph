import java.io.*;
import java.util.*;

public class IMDBGraphImpl implements IMDBGraph{
    
    private Map<String, MovieNode> _movieMap = new HashMap<String, MovieNode>();
    private Map<String, ActorNode> _actorMap = new HashMap<String, ActorNode>();

    private Collection<MovieNode> _movieSet;
    private Collection<ActorNode> _actorSet;
    
    /**
     * Implements an IMDBGraph
     */
    public IMDBGraphImpl (String actorPath, String actressPath) throws IOException {
        
        File actorList = new File(actorPath);
        File actressList = new File(actressPath);

        try {
            buildNodes(actorList);
            buildNodes(actressList);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Update sets to include both actresses and actors
        _movieSet = new HashSet<MovieNode>(_movieMap.values());
        _actorSet = new HashSet<ActorNode>(_actorMap.values());
        /*
        for (MovieNode movie : _movieMap.values()) {
            System.out.println(movie._name);
            _movieSet.add(movie);
        }
        System.out.println("Got here");
        
        for (ActorNode actor : _actorMap.values()) {
            System.out.println(actor._name);
            _actorSet.add(actor);
        }
        */
    }
    
    /**
     * Parses the IMDB, adding each actor and movies to the graph
     * @param list the IMDB file
     */
    private void buildNodes (File list) throws FileNotFoundException {
        
        Scanner scanner = new Scanner(list, "ISO-8859-1");

        //Jump to beggining of names
        while (scanner.hasNext()) {
            if (scanner.nextLine().contains("Name\t\t\tTitles")) {
                scanner.nextLine();
                scanner.nextLine();
                break;
            }
        }

        System.out.println("Reading database...");
        scanner.useDelimiter("\t");
        String actorName;
        String entry; 
        
        //Loop through each actor
        while (scanner.hasNextLine()) {
            actorName = scanner.next();
            List<String> titleList = new LinkedList<String>();
             
            while (true) {
                //Check that file continues
                if (scanner.hasNextLine()) {
                    entry = scanner.nextLine();
                }
                else {
                    break;
                }

                //Check that actor has more movies
                if (entry.isEmpty()) {
                    break;
                }

                String title = entry.substring(0, entry.indexOf(")", entry.indexOf("(" + 4)) + 1);
                title = title.trim();

                //Only add video movies to list
                if (!(entry.contains("(TV)") || title.charAt(0) == '\"')) {
                    titleList.add(title);
                }
            }
            
            if (titleList.size() > 0) {
                _actorMap.put(actorName, new ActorNode(actorName)); 
            } 

            //Build graph nodes for each actor/movie
            for (String movie : titleList) {
                //Add movie mapping if one does not exist
                if (!(_movieMap.containsKey(movie))) {
                    _movieMap.put(movie, new MovieNode(movie));
                }

                //Add movie to actor's neighbors
                _actorMap.get(actorName)._neighbors.add(_movieMap.get(movie));

                //Add actor to the movie's neighbors
                _movieMap.get(movie)._neighbors.add(_actorMap.get(actorName));
            }
        }

        System.out.println("Data read.");
    }

    /**
     * Adds an actor and list of movies to the graph
     * @param actor the actor
     * @param movies a list of movies
     */
    /*
    private void addToGraph (String actorName, List movieList) {

        if (movie.size() > 0) {
            _actorMap.put(actorName, new ActorNode(actorName)); 
        } 

        //Build graph nodes for each actor/movie
        for (String movieTitle : movieList) {
            //Add movie mapping if one does not exist
            if (!(_movieMap.containsKey(movieTitle))) {
                _movieMap.put(movieTitle, new MovieNode(movieTitle));
            }

            //Add movie to actor's neighbors
            _actorMap.get(actorName)._neighbors.add(_movieMap.get(movie));

            //Add actor to the movie's neighbors
            _movieMap.get(movie)._neighbors.add(_actorMap.get(actorName));
    }
    */

    /**
     * Returns a collection of the actors in the graph
     */
	public Collection<? extends Node> getActors () {
        return _actorSet;
    }

    /**
     * Returns a collection of the movies in the graph
     */
	public Collection<? extends Node> getMovies () {
        return _movieSet;
    }

    /**
     * Returns the node object of a given movie
     */
	public Node getMovie (String name) {
        return _movieMap.get(name);
    }

    /**
     * Returns the node object of a given actor
     */  
	public Node getActor (String name) {
        return _actorMap.get(name);
    }

    /**
     * Testing, delete before handing in
     */
    public static void main (String[] args) {
        try {
            //IMDBGraph graph = new IMDBGraphImpl("/home/ted/Desktop/B_Term/CS/IMDBGraph/src/actors_test.list", "/home/ted/Desktop/B_Term/CS/IMDBGraph/src/actresses_test.list");
            IMDBGraph graph = new IMDBGraphImpl("/home/ted/Desktop/B_Term/CS/IMDBGraph/data/actors.list", "/home/ted/Desktop/B_Term/CS/IMDBGraph/data/actresses.list");
            //Testing with 10,000 lines files
            //IMDBGraph graph = new IMDBGraphImpl("/home/ted/Desktop/B_Term/CS/IMDBGraph/data/actors_first_10000_lines.list", "/home/ted/Desktop/B_Term/CS/IMDBGraph/data/actresses_first_10000_lines.list");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

