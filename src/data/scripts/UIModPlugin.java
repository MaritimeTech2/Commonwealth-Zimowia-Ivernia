package data.scripts;

import com.fs.starfarer.api.impl.campaign.intel.UIPiekloMiningIntel;
import data.world.UnionIcyllaGen;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import exerelin.campaign.SectorManager;

public class UIModPlugin extends BaseModPlugin {
//    @Override
//    public void onApplicationLoad() throws Exception {
 //       super.onApplicationLoad();

        // Test that the .jar is loaded and working, using the most obnoxious way possible.
//        throw new RuntimeException("Template mod loaded! Remove this crash in TemplateModPlugin.");
//    }

    @Override
    public void onNewGame() {
        super.onNewGame();

        //The code below requires that Nexerelin is added as a library (not a dependency, it's only needed to compile the mod).
        boolean isNexerelinEnabled = Global.getSettings().getModManager().isModEnabled("nexerelin");

        if (!isNexerelinEnabled || SectorManager.getManager().isCorvusMode()) {
            new UnionIcyllaGen().generate(Global.getSector()); //create systems and do relations stuff
            Global.getSector().getIntelManager().addIntel(new UIPiekloMiningIntel()); //intel for the Pieklo Mining Operation
        }
    }

    @Override
    public void onGameLoad(boolean newGame) {
        if (!Global.getSector().hasScript(UITerraformingCorpsApplier.class)) {
            Global.getSector().addScript(new UITerraformingCorpsApplier()); //add a new EveryFrameScript
        }
    }

    @Override
    public void onNewGameAfterEconomyLoad() {

//        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
//        MarketAPI market = null;
//
//        market = Global.getSector().getEconomy().getMarket("ui_Icylla_market"); //MAKE SURE to add _market onto the end of the string, due to how MarketCreateHelper works
//        if (market != null) {
//            PersonAPI person = Global.getFactory().createPerson();
//            person.setId("ui_kazimir");
//            person.setFaction("ui");
//            person.setGender(FullName.Gender.MALE);
//            person.setRankId(Ranks.FACTION_LEADER);
//            person.setPostId(Ranks.POST_FACTION_LEADER);
//            person.setImportance(PersonImportance.VERY_HIGH);
//            person.getName().setFirst("Kazimir");
//            person.getName().setLast("lastname");
//            person.addTag(Tags.CONTACT_MILITARY);
//            person.setVoice(Voices.SOLDIER); //their default dialog, for vanilla missions, etc
//            person.setPortraitSprite("graphics/portraits/characters/ui_kazimir.png"); //this is wrong, enter the sprite into settings.json
//
//            ip.addPerson(person);
//            market.addPerson(person);
//            market.getCommDirectory().addPerson(person);
//            market.setAdmin(person);
//        }
    }
}
