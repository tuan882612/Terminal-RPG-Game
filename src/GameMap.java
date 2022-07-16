import java.util.ArrayList;

public class GameMap {
    String Default = ".";
    ArrayList<ArrayList<String>> Map = new ArrayList<>();
    public GameMap(){
        for(int i = 0; i < 25; i++){
            Map.add(new ArrayList<>());
            for(int j = 0; j < 25; j++){
                Map.get(i).add(j, getDefault());
            }
        }
    }

    public void DisplayMap(){
        Map.forEach( row -> {
            row.forEach( item -> System.out.print(item + "  "));
            System.out.println();
        });
    }

    public String getDefault(){
        return Default;
    }
    void UpdatePosition(int x, int y, Position person) {
        Map.get(person.getX()).set(person.getY(),".");
        Map.get(x).set(y, person.getHolder());
        person.setX(x);
        person.setY(y);
    }
    void SetPosition(Position person){
        Map.get(person.getX()).set(person.getY(), person.getHolder());
    }
}
