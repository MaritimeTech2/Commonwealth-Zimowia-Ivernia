package data.scripts.world;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.procgen.Constellation;
import com.fs.starfarer.api.impl.campaign.procgen.NameGenData;
import com.fs.starfarer.api.impl.campaign.procgen.ProcgenUsedNames;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import data.scripts.world.systems.UIEllenTrechend;
import data.scripts.world.systems.UIRiftSystem;
import data.scripts.world.systems.UIAlkanost;
import com.fs.starfarer.api.impl.campaign.GateEntityPlugin;
import data.scripts.world.systems.UISirin;


public class UnionIcyllaGen { //implements SectorGeneratorPlugin {

        public void generate(SectorAPI sector) {

            SharedData.getData().getPersonBountyEventData().addParticipatingFaction("ui"); //what does this do lol

            //generate star systems
            //StarSystemAPI ellentrechend = new UIEllenTrechend().generate(sector);
            //public static PLAYER_CAN_USE_GATE;
            new UIEllenTrechend().generate(sector);
            StarSystemAPI alkanost = new UIAlkanost().generate(sector);
            StarSystemAPI sirin = new UISirin().generate(sector);
            //new UIRiftSystem().generate(sector);

            //create constellation
            Constellation constellation = new Constellation(Constellation.ConstellationType.NORMAL, StarAge.AVERAGE);
            constellation.setNamePick(new ProcgenUsedNames.NamePick(new NameGenData("null", "null"), "Constellation Name", null));
            constellation.setLeavePickedNameUnused(true);
            constellation.setNameOverride("Okrzyk");
            //put the systems in the constellation
            //constellation.getSystems().add(ellentrechend);
            constellation.getSystems().add(alkanost);
            constellation.getSystems().add(sirin);
            //yes, you have to identify it twice
            ///ellentrechend.setConstellation(constellation);
            alkanost.setConstellation(constellation);
            sirin.setConstellation(constellation);

            //starting relations
            FactionAPI ui = sector.getFaction("ui");
            ui.setRelationship(Factions.PIRATES, -0.25f);
            ui.setRelationship(Factions.LUDDIC_PATH, -0.5f);
            ui.setRelationship(Factions.HEGEMONY, -0.3f);
            ui.setRelationship(Factions.TRITACHYON, -0.5f);
            ui.setRelationship(Factions.LUDDIC_CHURCH, -0.5f);
            ui.setRelationship(Factions.PERSEAN, 0.3f);
        }
}
