package data.scripts;

import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.impl.campaign.ids.Voices;
import com.fs.starfarer.api.impl.campaign.intel.UIPiekloMiningIntel;
import data.scripts.world.UnionIcyllaGen;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import exerelin.campaign.SectorManager;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;

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
        }
    }

    @Override
    public void onGameLoad(boolean newGame) {
        if (!Global.getSector().hasScript(UITerraformingCorpsApplier.class)) {
            Global.getSector().addScript(new UITerraformingCorpsApplier()); //add a new EveryFrameScript
        }

        boolean isNexerelinEnabled = Global.getSettings().getModManager().isModEnabled("nexerelin");

        if (!isNexerelinEnabled || SectorManager.getManager().isCorvusMode()) {
            if (Global.getSector().getEntityById("ui_pieklo") != null) {
                if (!Global.getSector().getPersistentData().containsKey("PiekloMiningIntelCreated")) {
                    Global.getSector().getIntelManager().addIntel(new UIPiekloMiningIntel()); //intel for the Pieklo Mining Operation
                    Global.getSector().getPersistentData().put("PiekloMiningIntelCreated", "e");
                }
            }
        }
    }

    @Override
    public void onNewGameAfterEconomyLoad() {

        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
        MarketAPI market = null;

        market = Global.getSector().getEconomy().getMarket("ui_iskala"); //MAKE SURE to add _market onto the end of the string, due to how MarketCreateHelper works
        if (market != null) {
            PersonAPI ui_kazimir = Global.getFactory().createPerson();
            ui_kazimir.setId("ui_kazimir");
            ui_kazimir.setFaction("ui");
            ui_kazimir.setGender(FullName.Gender.MALE);
            ui_kazimir.setRankId(Ranks.FACTION_LEADER);
            ui_kazimir.setPostId(Ranks.POST_FACTION_LEADER);
            ui_kazimir.setImportance(PersonImportance.VERY_HIGH);
            ui_kazimir.getName().setFirst("Kazimir");
            ui_kazimir.getName().setLast("Vasilenko");
            ui_kazimir.addTag(Tags.CONTACT_MILITARY);
            ui_kazimir.setVoice(Voices.SOLDIER); //their default dialog, for vanilla missions, etc
            ui_kazimir.setPortraitSprite("graphics/portraits/characters/ui_kazimir.png"); //this is wrong, enter the sprite into settings.json

            ip.addPerson(ui_kazimir);
            market.addPerson(ui_kazimir);
            market.getCommDirectory().addPerson(ui_kazimir);
            market.setAdmin(ui_kazimir);
        }
        market = Global.getSector().getEconomy().getMarket("ui_kuznia"); //MAKE SURE to add _market onto the end of the string, due to how MarketCreateHelper works
        if (market != null) {
            PersonAPI ui_anzhelika = Global.getFactory().createPerson();
            ui_anzhelika.setId("ui_anzhelika");
            ui_anzhelika.setFaction("ui");
            ui_anzhelika.setGender(FullName.Gender.FEMALE);
            ui_anzhelika.setRankId("ui_crown_princess");
            ui_anzhelika.setPostId("ui_crown_princess");
            ui_anzhelika.setImportance(PersonImportance.VERY_HIGH);
            ui_anzhelika.getName().setFirst("Anzhelika");
            ui_anzhelika.getName().setLast("Vasilenko");
            ui_anzhelika.addTag(Tags.CONTACT_MILITARY);
            ui_anzhelika.setVoice(Voices.SOLDIER); //their default dialog, for vanilla missions, etc
            ui_anzhelika.setPortraitSprite("graphics/portraits/characters/ui_anzhelika.png"); //this is wrong, enter the sprite into settings.json

            ip.addPerson(ui_anzhelika);
            market.addPerson(ui_anzhelika);
            market.getCommDirectory().addPerson(ui_anzhelika);
            market.setAdmin(ui_anzhelika);
        }
        market = Global.getSector().getEconomy().getMarket("ui_ladnysiez"); //MAKE SURE to add _market onto the end of the string, due to how MarketCreateHelper works
        if (market != null) {
            PersonAPI ui_zygfryd = Global.getFactory().createPerson();
            ui_zygfryd.setId("ui_anzhelika");
            ui_zygfryd.setFaction("ui");
            ui_zygfryd.setGender(FullName.Gender.MALE);
            ui_zygfryd.setRankId("ui_terra_brother");
            ui_zygfryd.setPostId("ui_terra_brother");
            ui_zygfryd.setImportance(PersonImportance.VERY_HIGH);
            ui_zygfryd.getName().setFirst("Zygfryd");
            ui_zygfryd.getName().setLast("Vasilenko");
            ui_zygfryd.addTag(Tags.CONTACT_SCIENCE);
            ui_zygfryd.setVoice(Voices.SCIENTIST); //their default dialog, for vanilla missions, etc
            ui_zygfryd.setPortraitSprite("graphics/portraits/characters/ui_zygfryd.png"); //this is wrong, enter the sprite into settings.json

            ip.addPerson(ui_zygfryd);
            market.addPerson(ui_zygfryd);
            market.getCommDirectory().addPerson(ui_zygfryd);
            market.setAdmin(ui_zygfryd);
        }
    }
}
