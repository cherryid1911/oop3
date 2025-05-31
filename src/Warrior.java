public class Warrior extends Player{
    private int cd;
    private int remain_cd;

    Warrior(int cooldown){
        super();
        cd=cooldown;
        remain_cd=0;
    }

    private void checkSpecialAbility() throws Exception{
        if (checkCD()){denySpecialAbility();}
    }

    private void LevelUp(){
        LevelUpPlayer();
        remain_cd=0;
        health_pool = health_pool + (Level*5);
        attack = attack + (Level*2);
        defense = defense + Level;
    }

    public void gameTick(){if (checkCD()){remain_cd--;}}

    public void abilityCast() throws Exception{
        checkSpecialAbility();
        remain_cd = cd;
        health_amount = Math.min(health_amount+(10*defense), health_pool);
        //implement hitting the enemies

    }

    private boolean checkCD(){return (remain_cd>0);}
}
