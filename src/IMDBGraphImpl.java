import java.io.IOException;
import java.util.Scanner;
import java.util.File;
import java.util.HashSet;
import java.util.regex.*;

public class IMDBGraphImpl {
    
    public IMDBGraphImpl (String actorPath, String actressPath) throws IOException {
        
        File actorList = new File(actorPath);
        File actressList = mew File(actressPath);
         
    }

    private Collection<Node> buildNodes (File list) {
        
        Scanner scanner = new Scanner(list, "ISO-8859-1")
        
        //Outer for loop will visit each actor
        //Create a new actor node
        //Build a list of all movies using inner loop
        //Add movies to actor
        //For each movie add actor to it, or create new movie node
 
    }
}

