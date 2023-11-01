import java.util.Scanner;
import java.util.ArrayList;

public class PhotoLineups {

   public static String formatter(ArrayList<String> old){
      String temp = old.get(0);
      for (int i = 1; i < old.size(); i++){
         temp += (", " + old.get(i));
      }
      return temp;
   }

   public static void printAllPermutations(ArrayList<String> permList, ArrayList<String> nameList) {
      //base case
      if (nameList.isEmpty()){ // If namelist is empty
         System.out.println(formatter(permList));
      }

      //recursive case
      for (int i = 0; i < nameList.size(); i++){ // looping through namelist...
         
         ArrayList<String> permTemp = new ArrayList<String>(permList); // create empty array
         ArrayList<String> temp = new ArrayList<String>(nameList); // Create duplicant nameList
         permTemp.add(nameList.get(i));
         temp.remove(nameList.get(i));
         printAllPermutations(permTemp, temp); //run it back

      }
      //System.out.println(permList); //print the permlist
      
   }

   public static void main(String[] args) {
      Scanner scnr = new Scanner(System.in);
      ArrayList<String> nameList = new ArrayList<String>();
      ArrayList<String> permList = new ArrayList<String>();
      String name;

      name = scnr.next();
      while (!name.equals("-1")){
         nameList.add(name);
         name = scnr.next();
      }
      scnr.close();

      printAllPermutations(permList, nameList);
   }
}
