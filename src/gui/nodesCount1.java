package gui;

import java.io.*;
import java.util.Scanner;

public class nodesCount1 {
    public static void main(String[] args) {
        // Testing only
       nodesCount1.find();
       
    }

    public static int find() {
    	 File f = new File("d:/output.txt");
    	 String searchString= "node.ndf";
        boolean result = false;
        Scanner in = null;
        int count=0;
        try {
            in = new Scanner(new FileReader(f));
            while(in.hasNextLine() ) {
                result = in.nextLine().indexOf(searchString) >= 0;
                count++;
            }
        }
        catch(IOException e) {
            e.printStackTrace();      
        }
        finally {
            try { in.close() ; } catch(Exception e) { /* ignore */ }  
        }
        
        System.out.printf("Result of searching for %s in %s was %d\n", searchString, f.getName(),+count);
        return count;
        
    }
}