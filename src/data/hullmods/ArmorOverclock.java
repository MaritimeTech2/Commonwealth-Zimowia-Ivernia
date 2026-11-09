package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.listeners.AdvanceableListener;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

import java.util.HashMap;
import java.util.Map;

public class ArmorOverclock extends BaseHullMod {

    //public static final float HULL_REDUCTION = 10f;
    public static final float COMBAT_REPAIR_ENG = 100f;
    public static final float ARMOR_INCREASE = 300f;
    public static final float ARMOR_MULT = 0.2f;
    public static final float MANEUVER_PENALTY = 30f;
    public static final float SPEED_PENALTY = 10f;

    public static Map mag = new HashMap();
    static {
        mag.put(HullSize.FRIGATE, 5f);
        mag.put(HullSize.DESTROYER, 10f);
        mag.put(HullSize.CRUISER, 20f);
        mag.put(HullSize.CAPITAL_SHIP, 40f);
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return "" + (int) Math.round(ARMOR_INCREASE) + "%";
        if (index == 1) return "" + (int) Math.round(ARMOR_MULT * 100f) + "%";
        if (index == 2) return "" + (int) Math.round(MANEUVER_PENALTY) + "%";
        if (index == 3) return "" + (int) Math.round(SPEED_PENALTY);
        return null;
    }

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        //boolean sMod = isSMod(stats);

        // reduces time engines and weapons take to repair
        //stats.getCombatEngineRepairTimeMult().modifyPercent(id,COMBAT_REPAIR_ENG);
        //stats.getCombatWeaponRepairTimeMult().modifyPercent(id,COMBAT_REPAIR_ENG);

        //increases ships armor
        stats.getArmorBonus().modifyPercent(id, ARMOR_INCREASE);

        //reduces ships effective armor
        stats.getEffectiveArmorBonus().modifyMult(id, ARMOR_MULT);
        stats.getMinArmorFraction().modifyMult(id, ARMOR_MULT);

        //nerfs maneuver to compensate for extra mass
        stats.getAcceleration().modifyMult(id, 1f - MANEUVER_PENALTY * 0.01f);
        stats.getDeceleration().modifyMult(id, 1f - MANEUVER_PENALTY * 0.01f);
        stats.getTurnAcceleration().modifyMult(id, 1f - MANEUVER_PENALTY * 0.01f);
        stats.getMaxTurnRate().modifyMult(id, 1f - MANEUVER_PENALTY * 0.01f);

        //reduces ships speed by 10
        stats.getMaxSpeed().modifyFlat(id,-SPEED_PENALTY);
    }
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        //ship.addListener(new ui_hullrepairlistener(ship));
    }

    public static class ui_hullrepairlistener implements AdvanceableListener {
        ShipAPI ship;

        public ui_hullrepairlistener(ShipAPI ship) {
            this.ship = ship;
        }

        public void advance(float amount) {
            float CR_LEVEL = this.ship.getCurrentCR();
            float HEAL_BASE = (Float) ArmorOverclock.mag.get(this.ship.getHullSize());
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
                !ship.getVariant().getHullMods().contains("czi_repair_overclock") &&
                !ship.getVariant().getHullMods().contains("czi_hull_overclock")); //&&
                //!ship.getVariant().getHullMods().contains("advancedcore");
    }


    public String getUnapplicableReason(ShipAPI ship) {
        if (!ship.getVariant().getHullMods().contains("czi_autoforge_repair")) {
            return "Can only be installed on ships already equipped with an Auto-forge unit!";
        }
        if (ship.getVariant().getHullMods().contains("czi_repair_overclock")) {
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
