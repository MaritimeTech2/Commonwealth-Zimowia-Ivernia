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
//import exerelin.campaign.intel.specialforces.SpecialForcesAssignmentAI;
//import org.lazywizard.lazylib.MathUtils;
import com.fs.starfarer.api.campaign.SectorEntityToken;

public class CZIEllenTrechend {
    public StarSystemAPI generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Ellen Trechend");

        system.setType(StarSystemGenerator.StarSystemType.TRINARY_2CLOSE);

        system.initNonStarCenter();

        //system.getLocation().set(28000, 10000); //Northeast of core
        system.getLocation().set(-30000, -15000); //Southeast of core

        system.setBackgroundTextureFilename("graphics/backgrounds/background6.jpg");

        SectorEntityToken center = system.getCenter();

        //system.getMemoryWithoutUpdate().set(MusicPlayerPluginImpl.MUSIC_SET_MEM_KEY, "czi_starsystem_01");

        // Planets
        PlanetAPI vesta = system.addPlanet("czi_vesta", center, "Vesta", "lava",
                0, // It's angular position around the star
                200, // The planet radius
                0, // The planet's orbit radius from the "focus"
                0); // The orbital period in days (how fast it orbits)
        vesta.getMarket().addCondition(Conditions.ORE_ULTRARICH);
        vesta.getMarket().addCondition(Conditions.RARE_ORE_ULTRARICH);
        vesta.getMarket().addCondition(Conditions.EXTREME_TECTONIC_ACTIVITY);
        vesta.getMarket().addCondition(Conditions.VERY_HOT);
        //vesta.getMarket().addCondition("ui_pieklo_tidal_disrupture"); //added to connect to a season mining script which was never done.

        PlanetAPI jasnazdaStar1 = system.addPlanet("czi_ellen_trechend1", center, "Ceres", "star_orange", 0f, 600f, 2000f, 40f);
        system.setStar(jasnazdaStar1);
        system.addCorona(jasnazdaStar1, 600, 2f, 0.1f, 2f);
        system.setLightColor(new Color(255, 255, 255)); // light color in entire system, affects all entities

        PlanetAPI jasnazdaStar2 = system.addPlanet("czi_ellen_trechend2", center, "Liber", "star_white", 120f, 450f, 2000f, 40f);
        system.setSecondary(jasnazdaStar2);
        system.addCorona(jasnazdaStar2, 400, 2f, 0.1f, 2f);

        PlanetAPI jasnazdaStar3 = system.addPlanet("czi_ellen_trechend3", center, "Liberia", "star_yellow", 240f, 700f, 2000f, 40f);
        system.setTertiary(jasnazdaStar3);
        system.addCorona(jasnazdaStar3, 800, 2f, 0.1f, 2f);


        PlanetAPI torthuil = system.addPlanet("czi_ivernia",
                center,
                "Ivernia",
                "terran",
                120f,
                180f,
                6450f,
                280f);
        torthuil.setCustomDescriptionId("czi_ivernia"); //reference descriptions.csv

        PlanetAPI catullus = system.addPlanet("czi_catullus",
                center,
                "Catullus",
                "barren-bombarded",
                200f,
                110f,
                4650f,
                363f);
        catullus.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        catullus.getMarket().addCondition(Conditions.RARE_ORE_SPARSE);
        catullus.getMarket().addCondition(Conditions.LOW_GRAVITY);


        PlanetAPI tarasque = system.addPlanet("czi_tarasque",
                center,
                "Tarasque",
                "gas_giant",
                180f,
                400f,
                15500f,
                720f);
        tarasque.getSpec().setPlanetColor(new Color(185, 14, 14, 255));
        tarasque.getSpec().setCloudColor(new Color(196, 86, 3, 255));
        tarasque.getSpec().setAtmosphereThickness(0.3f);
        tarasque.getSpec().setCloudRotation(5f);
        tarasque.getSpec().setAtmosphereColor(new Color(129, 3, 13, 245));
        tarasque.applySpecChanges();
        tarasque.getMarket().addCondition(Conditions.HIGH_GRAVITY);
        tarasque.getMarket().addCondition(Conditions.VOLATILES_DIFFUSE);
        tarasque.getMarket().addCondition(Conditions.EXTREME_WEATHER);
        tarasque.getMarket().addCondition(Conditions.DENSE_ATMOSPHERE);


        PlanetAPI atrimpas = system.addPlanet("czi_atrimpas",
                tarasque,
                "Atrimpas",
                "water",
                0f,
                100f,
                3000f,
                90f);
        atrimpas.setCustomDescriptionId("czi_atrimpas"); //reference descriptions.csv
        //atrimpas.setInteractionImage("illustrations", "volturn");
        atrimpas.getSpec().setTexture(Global.getSettings().getSpriteName("planets", "ui_frozen_water_world"));
        atrimpas.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "sindria"));
        atrimpas.getSpec().setAtmosphereThickness(.9f);
        atrimpas.getSpec().setAtmosphereThicknessMin(80f);
        atrimpas.getSpec().setAtmosphereColor(new Color(166, 163, 166,235));
        atrimpas.getSpec().setCloudRotation(10F);
        //atrimpas.getSpec().setCloudTexture(Global.getSettings().getSpriteName("hab_glows", "ui_clouds_storm"));
        //atrimpas.getSpec().setCloudTexture(Global.getSettings().getSpriteName("clouds", "clouds_terran03"));
        atrimpas.getSpec().setCloudColor(new Color(255, 255, 255, 130));
        atrimpas.applySpecChanges();

        PlanetAPI pastorela = system.addPlanet("czi_pastorela",
                tarasque,
                "Pastorela",
                "frozen",
                90f,
                90f,
                1600f,
                40f);
        pastorela.getMarket().addCondition(Conditions.VERY_COLD);
        pastorela.getMarket().addCondition(Conditions.RARE_ORE_SPARSE);
        pastorela.getMarket().addCondition(Conditions.VOLATILES_TRACE);


        PlanetAPI canso = system.addPlanet("czi_canso",
                tarasque,
                "Canso",
                "rocky_ice",
                230f,
                60f,
                2200f,
                60f);
        canso.getMarket().addCondition(Conditions.VERY_COLD);
        canso.getMarket().addCondition(Conditions.ORGANICS_TRACE);


        // Terrain
        //add first stable loc
        SectorEntityToken stableLoc1 = system.addCustomEntity("ellen_trechend_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(center, (float) Math.random() * 360f, 4150f, 220);

        /*//add second stable loc
        SectorEntityToken stableLoc2 = system.addCustomEntity("ui_ellen_trechend_stableloc_2", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc2.setCircularOrbit(center, MathUtils.getRandomNumberInRange(0f, 360f), 7000f, 520);*/

        //add comms relay
        SectorEntityToken comms1 = system.addCustomEntity("ellen_trechend_comms1", "Comms Beacon", "comm_relay", "czi");
        comms1.setCircularOrbit(center, (float) Math.random() * 360f, 3500f, 290);

        //add nav beacon
        SectorEntityToken nav1 = system.addCustomEntity("ellen_trechend_nav1", "Nav Beacon", "nav_buoy", "czi");
        nav1.setCircularOrbit(tarasque, (float) Math.random() * 360f, 4500,800);

        //add gate
        SectorEntityToken gate1 = system.addCustomEntity("ellen_trechend_gate", "Active Gate", "inactive_gate", Factions.NEUTRAL);
        gate1.setCircularOrbit(center, (float) Math.random() * 360f, 7450,380);
        //code to add tag which ties to rulescmd to make gate active
        gate1.addTag("iskalan_gate");




        //broken lava world ring
        system.addRingBand(center, "misc", "ui_rings_lava0", 256f, 0, Color.gray, 256f, 265f, 30f);

        //inner band
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, 3800f, 250f, Terrain.RING, null);

        //lazurowe morze ring
        system.addRingBand(atrimpas, "misc", "ui_rings_special0", 256f, 0, Color.gray, 256f, 400f, 30f, Terrain.RING, "Grace of Vanduo");

        //outer band
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, 8000f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_ice0", 256f, 1, Color.gray, 256f, 8200f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, 8300f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, 8600f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_ice0", 256f, 2, Color.gray, 256f, 8800f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_ice0", 256f, 0, Color.gray, 256f, 8900f, 250f, Terrain.RING, null);
        system.addRingBand(center, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, 9100f, 250f, Terrain.RING, null);

        //around the gas giant
        system.addRingBand(tarasque, "misc", "rings_ice0", 256f, 0, Color.gray, 256f, 700f, 250f, Terrain.RING, null);
        system.addRingBand(tarasque, "misc", "rings_ice0", 256f, 1, Color.gray, 256f, 800f, 250f, Terrain.RING, null);
        system.addRingBand(tarasque, "misc", "rings_ice0", 256f, 2, Color.gray, 256f, 900f, 250f, Terrain.RING, null);
        system.addRingBand(tarasque, "misc", "rings_ice0", 256f, 3, Color.gray, 256f, 1100f, 250f, Terrain.RING, null);

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
                9500, // distance from focus
                380f); //days it takes to orbit
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