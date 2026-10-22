package com.fs.starfarer.api.impl.campaign.econ;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.intel.PerseanLeagueMembership;
import com.fs.starfarer.api.impl.campaign.intel.events.EstablishedPolityScript;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class CZIInternalInfrastructure extends BaseMarketConditionPlugin{
    public static float INCOME_REDUCTION = -0.1f;

    public static String NAME_BASE = "Established Polity";
    public static String NAME_LEAGUE = "League development programs";



    public void apply(String id) {
        String text = Misc.ucFirst(getName().toLowerCase());
//		if (PerseanLeagueMembership.isLeagueMember()) {
//			text = "New Persean League member";
//		}

        market.getIncomeMult().modifyPercent(id, INCOME_REDUCTION);
        //market.ind
    }

    public void unapply(String id) {
        market.getIncomeMult().unmodifyFlat(id);
    }

    @Override
    public void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
        EstablishedPolityScript script = EstablishedPolityScript.get();
        if (script == null) return;

        Color h = Misc.getHighlightColor();

        float opad = 10f;

        if (PerseanLeagueMembership.isLeagueMember()) {
            tooltip.addPara("Your colonies are recently joined members of the Persean League, resulting in an increased "
                    + "flow of commerce.", opad);
        } else {
            tooltip.addPara("A network of autonomous polities has allowed this planet to host an above average "
                    + "number of industries, at the cost of increased cost of business.", opad);
        }

        tooltip.addPara("%s Income Reduction.",
                opad, h,
                "+" + (int)Math.round(INCOME_REDUCTION * 100f) + "%");
    }

    @Override
    public boolean hasCustomTooltip() {
        return true;
    }


   /* @Override
    public String getName() {
        if (PerseanLeagueMembership.isLeagueMember()) {
            return NAME_LEAGUE;
        }
        return NAME_BASE;
    }


    @Override
    public String getIconName() {
        if (PerseanLeagueMembership.isLeagueMember()) {
            return Global.getSettings().getSpriteName("events", "new_league_member");
        }
        return super.getIconName();
    }
*/



}
