package data.world;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import data.world.systems.UIJasnazda;

public class UnionIcyllaGen {



//implements SectorGeneratorPlugin {

        public void generate(SectorAPI sector) {
            new UIJasnazda().generate(sector);
            //new Kaeus().generate(sector);
            FactionAPI ui = sector.getFaction("ui");
            //new Aeki().generate(Global.getSector());

            SharedData.getData().getPersonBountyEventData().addParticipatingFaction("ui");

            ui.setRelationship(Factions.INDEPENDENT, -0.2f);

        }
}
