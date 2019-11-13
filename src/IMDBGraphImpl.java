import java.io.*;
import java.util.*;

public class IMDBGraphImpl implements IMDBGraph{
    
    private Map<String, MovieNode> _movieMap = new HashMap<String, MovieNode>();
    private Map<String, ActorNode> _actorMap = new HashMap<String, ActorNode>();

    private Collection<MovieNode> _movieSet = new HashSet<MovieNode>();
    private Collection<ActorNode> _actorSet = new HashSet<ActorNode>();

    public IMDBGraphImpl (String actorPath, String actressPath) throws IOException {
        
        File actorList = new File(actorPath);
        File actressList = new File(actressPath);

        System.out.println("Opening databases...");
        try {
            buildNodes(actorList);
            buildNodes(actressList);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Databases read.");
        //Update sets to include both actresses and actors
        _movieSet = new HashSet<MovieNode>(_movieMap.values());
        _actorSet = new HashSet<ActorNode>(_actorMap.values());

    }

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

        scanner.useDelimiter("\t");
        String actorName;
        String entry; 
        
        //Loop through each actor
        while (scanner.hasNextLine()) {
            actorName = scanner.next();
            List<String> titleList = new ArrayList<String>();

            System.out.println("Getting actor: " + actorName);
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

                String title = entry.substring(0, entry.indexOf(")") + 1);
                title = title.trim();

                //Only add video movies to list
                if (!(entry.contains("(TV)") || (title.charAt(0) == '\"'))) {
                    titleList.add(title);
                }
            }
            
            if (titleList.size() > 0) {
                _actorMap.put(actorName, new ActorNode(actorName)); 
            } 

            //Build graph nodes for each actor/movie
            for (String movie : titleList) {
                System.out.println("Adding movie: " + movie); 
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
    }

	public Collection<? extends Node> getActors () {
        return _actorSet;
    }

	public Collection<? extends Node> getMovies () {
        return _movieSet;
    }

	public Node getMovie (String name) {
        return _movieMap.get(name);
    }
    
	public Node getActor (String name) {
        return _actorMap.get(name);
    }

    public static void main (String[] args) {
        try {
            IMDBGraph graph = new IMDBGraphImpl("/home/ted/Desktop/B_Term/CS/IMDBGraph/src/actors_test.list", "/home/ted/Desktop/B_Term/CS/IMDBGraph/src/actresses_test.list");
            //IMDBGraph graph = new IMDBGraphImpl("/home/ted/Desktop/B_Term/CS/IMDBGraph/data/actors.list", "/home/ted/Desktop/B_Term/CS/IMDBGraph/data/actresses.list");
 
        System.out.println(graph.getActors());
        
        System.out.println(graph.getMovies());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}

