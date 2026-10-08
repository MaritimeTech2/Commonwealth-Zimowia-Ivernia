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

public class UIAlkanost {
    public StarSystemAPI generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Alkanost");

        system.setType(StarSystemGenerator.StarSystemType.SINGLE);

        ///system.getLocation().set(24000, 14000); //Northeast of core (static)
        //system.getLocation().set(15000, 30000); // Far Northeast of core (static)
        float w = Global.getSettings().getFloat("sectorWidth");
        float h = Global.getSettings().getFloat("sectorHeight");
        system.getLocation().set( ( (w/2f) - 8000f), ( (h/2f) - 6000f) );

        system.setBackgroundTextureFilename("graphics/backgrounds/background3.jpg");

        system.getMemoryWithoutUpdate().set(MusicPlayerPluginImpl.MUSIC_SET_MEM_KEY, "ui_starsystem_01");

        // create the star and generate the hyperspace anchor for this system
        PlanetAPI alkanoststar = system.initStar("Alkanost", // unique id for this star
                "star_orange", // id in planets.json
                750f, // radius (in pixels at default zoom)
                250); // corona radius, from star edge
        system.setLightColor(new Color(255, 215, 175)); // light color in entire system, affects all entities

        PlanetAPI zimowia = system.addPlanet("ui_zimowia",
                alkanoststar,
                "Zimowia",
                "terran",
                360f * (float) Math.random(),
                200f,
                6450f,
                380f);
        zimowia.setCustomDescriptionId("ui_lodowazemia"); //reference descriptions.csv
        zimowia.getSpec().setTexture(Global.getSettings().getSpriteName("planets", "ui_iskala"));
        zimowia.getSpec().setTilt(0f);
        zimowia.getSpec().setPitch(0f);
        zimowia.applySpecChanges();

        PlanetAPI agropelter = system.addPlanet("ui_agropelter",
                alkanoststar,
                "Agropelter",
                "barren-desert",
                360f * (float) Math.random(),
                140f,
                5000f,
                300f);
        agropelter.setCustomDescriptionId("ui_agropelter"); //reference descriptions.csv
        agropelter.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "sindria"));
        agropelter.getSpec().setAtmosphereThickness(0.15f);
        agropelter.getSpec().setAtmosphereColor(new Color(168,67,47,235));
        //agropelter.getSpec().setCloudTexture(Global.getSettings().getSpriteName("hab_glows", "ui_clouds_storm"));
        agropelter.getSpec().setCloudColor(new Color(184, 67, 44,220));
        agropelter.applySpecChanges();

        PlanetAPI swiatana3 = system.addPlanet("ui_swiatana3",
                alkanoststar,
                "Swiatana III",
                "barren_castiron",
                360f * (float) Math.random(),
                140f,
                10000f,
                320f);
        swiatana3.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        swiatana3.getMarket().addCondition(Conditions.ORE_SPARSE);

        /*PlanetAPI swiatana1 = system.addPlanet("ui_swiatana1",
                alkanoststar,
                "Swiatana I",
                "jungle",
                360f * (float) Math.random(),
                110f,
                2650f,
                200f);
        swiatana1.getMarket().addCondition(Conditions.INIMICAL_BIOSPHERE);
        swiatana1.getMarket().addCondition(Conditions.ORE_SPARSE);*/

        PlanetAPI swiatana4 = system.addPlanet("ui_swiatana4",
                alkanoststar,
                "Swiatana IV",
                "rocky_metallic",
                360f * (float) Math.random(),
                120f,
                7550f,
                340);
        swiatana4.getMarket().addCondition(Conditions.TECTONIC_ACTIVITY);
        swiatana4.getMarket().addCondition(Conditions.ORE_SPARSE);
        swiatana4.getMarket().addCondition(Conditions.RARE_ORE_SPARSE);
        swiatana4.getMarket().addCondition(Conditions.TOXIC_ATMOSPHERE);

        PlanetAPI jasnazda3 = system.addPlanet("ui_jasnazda3",
                zimowia,
                "Jasnazda III",
                "barren",
                240f,
                90f,
                500f,
                460f);
        jasnazda3.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        jasnazda3.getMarket().addCondition(Conditions.ORE_SPARSE);

        PlanetAPI jasnazda4 = system.addPlanet("ui_jasnazda4",
                alkanoststar,
                "Jasnazda IV",
                "gas_giant",
                210f,
                370f,
                15000f,
                900f);
        jasnazda4.getMarket().addCondition(Conditions.HIGH_GRAVITY);
        jasnazda4.getMarket().addCondition(Conditions.VOLATILES_TRACE);


        // Terrain
        //add first stable loc
        SectorEntityToken stableLoc1 = system.addCustomEntity("jasnazda_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(alkanoststar, MathUtils.getRandomNumberInRange(0f, 360f), 4500f, 220);

        /*//add second stable loc
        SectorEntityToken stableLoc2 = system.addCustomEntity("jasnazda_stableloc_2", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc2.setCircularOrbit(alkanoststar, MathUtils.getRandomNumberInRange(0f, 360f), 5500f, 520);*/

        //add comms relay
        SectorEntityToken comms1 = system.addCustomEntity("alkanost_comms1", "Comms Beacon", "comm_relay", "ui");
        comms1.setCircularOrbit(alkanoststar,MathUtils.getRandomNumberInRange(0f, 360f), 7050, 400);

        //add nav beacon
        SectorEntityToken nav1 = system.addCustomEntity("alkanost_nav1", "Nav Beacon", "nav_buoy", "ui");
        nav1.setCircularOrbit(alkanoststar,MathUtils.getRandomNumberInRange(0f, 360f), 11825,700);

        //add gate
        SectorEntityToken gate1 = system.addCustomEntity("alkanost_gate", null, "inactive_gate", Factions.NEUTRAL);
        gate1.setCircularOrbit(alkanoststar,MathUtils.getRandomNumberInRange(0f, 360f), 8250,420);
        //code to add tag which ties to rulescmd to make gate active
        gate1.addTag("iskalan_gate");
        //gate1.addTag("gatec");


        //zimowia magnetic field
        SectorEntityToken iskala_mag_field = system.addTerrain(Terrain.MAGNETIC_FIELD, //scar magneitic field
                new MagneticFieldTerrainPlugin.MagneticFieldParams(200f, // terrain effect band width
                        180f, // terrain effect middle radius
                        zimowia, // entity that it's around
                        180f, // visual aroura band start
                        300f, // visual aroura band end
                        new Color(50, 20, 100, 150), // base color
                        1f, // probability to spawn aurora sequence, checked once/day when no aurora in progress
                        new Color(90, 180, 140),
                        new Color(130, 145, 190),
                        new Color(165, 110, 225),
                        new Color(95, 55, 240),
                        new Color(45, 0, 250),
                        new Color(20, 0, 240),
                        new Color(10, 0, 150)));
        iskala_mag_field.setCircularOrbit(zimowia, 0, 0, 100);

        //inner band
        system.addRingBand(alkanoststar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 3000f, 250f, Terrain.RING, null);

        //outer band
        system.addRingBand(alkanoststar, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, 13000f, 250f, Terrain.RING, null);
        system.addRingBand(alkanoststar, "misc", "rings_ice0", 256f, 2, Color.gray, 256f, 13100f, 250f, Terrain.RING, null);
        system.addRingBand(alkanoststar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 13300f, 250f, Terrain.RING, null);



        JumpPointAPI jumpPoint1 = Global.getFactory().createJumpPoint(
                "inner_jump_point",
                "Inner Jump");
        jumpPoint1.setCircularOrbit(system.getEntityById("Alkanost"),
                360f * (float) Math.random(), //position
                1500f, // distance from focus
                90f); //days it takes to orbit
        jumpPoint1.setStandardWormholeToHyperspaceVisual();
        system.addEntity(jumpPoint1);

        JumpPointAPI jumpPoint2 = Global.getFactory().createJumpPoint(
                "outer_jump_point",
                "Outer Jump");
        jumpPoint2.setCircularOrbit(system.getEntityById("Alkanost"),
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