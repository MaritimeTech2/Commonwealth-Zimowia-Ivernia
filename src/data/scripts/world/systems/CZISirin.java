package data.scripts.world.systems;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.impl.MusicPlayerPluginImpl;
import com.fs.starfarer.api.impl.campaign.GateEntityPlugin;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.MagneticFieldTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;
import com.fs.starfarer.api.campaign.SectorEntityToken;

public class CZISirin {
    public StarSystemAPI generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Sirin");

        system.setType(StarSystemGenerator.StarSystemType.SINGLE);

        ///system.getLocation().set(24000, 14000); //Northeast of core (static)
        //system.getLocation().set(15000, 30000); // Far Northeast of core (static)
        float w = Global.getSettings().getFloat("sectorWidth");
        float h = Global.getSettings().getFloat("sectorHeight");
        system.getLocation().set( ( (w/2f) - 9500f), ( (h/2f) - 7000f) );

        system.setBackgroundTextureFilename("graphics/backgrounds/background3.jpg");

        system.getMemoryWithoutUpdate().set(MusicPlayerPluginImpl.MUSIC_SET_MEM_KEY, "czi_starsystem_01");

        // create the star and generate the hyperspace anchor for this system
        PlanetAPI sirinstar = system.initStar("Sirin", // unique id for this star
                "star_white", // id in planets.json
                750f, // radius (in pixels at default zoom)
                250); // corona radius, from star edge
        system.setLightColor(new Color(245, 225, 145)); // light color in entire system, affects all entities

        PlanetAPI sirina = system.addPlanet("czi_sirina",
                sirinstar,
                "Sinir",
                "arid",
                360f * (float) Math.random(),
                160f,
                2000f,
                320f);
        sirina.getMarket().addCondition(Conditions.THIN_ATMOSPHERE);
        sirina.getMarket().addCondition(Conditions.HOT);
        sirina.getMarket().addCondition(Conditions.HABITABLE);
        sirina.getMarket().addCondition(Conditions.FARMLAND_POOR);
        sirina.getMarket().addCondition(Conditions.ORGANICS_ABUNDANT);
        sirina.getMarket().addCondition(Conditions.ORE_ABUNDANT);

        PlanetAPI sirin1 = system.addPlanet("czi_sirin1",
                sirinstar,
                "Sirin I",
                "terran-eccentric",
                360f * (float) Math.random(),
                140f,
                4500f,
                320f);
        sirin1.getMarket().addCondition(Conditions.HABITABLE);
        sirin1.getMarket().addCondition(Conditions.ORE_SPARSE);
        sirin1.getMarket().addCondition(Conditions.ORGANICS_TRACE);
        sirin1.getMarket().addCondition(Conditions.FARMLAND_ADEQUATE);

        PlanetAPI sirin2 = system.addPlanet("czi_sirin2",
                sirinstar,
                "Sirin II",
                "toxic",
                360f * (float) Math.random(),
                140f,
                6000f,
                340);
        sirin2.getMarket().addCondition(Conditions.TECTONIC_ACTIVITY);
        sirin2.getMarket().addCondition(Conditions.TOXIC_ATMOSPHERE);
        sirin2.getMarket().addCondition(Conditions.DENSE_ATMOSPHERE);
        sirin2.getMarket().addCondition(Conditions.ORE_RICH);
        sirin2.getMarket().addCondition(Conditions.RARE_ORE_ABUNDANT);
        sirin2.getMarket().addCondition(Conditions.VOLATILES_ABUNDANT);

        PlanetAPI sirin3 = system.addPlanet("czi_sirin3",
                sirinstar,
                "Sirin III",
                "rocky_metallic",
                240f,
                110f,
                8000f,
                460f);
        sirin3.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        sirin3.getMarket().addCondition(Conditions.ORE_SPARSE);
        sirin3.getMarket().addCondition(Conditions.RARE_ORE_ABUNDANT);

        PlanetAPI sirin4 = system.addPlanet("czi_sirin4",
                sirinstar,
                "Sirin IV",
                "gas_giant",
                210f,
                370f,
                12000f,
                900f);
        sirin4.getMarket().addCondition(Conditions.HIGH_GRAVITY);
        sirin4.getMarket().addCondition(Conditions.DENSE_ATMOSPHERE);
        sirin4.getMarket().addCondition(Conditions.VOLATILES_DIFFUSE);
        sirin4.getSpec().setPlanetColor(new Color(150, 194, 194, 255));
        sirin4.getSpec().setAtmosphereColor(new Color(116, 116, 33, 255));
        sirin4.getSpec().setAtmosphereThickness(0.4f);
        sirin4.getSpec().setCloudRotation(5f);
        sirin4.getSpec().setCloudColor(new Color(129, 3, 13, 245));
        sirin4.applySpecChanges();

        PlanetAPI sirin5 = system.addPlanet("czi_sirin5",
                sirin4,
                "Sirin IIIa",
                "barren",
                240f,
                90f,
                900f,
                40f);
        sirin5.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        sirin5.getMarket().addCondition(Conditions.LOW_GRAVITY);
        sirin5.getMarket().addCondition(Conditions.ORE_ULTRARICH);
        sirin5.getMarket().addCondition(Conditions.RARE_ORE_SPARSE);

        PlanetAPI sirin6 = system.addPlanet("czi_sirin6",
                sirin4,
                "Sirin IIIb",
                "cryovolcanic",
                200f,
                70f,
                1800f,
                60f);
        sirin6.getMarket().addCondition(Conditions.THIN_ATMOSPHERE);
        sirin6.getMarket().addCondition(Conditions.COLD);
        sirin6.getMarket().addCondition(Conditions.LOW_GRAVITY);
        sirin6.getMarket().addCondition(Conditions.ORE_SPARSE);
        sirin6.getMarket().addCondition(Conditions.VOLATILES_ABUNDANT);


        // Terrain
        //add first stable loc
        SectorEntityToken stableLoc1 = system.addCustomEntity("sirin_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(sirinstar, MathUtils.getRandomNumberInRange(0f, 360f), 2850f, 220);

        //add second stable location
        SectorEntityToken comms1 = system.addCustomEntity("sirin_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        comms1.setCircularOrbit(sirinstar,MathUtils.getRandomNumberInRange(0f, 360f), 8800, 400);

        //add nav beacon
        /*SectorEntityToken nav1 = system.addCustomEntity("alkanost_nav1", "Nav Beacon", "nav_buoy", "ui");
        nav1.setCircularOrbit(sirinstar,MathUtils.getRandomNumberInRange(0f, 360f), 10825,700);*/



        //inner band
        system.addRingBand(sirinstar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 3000f, 250f, Terrain.RING, null);
        system.addRingBand(sirinstar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 3200f, 250f, Terrain.RING, null);

        //outer band
        system.addRingBand(sirinstar, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, 9000f, 250f, Terrain.RING, null);
        system.addRingBand(sirinstar, "misc", "rings_ice0", 256f, 2, Color.gray, 256f, 9100f, 250f, Terrain.RING, null);
        system.addRingBand(sirinstar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 9300f, 250f, Terrain.RING, null);
        system.addRingBand(sirinstar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 9500f, 250f, Terrain.RING, null);



        JumpPointAPI jumpPoint1 = Global.getFactory().createJumpPoint(
                "inner_jump_point",
                "Inner Jump");
        jumpPoint1.setCircularOrbit(system.getEntityById("Sirin"),
                360f * (float) Math.random(), //position
                1500f, // distance from focus
                90f); //days it takes to orbit
        jumpPoint1.setStandardWormholeToHyperspaceVisual();
        system.addEntity(jumpPoint1);

        JumpPointAPI jumpPoint2 = Global.getFactory().createJumpPoint(
                "outer_jump_point",
                "Outer Jump");
        jumpPoint2.setCircularOrbit(system.getEntityById("Sirin"),
                0f, //position
                10000f, // distance from focus
                400f); //days it takes to orbit
        jumpPoint2.setStandardWormholeToHyperspaceVisual();
        system.addEntity(jumpPoint2);

        system.autogenerateHyperspaceJumpPoints(true, false);

        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 2f;

        float radius = system.getMaxRadiusInHyperspace();
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f);
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);

        return system;
    }
}
