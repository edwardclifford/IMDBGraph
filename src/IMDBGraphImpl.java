import java.io.*;
import java.util.*;

public class IMDBGraphImpl implements IMDBGraph{
    
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
    }

    private Collection<? extends Node> buildNodes (File list) throws FileNotFoundException {
        
        Scanner scanner = new Scanner(list, "ISO-8859-1");

        System.out.println("Loading Database...");

        //Jump to beggining of names
        while (scanner.hasNext()) {
            if (scanner.nextLine().contains("Name\t\t\tTitles")) {
                scanner.nextLine();
                scanner.nextLine();
                break;
            }
        }

        //Loop through each actor

        scanner.useDelimiter("\t");
        String actorName;
        String entry; 

        while (scanner.hasNextLine()) {
            actorName = scanner.next();
            //System.out.println(actorName + ":");
            //
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
                //System.out.println(title);
            }
        }
        System.out.println("Done");
        return null; 
    }

	public Collection<? extends Node> getActors () {
        return null;
    }

	public Collection<? extends Node> getMovies () {
        return null;
    }

	public Node getMovie (String name) {
        return null;
    }
    
	public Node getActor (String name) {
        return null;
    }

    public static void main (String[] args) {
        try {
            //IMDBGraph graph = new IMDBGraphImpl("/home/ted/Desktop/B_Term/CS/IMDBGraph/src/actors_test.list", "/home/ted/Desktop/B_Term/CS/IMDBGraph/src/actresses_test.list");
            IMDBGraph graph = new IMDBGraphImpl("/home/ted/Desktop/B_Term/CS/IMDBGraph/data/actors.list", "/home/ted/Desktop/B_Term/CS/IMDBGraph/data/actresses.list");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}

