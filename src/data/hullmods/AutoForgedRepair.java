package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
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
    public static final float COMBAT_REPAIR = 50f;
    public static final float CAMPAIGN_REPAIR = 100f;
    public static final float CAMPAIGN_REPAIR_COST = 100f;

    public static Map mag = new HashMap();
    static {
        mag.put(HullSize.FRIGATE, 100f);
        mag.put(HullSize.DESTROYER, 25f);
        mag.put(HullSize.CRUISER, 50f);
        mag.put(HullSize.CAPITAL_SHIP, 100f);
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return "" + ((Float) mag.get(HullSize.FRIGATE)).intValue() + "%";
        if (index == 1) return "" + ((Float) mag.get(HullSize.DESTROYER)).intValue() + "%";
        if (index == 2) return "" + ((Float) mag.get(HullSize.CRUISER)).intValue() + "%";
        if (index == 3) return "" + ((Float) mag.get(HullSize.CAPITAL_SHIP)).intValue() + "%";
        return null;
    }
}
