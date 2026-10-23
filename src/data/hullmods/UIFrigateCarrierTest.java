package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.util.Misc;
//import org.lazywizard.lazylib.combat.CombatUtils;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;
import java.util.Map;

public class UIFrigateCarrierTest extends BaseHullMod {

    private final float SCRIPT_DELAY = 1f;

    public void advanceInCombat(ShipAPI ship, float amount) {

        CombatEngineAPI engine = Global.getCombatEngine();
        Map<String, Object> customData = engine.getCustomData();
        String checkFrigateStatusKey = "UIFrigateCarrierTestFrigateStatus_";
        String scriptDelayKey = "UIFrigateCarrierTestScriptDelayKey_";

        // if no ship with this hullmod has created the delay list yet, create it
        if (!customData.containsKey(scriptDelayKey)) {
            Map<String, Float> scriptDelayList = new HashMap<>();
            customData.put(scriptDelayKey, scriptDelayList);
        }
        @SuppressWarnings("unchecked") // will always be a Map<String, Float>
        Map<String, Float> scriptDelayList = (Map<String, Float>) customData.get(scriptDelayKey);
        // null check if this ship is in the list
        if (!scriptDelayList.containsKey(ship.getId())) {
            scriptDelayList.put(ship.getId(), engine.getTotalElapsedTime(false));
        }
        float delayTimestamp = scriptDelayList.get(ship.getId());
        // if this ship has not existed for longer then the script delay, return
        if (engine.getTotalElapsedTime(false) < delayTimestamp + SCRIPT_DELAY) return;
        // don't spawn frigate when travel drive is on
        if (ship.getTravelDrive().isOn()) return;


        // if no ship with this hullmod has created the status list yet, create it
        if (!customData.containsKey(checkFrigateStatusKey)) {
            Map<String, Boolean> frigateStatusList = new HashMap<>();
            customData.put(checkFrigateStatusKey, frigateStatusList);
        }
        @SuppressWarnings("unchecked") // will always be a Map<String, Boolean>
        Map<String, Boolean> frigateStatusList = (Map<String, Boolean>) customData.get(checkFrigateStatusKey);

        // null check frigate status list
        if (!frigateStatusList.containsKey(ship.getId())) {
            frigateStatusList.put(ship.getId(), false);
        }
        boolean frigateSpawned = frigateStatusList.get(ship.getId());

        // spawn the frigate if not already done
        if (!frigateSpawned) {
            spawnFrigate(ship);
            frigateStatusList.put(ship.getId(), true);
        }
    }

    public void spawnFrigate(ShipAPI ship) {

        // which team to spawn the frigate on
        FleetSide side;
        if (ship.getOwner() == 0) side = FleetSide.PLAYER;
        else side = FleetSide.ENEMY;

        // calculate launch location, behind the parent ship. Math improved by a friend, thanks friend
        double theta = -ship.getFacing() * Math.PI/180.0f;
        float dist = ship.getCollisionRadius();
        Vector2f loc = new Vector2f(0,0);
        Vector2f.add(ship.getLocation(), new Vector2f((float) (dist*Math.cos(theta)), (float) (dist*Math.sin(theta))), loc);

//        // spawn the frigate
//        float cr = ship.getCurrentCR();
//        ShipAPI child = CombatUtils.spawnShipOrWingDirectly("czi_bystryk_PD", FleetMemberType.SHIP,
//                side, cr, loc, ship.getFacing());
//
//        //if parent ship is ally in allied battle, make child an ally too
//        if (ship.isAlly()) child.setAlly(true);
//
//        // add velocity to the frigate when it spawns so it doesn't materialize inside
//        // the ship once the animation is done
////        Vector2f velocity = Misc.getUnitVectorAtDegreeAngle(-ship.getFacing());
////        velocity.scale(5000f);
////        Vector2f.add(child.getVelocity(), velocity, child.getVelocity());
//
//        // mimic a fighter launching from a carrier
//        child.setAnimatedLaunch();
    }
}