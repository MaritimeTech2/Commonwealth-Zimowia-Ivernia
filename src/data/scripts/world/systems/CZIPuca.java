package data.scripts.world.systems;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.impl.MusicPlayerPluginImpl;
import com.fs.starfarer.api.impl.campaign.GateEntityPlugin;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.BaseTiledTerrain;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.MagneticFieldTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;
import com.fs.starfarer.api.campaign.SectorEntityToken;

public class CZIPuca {

    public StarSystemAPI generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Puca");

        system.setType(StarSystemGenerator.StarSystemType.SINGLE);

        ///system.getLocation().set(24000, 14000); //Northeast of core (static)
        //system.getLocation().set(15000, 30000); // Far Northeast of core (static)
        float w = Global.getSettings().getFloat("sectorWidth");
        float h = Global.getSettings().getFloat("sectorHeight");
        system.getLocation().set( ( (w/2f) - 12500f), ( (h/2f) - 6500f) );

        system.setBackgroundTextureFilename("graphics/backgrounds/background3.jpg");

        system.getMemoryWithoutUpdate().set(MusicPlayerPluginImpl.MUSIC_SET_MEM_KEY, "czi_starsystem_01");

        // create the star and generate the hyperspace anchor for this system
        PlanetAPI pucastar = system.initStar("Puca", // unique id for this star
                "star_yellow", // id in planets.json
                750f, // radius (in pixels at default zoom)
                250); // corona radius, from star edge
        system.setLightColor(new Color(245, 215, 125)); // light color in entire system, affects all entities

        PlanetAPI puca1 = system.addPlanet("czi_puca1",
                pucastar,
                "Puca II",
                "desert",
                360f * (float) Math.random(),
                160f,
                4500f,
                320f);


        PlanetAPI puca2 = system.addPlanet("czi_puca2",
                pucastar,
                "Puca III",
                "toxic",
                360f * (float) Math.random(),
                140f,
                6000f,
                320f);


        PlanetAPI puca3 = system.addPlanet("czi_puca3",
                pucastar,
                "Puca IV",
                "jungle",
                360f * (float) Math.random(),
                140f,
                8000f,
                340);


        PlanetAPI puca4 = system.addPlanet("czi_puca4",
                pucastar,
                "Puca I",
                "lava_minor",
                240f,
                110f,
                2000f,
                460f);


        PlanetAPI puca5 = system.addPlanet("czi_puca5",
                pucastar,
                "Puca V",
                "gas_giant",
                210f,
                410f,
                12000f,
                900f);
        puca5.getSpec().setPlanetColor(new Color(120, 194, 154, 255));
        puca5.getSpec().setAtmosphereColor(new Color(116, 116, 33, 255));
        puca5.getSpec().setAtmosphereThickness(0.4f);
        puca5.getSpec().setCloudRotation(5f);
        puca5.getSpec().setCloudColor(new Color(129, 113, 13, 245));
        puca5.applySpecChanges();

        PlanetAPI pucaVa = system.addPlanet("czi_puca5a",
                puca5,
                "Sirin Va",
                "barren",
                240f,
                90f,
                1200f,
                40f);


        PlanetAPI pucaVb = system.addPlanet("czi_puca5b",
                puca5,
                "Sirin Vb",
                "frozen",
                200f,
                90f,
                2100f,
                60f);



        // Terrain
        //add first stable loc
        SectorEntityToken stableLoc1 = system.addCustomEntity("sirin_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(pucastar, MathUtils.getRandomNumberInRange(0f, 360f), 2850f, 220);

        //add second stable location
        SectorEntityToken comms1 = system.addCustomEntity("sirin_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        comms1.setCircularOrbit(pucastar,MathUtils.getRandomNumberInRange(0f, 360f), 8800, 400);

        //add nav beacon
        /*SectorEntityToken nav1 = system.addCustomEntity("alkanost_nav1", "Nav Beacon", "nav_buoy", "ui");
        nav1.setCircularOrbit(pucastar,MathUtils.getRandomNumberInRange(0f, 360f), 10825,700);*/



        //inner band
        system.addRingBand(pucastar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 3000f, 250f, Terrain.RING, null);
        system.addRingBand(pucastar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 3200f, 250f, Terrain.RING, null);

        /*//outer band
        system.addRingBand(pucastar, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, 9000f, 250f, Terrain.RING, null);
        system.addRingBand(pucastar, "misc", "rings_ice0", 256f, 2, Color.gray, 256f, 9100f, 250f, Terrain.RING, null);
        system.addRingBand(pucastar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 9300f, 250f, Terrain.RING, null);
        system.addRingBand(pucastar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 9500f, 250f, Terrain.RING, null);*/


        SectorEntityToken nebula = system.addTerrain(Terrain.NEBULA, new BaseTiledTerrain.TileParams(
                    "   xx " +
                        "  xx x" +
                        " xxxx " +
                        "xxxxxx" +
                        "  xx  " +
                        "    x ",
                6, 6, // size of the nebula grid, should match above string
                "terrain", "nebula_amber", 5, 5, null));
        nebula.getLocation().set(puca2.getLocation().x + 1000f, puca2.getLocation().y);
        nebula.setCircularOrbit(pucastar, 140f, 9000, 400);


        JumpPointAPI jumpPoint1 = Global.getFactory().createJumpPoint(
                "inner_jump_point",
                "Inner Jump");
        jumpPoint1.setCircularOrbit(system.getEntityById("Puca"),
                360f * (float) Math.random(), //position
                1500f, // distance from focus
                90f); //days it takes to orbit
        jumpPoint1.setStandardWormholeToHyperspaceVisual();
        system.addEntity(jumpPoint1);

        JumpPointAPI jumpPoint2 = Global.getFactory().createJumpPoint(
                "outer_jump_point",
                "Outer Jump");
        jumpPoint2.setCircularOrbit(system.getEntityById("Puca"),
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
