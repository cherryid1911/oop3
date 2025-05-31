public class Rogue extends Player{
    private int energy;
    private int cost;

    Rogue(int c){
        super();
        energy=100;
        cost=c;
    }

    public void LevelUp(){
        LevelUpPlayer();
        energy=100;
        attack=attack+(Level*3);
    }

    public void gameTick(){energy=Math.min(energy+10,100);}

    public void abilityCast() throws Exception{
        int diff =energy-cost;
        if (diff<0){denySpecialAbility();}
        energy=diff;
        //implement hitting enemies

    }
}
