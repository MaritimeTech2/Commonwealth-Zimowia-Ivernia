package data.scripts.world.systems;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
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



        // create the star and generate the hyperspace anchor for this system
        PlanetAPI swiatanaStar = system.initStar("Swiatana", // unique id for this star
                "star_orange", // id in planets.json
                750f, // radius (in pixels at default zoom)
                250); // corona radius, from star edge
        system.setLightColor(new Color(255, 255, 255)); // light color in entire system, affects all entities

        PlanetAPI iskala = system.addPlanet("ui_iskala",
                swiatanaStar,
                "Iskala",
                "terran",
                360f * (float) Math.random(),
                160f,
                4000f,
                730f);
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
                        new Color(50, 20, 100, 80), // base color
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



        JumpPointAPI jumpPoint1 = Global.getFactory().createJumpPoint(
                "inner_jump_point",
                "Inner Jump");
        jumpPoint1.setCircularOrbit(system.getEntityById("Swiatana"),
                360f * (float) Math.random(), //position
                1500f, // distance from focus
                90f); //days it takes to orbit
        jumpPoint1.setStandardWormholeToHyperspaceVisual();
        system.addEntity(jumpPoint1);

        //i lazy, let game design the rest for me... replace later
        float radiusAfter = StarSystemGenerator.addOrbitingEntities(system, swiatanaStar, StarAge.YOUNG,
                3, 5, // min/max entities to add
                6000f, // radius to start adding at
                4, // name offset - next planet will be <system name> <roman numeral of this parameter + 1>
                false, // whether to use custom or system-name based names
                false); // whether to allow habitable worlds

        system.autogenerateHyperspaceJumpPoints(true, true);

        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 2f;

        float radius = system.getMaxRadiusInHyperspace();
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f);
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);

        return system;
    }
}