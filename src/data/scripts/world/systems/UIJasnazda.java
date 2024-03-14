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
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;
import com.fs.starfarer.api.campaign.SectorEntityToken;

public class UIJasnazda {
    public StarSystemAPI generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Jasnazda");

        system.setType(StarSystemGenerator.StarSystemType.TRINARY_2CLOSE);

        system.initNonStarCenter();

        system.getLocation().set(28000, 10000); //Northeast of core

        system.setBackgroundTextureFilename("graphics/backgrounds/background3.jpg");

        SectorEntityToken center = system.getCenter();

        system.getMemoryWithoutUpdate().set(MusicPlayerPluginImpl.MUSIC_SET_MEM_KEY, "ui_starsystem_01");

        // Planets
        PlanetAPI pieklo = system.addPlanet("ui_pieklo", center, "Pieklo", "lava",
                0, // It's angular position around the star
                200, // The planet radius
                0, // The planet's orbit radius from the "focus"
                0); // The orbital period in days (how fast it orbits)
        //pieklo.getMarket().addCondition(Conditions.ORE_ULTRARICH);
        //pieklo.getMarket().addCondition(Conditions.RARE_ORE_ULTRARICH);
        pieklo.getMarket().addCondition(Conditions.EXTREME_TECTONIC_ACTIVITY);
        pieklo.getMarket().addCondition(Conditions.VERY_HOT);
        pieklo.getMarket().addCondition("ui_pieklo_tidal_disrupture");

        PlanetAPI jasnazdaStar1 = system.addPlanet("ui_jasnazda1", center, "Jasnazda", "star_orange", 0f, 600f, 2000f, 40f);
        system.setStar(jasnazdaStar1);
        system.addCorona(jasnazdaStar1, 600, 2f, 0.1f, 2f);
        system.setLightColor(new Color(255, 255, 255)); // light color in entire system, affects all entities

        PlanetAPI jasnazdaStar2 = system.addPlanet("ui_jasnazda2", center, "Jasnazda II", "star_white", 120f, 450f, 2000f, 40f);
        system.setSecondary(jasnazdaStar2);
        system.addCorona(jasnazdaStar2, 400, 2f, 0.1f, 2f);

        PlanetAPI jasnazdaStar3 = system.addPlanet("ui_jasnazda3", center, "Jasnazda III", "star_yellow", 240f, 700f, 2000f, 40f);
        system.setTertiary(jasnazdaStar3);
        system.addCorona(jasnazdaStar3, 800, 2f, 0.1f, 2f);

        PlanetAPI kuznia = system.addPlanet("ui_kuznia",
                center,
                "Kuznia",
                "barren-desert",
                120f,
                130f,
                6000f,
                230f);
        kuznia.setCustomDescriptionId("ui_kuznia"); //reference descriptions.csv
        kuznia.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "sindria"));
        kuznia.getSpec().setAtmosphereThickness(0.2f);
        kuznia.getSpec().setAtmosphereColor(new Color(168,67,47,235));
        //kuznia.getSpec().setCloudTexture(Global.getSettings().getSpriteName("hab_glows", "ui_clouds_storm"));
        kuznia.getSpec().setCloudColor(new Color(184, 67, 44,220));
        kuznia.applySpecChanges();

        PlanetAPI lazurowe_morze = system.addPlanet("ui_lazurowe_morze",
                center,
                "Lazurowe Morze",
                "water",
                0f,
                150f,
                6000f,
                230f);
        lazurowe_morze.setCustomDescriptionId("ui_lazurowe_morze"); //reference descriptions.csv
        lazurowe_morze.setInteractionImage("illustrations", "fikenhild");
        lazurowe_morze.getSpec().setTexture(Global.getSettings().getSpriteName("planets", "ui_frozen_water_world"));
        lazurowe_morze.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "sindria"));
        lazurowe_morze.getSpec().setAtmosphereThickness(0.3f);
        lazurowe_morze.getSpec().setAtmosphereColor(new Color(10, 33, 166,235));
        //lazurowe_morze.getSpec().setCloudTexture(Global.getSettings().getSpriteName("hab_glows", "ui_clouds_storm"));
        lazurowe_morze.getSpec().setCloudColor(new Color(255, 255, 255, 130));
        lazurowe_morze.applySpecChanges();

        PlanetAPI jasnazda3 = system.addPlanet("ui_jasnazda3",
                center,
                "Jasnazda III",
                "barren",
                240f,
                90f,
                6800f,
                300f);
        jasnazda3.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        jasnazda3.getMarket().addCondition(Conditions.ORE_SPARSE);

        PlanetAPI jasnazda4 = system.addPlanet("ui_jasnazda4",
                center,
                "Jasnazda IV",
                "barren-bombarded",
                200f,
                50f,
                5000f,
                360f);
        jasnazda4.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        jasnazda4.getMarket().addCondition(Conditions.RARE_ORE_SPARSE);
        jasnazda4.getMarket().addCondition(Conditions.LOW_GRAVITY);



        // Terrain
        //add first stable loc
        SectorEntityToken stableLoc1 = system.addCustomEntity("jasnazda_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(center, MathUtils.getRandomNumberInRange(0f, 360f), 5500f, 220);

        //add second stable loc
        SectorEntityToken stableLoc2 = system.addCustomEntity("jasnazda_stableloc_2", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc2.setCircularOrbit(center, MathUtils.getRandomNumberInRange(0f, 360f), 7000f, 520);

        //broken lava world ring
        system.addRingBand(center, "misc", "ui_rings_lava0", 256f, 0, Color.gray, 256f, 265f, 30f);

        //inner band
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, 4000f, 250f, Terrain.RING, null);

        //lazurowe morze ring
        system.addRingBand(lazurowe_morze, "misc", "ui_rings_special0", 256f, 0, Color.gray, 256f, 400f, 30f, Terrain.RING, "Grace of Lazurowe Morze");

        //outer band
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, 8500f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_ice0", 256f, 1, Color.gray, 256f, 8700f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, 8800f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 9100f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_ice0", 256f, 2, Color.gray, 256f, 9300f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_ice0", 256f, 0, Color.gray, 256f, 9400f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, 9600f, 250f, Terrain.RING, null);

        // Argon Gamma: Dead World
        //PlanetAPI arka = system.addPlanet("ui_Arka", aekiStar,"Arka","barren-bombarded",360f * (float) Math.random(),320f,3000f,41f);
        //arka.setCustomDescriptionId("ui-2"); //reference descriptions.csv
        //PlanetConditionGenerator.generateConditionsForPlanet(arka, StarAge.AVERAGE);



        JumpPointAPI jumpPoint1 = Global.getFactory().createJumpPoint(
                "inner_jump_point",
                "Inferno Jump");
        jumpPoint1.setCircularOrbit(center,
                60, //position
                2000, // distance from focus
                40f); //days it takes to orbit
        jumpPoint1.setStandardWormholeToHyperspaceVisual();
        system.addEntity(jumpPoint1);

        JumpPointAPI jumpPoint2 = Global.getFactory().createJumpPoint(
                "outer_jump_point",
                "Outer Jump");
        jumpPoint2.setCircularOrbit(center,
                270, //position
                7200, // distance from focus
                300f); //days it takes to orbit
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