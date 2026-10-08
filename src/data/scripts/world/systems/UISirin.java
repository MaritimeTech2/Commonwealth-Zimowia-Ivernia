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

public class UISirin {
    public StarSystemAPI generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Sirin");

        system.setType(StarSystemGenerator.StarSystemType.SINGLE);

        ///system.getLocation().set(24000, 14000); //Northeast of core (static)
        //system.getLocation().set(15000, 30000); // Far Northeast of core (static)
        float w = Global.getSettings().getFloat("sectorWidth");
        float h = Global.getSettings().getFloat("sectorHeight");
        system.getLocation().set( ( (w/2f) - 8750f), ( (h/2f) - 6650f) );

        system.setBackgroundTextureFilename("graphics/backgrounds/background3.jpg");

        system.getMemoryWithoutUpdate().set(MusicPlayerPluginImpl.MUSIC_SET_MEM_KEY, "ui_starsystem_01");

        // create the star and generate the hyperspace anchor for this system
        PlanetAPI sirinstar = system.initStar("Sirin", // unique id for this star
                "star_orange", // id in planets.json
                750f, // radius (in pixels at default zoom)
                250); // corona radius, from star edge
        system.setLightColor(new Color(255, 215, 175)); // light color in entire system, affects all entities


        PlanetAPI sirin1 = system.addPlanet("ui_sirin1",
                sirinstar,
                "Sirin I",
                "barren_castiron",
                360f * (float) Math.random(),
                110f,
                5650f,
                320f);
        sirin1.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        sirin1.getMarket().addCondition(Conditions.ORE_SPARSE);

        /*PlanetAPI swiatana1 = system.addPlanet("ui_swiatana1",
                sirinstar,
                "Swiatana I",
                "jungle",
                360f * (float) Math.random(),
                110f,
                2650f,
                200f);
        swiatana1.getMarket().addCondition(Conditions.INIMICAL_BIOSPHERE);
        swiatana1.getMarket().addCondition(Conditions.ORE_SPARSE);*/

        PlanetAPI sirin2 = system.addPlanet("ui_sirin2",
                sirinstar,
                "Sirin II",
                "rocky_metallic",
                360f * (float) Math.random(),
                120f,
                6000f,
                340);
        sirin2.getMarket().addCondition(Conditions.TECTONIC_ACTIVITY);
        sirin2.getMarket().addCondition(Conditions.ORE_SPARSE);
        sirin2.getMarket().addCondition(Conditions.RARE_ORE_SPARSE);
        sirin2.getMarket().addCondition(Conditions.TOXIC_ATMOSPHERE);

        PlanetAPI sirin3 = system.addPlanet("ui_sirin3",
                sirinstar,
                "Sirin III",
                "barren",
                240f,
                90f,
                9000f,
                460f);
        sirin3.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        sirin3.getMarket().addCondition(Conditions.ORE_SPARSE);

        PlanetAPI sirin4 = system.addPlanet("ui_sirin4",
                sirinstar,
                "Sirin IV",
                "gas_giant",
                210f,
                370f,
                15000f,
                900f);
        sirin4.getMarket().addCondition(Conditions.HIGH_GRAVITY);
        sirin4.getMarket().addCondition(Conditions.VOLATILES_TRACE);


        // Terrain
        //add first stable loc
        SectorEntityToken stableLoc1 = system.addCustomEntity("sirin_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(sirinstar, MathUtils.getRandomNumberInRange(0f, 360f), 4500f, 220);

        //add second stable location
        SectorEntityToken comms1 = system.addCustomEntity("sirin_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        comms1.setCircularOrbit(sirinstar,MathUtils.getRandomNumberInRange(0f, 360f), 7050, 400);

        //add nav beacon
        /*SectorEntityToken nav1 = system.addCustomEntity("alkanost_nav1", "Nav Beacon", "nav_buoy", "ui");
        nav1.setCircularOrbit(sirinstar,MathUtils.getRandomNumberInRange(0f, 360f), 10825,700);*/



        //inner band
        system.addRingBand(sirinstar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 3000f, 250f, Terrain.RING, null);

        //outer band
        system.addRingBand(sirinstar, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, 13000f, 250f, Terrain.RING, null);
        system.addRingBand(sirinstar, "misc", "rings_ice0", 256f, 2, Color.gray, 256f, 13100f, 250f, Terrain.RING, null);
        system.addRingBand(sirinstar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 13300f, 250f, Terrain.RING, null);



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
