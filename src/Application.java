import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Application {
    public static ArrayList<Creature> players;

    public static void App() {
        Scanner input = new Scanner(System.in);
        players = new ArrayList<>();

        GameUtil.displayMenu("main");
        int choice = input.nextInt();

        while (choice != 5){
            switch (choice) {
                case 1 -> {
                    GameUtil.displayMenu("gameOptions");
                    int gameChoice = input.nextInt();

                    while (gameChoice != 3) {
                        switch (gameChoice) {
                            case 1 -> {
                                if (players.size() >= 1) {
                                    System.out.println("\nPlaying with Monsters...\n");
                                } else {
                                    GameUtil.displayMenu("playerCountError");
                                }
                            }
                            case 2 -> {
                                if (players.size() == 2) {
                                    System.out.println("\nStarting the game...\n");
                                    CombatSystem.initialization(players);
                                } else {
                                    GameUtil.displayMenu("playerCountError");
                                }
                            }
                        }
                        GameUtil.displayMenu("gameOptions");
                        gameChoice = input.nextInt();
                    }
                }
                case 2 -> {
                    if (players.size() < 2) {
                        System.out.println("\nCreating character...\n");
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
                    } else {
                        System.out.println("\nYou have create the maximum amount of players.");
                        System.out.println("Please Start or Exit the game.");
                    }
                }
                case 3 -> {
                    File[] filesDirectory = new File("./Data/Saved").listFiles();

                    if (Objects.requireNonNull(filesDirectory).length > 0 && players.size() < 2) {
                        System.out.println("\nLoad Character...\n");
                        boolean loaded = false;
                        int index = 0;

                        for (File file : filesDirectory) {
                            try {
                                Scanner loadData = new Scanner(new File(String.valueOf(file)));
                                while (loadData.hasNext()){
                                    index++;
                                    System.out.println(
                                            index + ". " +
                                            loadData.next().replaceAll(",", " "));
                                }
                            } catch (FileNotFoundException error) {
                                System.out.println(error.getMessage());
                            }
                        }
                        System.out.print("Enter an option to load player: ");

                        while (!loaded) {
                            try {
                                int loadChoice = input.nextInt();

                                Scanner selectedFile = new Scanner(
                                        new File(String.valueOf(filesDirectory[loadChoice-1])));

                                String[] data = selectedFile.next().split(",");
                                if(GameUtil.validatePlayers(players, data[1])) {
                                    String[] weaponData = data[7].split("-");

                                    players.add(new Player(
                                            data[1],
                                            new Weapon(weaponData[0], weaponData[1], parseInt(weaponData[2])),
                                            parseInt(data[2]),
                                            parseInt(data[3]),
                                            parseInt(data[4]),
                                            parseInt(data[5]),
                                            parseInt(data[6])));

                                    System.out.println("\nPlayer loaded successfully...\n");
                                } else {
                                    System.out.println("\nPlayer already exist and can not be loaded.\n");
                                }
                                loaded = true;
                            } catch (IndexOutOfBoundsException | InputMismatchException | FileNotFoundException exception) {
                                System.out.println(exception.getMessage());
                                System.out.println("\nPlease enter a valid choice.\n");

                                for (File file : filesDirectory) {
                                    try {
                                        Scanner loadData = new Scanner(new File(String.valueOf(file)));
                                        while (loadData.hasNext()){
                                            index++;
                                            System.out.println(index + ". " + loadData.next().replaceAll(",", " "));
                                        }
                                    } catch (FileNotFoundException error) {
                                        System.out.println(error.getMessage());
                                    }
                                }
                                index = 0;
                                System.out.print("Enter an option to load player: ");
                            }
                        }
                    } else {
                        System.out.println("\nNo players available to load or maximum number of players have been created.\n");
                    }
                }
                case 4 -> {
                    if (players.size() > 0) {
                        System.out.println("\nSave Character...\n");
                        boolean saved = false;
                        int index = 0;

                        System.out.println();
                        for (Object player : players) {
                            index++;
                            System.out.println(index + ". " + player);
                        }
                        System.out.print("Enter an option to save player: ");

                        while (!saved) {
                            try {
                                int saveChoice = input.nextInt();
                                Player player = (Player) players.get(saveChoice - 1);

                                if (FileUtil.fileValidation(player.Name)) {
                                    FileUtil.ExportData(player);
                                } else {
                                    System.out.println("\nInstance of Player already has already been saved.\n");
                                }

                                saved = true;
                            } catch (IndexOutOfBoundsException | InputMismatchException exception) {
                                System.out.println("\nPlease enter a valid choice.");

                                for (Object player : players) {
                                    index++;
                                    System.out.println(index + ". " + player);
                                }
                                index = 0;
                                System.out.print("Enter an option to save player: ");
                            }
                        }
                    } else {
                        System.out.println("\nNo players have been created.\n");
                    }
                }
                default -> System.out.println("\nPlease enter a valid choice.\n");
            }

            GameUtil.displayMenu("main");
            choice = input.nextInt();
        }

        System.out.println("\nExiting the game...");
    }

    public static Player CharacterCreation() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter Character Name: ");

        String name = "";
        name += input.nextLine().replaceAll("\\s+","_");
        boolean validName = GameUtil.validateName(name);

        while(!validName){
            System.out.print("\nEnter Character Name: ");
            name = "";
            name += input.nextLine().replaceAll("\\s+","_");
            validName = GameUtil.validateName(name);
        }

        GameUtil.displayMenu("character");
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
                    GameUtil.displaySubMenu("stats", Strength, Dexterity, Constitution, count);
                    GameUtil.displayMenu("stats");
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
                GameUtil.displaySubMenu("stats", Strength, Dexterity, Constitution, count);
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

                    if(statChoice == 1) Strength += finalStat;
                    if (statChoice == 2) Dexterity += finalStat;
                    if (statChoice == 3) Constitution += finalStat;
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
