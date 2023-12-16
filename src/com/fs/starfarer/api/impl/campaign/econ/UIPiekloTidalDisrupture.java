package com.fs.starfarer.api.impl.campaign.econ;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

//condition for the planet Pieklo
public class UIPiekloTidalDisrupture extends BaseMarketConditionPlugin {

    //MUST BE CHANGED IN UIPiekloMiningIntel and UIPiekloMiningIntelScript as well
    public float activeDays = 5f; //90f
    public float inactiveDays = 5f; //270f

    @Override
    public void apply(String id) {
        super.apply(id);

        market.getHazard().modifyFlat(id, 2f, "Pieklo's Tidal Disrupture");
    }

    @Override
    public boolean showIcon() {
        return true;
    }

    @Override
    public void unapply(String id) {
        super.unapply(id);

        market.getHazard().unmodifyFlat(id);
    }

    @Override
    protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
        super.createTooltipAfterDescription(tooltip, expanded);

        if (market == null) {
            return;
        }

        tooltip.addPara("%s hazard rating",
                10f, Misc.getHighlightColor(),
                "+200%");

        if (Global.getSector().getMemory().getBoolean("$ui_piekloMiningIsActive")) {
            float e = activeDays - Global.getSector().getClock().getElapsedDaysSince(Global.getSector().getMemory().getLong("$ui_piekloMiningIntelTimestamp"));
            int daysLeftActive = Math.round(e);

            tooltip.addPara("Days left in the current mining season: %s",
                    10f, Misc.getHighlightColor(),
                    "" + daysLeftActive);
        }
        else {
            float e = inactiveDays - Global.getSector().getClock().getElapsedDaysSince(Global.getSector().getMemory().getLong("$ui_piekloMiningIntelTimestamp"));
            int daysLeftActive = Math.round(e);

            tooltip.addPara("Days left until next mining season: %s",
                    10f, Misc.getHighlightColor(),
                    "" + daysLeftActive);
        }


    }
}
