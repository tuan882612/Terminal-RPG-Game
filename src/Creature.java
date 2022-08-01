import java.time.LocalDate;

public abstract class Creature implements Comparable<Creature>{
    String Name;
    public final LocalDate Created;
    int HitPoints;
    int ArmorClass;
    int Strength;
    int Dexterity;
    int Constitution;
    boolean Disarmed = false;
    int DisarmedCount = 0;

    public Creature(String name,
                    int hitPoints,
                    int armorClass,
                    int strength,
                    int dexterity,
                    int constitution) {
        this.Created = LocalDate.now();
        setName(name);
        setHitPoints(hitPoints);
        setArmorClass(armorClass);
        setStrength(strength);
        setDexterity(dexterity);
        setConstitution(constitution);
    }

    public Creature(String name,
                    int strength,
                    int dexterity,
                    int constitution) {
        this.Created = LocalDate.now();
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
            System.out.println(type + " " + getName() + "'s Health Points has reached 0 and is defeated.\n");
        } else {
            System.out.println("\n" + getName() + " has taken " + damage + " points of damage.\n");
        }
    }

    @Override
    public int compareTo(Creature creature) {
        int comp = creature.getHitPoints();
        return getHitPoints()-comp;
    }

    public boolean equals(Object object) {
        return object != null
                && getClass() == object.getClass()
                && Name.equals(((Creature) object).getName());
    }

    public abstract void attack(Creature creature);

    public abstract void disarm(Creature creature);
}
