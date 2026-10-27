package data.oldjava;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.impl.campaign.ids.Entities;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;

public class UIRiftSystem {

    public StarSystemAPI generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Mysterious Rift");

        SectorEntityToken nebula = Misc.addNebulaFromPNG("data/campaign/terrain/valhalla_nebula.png",
                0, 0, // center of nebula
                system, // location to add to
                "terrain", "nebula", //"nebula_blue", // texture to use, uses xxx_map for map
                4, 4, StarAge.AVERAGE); // number of cells in texture

        system.setType(StarSystemGenerator.StarSystemType.NEBULA);

        system.getLocation().set(24000, 10000); //Northeast of core

        system.setBackgroundTextureFilename("graphics/backgrounds/background3.jpg");

        system.initNonStarCenter();

        //system.generateAnchorIfNeeded();

        //system.setDoNotShowIntelFromThisLocationOnMap(true);



        JumpPointAPI rift = Global.getFactory().createJumpPoint(
                "ui_spatial_rift",
                "Spatial Rift");
        rift.setLocation(0f, 0f);
        rift.setStandardWormholeToHyperspaceVisual();
        system.addEntity(rift);

        SectorEntityToken spatialRift = system.getEntityById("ui_spatial_rift");



//        PlanetAPI planet = system.addPlanet("ui_planet", spatialRift, "Entity", "barren",
//                0, // It's angular position around the star
//                10, // The planet radius
//                500, // The planet's orbit radius from the "focus"
//                0); // The orbital period in days (how fast it orbits)

        CustomCampaignEntityAPI beacon = system.addCustomEntity("ui_blacksite_warning_beacon", null, Entities.WARNING_BEACON, Factions.NEUTRAL);
        beacon.setCircularOrbitPointingDown(spatialRift, 0, 200, 30);
        beacon.getMemoryWithoutUpdate().set("$ui_blacksite_warning_beacon", true);

        SectorEntityToken blacksiteGate = system.addCustomEntity("ui_blacksite_gate", // unique id
                "Mysterious Gate", // name - if null, defaultName from custom_entities.json will be used
                "inactive_gate", // type of object, defined in custom_entities.json
                null); // faction
        blacksiteGate.setCircularOrbit(spatialRift, 0, 3000, 200);
        blacksiteGate.getMemoryWithoutUpdate().set("$ui_blacksite_gate", true);

        system.autogenerateHyperspaceJumpPoints(false, false);

        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 2f;

        float radius = 200;
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f);
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);

        return system;
    }
}
