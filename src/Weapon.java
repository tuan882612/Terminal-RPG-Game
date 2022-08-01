public class Weapon {
    String Name;
    String DiceType;
    int Bonus = 0;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDiceType() {
        return DiceType;
    }

    public void setDiceType(String diceType) {
        DiceType = diceType;
    }

    public int getBonus() {
        return Bonus;
    }

    public void setBonus(int bonus) {
        Bonus = bonus;
    }

    public Weapon(String weapon, String dice) {
        setDiceType(dice);
        setName(weapon);
    }

    public Weapon(String weapon, String dice, int bonus) {
        setDiceType(dice);
        setName(weapon);
        setBonus(bonus);
    }

    public int rollDamage() {
        return GameUtil.rollDice(getDiceType());
    }

    @Override
    public String toString(){
        return getName() + "-" + getDiceType() + "-" + getBonus();
    }
}