package data.scripts;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.List;

// Applies the TerraformingCorps market condition to every Iskalan market, the
// point of this script is to account for nexerelin invasions, colonization, etc - RaiDz77
public class UITerraformingCorpsApplier implements EveryFrameScript {

    public int i = 0;

    @Override
    public void advance(float amount) {
        if (i < 60) { //we don't need to have the whole script run EVERY frame, this makes it run every 60 frames (if the player has not changed fps settings)
            i++;
            return;
        }

        i = 0;

        List<MarketAPI> ui_markets = Misc.getFactionMarkets(Global.getSector().getFaction("ui"));
        for (MarketAPI market : ui_markets) {
            if (market.hasCondition("ui_terraforming_corps")) continue;
            else {
                market.addCondition("ui_terraforming_corps");
            }
        }
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }
}
