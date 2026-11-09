package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.listeners.AdvanceableListener;

import java.util.HashMap;
import java.util.Map;

public class RepairOverclock extends BaseHullMod {

    public static final float CASUALTY_INCREASE = 40f;
    public static final float CR_REPAIR = 3f;
    public static final float REPAIR_CAMPAIGN = 50f;
    public static final float CREW_REDUCTION = 40f;

    public static Map mag = new HashMap();
    static {
        mag.put(HullSize.FRIGATE, 5f);
        mag.put(HullSize.DESTROYER, 10f);
        mag.put(HullSize.CRUISER, 20f);
        mag.put(HullSize.CAPITAL_SHIP, 40f);
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return "" + ((Float) mag.get(HullSize.FRIGATE)).intValue();
        if (index == 1) return "" + ((Float) mag.get(HullSize.DESTROYER)).intValue();
        if (index == 2) return "" + ((Float) mag.get(HullSize.CRUISER)).intValue();
        if (index == 3) return "" + ((Float) mag.get(HullSize.CAPITAL_SHIP)).intValue();
        if (index == 4) return "" + (int) Math.round(CR_REPAIR) + "%";
        if (index == 5) return "" + (int) Math.round(REPAIR_CAMPAIGN) + "%";
        if (index == 6) return "" + (int) Math.round(CREW_REDUCTION) + "%";
        if (index == 7) return "" + (int) Math.round(CASUALTY_INCREASE) + "%";
        return null;
    }

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

        //boolean sMod = isSMod(stats);

        // increase CR recovery speed
        stats.getBaseCRRecoveryRatePercentPerDay().modifyFlat(id,CR_REPAIR);

        // increases repair in campaign
        stats.getRepairRatePercentPerDay().modifyPercent(id, REPAIR_CAMPAIGN);

        //reduce crew size and increase causalties
        stats.getMinCrewMod().modifyMult(id, 1f - (CREW_REDUCTION) * 0.01f);
        stats.getCrewLossMult().modifyMult(id, 1f + (CASUALTY_INCREASE) * 0.01f);


    }
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        ship.addListener(new ui_hullrepairlistener(ship));
    }

    public static class ui_hullrepairlistener implements AdvanceableListener {
        ShipAPI ship;

        public ui_hullrepairlistener(ShipAPI ship) {
            this.ship = ship;
        }

        public void advance(float amount) {
            float CR_LEVEL = this.ship.getCurrentCR();
            float HEAL_BASE = (Float) RepairOverclock.mag.get(this.ship.getHullSize());
            float HEAL_PER_SECOND = amount*(CR_LEVEL/0.7f)*HEAL_BASE;
            float MAX_HP = this.ship.getMaxHitpoints();
            float CURRENT_HP = this.ship.getHitpoints();
            if (CURRENT_HP < MAX_HP) {
                ship.setHitpoints(ship.getHitpoints() + HEAL_PER_SECOND);
                if (ship.getHitpoints()>MAX_HP){
                    ship.setHitpoints(MAX_HP);
                }
            }
            else {}

        }

    }
    @Override
    public boolean isApplicableToShip(ShipAPI ship) {
        return (ship.getVariant().getHullMods().contains("czi_autoforge_repair") &&
                !ship.getVariant().getHullMods().contains("czi_armor_overclock") &&
                !ship.getVariant().getHullMods().contains("czi_hull_overclock")); //&&
                //!ship.getVariant().getHullMods().contains("advancedcore");
    }


    public String getUnapplicableReason(ShipAPI ship) {
        if (!ship.getVariant().getHullMods().contains("czi_autoforge_repair")) {
            return "Can only be installed on ships already equipped with an Auto-forge unit!";
        }
        if (ship.getVariant().getHullMods().contains("czi_armor_overclock")) {
            return "Incompatible with Armor Overclock!";
        }
        if (ship.getVariant().getHullMods().contains("czi_hull_overclock")) {
            return "Incompatible with Hull Overclock";
        }
        /*if (ship.getVariant().getHullMods().contains(HullMods.DISTRIBUTED_FIRE_CONTROL)) {
            return "Incompatible with Distributed Fire Control";
        }*/

        return null;
    }
}
