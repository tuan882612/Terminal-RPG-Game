public class Monster extends Creature{
    MonsterType Type;

    public Monster(String name,
                   String type,
                   int hitPoints,
                   int armorClass,
                   int strength,
                   int dexterity,
                   int constitution
                   ) {
        super(name, hitPoints, armorClass, strength, dexterity, constitution);
        setMonsterType(type);
    }

    public void setMonsterType(String type) {
        Type = MonsterType.valueOf(type);
    }

    public MonsterType getMonsterType() {
        return this.Type;
    }

    @Override
    public String toString() {
        return "\n" + getName()
                + " " + getMonsterType()
                + " " + getHitPoints()
                + " " + getStrength()
                + " " + getDexterity()
                + " " + getConstitution();
    }

    @Override
    public void attack(Creature creature) {
        int damage = GameUtil.rollDice("d20") + getDexterity();

        if (creature.getHitPoints() != 0){
            if (damage >= creature.getArmorClass()){
                System.out.println("\nMonster "+getName() + " attacks " + creature.getName() +
                        " (" + getArmorClass() + " to hit)...HITS!");

                creature.takeDamage(GameUtil.rollDice("d6") + getStrength());
            } else {
                System.out.println("\nMonster "+getName() + " attacks " + creature.getName() +
                        " (" + creature.getArmorClass() + " to hit)...MISSES!");
            }
        }
    }

    @Override
    public void disarm(Creature creature) {}
}
