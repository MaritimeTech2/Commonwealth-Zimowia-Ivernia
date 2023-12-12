package com.fs.starfarer.api.impl.campaign.econ.impl;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;

//industry for mining the planet Pieklo
public class UIPiekloMining extends BaseIndustry {

    public void apply() {
        super.apply(true);

        if (Global.getSector().getMemory().getBoolean("$ui_piekloMiningIsActive")) {
            supply(Commodities.ORE, 15);
            supply(Commodities.RARE_ORE, 13);
        }

        demand(Commodities.HEAVY_MACHINERY, 4);
        demand(Commodities.DRUGS, 5);

        Pair<String, Integer> deficit = getMaxDeficit(Commodities.HEAVY_MACHINERY);
        applyDeficitToProduction(0, deficit,
                Commodities.ORE,
                Commodities.RARE_ORE);

        if (!isFunctional()) {
            supply.clear();
        }
    }


    @Override
    public void unapply() {
        super.unapply();
    }

    protected boolean hasPostDemandSection(boolean hasDemand, IndustryTooltipMode mode) {
        Pair<String, Integer> deficit = getMaxDeficit(Commodities.DRUGS);
        if (deficit.two <= 0) return false;
        //return mode == IndustryTooltipMode.NORMAL && isFunctional();
        return mode != IndustryTooltipMode.NORMAL || isFunctional();
    }

    @Override
    protected void addPostDemandSection(TooltipMakerAPI tooltip, boolean hasDemand, IndustryTooltipMode mode) {
        //if (mode == IndustryTooltipMode.NORMAL && isFunctional()) {
        if (mode != IndustryTooltipMode.NORMAL || isFunctional()) {
            Color h = Misc.getHighlightColor();
            float opad = 10f;
            float pad = 3f;

            Pair<String, Integer> deficit = getMaxDeficit(Commodities.DRUGS);
            if (deficit.two > 0) {
                tooltip.addPara(getDeficitText(Commodities.DRUGS) + ": %s units.", pad, h, "" + deficit.two);
            }
        }
    }


    @Override
    public boolean isAvailableToBuild() {
        if (!super.isAvailableToBuild()) return false;

        if (market.hasCondition("ui_pieklo_tidal_disrupture")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean showWhenUnavailable() {
        return false;
    }

    @Override
    public String getUnavailableReason() {
        if (!super.isAvailableToBuild()) return super.getUnavailableReason();

        return "Can only be built on the planet Pieklo";
    }

    @Override
    public String getCurrentImage() {
        return super.getCurrentImage();
    }

    @Override
    protected boolean canImproveToIncreaseProduction() {
        return true;
    }
}





