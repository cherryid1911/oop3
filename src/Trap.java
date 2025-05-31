public class Trap extends Enemy{
    private int visibility_time;
    private int invisibility_time;
    private int ticks_count;
    private boolean visible;

    Trap(int vt, int it){
        super();
        ticks_count=0;
        visible=true;
    }
    

}
