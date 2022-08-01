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

    public MonsterType getMonsterType() {
        return Type;
    }

    public void setMonsterType(String type) {
        Type = MonsterType.valueOf(type);
    }

    @Override
    public void attack(Creature creature) {

    }

    @Override
    public void disarm(Creature creature) {

    }
}
