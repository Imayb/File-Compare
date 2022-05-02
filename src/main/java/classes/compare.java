package classes;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class compare {

    public static long filesCompareByLine(Path path1, Path path2) throws IOException {
        try (BufferedReader bf1 = Files.newBufferedReader(path1);
             BufferedReader bf2 = Files.newBufferedReader(path2)) {

            long lineNumber = 1;
            String line1 = "", line2 = "";
            while ((line1 = bf1.readLine()) != null) {
                line2 = bf2.readLine();
                if (line2 == null || !line1.equals(line2)) {
                    return lineNumber;
                }
                lineNumber++;
            }
            if (bf2.readLine() == null) {
                return -1;
            }
            else {
                return lineNumber;
            }
        }
    }
    //this compares the two strings word by word
    public static void filesCompareByWord(String string1, String string2){

        //splits both strings into an array of strings separated by a space
        String[] stringArray1 = string1.split(" ");
        String[] stringArray2 = string2.split(" ");
        ArrayList<String> arrayL1 = convert(stringArray1);
        ArrayList<String> arrayL2 = convert(stringArray2);



        //this arraylist collects the similar words
        ArrayList<String> temporaryWords = new ArrayList<String>();

        //this for loop creates an arrayList of the similar words
        for(int i = 0; i < arrayL2.size(); i++){
            if(arrayL1.contains(arrayL2.get(i)))
                //   System.out.println("Something else");
                if(!temporaryWords.contains(arrayL2.get(i))){
                    temporaryWords.add(arrayL2.get(i));
                    //System.out.println(temporaryWords.get(i));
                }
           // System.out.println(temporaryWords.size());

        }
//        for(int i = 0; i < temporaryWords.size(); i++){
//            System.out.println(temporaryWords.get(i) );
//        }

        //This method find the percentage of similarity between the two documents
        double sum = percentage(temporaryWords.size(),arrayL2.size());
        createReport(arrayL2, temporaryWords,temporaryWords.size());



    }
    public static ArrayList<String> convert(String[] str){
        ArrayList<String> array = new ArrayList<>();

        for(int i = 0;i< str.length; i++){
            array.add(String.valueOf(str[i]));

        }

        return(array);
    }

    //this method creates a file with the report information for the compare
    public static void createReport(ArrayList arr, ArrayList temp, double length){
        String fileName= "C:\\Users\\monic\\Documents\\ComparisonReport.txt";

        try{


            File myReport = new File(fileName);


            if (myReport.createNewFile()){

            } else if(myReport.exists()){
               while(myReport.exists()){
                   int i = 1;
                   fileName = "ComparisonReport" + i + ".txt";
                   myReport = new File(fileName);
                   i++;
               }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write("ComparisonReport\n");
            for(int i = 0; i < temp.size(); i++){
                myWriter.write(String.valueOf(temp.get(i)) );
            }
//            double percent = percentage(length, arr.size());
//            myWriter.write("Percentage similarity is " + percent );
            double percent = Math.round(percentage(length, arr.size()) *100)/100.0; // ????
            myWriter.write("Percentage similarity is " + percent + " %");
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
           // System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }

    // ???????
    public static double percentage(double shortList, double longList){
        double percent = (shortList/longList) * 100;

        System.out.print(percent);
        return percent;
    }

}
