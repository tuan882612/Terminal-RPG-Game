import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class GameUtil {
    public static void displayMenu(String type){
        switch (type) {
            case "main" -> {
                System.out.println("\n1. Start Game");
                System.out.println("2. Create Characters");
                System.out.println("3. Load Character");
                System.out.println("4. Save Character");
                System.out.println("5. Exit");
                System.out.print("Enter an option: ");
            }
            case "gameOptions" -> {
                System.out.println("\n1. Play with Random Monsters");
                System.out.println("2. Play with Players Only (PvP)");
                System.out.println("3. Back");
                System.out.print("Enter an option: ");
            }
            case "character" -> {
                System.out.println("\n1. Manual stats");
                System.out.println("2. Random Stats");
                System.out.print("Manual or Random Stats? ");
            }
            case "stats" -> {
                System.out.println("1. Add STR");
                System.out.println("2. Add DEX");
                System.out.println("3. Add CON");
                System.out.println("4. Reset");
                System.out.println("5. Finish");
                System.out.print("Enter an option: ");
            }
            case "movement" -> {
                System.out.println("\n1. move");
                System.out.println("2. exit");
                System.out.print("Enter an option: ");
            }
            case "turnOptions" -> {
                System.out.println("\n1. Attack ");
                System.out.println("2. Disarm");
                System.out.print("Enter an option: ");
            }
            case "playerCountError" -> {
                System.out.println("\nYou do not have enough Players to play.");
                System.out.println("Please create another Character.");
            }
        }
    }

    public static void displaySubMenu(String type,
                                      int Str,
                                      int Dex,
                                      int Con,
                                      int count){
        if (Objects.equals(type, "stats")){
            System.out.println("\nSTR: " + Str);
            System.out.println("DEX: " + Dex);
            System.out.println("CON: " + Con);
            System.out.println("Remaining: " + count + "\n");
        }
    }

    public static int rollDice(String input){
        String[] preString = input.split("d");

        try {
            String[] data;
            try {
                data = preString[1].split("\\+");
            } catch (NumberFormatException error){
                data = preString[1].split("");
            }

            int constant = data.length != 1? parseInt(data[1]) : 0;
            int num = !(preString[0].isEmpty())? parseInt(preString[0]) : 1;
            int sides = parseInt(data[0]);
            int res = 0;

            for(int i = 0; i < num; i++){
                int n = new Random().nextInt(sides)+1;
                res += n;
            }

            return res + constant;
        } catch (NumberFormatException error) {
            System.out.println("Invalid input") ;
            return -1;
        }
    }

    public static boolean validateName(String name) {
        boolean capLet = Character.isUpperCase(name.charAt(0));
        boolean special = name.matches("^[a-zA-Z]*$");
        int count = 0;
        String msg1 = "";
        String msg2 = "";

        if (!capLet) {
            count++;
            msg1 = "\n" + count + ". The first letter of your name is not capitalized.";
        }

        if (!special) {
            count++;
            msg2 = "\n" + count + ". Your name contains a special character or number.";
        }

        if (!capLet || !special){
            System.out.println(msg1 + msg2);
            return false;
        }
        return true;
    }

    public static boolean validatePlayers(ArrayList<Creature> players, String name) {
        for(Creature creature : players){
            if (creature.toString().split(" ")[1].equals(name)) return false;
        }
        return true;
    }
}
