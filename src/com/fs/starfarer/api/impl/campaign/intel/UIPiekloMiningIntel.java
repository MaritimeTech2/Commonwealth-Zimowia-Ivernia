package com.fs.starfarer.api.impl.campaign.intel;

import java.awt.Color;
import java.util.Set;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignClockAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.ui.SectorMapAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class UIPiekloMiningIntel extends BaseIntelPlugin {

    public float activeDays = 5f; //90f
    public float inactiveDays = 5f; //270f

    @Override
    public void advance(float amount) {
        super.advance(amount);

        MemoryAPI memory = Global.getSector().getMemory();
        CampaignClockAPI clock = Global.getSector().getClock();

        if (!memory.contains("ui_piekloMiningIntelTimestamp")) {
            memory.set("ui_piekloMiningIntelTimestamp", Global.getSector().getClock().getTimestamp());
            memory.set("ui_piekloMiningIsActive", false);
        }

        if (memory.getBoolean("ui_piekloMiningIsActive")) {
            //shut off mining
            if (activeDays < clock.getElapsedDaysSince(memory.getLong("ui_piekloMiningIntelTimestamp"))) {
                memory.set("ui_piekloMiningIntelTimestamp", Global.getSector().getClock().getTimestamp());
                memory.set("ui_piekloMiningIsActive", false);
            }
        }
        else {
            //turn on mining
            if (inactiveDays < clock.getElapsedDaysSince(memory.getLong("ui_piekloMiningIntelTimestamp"))) {
                memory.set("ui_piekloMiningIntelTimestamp", Global.getSector().getClock().getTimestamp());
                memory.set("ui_piekloMiningIsActive", true);
            }
        }
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

        info.addPara("The Union of Iskala sponsors periodic mining operations on the planet Pieklo." +
                " Although mining operations are impossible during certain seasons, sector-wide ore markets are" +
                " always greatly effected when the operation is in effect.", opad);

        if (memory.getBoolean("ui_piekloMiningIsActive")) {
            float e = activeDays - clock.getElapsedDaysSince(memory.getLong("ui_piekloMiningIntelTimestamp"));
            int daysLeftActive = Math.round(e);

            info.addPara("Days left for this mining operation: " + daysLeftActive, opad, h, "" + daysLeftActive);
        }
        else {
            float e = inactiveDays - clock.getElapsedDaysSince(memory.getLong("ui_piekloMiningIntelTimestamp"));
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

        tags.add("ui");

        return tags;
    }

    public String getSortString() {
        return "Pieklo Mining Operation";
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







