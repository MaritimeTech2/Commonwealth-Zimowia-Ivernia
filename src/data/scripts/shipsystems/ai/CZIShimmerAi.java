package data.scripts.shipsystems.ai;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipSystemAIScript;
import com.fs.starfarer.api.combat.ShipSystemAPI;
import com.fs.starfarer.api.combat.ShipwideAIFlags;
import com.fs.starfarer.api.util.IntervalUtil;
import org.lwjgl.util.vector.Vector2f;

import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.combat.AIUtils;


public class CZIShimmerAi implements ShipSystemAIScript{

    private ShipAPI ship;
    private ShipSystemAPI system;
    private boolean Is_System_On;
    private boolean Current_Flux_Level;
    private final IntervalUtil IntervalTick = new IntervalUtil (1f,1.5f);
    private float RANGE = 2000 , Enemy_ship_Range;

    public void init(ShipAPI ship, ShipSystemAPI system, ShipwideAIFlags flags, CombatEngineAPI engine) {
        this.ship = ship;
        this.system = system;
    }

    public void advance(float amount, Vector2f missileDangerDir, Vector2f collisionDangerDir, ShipAPI target) {
        IntervalTick.advance(amount);
        //has the tick passed?
        if(IntervalTick.intervalElapsed()){
            //rip bozo if you think i know why this is here
            if(Current_Flux_Level){
                if(ship.getFluxLevel() <= 0.60f) Current_Flux_Level = false;
                return;
            }
            //checks if the system is not active and if Is_system_on is true, if so sets is_system_on to false.
            if(!system.isActive() && Is_System_On) Is_System_On = false;

            //make the ai turn on shipsystem if flux is low enough.  checking if the system is off and that enemies are near
            if(ship.getFluxLevel() <= 0.75f) {
                ShipAPI enemy_ship = (ShipAPI) AIUtils.getNearestEnemy(ship);
                //checks if enemies are within 2000su
                if(enemy_ship != null){
                    Enemy_ship_Range = MathUtils.getDistance(enemy_ship,ship);
                }else{
                    Enemy_ship_Range = RANGE+100;
                }
                if(!Is_System_On && Enemy_ship_Range <RANGE){
                    ship.useSystem();
                    Is_System_On = true;
                }
                return;
            }
            //i think this just shuffles the ai logic between the flux checks until the flux hits the crit points
            else{
                Current_Flux_Level = true;
            }
            //checks if the flux is high enough, and tells ai to shut off system
            if((ship.getFluxLevel() >= 0.85f || Enemy_ship_Range >RANGE) && Is_System_On) {
                ship.useSystem();
                Is_System_On = false;
            }
        }
    }
}
