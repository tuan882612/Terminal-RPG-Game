import java.time.LocalDate;

public class Player {
    String Name;
    private final LocalDate Created;
    Weapon Weapon;
    int HitPoints;
    int ArmorClass;
    int Strength;
    int Dexterity;
    int Constitution;
    boolean Disarmed = false;

    int DisarmedCount = 0;
    public Player(String name,
                  Weapon weapon,
                  int hitPoints,
                  int armorClass,
                  int strength,
                  int dexterity,
                  int constitution) {
        this.Created = LocalDate.now();
        setWeapon(weapon);
        setName(name);
        setHitPoints(hitPoints);
        setArmorClass(armorClass);
        setStrength(strength);
        setDexterity(dexterity);
        setConstitution(constitution);
    }

    public Player(String name,
                  Weapon weapon,
                  int strength,
                  int dexterity,
                  int constitution) {
        this.Created = LocalDate.now();
        setWeapon(weapon);
        setName(name);
        setStrength(strength);
        setDexterity(dexterity);
        setConstitution(constitution);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public LocalDate getCreated() {
        return Created;
    }

    public int getHitPoints() {
        return HitPoints;
    }

    public void setHitPoints(int hitPoints) {
        HitPoints = hitPoints;
    }

    public Weapon getWeapon() {
        return Weapon;
    }

    public void setWeapon(Weapon weapon) {
        Weapon = weapon;
    }

    public int getStrength() {
        return Strength;
    }

    public void setStrength(int strength) {
        Strength = strength;
    }

    public int getDexterity() {
        return Dexterity;
    }

    public void setDexterity(int dexterity) {
        Dexterity = dexterity;
    }

    public int getConstitution() {
        return Constitution;
    }

    public void setConstitution(int constitution) {
        Constitution = constitution;
    }

    public int getArmorClass() {
        return ArmorClass;
    }

    public void setArmorClass(int armorClass) {
        ArmorClass = armorClass;
    }

    public boolean getDisarmed() {
        return Disarmed;
    }

    public void setDisarmed(boolean state) {
        Disarmed = state;
    }

    public int getDisarmedCount() {
        return DisarmedCount;
    }

    public void setDisarmedCount(int disarmedCount) {
        DisarmedCount = disarmedCount;
    }

    public void takeDamage(int damage){
        setHitPoints(getHitPoints()-damage);

        if(getHitPoints() < 0){
            String type = getClass().toString().split(" ")[1];
            setHitPoints(0);
            System.out.println(
                    type + " " +
                            getName() +
                            "'s Health Points has reached 0 and is defeated.\n");
        } else {
            System.out.println(getName() + " has taken " + damage + " points of damage.\n");
        }
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
}