/*
 * Creates a graph of actors and movies from two databases.
 * Run with at least 4Gb memory allocated to the JVM.
 * Devs: Ted Clifford and Marie Tessier
 * (C) 11.15.2019
 */

import java.io.*;
import java.util.*;

public class IMDBGraphImpl implements IMDBGraph{

    private static class ActorNode implements Node {
        public String _name;
        public Collection<MovieNode> _neighbors = new HashSet<MovieNode>();

        /**
        * Implements a node storing information about an actor
        * @param name the name of the actor
        */
        public ActorNode (String name) {
            _name = name;
        }
    
        /**
        * Getter for the name of the actor
        * @return the actor's name
        */
        public String getName () {
        return _name;
        }
    
        /**
        * Getter for the movies that the actor appeared in
        * @return a collection of MovieNodes
        */
        public Collection<MovieNode> getNeighbors () {
            return _neighbors;
        }
    
        @Override
        public boolean equals (Object o) {
            final Node other = (Node) o;
            return _name == other.getName();
        }
    
        @Override 
        public int hashCode () {
            //final String hashString = "" + _name.hashCode() + _neighbors.hashCode();
            //return hashString.hashCode();
            return _name.hashCode();
        }
    }

    private static class MovieNode implements Node {
        public String _name;
        public Collection<ActorNode> _neighbors = new HashSet<ActorNode>();

        /**
         * Implements a node storing information about a movie 
         */
        public MovieNode (String name) {
            _name = name;
        }
    
        /**
        * Getter for the name of the movie
        */
        public String getName () {
            return _name;
        }
    
        /**
        * Getter for a collection of the actors that star in the movie
        */
        public Collection<ActorNode> getNeighbors () {
            return _neighbors;
        }
    
        @Override
        public boolean equals (Object o) {
            final Node other = (Node) o;
            return _name == other.getName(); 
        }
    
        @Override 
        public int hashCode () {
            //final String hashString = "" + _name.hashCode() + _neighbors.hashCode();
            //return hashString.hashCode();
            return _name.hashCode();
        }
        
    }     
    /**
     * Maps an actor's name to his or her ActorNode containing the movies they starred in
     */
    private Map<String, MovieNode> _movieMap = new HashMap<String, MovieNode>();

    /**
     * Maps a movie name to its MovieNode containing the actors that starred in it
     */
    private Map<String, ActorNode> _actorMap = new HashMap<String, ActorNode>();

    /**
     * A collection of all the movies 
     */
    private Collection<MovieNode> _movieSet;

    /**
     * A collection of all the actors
     */
    private Collection<ActorNode> _actorSet;
    
    /**
     * Implements an IMDBGraph
     */
    public IMDBGraphImpl (String actorPath, String actressPath) throws IOException {
        
        final File actorList = new File(actorPath);
        final File actressList = new File(actressPath);

        try {
            parseDatabase(actorList);
            parseDatabase(actressList);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Adding nodes to sets...");
        //Update sets to include both actresses and actors
        _movieSet = new HashSet<MovieNode>(_movieMap.values());
        _actorSet = new HashSet<ActorNode>(_actorMap.values());
        System.out.println("Done.");
    }
    
    /**
     * Parses the IMDB, adding each actor and movies to the graph
     * @param database the IMDB file
     */
    private void parseDatabase (File database) throws FileNotFoundException {
        final long startTime = System.currentTimeMillis(); //V

        Scanner scanner = new Scanner(database, "ISO-8859-1");

        //Jump to beggining of names
        while (scanner.hasNext()) {
            if (scanner.nextLine().contains("Name\t\t\tTitles")) {
                scanner.nextLine();
                break;
            }
        }

        scanner.useDelimiter("\t");
        String actorName;
        String entry; 
        
        System.out.println("Reading database..."); //V
        int actorCounter = 0; //V
        int movieCounter = 0; //V

        //Loop through each actor
        while (scanner.hasNextLine()) {
            actorName = scanner.next();
            List<String> titleList = new LinkedList<String>();

            //Loop through each IMDB entry under an actor
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

                //Create title of movie that includes the year and removes tabs
                String title = entry.substring(0, entry.indexOf(")", entry.indexOf("(" + 4)) + 1);
                title = title.trim();

                //Only add video movies to list
                if (!(entry.contains("(TV)") || title.charAt(0) == '\"')) {
                    titleList.add(title);
                    movieCounter++; //V
                }
                
            }
            if (titleList.size() > 0) { //V
                    actorCounter++;
            }
            addToGraph(actorName, titleList);
        }

        final long endTime = System.currentTimeMillis(); //V
        System.out.println("Finished."); //V
        System.out.println("Added " + actorCounter + " actors."); //V
        System.out.println("Added " + movieCounter + " movies."); //V

        System.out.println("Execution time: " + (endTime - startTime) + "ms."); //V
        System.out.println("------------------------"); //V

    }

    /**
     * Adds an actor and list of movies to the graph
     * @param actor the actor
     * @param movies a list of movies
     */
    private void addToGraph (String actorName, List<String> movieList) {

        //Create new actor if one doesn't already exist
        if (movieList.size() > 0) {
            if (!(_actorMap.containsKey(actorName))) {
                _actorMap.put(actorName, new ActorNode(actorName)); 
            }
        } 

        //Build graph nodes for each actor/movie
        for (String movieTitle : movieList) {
            //Add movie mapping if one does not exist
            if (!(_movieMap.containsKey(movieTitle))) {
                _movieMap.put(movieTitle, new MovieNode(movieTitle));
            }

            //Add movie to actor's neighbors
            _actorMap.get(actorName)._neighbors.add(_movieMap.get(movieTitle));

            //Add actor to the movie's neighbors
            _movieMap.get(movieTitle)._neighbors.add(_actorMap.get(actorName));
        }
    }

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
}

