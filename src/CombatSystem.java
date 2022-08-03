import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import static java.lang.Integer.*;

public class CombatSystem {
    public static void initialization(ArrayList<Creature> players, int mode){
        Scanner input = new Scanner(System.in);

        switch (mode) {
            case 1 -> {
                ArrayList<String> temp = FileUtil.LoadData("./Data/monsters-1.csv");

                System.out.println("Enter number of Monsters in range of (1-"
                        + temp.size() + ") you want to play against");
                System.out.print("Number: ");

                int num = input.nextInt();

                if (num > temp.size()) {
                    System.out.println("\nNumber you enter was greater than maximum limit and defaulted to: " + temp.size());
                    num = temp.size();
                } else if (num < 1) {
                    System.out.println("\nNumber you enter was less than minimum limit and defaulted to: 1");
                    num = 1;
                }

                ArrayList<Creature> preMonsters = new ArrayList<>();

                for(String preData : temp.subList(0, num)){
                    String[] parsed = preData.split(",");

                    Monster monster = new Monster(parsed[0],
                                                parsed[1],
                                                parseInt(parsed[2]),
                                                parseInt(parsed[3]),
                                                parseInt(parsed[4]),
                                                parseInt(parsed[5]),
                                                parseInt(parsed[6]));
                    preMonsters.add(monster);
                }

                InitiativeUtil initUtil = new InitiativeUtil(preMonsters);
                ArrayList<Integer> index = initUtil.rollInitiative();

                ArrayList<Creature> monsters = new ArrayList<>();

                for(int i = 0; i < preMonsters.size(); i++) {
                    monsters.add(preMonsters.get(index.get(i)));
                }
                Player player1;

                if (players.size() > 1) {
                    System.out.println("\nThere was more than 1 player detected.\n");

                    for(Creature player : players) {
                        System.out.println(players.indexOf(player)+1 + ". " + player.getName());
                    }

                    System.out.println("Which character do you want to choose? ");
                    System.out.print("Enter an option: ");

                    num = input.nextInt();

                    if (num < 0) num = 0;
                    else if (num > 1) num = 1;

                    player1 = (Player) players.get(num-1);
                } else {
                    player1 = (Player) players.get(0);
                }

                Position playerPos = new Position(12,0, "P");
                ArrayList<Position> monsterPos = new ArrayList<>();

                int stackPosition = 2;

                for (Creature monster : preMonsters) {
                    monsterPos.add(new Position(stackPosition,24,
                            "M" + (preMonsters.indexOf(monster)+1) + " "));
                    stackPosition += 4;
                }
                GameMap GMap = new GameMap();

                GMap.SetPosition(playerPos);

                for (Position mPosition : monsterPos) {
                    GMap.SetPosition(mPosition);
                }

                boolean gameState1 = checkMonsterHealth(monsters);
                int round = 1;

                while (!gameState1 && player1.getHitPoints() > 0) {
                    movementPlayer(GMap, playerPos, player1);

                    for (Position monster : monsterPos) {
                        movementAiMonster(GMap, monster, playerPos);
                    }

                    GMap.DisplayMap();

                    for (Creature monster : monsters) {
                        Position currMonster = monsterPos.get(monsters.indexOf(monster));

                        System.out.println("\nTurn: " + monster.getName());
                        System.out.println("Player Icon: " + currMonster.getIcon());

                        if (currMonster.validatePosition(playerPos)) {
                            monster.attack(player1);
                        } else {
                            System.out.println("\nMonster not in range to attack player.");
                        }
                    }

                    int count = 1;

                    System.out.println();
                    for (Creature monster : monsters) {
                        Position currMonster = monsterPos.get(monsters.indexOf(monster));

                        if (currMonster.validatePosition(playerPos)) {
                            System.out.println(count + ". " + monster.getName());
                            count++;
                        }
                    }

                    if (count == 1){
                        System.out.println("\nMonsters not in range for player to attack.");
                    } else {
                        System.out.println("\nMonsters in range to attack.");
                        System.out.print("Enter an option: ");

                        int options = input.nextInt();

                        if (options < 1) options = 1;
                        if (options > count) options = count;

                        player1.attack(monsters.get(options));
                    }

                    gameState1 = checkMonsterHealth(monsters);

                    round++;
                    System.out.println("\nRound: " + round);

                    System.out.println("\n1. Yes ");
                    System.out.println("2. No ");
                    System.out.print("Do you want to continue? ");
                    int option = input.nextInt();

                    switch (option) {
                        case 1 -> System.out.println("\nGame will continue.");
                        case 2 -> gameState1 = true;
                    }
                }
            }
            case 2 -> {
                Player player1 = (Player) players.get(0);
                Position Pos1 = new Position(12,0, "O");

                Player player2 = (Player) players.get(1);
                Position Pos2 = new Position(12,24, "D");

                int dex1 = player1.getDexterity() + GameUtil.rollDice("d20");
                int dex2 = player2.getDexterity() + GameUtil.rollDice("d20");

                if (dex1 == dex2){
                    while (dex1 == dex2){
                        dex1 = player1.getDexterity() + GameUtil.rollDice("d20");
                        dex2 = player2.getDexterity() + GameUtil.rollDice("d20");
                    }
                } else if (dex1 < dex2){
                    player1 = (Player) players.get(1);
                    player2 = (Player) players.get(0);
                }

                GameMap GMap = new GameMap();
                GMap.SetPosition(Pos1);
                GMap.SetPosition(Pos2);

                boolean GameState2 = false;
                int round = 1;

                while (!GameState2) {
                    movementPlayer(GMap, Pos1, player1);
                    movementPlayer(GMap, Pos2, player2);

                    if (Pos1.validatePosition(Pos2)) {
                        turnOptions(player1, player2);
                        turnOptions(player2, player1);
                    } else {
                        System.out.println("\nPlayer not in range to play.");
                        System.out.println("Moving on to next round.");
                    }

                    round++;
                    System.out.println("\nRound: " + round);

                    System.out.println("\n1. Yes ");
                    System.out.println("2. No ");
                    System.out.print("Do you want to continue? ");
                    int option = input.nextInt();

                    switch (option) {
                        case 1 -> System.out.println("\nGame will continue.");
                        case 2 -> GameState2 = true;
                    }
                }
            }
        }
    }

    public static void movementAiMonster(GameMap map, Position monster, Position player) {
        int num = max(player.getX(), player.getY());
        map.UpdatePosition(new Random().nextInt(num), new Random().nextInt(num), monster);
    }

    private static boolean checkMonsterHealth(ArrayList<Creature> monsters) {
        for(Creature monster : monsters) {
            if (monster.getHitPoints() > 0) return false;
        }
        return true;
    }

    public static void movementPlayer(GameMap map, Position pos, Player player) {
        Scanner input = new Scanner(System.in);

        boolean exit = false;
        int moves = 5;

        while (moves > 0 && !exit) {
            map.DisplayMap();

            System.out.println("\nTurn: " + player.getName());
            System.out.println("Player Icon: " + pos.getIcon());
            System.out.println("\nNumbers of moves left: " + moves);

            GameUtil.displayMenu("movement");

            int choice = input.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("\nEnter X: ");
                    int y = input.nextInt()-1;

                    System.out.print("Enter Y: ");
                    int x = input.nextInt()-1;

                    y = (y > 24) ? 24 : Math.max(y, 0);
                    x = (x > 24) ? 24 : Math.max(x, 0);

                    map.UpdatePosition(x, y, pos);
                }
                case 2 -> exit = true;
            }
            moves--;
        }
    }

    public static void turnOptions(Player player1, Player player2){
        Scanner input = new Scanner(System.in);

        System.out.println("\nTurn: " + player1.getName());

        if (!player1.getDisarmed()) {
            GameUtil.displayMenu("turnOptions");

            int option = input.nextInt();

            switch (option) {
                case 1 -> player1.attack(player2);
                case 2 -> player1.disarm(player2);
            }
        } else {
            System.out.println("\n" + player1.getName() + " has been disarmed.");
            System.out.println(player1.getDisarmedCount() + " rounds left");

            player1.setDisarmedCount(player1.getDisarmedCount() - 1);

            if (player1.getDisarmedCount() == 0) player1.setDisarmed(false);
        }
    }
}