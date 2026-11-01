package data.scripts;

import com.fs.starfarer.api.campaign.CharacterDataAPI;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.CharacterStatsSkillEffect;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.impl.campaign.ids.Voices;
//import com.fs.starfarer.api.impl.campaign.intel.UIPiekloMiningIntel;
import data.scripts.world.AtrimpasSwitcherNex;
import data.scripts.world.Commonwealth_ZI_Gen;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
//import exerelin.campaign.SectorManager;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import exerelin.campaign.PlayerFactionStore;
import exerelin.campaign.SectorManager;
import exerelin.campaign.intel.BuyColonyIntel;

public class CZIModPlugin extends BaseModPlugin {
    public static boolean isExerelin = false;
    //    @Override
    //    public void onApplicationLoad() throws Exception {
 //       super.onApplicationLoad();

        // Test that the .jar is loaded and working, using the most obnoxious way possible.
//        throw new RuntimeException("Template mod loaded! Remove this crash in TemplateModPlugin.");
//    }

    @Override
    public void onApplicationLoad() {
        if(Global.getSettings().getModManager().isModEnabled("nexerelin"))isExerelin = true;
    }
    @Override
    public void onNewGame() {
        super.onNewGame();
       //The code below requires that Nexerelin is added as a library (not a dependency, it's only needed to compile the mod).
        boolean isNexerelinEnabled = Global.getSettings().getModManager().isModEnabled("nexerelin");
        //gets data for corvus mode ?(whats corvus mode)?
      if (!isNexerelinEnabled || SectorManager.getManager().isCorvusMode()) {
          new Commonwealth_ZI_Gen().generate(Global.getSector()); //create systems and do relations stuff
          Global.getSector().addScript(new AtrimpasSwitcherNex());
          return;
        }
        //create star systems in nexerelin NON-random sector generation (ie, non corvus?)
        //boolean isNexerelinEnabled = Global.getSettings().getModManager().isModEnabled("nexerelin");
        if (!isNexerelinEnabled || !Global.getSector().getMemoryWithoutUpdate().getBoolean("$nex_randomSector")) {
            new Commonwealth_ZI_Gen().generate(Global.getSector()); //create systems and do relations stuff
        }
        else{
            new Commonwealth_ZI_Gen().generate(Global.getSector()); //create systems and do relations stuff
        }
    }


    @Override
    public void onGameLoad(boolean newGame) {
        /*if (!Global.getSector().hasScript(UITerraformingCorpsApplier.class)) {
            Global.getSector().addScript(new UITerraformingCorpsApplier()); //add a new EveryFrameScript
        }*/

        boolean isNexerelinEnabled = Global.getSettings().getModManager().isModEnabled("nexerelin");

        /*if (!isNexerelinEnabled || SectorManager.getManager().isCorvusMode()) {
            if (Global.getSector().getEntityById("ui_pieklo") != null) {
                if (!Global.getSector().getPersistentData().containsKey("PiekloMiningIntelCreated")) {
                    Global.getSector().getIntelManager().addIntel(new UIPiekloMiningIntel()); //intel for the Pieklo Mining Operation
                    Global.getSector().getPersistentData().put("PiekloMiningIntelCreated", "e");
                }
            }
        }*/
    }

    @Override
    public void onNewGameAfterEconomyLoad() {

        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
        MarketAPI market = null;

        market = Global.getSector().getEconomy().getMarket("czi_zimowia"); //MAKE SURE to add _market onto the end of the string, due to how MarketCreateHelper works
        if (market != null) {
            //adds the king kazimir
            PersonAPI czi_kazimir = Global.getFactory().createPerson();
            czi_kazimir.setId("czi_kazimir");
            czi_kazimir.setFaction("czi");
            czi_kazimir.setGender(FullName.Gender.MALE);
            czi_kazimir.setRankId(Ranks.FACTION_LEADER);
            czi_kazimir.setPostId(Ranks.POST_FACTION_LEADER);
            czi_kazimir.setImportance(PersonImportance.VERY_HIGH);
            czi_kazimir.getName().setFirst("Kazimir");
            czi_kazimir.getName().setLast("Vasilenko");
            czi_kazimir.addTag(Tags.CONTACT_MILITARY);
            czi_kazimir.setVoice(Voices.SOLDIER); //their default dialog, for vanilla missions, etc
            czi_kazimir.setPortraitSprite("graphics/portraits/characters/czi_kazimir.png"); //this is wrong, enter the sprite into settings.json

            ip.addPerson(czi_kazimir);
            market.addPerson(czi_kazimir);
            market.getCommDirectory().addPerson(czi_kazimir);
            market.setAdmin(czi_kazimir);
        }
        market = Global.getSector().getEconomy().getMarket("czi_agropelter"); //MAKE SURE to add _market onto the end of the string, due to how MarketCreateHelper works
        if (market != null) {
            //adds princess Anzhelika
            PersonAPI czi_anzhelika = Global.getFactory().createPerson();
            czi_anzhelika.setId("czi_anzhelika");
            czi_anzhelika.setFaction("czi");
            czi_anzhelika.setGender(FullName.Gender.FEMALE);
            czi_anzhelika.setRankId("ui_crown_princess");
            czi_anzhelika.setPostId("ui_crown_princess");
            czi_anzhelika.setImportance(PersonImportance.VERY_HIGH);
            czi_anzhelika.getName().setFirst("Anzhelika");
            czi_anzhelika.getName().setLast("Vasilenko");
            czi_anzhelika.addTag(Tags.CONTACT_MILITARY);
            czi_anzhelika.setVoice(Voices.SOLDIER); //their default dialog, for vanilla missions, etc
            czi_anzhelika.setPortraitSprite("graphics/portraits/characters/czi_anzhelika.png"); //this is wrong, enter the sprite into settings.json

            ip.addPerson(czi_anzhelika);
            market.addPerson(czi_anzhelika);
            market.getCommDirectory().addPerson(czi_anzhelika);
            //market.setAdmin(czi_anzhelika);
        }
        market = Global.getSector().getEconomy().getMarket("czi_ivernia"); //MAKE SURE to add _market onto the end of the string, due to how MarketCreateHelper works
        if (market != null) {
            //adds prince zygfryd
            PersonAPI czi_zygfryd = Global.getFactory().createPerson();
            czi_zygfryd.setId("czi_zygfryd");
            czi_zygfryd.setFaction("czi");
            czi_zygfryd.setGender(FullName.Gender.MALE);
            czi_zygfryd.setRankId("ui_terra_brother");
            czi_zygfryd.setPostId("ui_terra_brother");
            czi_zygfryd.setImportance(PersonImportance.VERY_HIGH);
            czi_zygfryd.getName().setFirst("Zygfryd");
            czi_zygfryd.getName().setLast("Vasilenko");
            czi_zygfryd.addTag(Tags.CONTACT_SCIENCE);
            czi_zygfryd.setVoice(Voices.SCIENTIST); //their default dialog, for vanilla missions, etc
            czi_zygfryd.setPortraitSprite("graphics/portraits/characters/czi_zygfryd.png"); //this is wrong, enter the sprite into settings.json

            ip.addPerson(czi_zygfryd);
            market.addPerson(czi_zygfryd);
            market.getCommDirectory().addPerson(czi_zygfryd);
            //market.setAdmin(czi_zygfryd);
        }
    }

    @Override
    public void onNewGameAfterTimePass() {
        SectorAPI sector = Global.getSector();
        boolean haveNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");

        // Handle Nex governorship event
        if (haveNexerelin && SectorManager.getManager().isCorvusMode()
                && AtrimpasSwitcherNex.Commonwealth.equals(PlayerFactionStore.getPlayerFactionIdNGC())) {
            SectorEntityToken Atrimpas = Global.getSector().getEntityById(AtrimpasSwitcherNex.Atrimpas);
            if (Atrimpas == null) return;
            MarketAPI market = Atrimpas.getMarket();
            if (market != null) {
                BuyColonyIntel intel = new BuyColonyIntel(market.getFactionId(), market);
                intel.init();
            }
        }
    }
    // ======================================================
    //          HELPER METHODS (from Mayasuran Navy)
    // ======================================================
    /*private static void setExerelin(boolean bool) {
        isExerelin = bool;
    }

    private static void initMN() {
        if (isExerelin && !SectorManager.getManager().isCorvusMode()) {
            return;
        }
        new Commonwealth_ZI_Gen().generate(Global.getSector());
    }*/

}
