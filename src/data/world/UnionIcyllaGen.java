package data.world;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.procgen.Constellation;
import com.fs.starfarer.api.impl.campaign.procgen.NameGenData;
import com.fs.starfarer.api.impl.campaign.procgen.ProcgenUsedNames;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import data.world.systems.UIJasnazda;
import data.world.systems.UIRiftSystem;
import data.world.systems.UISwiatana;

public class UnionIcyllaGen { //implements SectorGeneratorPlugin {

        public void generate(SectorAPI sector) {

            SharedData.getData().getPersonBountyEventData().addParticipatingFaction("ui"); //what does this do lol

            //generate star systems
            StarSystemAPI jasnazda = new UIJasnazda().generate(sector);
            StarSystemAPI swiatana = new UISwiatana().generate(sector);
            new UIRiftSystem().generate(sector);

            //create constellation
            Constellation constellation = new Constellation(Constellation.ConstellationType.NORMAL, StarAge.AVERAGE);
            constellation.setNamePick(new ProcgenUsedNames.NamePick(new NameGenData("null", "null"), "Constellation Name", null));
            constellation.setLeavePickedNameUnused(true);
            constellation.setNameOverride("Okrzyk");
            //put the systems in the constellation
            constellation.getSystems().add(jasnazda);
            constellation.getSystems().add(swiatana);
            //yes, you have to identify it twice
            jasnazda.setConstellation(constellation);
            swiatana.setConstellation(constellation);

            //starting relations
            FactionAPI ui = sector.getFaction("ui");
            ui.setRelationship(Factions.PIRATES, -0.5f);
            ui.setRelationship(Factions.LUDDIC_PATH, -0.5f);
        }
}
