import java.util.ArrayList;
import java.util.Random;

public class Player extends Creature{
    Weapon Weapon;

    public Player(String name,
                  Weapon weapon,
                  int hitPoints,
                  int armorClass,
                  int strength,
                  int dexterity,
                  int constitution) {
        super(name,hitPoints,armorClass,strength,dexterity,constitution);
        setWeapon(weapon);
    }

    public Player(String name,
                  Weapon weapon,
                  int strength,
                  int dexterity,
                  int constitution) {
        super(name,strength,dexterity,constitution);
        setWeapon(weapon);
    }

    public Weapon getWeapon() {
        return Weapon;
    }

    public void setWeapon(Weapon weapon) {
        Weapon = weapon;
    }

    public int rollInitiative() {
        return GameUtil.rollDice("d20");
    }

    @Override
    public String toString(){
        return getCreated()
                + " " + getName()
                + " " + getHitPoints()
                + " " + getArmorClass()
                + " " + getStrength()
                + " " + getDexterity()
                + " " + getConstitution()
                + " " + getWeapon().toString();
    }

    public void Summary(){
        System.out.println("\nCreated:       " + getCreated());
        System.out.println("Name:          " + getName());
        System.out.println("Hit Points:    " + getHitPoints());
        System.out.println("Armor Class:   " + getArmorClass());
        System.out.println("Strength:      " + getStrength());
        System.out.println("Dexterity:     " + getDexterity());
        System.out.println("Constitution:  " + getConstitution());
        System.out.println("Weapon Name:   " + getWeapon().getName());
        System.out.println("Weapon Damage: " + getWeapon().getDiceType());
        System.out.println("Weapon Bonus:  " + getWeapon().getBonus());
    }

    @Override
    public void attack(Creature creature) {
        int damage = GameUtil.rollDice("d20") +
                getDexterity() +
                getWeapon().getBonus();

        if (creature.getHitPoints() != 0){
            if (damage >= creature.getArmorClass()){
                System.out.println("\n"+getName() + " attacks " + creature.getName() +
                        " with " + getWeapon().Name +
                        " (" + getArmorClass() + " to hit)...HITS!");

                damage = GameUtil.rollDice(getWeapon().getDiceType()) + getStrength();

                creature.takeDamage(damage);
            } else {
                System.out.println("\n"+getName() + " attacks " + creature.getName() +
                        " with " + getWeapon().Name +
                        " (" + creature.getArmorClass() + " to hit)...MISSES!");
            }
        }
    }

    @Override
    public void disarm(Creature creature) {
        int offense = GameUtil.rollDice("d20") + getStrength();
        int defense = GameUtil.rollDice("d20") + creature.getStrength();

        if (offense > defense){
            creature.setDisarmed(true);
            creature.setDisarmedCount(2);
            System.out.println("\n" + getName() + " disarmed " + creature.getName() + ".");
        } else {
            System.out.println("\n" + getName() + " wasn't able to disarm " + creature.getName() + ".");
        }
    }

}