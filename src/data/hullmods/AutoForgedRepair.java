package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.listeners.AdvanceableListener;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.HashMap;
import java.util.Map;

public class AutoForgedRepair extends BaseHullMod {

    public static final float CASUALTY_REDUCTION = 40f;
    public static final float COMBAT_REPAIR_ENG = 100f;
    public static final float CAMPAIGN_REPAIR = 100f;
    public static final float CAMPAIGN_REPAIR_COST = 50f;

    public static Map mag = new HashMap();
    static {
        mag.put(HullSize.FRIGATE, 15f);
        mag.put(HullSize.DESTROYER, 20f);
        mag.put(HullSize.CRUISER, 30f);
        mag.put(HullSize.CAPITAL_SHIP, 50f);
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return "" + ((Float) mag.get(HullSize.FRIGATE)).intValue() + "%";
        if (index == 1) return "" + ((Float) mag.get(HullSize.DESTROYER)).intValue() + "%";
        if (index == 2) return "" + ((Float) mag.get(HullSize.CRUISER)).intValue() + "%";
        if (index == 3) return "" + ((Float) mag.get(HullSize.CAPITAL_SHIP)).intValue() + "%";
        if (index == 4) return "" + (int) Math.round(COMBAT_REPAIR_ENG * 100f) + "%";
        if (index == 5) return "" + (int) Math.round(CAMPAIGN_REPAIR * 100f) + "%";
        return null;
    }

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

        boolean sMod = isSMod(stats);
        // reduces crew causaulties due to hull damage
        stats.getCrewLossMult().modifyPercent(id,CASUALTY_REDUCTION);
        // reduces time engines and weapons take to repair
        stats.getCombatEngineRepairTimeMult().modifyPercent(id,COMBAT_REPAIR_ENG);
        stats.getCombatWeaponRepairTimeMult().modifyPercent(id,COMBAT_REPAIR_ENG);
        // increases repair rate speed for ships after a battle in the campaign layer
        stats.getRepairRatePercentPerDay().modifyPercent(id, CAMPAIGN_REPAIR);

        // gives the ships a hull repair modifier while in battle
        //stats.getHullCombatRepairRatePercentPerSecond()
        //stats.getMaxCombatHullRepairFraction()
    }
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        ship.addListener(new ui_hullrepairlistener(ship));
    }

    public static class ui_hullrepairlistener implements AdvanceableListener {
        ShipAPI ship;
//        Float maxHealPercentage = 0.3F;
//        Float healPerSecond = 0.0F;
//        Float healedSoFar = 0.0F;
//        Float delay = 0.0F;

        public ui_hullrepairlistener(ShipAPI ship) {
            this.ship = ship;
        }

        public void advance(float amount) {
            float CR_LEVEL = this.ship.getCurrentCR();
            float HEAL_BASE = (Float)AutoForgedRepair.mag.get(this.ship.getHullSize());
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
}
