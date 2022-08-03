import java.util.ArrayList;

public class GameMap {
    ArrayList<ArrayList<String>> Map;

    public GameMap(){
        Map = new ArrayList<>();

        for(int i = 0; i < 25; i++){
            Map.add(new ArrayList<>());
            for(int j = 0; j < 25; j++){
                Map.get(i).add(j, ".");
            }
        }
    }

    public void DisplayMap(){
        Map.forEach( row -> {
            row.forEach( item -> {
                if (item.length() == 1) {
                    System.out.print(item + "  ");
                } else {
                    System.out.print(item);
                }
            });
            System.out.println();
        });
    }

    void UpdatePosition(int x, int y, Position person) {
        Map.get(person.getX()).set(person.getY(),".");
        Map.get(x).set(y, person.getIcon());
        person.setX(x);
        person.setY(y);
    }

    void SetPosition(Position person){
        Map.get(person.getX()).set(person.getY(), person.getIcon());
    }
}
