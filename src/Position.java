public class Position {
    int X = 0;
    int Y = 0;
    String Holder;

    public Position(int x, int y, String holder){
        setX(x);
        setY(y);
        setHolder(holder);
    }

    public String getHolder() {
        return Holder;
    }

    private void setHolder(String holder) {
        Holder = holder;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public boolean Verify(Position obj) {
        return getX() != obj.getX() && getY() != obj.getY();
    }
}
