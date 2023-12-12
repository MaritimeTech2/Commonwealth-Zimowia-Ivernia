package com.fs.starfarer.api.impl.campaign.econ;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.Global;

import java.util.List;

// Market condition that increases defence with fewer planets in faction - RaiDz77
public class UITerraformingCorps extends BaseMarketConditionPlugin {

    public float GROUND_DEFENSE_BONUS = 0f;

    @Override
    public void apply(String id) {
        super.apply(id); //I don't fully understand what this does, but all market condition scripts have it, so we do too

        if (!market.getFactionId().contentEquals("ui")) { //don't apply this to any faction other than ours
            unapply(id);
            return;
        }

        // Decide the market buff based on faction size
        int dominanceScore = 0; //custom system that I'll use to determine the "value" of each planet
        List<MarketAPI> ui_markets = Misc.getFactionMarkets(Global.getSector().getFaction("ui"));

        if (!ui_markets.isEmpty()) { //don't know if this null check is necessary, but always good to be safe
            for (MarketAPI theMarket : ui_markets) { //this is called an enhanced "for" loop, it iterates through a list you give it
                int size = theMarket.getSize(); //get population size of the market

                switch (size) { //this is a switch, uh, too much to explain here
                    case 0: //size 1, should never happen but just to be sure
                        dominanceScore += 0;
                        break;
                    case 1: //size 1, should never happen but just to be sure
                        dominanceScore += 0;
                        break;
                    case 2: //size 2, should never happen but just to be sure
                        dominanceScore += 0;
                        break;
                    case 3:
                        dominanceScore += 1;
                        break;
                    case 4:
                        dominanceScore += 2;
                        break;
                    case 5:
                        dominanceScore += 4;
                        break;
                    case 6:
                        dominanceScore += 6;
                        break;
                    case 7:
                        dominanceScore += 7;
                        break;
                    default: //for size 8 or higher
                        dominanceScore += 8;
                        break;
                }
            }
        }

        // Select how much of a bonus to give the faction's ground defences
        if (dominanceScore <= 8) GROUND_DEFENSE_BONUS = 7f; //if statements without brackets will only consider the next "line" of code inside the if statement
        else if (dominanceScore <= 12) GROUND_DEFENSE_BONUS = 6f;
        else if (dominanceScore <= 16) GROUND_DEFENSE_BONUS = 5f;
        else if (dominanceScore <= 20) GROUND_DEFENSE_BONUS = 3.5f;
        else if (dominanceScore <= 24) GROUND_DEFENSE_BONUS = 1f;
        else GROUND_DEFENSE_BONUS = 0f;

        market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).modifyMult(id, 1f + GROUND_DEFENSE_BONUS, Misc.ucFirst(condition.getName().toLowerCase()));
    }

    @Override
    public boolean showIcon() {
        return market.getFactionId().contentEquals("ui");
    }

    @Override
    public void unapply(String id) {
        super.unapply(id);

        market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).unmodify(id);
    }

    @Override
    protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
        super.createTooltipAfterDescription(tooltip, expanded);

        if (market == null) {
            return;
        }

        tooltip.addPara("%s ground defenses",
                10f, Misc.getHighlightColor(),
                "+" + (int) Math.round(GROUND_DEFENSE_BONUS * 100f) + "%");
    }
}
