package data.world;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import data.world.systems.UIJasnazda;
import data.world.systems.UISwiatana;

public class UnionIcyllaGen { //implements SectorGeneratorPlugin {

        public void generate(SectorAPI sector) {
            new UIJasnazda().generate(sector);
            new UISwiatana().generate(sector);

            FactionAPI ui = sector.getFaction("ui");

            //SharedData.getData().getPersonBountyEventData().addParticipatingFaction("ui"); //what does this do lol

            ui.setRelationship(Factions.PIRATES, -0.5f);
            ui.setRelationship(Factions.LUDDIC_PATH, -0.5f);
        }
}
