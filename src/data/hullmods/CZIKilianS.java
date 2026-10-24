package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.mission.FleetSide;
//import org.lazywizard.lazylib.combat.CombatUtils;
import org.lwjgl.util.vector.Vector2f;

public class CZIKilianS extends BaseHullMod {

    public static final float addcrew = 5f;
    public static final float lesscargo = 40f;
    public static final float lessfuel = 30f;
    public static final float recoverycost = 3f;
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        //increase crew requirements
        stats.getMinCrewMod().modifyFlat(id, addcrew);
        //reduce cargo and fuel capacity
        stats.getCargoMod().modifyFlat(id, (-1*lesscargo) );
        stats.getFuelMod().modifyFlat(id, (-1*lessfuel) );
        //reduce the deployment cost of the parent ship
        stats.getSuppliesToRecover().modifyFlat(id, -recoverycost);
        stats.getDynamic().getMod(Stats.DEPLOYMENT_POINTS_MOD).modifyFlat(id, -recoverycost);
    }
//    public void apply(MutableShipStatsAPI stats, String id ){
//        stats.getDynamic().getMod(Stats.DEPLOYMENT_POINTS_MOD).modifyFlat(id,recoverycost);
//
//    }
    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
        // only run script once
        Map<String, Object> customData = Global.getCombatEngine().getCustomData();
        String key = ship.getId();
        if (!customData.containsKey(key)) customData.put(key, true);
        else return;

        // add frigate to reserves
        CombatFleetManagerAPI fleetManager = Global.getCombatEngine().getFleetManager(ship.getOwner());
        FleetMemberAPI frigate = Global.getFactory().createFleetMember(FleetMemberType.SHIP, "czi_kilian_standard");
        frigate.getCrewComposition().addCrew(frigate.getNeededCrew());

        fleetManager.addToReserves(frigate);
    }
}
