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
            player1 = ((Player) players.get(1));
            player2 = (Player) players.get(0);
        }

        GameMap GMap = new GameMap();
        GMap.SetPosition(Pos1);
        GMap.SetPosition(Pos2);

        boolean GameState = false;

        while (!GameState){
            movement(GMap, Pos1, player1);
            movement(GMap, Pos2, player2);

            if(player1.getDisarmed() == 0) {
                if (Pos1.Verify(Pos2)) {
                    System.out.println("\n1. Attack ");
                    System.out.println("2. Disarm");
                    System.out.print("Enter an option: ");
                    int option = input.nextInt();

                    switch (option) {
                        case 1 -> Attack(player1, player2);
                        case 2 -> Disarm(player1, player2);
                    }
                }
            } else {
                System.out.println(player1.getName()+" has been disarmed.");
                System.out.println(player1.getDisarmedCount()+" rounds left");

                player1.setDisarmedCount(player1.getDisarmedCount()-1);

                if(player1.getDisarmedCount() == 0){
                    player1.setDisarmed(1);
                }
            }

            if(player2.getDisarmed() == 0) {
                if (Pos1.Verify(Pos2)) {
                    System.out.println("\n1. Attack");
                    System.out.println("2. Disarm");
                    System.out.print("Enter an option: ");
                    int option = input.nextInt();

                    switch (option) {
                        case 1 -> Attack(player2, player1);
                        case 2 -> Disarm(player2, player1);
                    }
                }
            } else {
                System.out.println(player2.getName()+" has been disarmed.");
                System.out.println(player2.getDisarmedCount()+" rounds left");

                player2.setDisarmedCount(player2.getDisarmedCount()-1);

                if(player2.getDisarmedCount() == 0){
                    player2.setDisarmed(1);
                }
            }
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

    public static void movement(GameMap map, Position pos, Player player){
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

                    if (y < 0){
                        y = 0;
                    } else if (y > 24) {
                        y = 24;
                    }

                    if (x < 0){
                        x = 0;
                    } else if (x > 24) {
                        x = 24;
                    }

                    map.UpdatePosition(x, y, pos);
                }
                case 2 -> exit = true;
            }
            moves--;
        }
    }

    public static void Attack(Player attacker, Player defender){
        int damage = GameUtil.RollDice("d20") +
                attacker.getDexterity() +
                attacker.getWeapon().getBonus();

        if (defender.getHitPoints() != 0){
            if (damage >= defender.getArmorClass()){
                System.out.println(attacker.getName() + " attacks " + defender.getName() +
                        " with " + attacker.getWeapon().Name +
                        " (" + defender.getArmorClass() + " to hit)...HITS!");

                damage = GameUtil.RollDice(attacker.getWeapon().getDiceType()) +
                        attacker.getStrength();

                defender.takeDamage(damage);
            } else {
                System.out.println("\n"+attacker.getName() + " attacks " + defender.getName() +
                        " with " + attacker.getWeapon().Name +
                        " (" + defender.getArmorClass() + " to hit)...MISSES!");
            }
        }
    }

    public static void Disarm(Player attacker, Player defender){
        int offense = GameUtil.RollDice("d20")+attacker.Strength;
        int defense = GameUtil.RollDice("d20")+defender.Strength;

        if (offense > defense){
            defender.setDisarmed(1);
            defender.setDisarmedCount(2);
            System.out.println("\n"+attacker.getName()+" disarmed "+defender.getName()+".");
        } else {
            System.out.println("\n"+attacker.getName()+" wasn't able to disarm "+defender.getName()+".");
        }
    }
}