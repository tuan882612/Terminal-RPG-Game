import Util.GameUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class CombatSystem {
    public static void initialization(ArrayList<Object> players){
        Scanner input = new Scanner(System.in);

        Player player1 = (Player) players.get(0);
        Position Pos1 = new Position(12,0, "O");

        Player player2 = (Player) players.get(1);
        Position Pos2 = new Position(12,24, "D");

        int dex1 = player1.getDexterity() + GameUtil.RollDice("d20");
        int dex2 = player2.getDexterity() + GameUtil.RollDice("d20");

        if (dex1 == dex2){
            while (dex1 == dex2){
                dex1 = player1.getDexterity() + GameUtil.RollDice("d20");
                dex2 = player2.getDexterity() + GameUtil.RollDice("d20");
            }
        } else if (dex1 < dex2){
            player1 = (Player) players.get(1);
            player2 = (Player) players.get(0);
        }

        GameMap GMap = new GameMap();
        GMap.SetPosition(Pos1);
        GMap.SetPosition(Pos2);

        boolean GameState = false;

        while (!GameState){
            Movement(GMap, Pos1, player1);
            Movement(GMap, Pos2, player2);

            TurnOptions(player1, player2, Pos1, Pos2);
            TurnOptions(player2, player1, Pos2, Pos1);

            System.out.println("\n1. Yes ");
            System.out.println("2. No ");
            System.out.print("Do you want to continue? ");
            int option = input.nextInt();

            switch (option) {
                case 1 -> System.out.println("\nGame will continue.");
                case 2 -> GameState = true;
            }
        }
    }

    public static void Movement(GameMap map, Position pos, Player player){
        Scanner input = new Scanner(System.in);

        System.out.println("\nTurn: " + player.getName()+"\n");
        boolean exit = false;
        int moves = 5;

        while (moves > 0 && !exit){
            map.DisplayMap();

            System.out.println("\nNumbers of moves left: " + moves);
            GameUtil.DisplayMenu("movement");

            int choice = input.nextInt();

            switch (choice){
                case 1 -> {
                    System.out.print("\nEnter X: ");
                    int x = input.nextInt();

                    System.out.print("Enter Y: ");
                    int y = input.nextInt();

                    y = (y > 24)? 24: Math.max(y, 0);
                    x = (x > 24)? 24: Math.max(x, 0);

                    map.UpdatePosition(x, y, pos);
                }
                case 2 -> exit = true;
            }
            moves--;
        }
    }

    public static void TurnOptions(Player player1, Player player2, Position pos1, Position pos2){
        Scanner input = new Scanner(System.in);

        if(!player1.getDisarmed() && pos1.Verify(pos2)) {
            GameUtil.DisplayMenu("turnOptions");
            int option = input.nextInt();

            switch (option) {
                case 1 -> player1.attack(player2);
                case 2 -> player1.disarm(player2);
            }
        } else {
            System.out.println(player1.getName()+" has been disarmed.");
            System.out.println(player1.getDisarmedCount()+" rounds left");

            player1.setDisarmedCount(player1.getDisarmedCount()-1);

            if(player1.getDisarmedCount() == 0){
                player1.setDisarmed(true);
            }
        }
    }
}