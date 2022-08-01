public class Position {
    int X = 0;
    int Y = 0;
    String Icon;

    public Position(int x, int y, String icon){
        setX(x);
        setY(y);
        setIcon(icon);
    }

    public String getIcon() {
        return Icon;
    }

    private void setIcon(String icon) {
        Icon = icon;
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

    public boolean validatePosition(Position obj) {
        return getX() != obj.getX() && getY() != obj.getY();
    }
}
