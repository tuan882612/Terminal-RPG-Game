package Util;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtil {
    public static ArrayList<String> LoadData(String fileName) throws FileNotFoundException {
        Scanner data =  new Scanner(new File(fileName)).useDelimiter("\n");
        ArrayList<String> load = new ArrayList<>();

        while(data.hasNext()){
            load.add(data.next());
        }

        return load;
    }

    public static void ExportData(ArrayList<Object> data) throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.print("\nEnter filename: ");

        String fileName = "./Logs/";
        fileName += input.nextLine().replaceAll("\\s+","_");

        FileWriter temp = new FileWriter(fileName+".log", true);
        PrintWriter export = new PrintWriter(temp);

        data.forEach(obj -> export.println(toDataFormat(obj)));
        export.close();
    }

    public static String toDataFormat(Object object){
        return object.toString().replaceAll("\\s+",",");
    }
}
