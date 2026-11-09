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
import data.scripts.world.systems.CZIEllenTrechend;
import data.scripts.world.systems.CZIAlkanost;
import data.scripts.world.systems.CZIPuca;
import data.scripts.world.systems.CZISirin;


    public class Commonwealth_ZI_Gen { //implements SectorGeneratorPlugin {

        public void generate(SectorAPI sector) {

            SharedData.getData().getPersonBountyEventData().addParticipatingFaction("ui"); //what does this do lol

            //generate star systems
            //StarSystemAPI ellentrechend = new UIEllenTrechend().generate(sector);
            new CZIEllenTrechend().generate(sector);
            StarSystemAPI alkanost = new CZIAlkanost().generate(sector);
            StarSystemAPI sirin = new CZISirin().generate(sector);
            StarSystemAPI puca = new CZIPuca().generate(sector);
            //new UIRiftSystem().generate(sector);

            //create constellation
            Constellation constellation = new Constellation(Constellation.ConstellationType.NEBULA, StarAge.AVERAGE);
            constellation.setNamePick(new ProcgenUsedNames.NamePick(new NameGenData("null", "null"), "Constellation Name", null));
            constellation.setLeavePickedNameUnused(true);
            constellation.setNameOverride("Okrzyk");
            //put the systems in the constellation
            //constellation.getSystems().add(ellentrechend);
            constellation.getSystems().add(alkanost);
            constellation.getSystems().add(sirin);
            constellation.getSystems().add(puca);
            //yes, you have to identify it twice
            ///ellentrechend.setConstellation(constellation);
            alkanost.setConstellation(constellation);
            sirin.setConstellation(constellation);
            puca.setConstellation(constellation);

            //starting relations
            FactionAPI czi = sector.getFaction("czi");
            czi.setRelationship(Factions.PIRATES, -0.4f);
            czi.setRelationship(Factions.LUDDIC_PATH, -0.5f);
            czi.setRelationship(Factions.HEGEMONY, -0.3f);
            czi.setRelationship(Factions.TRITACHYON, -0.5f);
            czi.setRelationship(Factions.LUDDIC_CHURCH, -0.5f);
            czi.setRelationship(Factions.PERSEAN, 0.35f);
            czi.setRelationship(Factions.INDEPENDENT, 0.1f);
        }
}
