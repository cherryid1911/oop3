public class Mage extends Player{
    private int mana_pool;
    private int mana_amount;
    private int mana_cost;
    private int spell_power;
    private int hits_count;
    private int ability_range;

    Mage(int mp, int mc, int sp, int hc, int ar){
        super();
        mana_pool=mp;
        mana_amount = mp/4;
        mana_cost=mc;
        spell_power=sp;
        hits_count=hc;
        ability_range=ar;
    }

    public void LevelUp(){
        LevelUpPlayer();
        mana_pool=mana_pool + (Level*25);
        mana_amount = Math.min(mana_amount+mana_pool/4,mana_pool);
        spell_power = spell_power + (Level*10);
    }

    public void gameTick(){mana_amount=Math.min(mana_pool,mana_amount+Level);}

    public void abilityCast() throws Exception{
        checkSpecialAbility();
        int hits=0;
        //implement hitting enemies


    }

    private void checkSpecialAbility() throws Exception{
        int diff = mana_amount-mana_cost;
        if (diff<0){denySpecialAbility();}
        mana_amount=diff;
    }
}
