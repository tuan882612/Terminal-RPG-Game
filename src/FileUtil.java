import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class FileUtil {
    public static ArrayList<String> LoadData(String fileName) {
        Scanner data = null;

        try {
            data = new Scanner(new File(fileName));
        } catch (FileNotFoundException error) {
            System.out.println("Data file does not exist.");
            System.exit(1);
        }

        ArrayList<String> load = new ArrayList<>();

        while(data.hasNext()){
            load.add(data.nextLine());
        }

        return load;
    }

    public static void ExportData(Creature player) {
        String fileName = "./Data/Saved/";

        String name = player.toString().split(" ")[1];
        fileName += name.replaceAll("\\s+","_") + ".csv";

        if (fileValidation(name + ".csv")) {
            FileWriter temp;

            try {
                temp = new FileWriter(fileName);
            } catch (IOException error) {
                throw new RuntimeException(error);
            }

            PrintWriter export = new PrintWriter(temp);

            export.println(toDataFormat(player));
            export.close();
            System.out.println("\nPlayer saved successfully...\n");
        } else {
            System.out.println("\nInstance of Player already has already been saved.\n");
        }
    }

    public static boolean fileValidation(String find) {
        File[] data = new File("./Data/Saved").listFiles();

        for(File i: Objects.requireNonNull(data)){
            if (Objects.equals(i.toString().split("\\\\")[3], find)) return false;
        }

        return true;
    }

    public static String toDataFormat(Object object){
        return object.toString().replaceAll("\\s+",",");
    }
}
