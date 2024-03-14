package data.scripts.world.systems;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.impl.MusicPlayerPluginImpl;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.MagneticFieldTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;
import com.fs.starfarer.api.campaign.SectorEntityToken;

public class UISwiatana {
    public StarSystemAPI generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Swiatana");

        system.setType(StarSystemGenerator.StarSystemType.SINGLE);

        system.getLocation().set(24000, 14000); //Northeast of core

        system.setBackgroundTextureFilename("graphics/backgrounds/background3.jpg");

        system.getMemoryWithoutUpdate().set(MusicPlayerPluginImpl.MUSIC_SET_MEM_KEY, "ui_starsystem_01");

        // create the star and generate the hyperspace anchor for this system
        PlanetAPI swiatanaStar = system.initStar("Swiatana", // unique id for this star
                "star_orange", // id in planets.json
                750f, // radius (in pixels at default zoom)
                250); // corona radius, from star edge
        system.setLightColor(new Color(255, 215, 175)); // light color in entire system, affects all entities

        PlanetAPI iskala = system.addPlanet("ui_iskala",
                swiatanaStar,
                "Iskala",
                "terran",
                360f * (float) Math.random(),
                160f,
                6500f,
                720f);
        iskala.setCustomDescriptionId("ui_iskala"); //reference descriptions.csv
        iskala.getSpec().setTexture(Global.getSettings().getSpriteName("planets", "ui_iskala"));
        iskala.getSpec().setTilt(0f);
        iskala.getSpec().setPitch(0f);
        iskala.applySpecChanges();

        PlanetAPI ladnysiez = system.addPlanet("ui_ladnysiez",
                swiatanaStar,
                "Ladnysiez",
                "terran-eccentric",
                360f * (float) Math.random(),
                90f,
                5000f,
                360f);
        ladnysiez.setCustomDescriptionId("ui_ladnysiez"); //reference descriptions.csv

        PlanetAPI swiatana3 = system.addPlanet("ui_swiatana3",
                swiatanaStar,
                "Swiatana III",
                "barren_castiron",
                360f * (float) Math.random(),
                110f,
                5650f,
                300f);
        swiatana3.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        swiatana3.getMarket().addCondition(Conditions.ORE_SPARSE);

        PlanetAPI swiatana1 = system.addPlanet("ui_swiatana1",
                swiatanaStar,
                "Swiatana I",
                "jungle",
                360f * (float) Math.random(),
                110f,
                2650f,
                200f);
        swiatana1.getMarket().addCondition(Conditions.INIMICAL_BIOSPHERE);
        swiatana1.getMarket().addCondition(Conditions.ORE_SPARSE);

        PlanetAPI swiatana4 = system.addPlanet("ui_swiatana4",
                swiatanaStar,
                "Swiatana IV",
                "rocky_metallic",
                360f * (float) Math.random(),
                120f,
                7500f,
                400f);
        swiatana4.getMarket().addCondition(Conditions.TECTONIC_ACTIVITY);
        swiatana4.getMarket().addCondition(Conditions.ORE_SPARSE);
        swiatana4.getMarket().addCondition(Conditions.RARE_ORE_SPARSE);
        swiatana4.getMarket().addCondition(Conditions.TOXIC_ATMOSPHERE);

        PlanetAPI swiatana5 = system.addPlanet("ui_swiatana5",
                swiatanaStar,
                "Swiatana V",
                "gas_giant",
                180f,
                400f,
                10000f,
                500f);
        swiatana5.getSpec().setPlanetColor(new Color(185, 14, 14, 255));
        swiatana5.getSpec().setCloudColor(new Color(196, 86, 3, 255));
        swiatana5.getSpec().setAtmosphereThickness(0.3f);
        swiatana5.getSpec().setCloudRotation(5f);
        swiatana5.getSpec().setAtmosphereColor(new Color(129, 3, 13, 245));
        swiatana5.applySpecChanges();
        swiatana5.getMarket().addCondition(Conditions.HIGH_GRAVITY);
        swiatana5.getMarket().addCondition(Conditions.VOLATILES_DIFFUSE);
        swiatana5.getMarket().addCondition(Conditions.EXTREME_WEATHER);
        swiatana5.getMarket().addCondition(Conditions.DENSE_ATMOSPHERE);

        PlanetAPI swiatana6 = system.addPlanet("ui_swiatana6",
                swiatanaStar,
                "Swiatana VI",
                "frozen",
                90f,
                120f,
                9000f,
                400f);
        swiatana6.getMarket().addCondition(Conditions.VERY_COLD);
        swiatana6.getMarket().addCondition(Conditions.RARE_ORE_SPARSE);
        swiatana6.getMarket().addCondition(Conditions.VOLATILES_TRACE);

        PlanetAPI swiatana7 = system.addPlanet("ui_swiatana7",
                swiatanaStar,
                "Swiatana VII",
                "rocky_ice",
                230f,
                120f,
                11000f,
                400f);
        swiatana7.getMarket().addCondition(Conditions.VERY_COLD);
        swiatana7.getMarket().addCondition(Conditions.ORGANICS_TRACE);



        // Terrain
        //add first stable loc
        SectorEntityToken stableLoc1 = system.addCustomEntity("jasnazda_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(swiatanaStar, MathUtils.getRandomNumberInRange(0f, 360f), 4500f, 220);

        //add second stable loc
        SectorEntityToken stableLoc2 = system.addCustomEntity("jasnazda_stableloc_2", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc2.setCircularOrbit(swiatanaStar, MathUtils.getRandomNumberInRange(0f, 360f), 5500f, 520);

        //iskala magnetic field
        SectorEntityToken iskala_mag_field = system.addTerrain(Terrain.MAGNETIC_FIELD, //scar magneitic field
                new MagneticFieldTerrainPlugin.MagneticFieldParams(200f, // terrain effect band width
                        170f, // terrain effect middle radius
                        iskala, // entity that it's around
                        170f, // visual aroura band start
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
        iskala_mag_field.setCircularOrbit(iskala, 0, 0, 100);

        //inner band
        system.addRingBand(swiatanaStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 3000f, 250f, Terrain.RING, null);

        //around the gas giant
        system.addRingBand(swiatana5, "misc", "rings_ice0", 256f, 0, Color.gray, 256f, 700f, 250f, Terrain.RING, null);
        system.addRingBand(swiatana5, "misc", "rings_ice0", 256f, 1, Color.gray, 256f, 800f, 250f, Terrain.RING, null);
        system.addRingBand(swiatana5, "misc", "rings_ice0", 256f, 2, Color.gray, 256f, 900f, 250f, Terrain.RING, null);
        system.addRingBand(swiatana5, "misc", "rings_ice0", 256f, 3, Color.gray, 256f, 1100f, 250f, Terrain.RING, null);

        //outer band
        system.addRingBand(swiatanaStar, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, 13000f, 250f, Terrain.RING, null);
        system.addRingBand(swiatanaStar, "misc", "rings_ice0", 256f, 2, Color.gray, 256f, 13100f, 250f, Terrain.RING, null);
        system.addRingBand(swiatanaStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 13300f, 250f, Terrain.RING, null);



        JumpPointAPI jumpPoint1 = Global.getFactory().createJumpPoint(
                "inner_jump_point",
                "Inner Jump");
        jumpPoint1.setCircularOrbit(system.getEntityById("Swiatana"),
                360f * (float) Math.random(), //position
                1500f, // distance from focus
                90f); //days it takes to orbit
        jumpPoint1.setStandardWormholeToHyperspaceVisual();
        system.addEntity(jumpPoint1);

        JumpPointAPI jumpPoint2 = Global.getFactory().createJumpPoint(
                "outer_jump_point",
                "Outer Jump");
        jumpPoint2.setCircularOrbit(system.getEntityById("Swiatana"),
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