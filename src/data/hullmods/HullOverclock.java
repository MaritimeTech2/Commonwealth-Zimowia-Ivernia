package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.listeners.AdvanceableListener;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

import java.util.HashMap;
import java.util.Map;

public class HullOverclock extends BaseHullMod {

    public static final float COMBAT_REPAIR_ENG = 100f;
    public static final float HULL_REDUCTION = 35f;

    public static Map DP_REDUCTION = new HashMap();
    static {
        DP_REDUCTION.put(HullSize.FRIGATE, 1f);
        DP_REDUCTION.put(HullSize.DESTROYER, 2f);
        DP_REDUCTION.put(HullSize.CRUISER, 3f);
        DP_REDUCTION.put(HullSize.CAPITAL_SHIP, 4f);
    }

    public static Map mag = new HashMap();
    static {
        mag.put(HullSize.FRIGATE, 15f);
        mag.put(HullSize.DESTROYER, 30f);
        mag.put(HullSize.CRUISER, 40f);
        mag.put(HullSize.CAPITAL_SHIP, 80f);
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return "" + ((Float) mag.get(HullSize.FRIGATE)).intValue();
        if (index == 1) return "" + ((Float) mag.get(HullSize.DESTROYER)).intValue();
        if (index == 2) return "" + ((Float) mag.get(HullSize.CRUISER)).intValue();
        if (index == 3) return "" + ((Float) mag.get(HullSize.CAPITAL_SHIP)).intValue();
        if (index == 4) return "" + (int) Math.round(COMBAT_REPAIR_ENG) + "%";
        if (index == 5) return "" + (int) Math.round(HULL_REDUCTION) + "%";
        //if (index == 6) return "" + (int) Math.round(ARMOR_INCREASE) + "%";
        if (index == 6) return "" + ((Float) DP_REDUCTION.get(HullSize.FRIGATE)).intValue();
        if (index == 7) return "" + ((Float) DP_REDUCTION.get(HullSize.DESTROYER)).intValue();
        if (index == 8) return "" + ((Float) DP_REDUCTION.get(HullSize.CRUISER)).intValue();
        if (index == 9) return "" + ((Float) DP_REDUCTION.get(HullSize.CAPITAL_SHIP)).intValue();
        return null;
    }

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

        //boolean sMod = isSMod(stats);

        // reduces time engines and weapons take to repair
        stats.getCombatEngineRepairTimeMult().modifyPercent(id,COMBAT_REPAIR_ENG);
        stats.getCombatWeaponRepairTimeMult().modifyPercent(id,COMBAT_REPAIR_ENG);

        //get hullsize for dp reduction
        Map map = DP_REDUCTION;
        //reduces ships deployment cost
        stats.getDynamic().getMod(Stats.DEPLOYMENT_POINTS_MOD).modifyFlat(id,(Float) map.get(hullSize));
        stats.getSuppliesToRecover().modifyFlat(id,(Float) map.get(hullSize));

        // nerfs the hull to account for increased regen
        stats.getHullBonus().modifyPercent(id,-HULL_REDUCTION);
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
            float HEAL_BASE = (Float) HullOverclock.mag.get(this.ship.getHullSize());
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
                !ship.getVariant().getHullMods().contains("czi_repair_overclock") &&
                !ship.getVariant().getHullMods().contains("reinforcedhull"));
    }


    public String getUnapplicableReason(ShipAPI ship) {
        if (!ship.getVariant().getHullMods().contains("czi_autoforge_repair")) {
            return "Can only be installed on ships already equipped with an Auto-forge unit!";
        }
        if (ship.getVariant().getHullMods().contains("czi_armor_overclock")) {
            return "Incompatible with Armor Overclock!";
        }
        if (ship.getVariant().getHullMods().contains("czi_repair_overclock")) {
            return "Incompatible with Repair Overclock";
        }
        if (ship.getVariant().getHullMods().contains("reinforcedhull")) {
            return "Unable to Reinforce the Hull Further";
        }

        return null;
    }
}
