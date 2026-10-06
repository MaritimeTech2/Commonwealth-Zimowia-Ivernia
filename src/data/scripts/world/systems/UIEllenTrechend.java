package data.scripts.world.systems;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.listeners.GateTransitListener;
import com.fs.starfarer.api.impl.MusicPlayerPluginImpl;
import com.fs.starfarer.api.impl.campaign.GateEntityPlugin;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.intel.misc.GateIntel;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.rulecmd.missions.GateCMD;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import exerelin.campaign.intel.specialforces.SpecialForcesAssignmentAI;
import org.lazywizard.lazylib.MathUtils;
import com.fs.starfarer.api.campaign.SectorEntityToken;

public class UIEllenTrechend {
    public StarSystemAPI generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Ellen Trechend");

        system.setType(StarSystemGenerator.StarSystemType.TRINARY_2CLOSE);

        system.initNonStarCenter();

        //system.getLocation().set(28000, 10000); //Northeast of core
        system.getLocation().set(-30000, -15000); //Southeast of core

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
        //pieklo.getMarket().addCondition("ui_pieklo_tidal_disrupture");

        PlanetAPI jasnazdaStar1 = system.addPlanet("ui_ellen_trechend1", center, "Ellen Trechend", "star_orange", 0f, 600f, 2000f, 40f);
        system.setStar(jasnazdaStar1);
        system.addCorona(jasnazdaStar1, 600, 2f, 0.1f, 2f);
        system.setLightColor(new Color(255, 255, 255)); // light color in entire system, affects all entities

        PlanetAPI jasnazdaStar2 = system.addPlanet("ui_ellen_trechend2", center, "Ellen Trechend II", "star_white", 120f, 450f, 2000f, 40f);
        system.setSecondary(jasnazdaStar2);
        system.addCorona(jasnazdaStar2, 400, 2f, 0.1f, 2f);

        PlanetAPI jasnazdaStar3 = system.addPlanet("ui_ellen_trechend3", center, "Ellen Trechend III", "star_yellow", 240f, 700f, 2000f, 40f);
        system.setTertiary(jasnazdaStar3);
        system.addCorona(jasnazdaStar3, 800, 2f, 0.1f, 2f);


        PlanetAPI torthuil = system.addPlanet("ui_torthuil",
                center,
                "Torthuil",
                "terran",
                120f,
                150f,
                6450f,
                330f);
        torthuil.setCustomDescriptionId("ui_torthuil"); //reference descriptions.csv


        PlanetAPI swiatana5 = system.addPlanet("ui_swiatana5",
                center,
                "Swiatana V",
                "gas_giant",
                180f,
                400f,
                10000f,
                1200f);
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


        PlanetAPI vanduo = system.addPlanet("ui_vanduo",
                swiatana5,
                "Vanduo",
                "water",
                0f,
                100f,
                2500f,
                90f);
        vanduo.setCustomDescriptionId("ui_vanduo"); //reference descriptions.csv
        vanduo.setInteractionImage("illustrations", "fikenhild");
        vanduo.getSpec().setTexture(Global.getSettings().getSpriteName("planets", "ui_frozen_water_world"));
        vanduo.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "sindria"));
        vanduo.getSpec().setAtmosphereThickness(0.3f);
        vanduo.getSpec().setAtmosphereColor(new Color(10, 33, 166,235));
        //vanduo.getSpec().setCloudTexture(Global.getSettings().getSpriteName("hab_glows", "ui_clouds_storm"));
        vanduo.getSpec().setCloudColor(new Color(255, 255, 255, 130));
        vanduo.applySpecChanges();

        PlanetAPI swiatana6 = system.addPlanet("ui_swiatana6",
                swiatana5,
                "Swiatana VI",
                "frozen",
                90f,
                90f,
                1600f,
                40f);
        swiatana6.getMarket().addCondition(Conditions.VERY_COLD);
        swiatana6.getMarket().addCondition(Conditions.RARE_ORE_SPARSE);
        swiatana6.getMarket().addCondition(Conditions.VOLATILES_TRACE);


        PlanetAPI swiatana7 = system.addPlanet("ui_swiatana7",
                swiatana5,
                "Swiatana VII",
                "rocky_ice",
                230f,
                90f,
                1900f,
                60f);
        swiatana7.getMarket().addCondition(Conditions.VERY_COLD);
        swiatana7.getMarket().addCondition(Conditions.ORGANICS_TRACE);


        // Terrain
        //add first stable loc
        SectorEntityToken stableLoc1 = system.addCustomEntity("ui_ellen_trechend_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(center, MathUtils.getRandomNumberInRange(0f, 360f), 5500f, 220);

        /*//add second stable loc
        SectorEntityToken stableLoc2 = system.addCustomEntity("ui_ellen_trechend_stableloc_2", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc2.setCircularOrbit(center, MathUtils.getRandomNumberInRange(0f, 360f), 7000f, 520);*/

        //add comms relay
        SectorEntityToken comms1 = system.addCustomEntity("ui_ellen_trechend_comms1", "Comms Beacon", "comm_relay", "ui");
        comms1.setCircularOrbit(center,MathUtils.getRandomNumberInRange(0f, 360f), 6500f, 290);

        //add nav beacon
        SectorEntityToken nav1 = system.addCustomEntity("ui_ellen_trechend_nav1", "Nav Beacon", "nav_buoy", "ui");
        nav1.setCircularOrbit(center,MathUtils.getRandomNumberInRange(0f, 360f), 11000,1500);

        //add gate
        SectorEntityToken gate1 = system.addCustomEntity("ui_ellen_trechend_gate", "Active Gate", "inactive_gate", Factions.NEUTRAL);
        gate1.setCircularOrbit(center,MathUtils.getRandomNumberInRange(0f, 360f), 8250,1350);
        //code to hopefully make gate active
        /*((GateEntityPlugin) gate1.getCustomPlugin()).canUseGates();
        //((GateEntityPlugin)gate1.getCustomPlugin()).;
        GateEntityPlugin.getGateData().scanned.add(gate1);
        ((GateEntityPlugin)gate1.getCustomPlugin()).isActive();
        gate1.getCustomPlugin().advance(0f); // makes gate activate if already did quest
        //GateCMD.notifyScanned(gate1);*/


        //broken lava world ring
        system.addRingBand(center, "misc", "ui_rings_lava0", 256f, 0, Color.gray, 256f, 265f, 30f);

        //inner band
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, 3800f, 250f, Terrain.RING, null);

        //lazurowe morze ring
        system.addRingBand(vanduo, "misc", "ui_rings_special0", 256f, 0, Color.gray, 256f, 400f, 30f, Terrain.RING, "Grace of Lazurowe Morze");

        //outer band
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, 8000f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_ice0", 256f, 1, Color.gray, 256f, 8200f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, 8300f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 8600f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_ice0", 256f, 2, Color.gray, 256f, 8800f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_ice0", 256f, 0, Color.gray, 256f, 8900f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, 9100f, 250f, Terrain.RING, null);

        //around the gas giant
        system.addRingBand(swiatana5, "misc", "rings_ice0", 256f, 0, Color.gray, 256f, 700f, 250f, Terrain.RING, null);
        system.addRingBand(swiatana5, "misc", "rings_ice0", 256f, 1, Color.gray, 256f, 800f, 250f, Terrain.RING, null);
        system.addRingBand(swiatana5, "misc", "rings_ice0", 256f, 2, Color.gray, 256f, 900f, 250f, Terrain.RING, null);
        system.addRingBand(swiatana5, "misc", "rings_ice0", 256f, 3, Color.gray, 256f, 1100f, 250f, Terrain.RING, null);

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