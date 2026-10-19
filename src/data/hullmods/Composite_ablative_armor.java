package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class Composite_ablative_armor extends BaseHullMod {

    public static float ARMOR_MULT = 0.20f;
    //public static float WEAPON_ENGINE_DAMAGE_REDUCTION = 0.25f;
    //public static float EMP_REDUCTION = 0.5f;
    public static final float CAMPAIGN_REPAIR = 100f;


    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        // reduce effective armor strength for armor calculation
        stats.getEffectiveArmorBonus().modifyMult(id, ARMOR_MULT);
        stats.getMinArmorFraction().modifyMult(id, ARMOR_MULT);
        // reduce damage taken by weapons and engine, but not increase their health
        //stats.getWeaponDamageTakenMult().modifyPercent(id, WEAPON_ENGINE_DAMAGE_REDUCTION);
        //stats.getEngineDamageTakenMult().modifyPercent(id,WEAPON_ENGINE_DAMAGE_REDUCTION);
        // reduce emp damage taken
        //stats.getEmpDamageTakenMult().modifyPercent(id,EMP_REDUCTION);
        //increases repair speed in campaign map.
        stats.getRepairRatePercentPerDay().modifyPercent(id, CAMPAIGN_REPAIR);
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return "" + (int) Math.round(ARMOR_MULT * 100f) + "%";
        //if (index == 1) return "" + (int) Math.round(EMP_REDUCTION * 100f) + "%";
        if (index == 1) return "" + (int) Math.round(CAMPAIGN_REPAIR) + "%";
        return null;
    }


}