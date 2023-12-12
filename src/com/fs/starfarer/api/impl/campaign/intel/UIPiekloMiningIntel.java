package com.fs.starfarer.api.impl.campaign.intel;

import java.awt.Color;
import java.util.Set;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.ui.SectorMapAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.UIPiekloMiningIntelScript;

public class UIPiekloMiningIntel extends BaseIntelPlugin {

    //MUST BE CHANGED IN UIPiekloMiningIntelScript and UIPiekloTidalDisrupture as well
    public float activeDays = 5f; //90f
    public float inactiveDays = 5f; //270f

    public UIPiekloMiningIntel() {
        Global.getSector().addScript(new UIPiekloMiningIntelScript(this));
    }

    @Override
    public void createIntelInfo(TooltipMakerAPI info, ListInfoMode mode) {
        Color c = getTitleColor(mode);
        info.addPara(getName(), c, 0f);
        addBulletPoints(info, mode);
    }

    @Override
    public void createSmallDescription(TooltipMakerAPI info, float width, float height) {
        Color h = Misc.getHighlightColor();
        float opad = 10f;

        MemoryAPI memory = Global.getSector().getMemory();
        CampaignClockAPI clock = Global.getSector().getClock();

        if (!memory.getBoolean("$ui_piekloMiningCanceledPermanently")) {
            info.addPara("The Union of Iskala sponsors periodic mining operations on the planet Pieklo." +
                    " Although mining operations are impossible during certain seasons, sector-wide ore markets are" +
                    " always greatly effected when the operation is in effect.", opad);
            info.addPara("You can keep track of this event by marking it as important", opad);
            info.addPara("If the mining operation is disrupted, or Kuznia no longer belongs" +
                    " to the Union of Iskala, the mining operation would likely be canceled permanently", opad);
        }
        else {
            info.addPara("The Union of Iskala has given up their attempts to mine Pieklo." +
                    " Although mining operations are impossible during certain seasons, sector-wide ore markets are" +
                    " always greatly effected when the mining operation is in effect.", opad);
        }

        if (memory.getBoolean("$ui_piekloMiningIsActive")) {
            float e = activeDays - clock.getElapsedDaysSince(memory.getLong("$ui_piekloMiningIntelTimestamp"));
            int daysLeftActive = Math.round(e);

            info.addPara("Days left for the current mining operation: " + daysLeftActive, opad, h, "" + daysLeftActive);
        }
        else {
            float e = inactiveDays - clock.getElapsedDaysSince(memory.getLong("$ui_piekloMiningIntelTimestamp"));
            int daysLeftActive = Math.round(e);

            info.addPara("Days until next mining operation: " + daysLeftActive, opad, h, "" + daysLeftActive);
        }
    }

    @Override
    public String getIcon() {
        return Global.getSettings().getSpriteName("intel", "ui_pieklo_mining");
    }

    @Override
    public Set<String> getIntelTags(SectorMapAPI map) {
        Set<String> tags = super.getIntelTags(map);

        for (String tag : tags) {
            if (tag.equals(Tags.INTEL_IMPORTANT) || tag.equals(Tags.INTEL_NEW)) {
                //do nothing
            }
            else {
                tags.remove(tag);
            }
        }
        if (Global.getSector().getEntityById("ui_pieklo").getMarket().getFactionId().equals("neutral")) {
            tags.add("ui");
        }
        else {
            tags.add(Global.getSector().getEntityById("ui_pieklo").getMarket().getFactionId());
        }

        return tags;
    }

    public String getName() {
        return "Pieklo Mining Operation";
    }

    @Override
    public FactionAPI getFactionForUIColors() {
        return super.getFactionForUIColors();
    }

    public String getSmallDescriptionTitle() {
        return getName();
    }

    @Override
    public SectorEntityToken getMapLocation(SectorMapAPI map) {
        return Global.getSector().getEntityById("ui_pieklo");
    }

    @Override
    public boolean shouldRemoveIntel() {
        return false;
    }

    @Override
    public String getCommMessageSound() {
        return "ui_discovered_entity";
    }
}







