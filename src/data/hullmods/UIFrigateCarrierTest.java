package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.mission.FleetSide;
import org.lazywizard.lazylib.combat.CombatUtils;
import org.lwjgl.util.vector.Vector2f;

public class UIFrigateCarrierTest extends BaseHullMod {

    public void advanceInCombat(ShipAPI ship, float amount) {

        CombatEngineAPI engine = Global.getCombatEngine();

        if (engine.isSimulation()) { //simulations don't use travel drive
            if (!engine.getCustomData().containsKey(ship.getFleetMemberId() + "_usedTravelDrive")) {

                engine.getCustomData().put(ship.getFleetMemberId() + "_usedTravelDrive", 2);

                spawnFrigate(ship);
            }
        }
        else { //has travel drive been used, and is finished?
            if (ship.getTravelDrive().getState().equals(ShipSystemAPI.SystemState.IDLE)) {
                if (engine.getCustomData().containsKey(ship.getFleetMemberId() + "_usedTravelDrive") &&
                        (int) engine.getCustomData().get(ship.getFleetMemberId() + "_usedTravelDrive") == 1) {

                    engine.getCustomData().put(ship.getFleetMemberId() + "_usedTravelDrive", 2);

                    spawnFrigate(ship);

                }
            } else if (ship.getTravelDrive().getState().equals(ShipSystemAPI.SystemState.ACTIVE)) {
                engine.getCustomData().put(ship.getFleetMemberId() + "_usedTravelDrive", 1);
            }
        }
    }

    public void spawnFrigate(ShipAPI ship) {

        //who owns the parent?
        FleetSide side;
        if (ship.getOwner() == 0) side = FleetSide.PLAYER;
        else side = FleetSide.ENEMY;

//        float dist = ship.getCollisionRadius();
//        double angle = 270 * (3.14159/180); //must be in radians
//        Vector2f loc = new Vector2f(ship.getLocation().x + (dist * (float) Math.cos(angle)),
//                ship.getLocation().y + (dist * (float) Math.sin(angle)));

        //launch location, behind the parent ship. Math improved by a friend
        double theta = -ship.getFacing() * Math.PI/180.0f;
        float dist = ship.getCollisionRadius();
        Vector2f loc = new Vector2f(0,0);
        Vector2f.add(ship.getLocation(), new Vector2f((float) (dist*Math.cos(theta)), (float) (dist*Math.sin(theta))), loc);

        //I love you lazylib
        ShipAPI child = CombatUtils.spawnShipOrWingDirectly("cerberus_Hardened", FleetMemberType.SHIP,
                side, 0.7f, loc, ship.getFacing());

        //if parent ship is ally, make child an ally too
        if (ship.isAlly()) child.setAlly(true);

        child.setAnimatedLaunch();
    }
}
