import Util.FileUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static Util.GameUtil.DisplayMenu;
import static Util.GameUtil.DisplaySubMenu;
import static java.lang.Integer.parseInt;

public class Application {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);

        DisplayMenu("main");
        ArrayList<Object> players = new ArrayList<>();
        int choice = input.nextInt();
        int count = 2;

        while (choice != 3){
            switch (choice) {
                case 1 -> {
                    if (count == 0) {
                        System.out.println("\nStarting the game...\n");
                        CombatSystem.initialization(players);
                    } else {
                        System.out.println("\nYou do not have enough Players to play.");
                        System.out.println("Please create another Character.");
                    }
                }
                case 2 -> {
                    if (count > 0) {
                        System.out.println("\nCreating character\n");
                        Player character = null;
                        int choice1 = 0;

                        while (choice1 != 1){
                            character = CharacterCreation();
                            character.Summary();

                            System.out.println("\n1. Continue with Character");
                            System.out.println("2. ReCreate Character again");
                            System.out.print("Select option: ");

                            choice1 = input.nextInt();
                            System.out.println();
                        }

                        players.add(character);
                        count--;
                    } else {
                        System.out.println("\nYou have create the maximum amount of players.");
                        System.out.println("Please Start or Exit the game.");
                    }
                }
                default -> System.out.println("\nPlease enter a valid choice.\n");
            }

            DisplayMenu("main");
            choice = input.nextInt();
        }

        if (count < 2) {
            System.out.println("\n1. yes\n2. no");
            System.out.print("Do you want to export your Player data? ");

            if (input.nextInt() == 1) {
                FileUtil.ExportData(players);
            }
        }

        System.out.println("\nExiting the game...");
    }

    public static Player CharacterCreation() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter Character Name: ");

        String name = "";
        name += input.nextLine().replaceAll("\\s+","_");

        DisplayMenu("character");
        int choice = input.nextInt();
        System.out.println();

        int Strength = 0;
        int Dexterity = 0;
        int Constitution = 0;
        int count = 10;

        switch (choice) {
            case 1 -> {
                boolean exit = false;

                while(count > 0 && !exit){
                    DisplaySubMenu("stats", Strength, Dexterity, Constitution, count);
                    DisplayMenu("stats");
                    int statChoice = input.nextInt();

                    switch (statChoice){
                        case 1 -> Strength++;
                        case 2 -> Dexterity++;
                        case 3 -> Constitution++;
                        case 4 -> {
                            Strength = 0;
                            Dexterity = 0;
                            Constitution = 0;
                            count = 10;
                        }
                        case 5 -> exit = true;
                    }
                    if (statChoice < 5 && statChoice != 4) count--;
                }
                DisplaySubMenu("stats", Strength, Dexterity, Constitution, count);
            }
            case 2 -> {
                int finalStat;

                while(count != 0){
                    int randStat = new Random().nextInt(2);
                    int statChoice = new Random().nextInt(0,3)+1;

                    if(count - randStat < 0){
                        int error = Math.abs(count - randStat);

                        count -= randStat + error;
                        finalStat = randStat - error;
                    } else {
                        count = count - randStat;

                        finalStat = randStat;
                    }

                    if(statChoice == 1){
                        Strength += finalStat;
                    }
                    if (statChoice == 2) {
                        Dexterity += finalStat;
                    }
                    if (statChoice == 3){
                        Constitution += finalStat;
                    }
                }
            }
            default -> System.out.println("Invalid input");
        }

        Weapon weapon = WeaponCreation();
        return new Player(name, weapon, 50+Constitution, 15+Dexterity, Strength, Dexterity, Constitution);
    }

    public static Weapon WeaponCreation() {
        Scanner input = new Scanner(System.in);

        ArrayList<String> choices = null;

        try {
            choices = FileUtil.LoadData("./Data/weapons.csv");
        } catch (FileNotFoundException error) {
            System.out.println("Data file does not exist.");
            System.exit(1);
        }

        int count = 1;

        for(String row : choices) {
            String[] rowParsed = row.split(",");

            System.out.println(
                    count + ". " + rowParsed[0] +
                    ", Damage: " + rowParsed[1] +
                    ", Bonus To-Hit: " + rowParsed[2]);
            count += 1;
        }
        System.out.print("Select weapon: ");

        int option = input.nextInt()-1;
        String[] parsed = choices.get(option).split(",");

        return new Weapon(parsed[0], parsed[1], parseInt(parsed[2]));
    }
}
