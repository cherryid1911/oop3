public class Player extends Unit{
    protected int Experience;
    protected int Level;

    Player(){
        super();
        Experience=0;
        Level=1;
    }


    private boolean checkLevelUp(){
        if (Experience<Level*50){return false;}
        return true;
    }

    protected void LevelUpPlayer(){
        Experience=Experience-Level*50;
        Level++;
        health_pool = health_pool + (Level*10);
        health_amount = health_pool;
        attack = attack + (Level*4);
        defense = defense + Level;
    }

    protected void denySpecialAbility() throws Exception{
        throw new Exception("You don't have enough resources" +
                "to case special ability!");
    }


}
