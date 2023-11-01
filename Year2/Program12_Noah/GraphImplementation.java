// GraphImplementation.java - supplied code for graph assignment

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class GraphImplementation extends GraphAbstract {
    ArrayList<GraphNode> nodes = new ArrayList<GraphNode>(0);

    // Main entry point
    public static void main(String[] args) {

      // Instantiate code
        GraphImplementation impl = new GraphImplementation();
        Scanner input = new Scanner(System.in);
        String fileName = input.nextLine();
        String startCity = input.nextLine();
        // Read distances chart
        System.out.println("Reading Chart: " + fileName);
        impl.readGraph(fileName);
        System.out.println();
/*
        // Print depth first search
        System.out.println("Depth First Search:");
        impl.depthFirst(startCity);
        System.out.println();
        
        System.out.println("Breadth First Search:");
        impl.breadthFirst(startCity);
        System.out.println();
        */
    
    }

    

    // Reads mileage chart from CSV file
    public void readGraph(String filename) {
        ArrayList<String> lines = readFile(filename);
        String cities = lines.get(0);
        for (String s: cities.split(",")){
            GraphNode node = new GraphNode(s);
            nodes.add(node);
        }
        nodes.remove(0);

        for (int i = 1; i < lines.size(); i++){
            String connect = lines.get(i);
            String[] connections = connect.split(",");
            ArrayList<GraphEdge> cityconnect = new ArrayList<GraphEdge>();
            for (int j = 1; j < connections.length; j++){
                if (connections[j] != ""){cityconnect.add(new GraphEdge(getDex(nodes, connections[0]), j-1, Integer.parseInt(connections[j])));}
            }

            for (GraphEdge x: cityconnect){
                int e = 0;
                while (e < nodes.size()){
                    if (x.fromIndex == e){addOrder(nodes.get(e).edges, x);}
                    if (x.toIndex == e){addOrder(nodes.get(e).edges, x);}
                    e++;
                }
            }
        }
        printGraph(); // testing
    }

    public void addOrder(ArrayList<GraphEdge> list, GraphEdge x){ //HELPER
        int i = 0;
        if (list.isEmpty()){list.add(x);}
        if (list.contains(x)){
            // This is a dupe
        } else {
            while (list.get(i).compareTo(x) < 0 && i < list.size()-1){
                i++;
            }
            list.add(i, x);
        }
    }

    public int getDex(ArrayList<GraphNode> list, String item){ //HELPER
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).name.equals(item)){return i;}
        }
        return -1;
    }

    public void depthFirst(String startCity) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        depthFirst(getDex(nodes, startCity), temp);
    }

    // Recursive helper method
    public void depthFirst(int index, ArrayList<Integer> visited) {
        while (!visited.contains(index)){
            visited.add(index);
            System.out.println("Visited " + nodes.get(index).name);
            for (GraphEdge g: nodes.get(index).edges){
                if (!visited.contains(g.toIndex)){depthFirst(g.toIndex, visited);}
                else if (!visited.contains(g.fromIndex)){depthFirst(g.fromIndex, visited);}
            }
        }
    }

    public void breadthFirst(String startCity) {
        // YOUR CODE HERE
    }


    // Helper functions

    /**
     * Reads the contents of file to {@code ArrayList}
     * @param filename the file to read from
     * @return an ArrayList of the contents
     */
    static ArrayList<String> readFile(String filename) {
        ArrayList<String> contents = new ArrayList<>();
        try(Scanner reader = new Scanner(new File(filename))) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine().trim();
                if (!line.isEmpty())
                    contents.add(line);
            }
        } catch (IOException e) {
            System.err.println("Cannot read chart: " + filename);
        }
        return contents;
    }

    public void printGraph(){ // For testing purposes
        for (int i = 0; i < nodes.size(); i++){
            System.out.println(nodes.get(i).name + printEdges(nodes.get(i)));
        }

    }
    public String printEdges(GraphNode g){
        String ans = "-{";
        for (GraphEdge e: g.edges){
            ans += (e.fromIndex + " --> " + e.toIndex + ", ");
        }
        ans += "}";
        return ans;
    }

}
