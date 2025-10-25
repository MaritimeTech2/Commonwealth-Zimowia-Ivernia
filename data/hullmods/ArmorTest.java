package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.combat.DefenseUtils;
import com.fs.starfarer.api.combat.ArmorGridAPI;

import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ArmorTest extends BaseHullMod {

    private static final Logger log = Global.getLogger(ArmorTest.class);

    static {
        log.setLevel(Level.ALL);
    }
//    private static final Set<String> BLOCKED_HULLMODS = new HashSet<>(7);

//    public static final float COST_REDUCTION_LG = 10f;
//    public static final float COST_REDUCTION_MED = 6f;
//    public static final float COST_REDUCTION_SM = 2f;
//    private static final float SUPPLY_USE_MULT = 1.25f;
//    private static final float OVERLOAD_DUR_MULT = 2f;
    public static float Eff_Armor_Reduce = 0.5f;
    public static float ARMOR_CAP = 2000f;
    public static float REGEN_PER_SEC_PERCENT = 50f;
    public static float Max_Total_Regen = 0.9f; //divides the max health able to be regen
    public static float Max_Regen_Rate = 30f;
    public static float CALC_FLAT = 200f;
    public static float DISRUPTION_TIME = 2f;
    public static float DAMAGE_CAP = 2000f;
    public static float DAMAGE_CAP_REDUCTION = 0.9f;


//    static
//    {
        // These hullmods will automatically be removed
        // Not as elegant as blocking them in the first place, but
        // this method doesn't require editing every hullmod's script
//        BLOCKED_HULLMODS.add("safetyoverrides"); // It would be bad
//        BLOCKED_HULLMODS.add("eis_aquila"); // Also bad - no biscuit for you, selkie!
//        BLOCKED_HULLMODS.add("converted_hangar"); // No space, sorry, not sorry
//        BLOCKED_HULLMODS.add("roider_fighterClamps"); // not possible, sorry
//        BLOCKED_HULLMODS.add("targetingunit"); // No general range boost for you!
//        BLOCKED_HULLMODS.add("dedicated_targeting_core"); // No slightly crappier range boost for you, either!
        //BLOCKED_HULLMODS.add("reinforced_hull");  //nope, sorry cant fit inside these ships
//    }

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id)
    {
//        stats.getDynamic().getMod(Stats.LARGE_BALLISTIC_MOD).modifyFlat(id, -COST_REDUCTION_LG); // Overpriced weapons get a reduction,
//        stats.getDynamic().getMod(Stats.LARGE_ENERGY_MOD).modifyFlat(id, -COST_REDUCTION_LG); // Mainly to push the player to use Blade Breaker tech,
//        stats.getDynamic().getMod(Stats.MEDIUM_BALLISTIC_MOD).modifyFlat(id, -COST_REDUCTION_MED); // And to keep things feeling different and scary.
//        stats.getDynamic().getMod(Stats.MEDIUM_ENERGY_MOD).modifyFlat(id, -COST_REDUCTION_MED); // The more you feel like you're getting away with something,
//        stats.getDynamic().getMod(Stats.SMALL_BALLISTIC_MOD).modifyFlat(id, -COST_REDUCTION_SM); // The more fun their weapons are to use,
//        stats.getDynamic().getMod(Stats.SMALL_ENERGY_MOD).modifyFlat(id, -COST_REDUCTION_SM); // Well, that and the sweet-ass FX.
//        stats.getSuppliesPerMonth().modifyMult(id, SUPPLY_USE_MULT); // Got to pay the piper.
//        stats.getOverloadTimeMod().modifyMult(id, OVERLOAD_DUR_MULT); // And it comes at such a cost.

        //reduces armor effectiveness in half, in a similar manner to the invictus:
        stats.getEffectiveArmorBonus().modifyFlat(id, Eff_Armor_Reduce);
        stats.getMinArmorFraction().modifyMult(id, Eff_Armor_Reduce);//copied from ablative, not so sure on what it does though
    }


    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id)
    {

//        for (String tmp : BLOCKED_HULLMODS)
//        {
//            if (ship.getVariant().getHullMods().contains(tmp))
//            {
//                ship.getVariant().removeMod(tmp);
//            }
//        }

    }

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
        super.advanceInCombat(ship, amount);
//        if (!DefenseUtils.hasArmorDamage(ship)) {
//            return;//if no armor damage detected leaves the class
//
//        }
//        if (ship.isHulk()) return;//if ship is dead, doesnt regen
//        if (ship.getFluxTracker().isVenting() || ship.isPhased()) return;//if ship is venting or in phase doesnt regen
        //following code somehow returns time at least in kotlin, for those wanting a certain time limit on regen
//        ship.getMutableStats().getDynamic().getStat("ui_armortest").modifyFlat("nuller", -1f)
//        float timer = ship.getMutableStats().getDynamic().getStat("ui_armortest").getModifiedValue() + amount;
//        ship.getMutableStats().getDynamic().getStat("ui_armortest").modifyFlat("tracker", timer);
//        if (timer < DISRUPTION_TIME) return
//        ArmorGridAPI armorgrid  = ship.getArmorGrid();//dont know why this is here
        //ammount is the ammount of frames have been skipped since the last time the method was called.
        //amount is also likely the equivalent of delta, and is the time between frames.
        float[][] grid = ship.getArmorGrid().getGrid();
        float max = ship.getArmorGrid().getMaxArmorInCell();
//        val statusMult = if (ship.fluxTracker.isOverloaded) 0.5f else 1f //lowers regen if ship is overloaded.
//        float baseCell = max * ship.getHullSpec().getArmorRating();//i have no idea what this does. seems extraneous
        float repairAmount = max * (REGEN_PER_SEC_PERCENT / 100f) * amount;
//        Max_Regen_Rate=Max_Regen_Rate/15;//according to the API, the max value per cell is 1/15th of the value given to the player
//        if (repairAmount>Max_Regen_Rate){repairAmount=Max_Regen_Rate;}

        //grid.length // col
        //grid[0].length // row

        // Iterate through all armor cells and find any that aren't at max
        for (int x=0;  x==grid.length; x++) {
            for (int y=0;  y==grid[0].length; y++) {
                if (grid[x][y] < Max_Total_Regen*max) {
                    float regen = (grid[x][y] + repairAmount); //.coerceAtMost(max); sets the repair to be no more than max armor
                    ship.getArmorGrid().setArmorValue(x, y, regen);
                    log.debug("The armor loop does in fact loop.");
                }
//                else {return;}
            }
        }
        ship.syncWithArmorGridState();
    }

    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0) return "" + (int) (Eff_Armor_Reduce*100) + "%";
        if (index == 1) return "" + (int) REGEN_PER_SEC_PERCENT + "%";
        if (index == 2) return "" + (int) Math.round(Max_Regen_Rate) + "";
        return null;
    }



//    @Override
//    public boolean affectsOPCosts() {
//        return true;
//    }


}
