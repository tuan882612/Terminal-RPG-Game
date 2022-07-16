package Util;

import java.util.Objects;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class GameUtil {
    public static void DisplayMenu(String type){
        switch (type) {
            case "main" -> {
                System.out.println("\n1. Start Game");
                System.out.println("2. Create Characters");
                System.out.println("3. Exit");
                System.out.print("Enter choice: ");
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
                System.out.print("Enter choice: ");
            }
            case "movement" -> {
                System.out.println("\n1. move");
                System.out.println("2. exit");
                System.out.print("Enter an option: ");
            }
        }
    }

    public static void DisplaySubMenu(String type,
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

    public static int RollDice(String input){
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
}
