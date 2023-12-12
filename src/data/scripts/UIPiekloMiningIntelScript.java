package data.scripts;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketConditionAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.impl.campaign.intel.UIPiekloMiningIntel;
import com.fs.starfarer.api.impl.campaign.intel.deciv.DecivTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static data.world.utilities.MarketCreateHelper.addMarketplace;

public class UIPiekloMiningIntelScript implements EveryFrameScript {

    //MUST BE CHANGED IN UIPiekloMiningIntel and UIPiekloTidalDisrupture as well
    public float activeDays = 5f; //90f
    public float inactiveDays = 5f; //270f

    UIPiekloMiningIntel intel;

    //public boolean done = false;

    public UIPiekloMiningIntelScript(UIPiekloMiningIntel miningIntel) {
        Global.getSector().getMemory().set("$ui_piekloMiningIntelTimestamp", Global.getSector().getClock().getTimestamp());
        Global.getSector().getMemory().set("$ui_piekloMiningIsActive", false);
        Global.getSector().getMemory().set("$ui_piekloMiningCanceledPermanently", false);
        //Global.getSector().getMemory().set("$ui_piekloMiningCanceledTemporarily", false);
        intel = miningIntel;
    }

    @Override
    public void advance(float amount) {

        MemoryAPI memory = Global.getSector().getMemory();

        // Check if mining should be marked as canceled permanently
        //does kuznia belong to the union?
        if (!Global.getSector().getEntityById("ui_kuznia").getMarket().getFactionId().equals("ui")) {
            memory.set("$ui_piekloMiningCanceledPermanently", true);
        }
        //has pieklo been satbombed or deciv?
        if (!Global.getSector().getEntityById("ui_pieklo").getMarket().getFactionId().equals("ui") && memory.getBoolean("$ui_piekloMiningIsActive")) {
            memory.set("$ui_piekloMiningCanceledPermanently", true);
        }
        //has the mining been raided?
        if (Global.getSector().getEntityById("ui_pieklo").getMarket().hasIndustry("ui_piekloMining")) {
            if (Global.getSector().getEntityById("ui_pieklo").getMarket().getIndustry("ui_piekloMining").isDisrupted()) {
                memory.set("$ui_piekloMiningCanceledPermanently", true);
            }
        }

        //if mining is canceled
        if (memory.getBoolean("$ui_piekloMiningCanceledPermanently")) { // || memory.getBoolean("$ui_piekloMiningCanceledTemporarily")
            if (!Global.getSector().getPersistentData().containsKey("UI_PiekloMiningCancelIntelSent")) {
                MarketAPI thisMarket = Global.getSector().getEntityById("ui_pieklo").getMarket();
                if (thisMarket != null && thisMarket.getFactionId().equals("ui")) {
                    DecivTracker.removeColony(thisMarket, false);
                }
                intel.sendUpdateIfPlayerHasIntel("listInfoParam", false);
                Global.getSector().getPersistentData().put("UI_PiekloMiningCancelIntelSent", "e");
            }
            //done = true;
        }
        else { //control timestamp timer
            if (memory.getBoolean("$ui_piekloMiningIsActive")) {
                //shut off mining
                if (activeDays < Global.getSector().getClock().getElapsedDaysSince(memory.getLong("$ui_piekloMiningIntelTimestamp"))) {

                    memory.set("$ui_piekloMiningIntelTimestamp", Global.getSector().getClock().getTimestamp());
                    memory.set("$ui_piekloMiningIsActive", false);
                    MarketAPI thisMarket = Global.getSector().getEntityById("ui_pieklo").getMarket();
                    if (thisMarket != null && thisMarket.getFactionId().equals("ui")) {
                        DecivTracker.removeColony(thisMarket, false);
                    }
                    intel.sendUpdateIfPlayerHasIntel("listInfoParam", true);
                }
            }
            else {
                //turn on mining
                if (inactiveDays < Global.getSector().getClock().getElapsedDaysSince(memory.getLong("$ui_piekloMiningIntelTimestamp"))) {

                    memory.set("$ui_piekloMiningIntelTimestamp", Global.getSector().getClock().getTimestamp());
                    memory.set("$ui_piekloMiningIsActive", true);
                    PlanetAPI pieklo = (PlanetAPI) Global.getSector().getEntityById("ui_pieklo");
                    List<MarketConditionAPI> conditions = pieklo.getMarket().getConditions();
                    List<String> conditionsToAdd = new ArrayList<>();
                    for (MarketConditionAPI mar : conditions) {
                        conditionsToAdd.add(mar.getId());
                    }
                    conditionsToAdd.add(Conditions.POPULATION_3);
                    //conditionsToAdd.add(Conditions.POPULATION_3);
                    if (pieklo.getMarket().getFactionId().equals("neutral")) {
                        MarketAPI piekloMarket = addMarketplace("ui", pieklo, null,
                                "Pieklo",
                                3,
                                conditionsToAdd,
                                Arrays.asList(
                                        Submarkets.SUBMARKET_OPEN,
                                        Submarkets.SUBMARKET_BLACK
                                ),
                                Arrays.asList(
                                        Industries.POPULATION,
                                        Industries.SPACEPORT,
                                        "ui_piekloMining"
                                ),
                                0.3f,
                                false,
                                false);
                        pieklo.getMarket().setSurveyLevel(MarketAPI.SurveyLevel.FULL);
                        intel.sendUpdateIfPlayerHasIntel("listInfoParam", true);
                    }
                }
            }
        }
    }

    @Override
    public boolean isDone() {
        return false; //done;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }
}
