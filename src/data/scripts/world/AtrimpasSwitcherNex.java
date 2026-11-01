package data.scripts.world;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CommDirectoryEntryAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import data.scripts.CZIModPlugin;
import exerelin.ExerelinConstants;
import exerelin.campaign.ColonyManager;
import exerelin.campaign.PlayerFactionStore;
import exerelin.campaign.SectorManager;
import exerelin.utilities.NexUtilsReputation;

public class AtrimpasSwitcherNex implements EveryFrameScript{

    public static final String Commonwealth = "czi";
    public static final String Atrimpas = "czi_atrimpas";

    private boolean done = false;
    //private boolean setRep = false;
    private SectorEntityToken AtrimpasEntity = null;
    private MarketAPI market = null;

    @Override
    public void advance(float amount) {
        if (!done) {
            //grabs atrimpas entity
            AtrimpasEntity = Global.getSector().getEntityById(Atrimpas);
            //keeping this here because i may need it to set player faction reps
            /*if (!setRep) {
                FactionAPI mayasura = Global.getSector().getFaction(MAYASURA);
                SharedData.getData().getPersonBountyEventData().addParticipatingFaction(MAYASURA);

                // friends
                mayasura.setRelationship("persean", 1f);         // always together, friends 'til forever
                mayasura.setRelationship("kadur_remnant", 0.2f);        // we both saw our homes destroyed by the Hegemony

                // neutral
                mayasura.setRelationship("sindrian_diktat", 0f);        // my enemy's enemy is awfully close to my enemy

                // enemies
                mayasura.setRelationship("hegemony", -1f);              // death to the hegemon
                mayasura.setRelationship("luddic_church", -1f);         // you let it happen
                mayasura.setRelationship("luddic_path", -1f);           // unforgivable

                setRep = true;
            }*/
            //nessecary to set market as atrimpas
            if (AtrimpasEntity != null) {
                //AtrimpasEntity.setCustomDescriptionId("planet_mairaath_mayasura");//unnesecary, but keeping it here just incase
                market = AtrimpasEntity.getMarket();
            }

            if (market != null && !market.isPlanetConditionMarketOnly() && !market.isPlayerOwned()) {

                //unnessecary lines of code used to change maraaith to fit mayasuran navy mod desires
                /*AtrimpasEntity.setFaction(Commonwealth);
                market.setFactionId(Commonwealth);
                market.removeSubmarket(Submarkets.SUBMARKET_OPEN);
                market.addSubmarket(Submarkets.SUBMARKET_OPEN);
                market.addSubmarket(Submarkets.GENERIC_MILITARY);
                market.addIndustry(Industries.HEAVYINDUSTRY);
                market.addIndustry(Industries.MILITARYBASE);
                market.addIndustry(Industries.GROUNDDEFENSES);
                market.addIndustry("elitemayasuranguard");
                market.getMemoryWithoutUpdate().set("$startingFactionId", Commonwealth);

                for (CommDirectoryEntryAPI dir : market.getCommDirectory().getEntriesCopy()) {
                    if (dir.getType() != CommDirectoryEntryAPI.EntryType.PERSON) {
                        continue;
                    }
                    PersonAPI person = (PersonAPI) dir.getEntryData();
                    person.setFaction(Commonwealth);
                }*/

                // Let player govern atrimpas
                if (CZIModPlugin.isExerelin) {
                    market.getMemoryWithoutUpdate().set(ExerelinConstants.MEMKEY_MARKET_STARTING_FACTION, market.getFactionId());
                    market.getMemoryWithoutUpdate().set("$startingFactionId", Commonwealth);
                    if (Commonwealth.equals(PlayerFactionStore.getPlayerFactionIdNGC())) {
                        market.setPlayerOwned(true);
                        SectorManager.updateSubmarkets(market, Factions.PLAYER, Factions.PLAYER);
                        FactionAPI player = Global.getSector().getPlayerFaction();
                        ColonyManager.reassignAdminIfNeeded(market, player, player);
                        NexUtilsReputation.syncPlayerRelationshipsToFaction();
                    }

                }

                done = true;
            }
        }
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public boolean runWhilePaused() {
        return true;
    }
}
