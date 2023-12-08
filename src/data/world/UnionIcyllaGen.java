package data.world;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.impl.campaign.ids.Voices;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import data.world.systems.Aeki;
import data.world.systems.Kaeus;
public class UnionIcyllaGen {



//implements SectorGeneratorPlugin {

        public void generate(SectorAPI sector) {
            new Aeki().generate(sector);
            new Kaeus().generate(sector);
            FactionAPI ui = sector.getFaction("ui");
            //new Aeki().generate(Global.getSector());

            SharedData.getData().getPersonBountyEventData().addParticipatingFaction("ui");

            ui.setRelationship(Factions.INDEPENDENT, -0.2f);

        }
}
