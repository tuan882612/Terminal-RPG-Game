import java.util.ArrayList;
import java.util.Objects;

public class InitiativeUtil {
    private final ArrayList<Creature> players;
    private ArrayList<Integer> indices;

    public InitiativeUtil(ArrayList<Creature> arr) {
        players = arr;
    }

    public ArrayList<Integer> rollInitiative() {
        ArrayList<Integer> rolls = new ArrayList<>();

        for (Creature player : players) {
            String className = player.getClass().toString().split(" ")[1];

            if (Objects.equals(className, "Player")) {
                rolls.add(((Player) player).rollInitiative());
            } else if (Objects.equals(className, "Monster")) {
                rolls.add(GameUtil.rollDice("d20"));
            }
        }

        ArrayIndexComparator c = new ArrayIndexComparator(rolls);

        indices = c.createIndexArray();
        indices.sort(c);

        System.out.println("\nInitiative Order");
        for (int i = 0; i < players.size(); i++) {
            System.out.printf("%d. %s (rolled %d) - class: %s\n",
                    i + 1,
                    players.get(indices.get(i)).getName(),
                    rolls.get(indices.get(i)),
                    players.get(indices.get(i)).getClass().toString().split(" ")[1]);
        }

        resolveDuplicates(rolls, c, 0, indices.size() - 1);

        return indices;
    }

    private int resolveDuplicates(ArrayList<Integer> rolls, ArrayIndexComparator c, int startIdx, int endIdx) {
        for (int i = startIdx, j = i; i < endIdx; i++, j++) {
            while (j < endIdx && rolls.get(indices.get(i)).compareTo(rolls.get(indices.get(j + 1))) == 0) {
                j++;
            }

            if (i != j) {
                for (int k = i; k <= j; k++) {
                    String className = players.get(indices.get(k)).getClass().toString().split(" ")[1];

                    if (Objects.equals(className, "Player")) {
                        rolls.set(indices.get(k), ((Player) players.get(indices.get(k))).rollInitiative());
                    } else if (Objects.equals(className, "Monster")) {
                        rolls.set(indices.get(k), GameUtil.rollDice("d20"));
                    }
                }

                indices.sort(c);

                i = resolveDuplicates(rolls, c, i, j);
            }
        }

        return endIdx;
    }
}
