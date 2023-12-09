package data.world.systems;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;
import com.fs.starfarer.api.campaign.SectorEntityToken;

public class UIJasnazda {
    public void generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Jasnazda");

        system.setType(StarSystemGenerator.StarSystemType.TRINARY_2CLOSE);

        system.initNonStarCenter();

        system.getLocation().set(28000, 10000); //Northeast of core

        system.setBackgroundTextureFilename("graphics/backgrounds/background3.jpg");



        // Planets
        PlanetAPI pieklo = system.addPlanet("ui_pieklo", null, "Pieklo", "lava",
                0, // It's angular position around the star
                200, // The planet radius
                0, // The planet's orbit radius from the "focus"
                0); // The orbital period in days (how fast it orbits)

        PlanetAPI jasnazdaStar1 = system.addPlanet("ui_jasnazda1", pieklo, "Jasnazda", "star_orange", 0f, 600f, 1500f, 40f);
        system.setStar(jasnazdaStar1);
        system.addCorona(jasnazdaStar1, 600, 2f, 0.1f, 2f);
        system.setLightColor(new Color(255, 255, 255)); // light color in entire system, affects all entities

        PlanetAPI jasnazdaStar2 = system.addPlanet("ui_jasnazda2", pieklo, "Jasnazda II", "star_white", 120f, 450f, 1500f, 40f);
        system.setSecondary(jasnazdaStar2);
        system.addCorona(jasnazdaStar2, 400, 2f, 0.1f, 2f);

        PlanetAPI jasnazdaStar3 = system.addPlanet("ui_jasnazda3", pieklo, "Jasnazda III", "star_yellow", 240f, 700f, 1500f, 40f);
        system.setTertiary(jasnazdaStar3);
        system.addCorona(jasnazdaStar3, 800, 2f, 0.1f, 2f);

        PlanetAPI kuznia = system.addPlanet("ui_kuznia",
                pieklo,
                "Kuznia",
                "barren-desert",
                360f * (float) Math.random(),
                130f,
                4000f,
                230f);
        kuznia.setCustomDescriptionId("ui_kuznia"); //reference descriptions.csv
        kuznia.getSpec().setAtmosphereThickness(0.2f);
        kuznia.getSpec().setAtmosphereColor(new Color(168,67,47,235));
        //kuznia.getSpec().setCloudTexture("graphics/planets/ui_clouds_storm");
        //kuznia.getSpec().setCloudColor(new Color(184, 67, 44,220));
        kuznia.applySpecChanges();

        PlanetAPI lazurowe_morze = system.addPlanet("ui_lazurowe_morze",
                pieklo,
                "Lazurowe Morze",
                "water",
                360f * (float) Math.random(),
                150f,
                5000f,
                360f);
        lazurowe_morze.setCustomDescriptionId("ui_lazurowe_morze"); //reference descriptions.csv
        lazurowe_morze.setInteractionImage("illustrations", "fikenhild");
        lazurowe_morze.getSpec().setTexture(Global.getSettings().getSpriteName("planets", "ui_frozen_water_world"));
        lazurowe_morze.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "sindria"));
        lazurowe_morze.getSpec().setAtmosphereThickness(0.3f);
        lazurowe_morze.getSpec().setAtmosphereColor(new Color(106, 26, 186,235));
        //lazurowe_morze.getSpec().setCloudTexture("graphics/planets/ui_clouds_storm");
        lazurowe_morze.applySpecChanges();



        // Terrain
        //add first stable loc
        SectorEntityToken stableLoc1 = system.addCustomEntity("jasnazda_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(pieklo, MathUtils.getRandomNumberInRange(0f, 360f), 4500f, 220);

        //add second stable loc
        SectorEntityToken stableLoc2 = system.addCustomEntity("jasnazda_stableloc_2", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc2.setCircularOrbit(pieklo, MathUtils.getRandomNumberInRange(0f, 360f), 5500f, 520);

        //broken lava world ring
        system.addRingBand(pieklo, "misc", "ui_rings_lava0", 256f, 0, Color.gray, 256f, 265f, 30f);

        //inner band
        system.addRingBand(pieklo, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, 3000f, 250f, Terrain.RING, null);

        //lazurowe morze ring
        system.addRingBand(lazurowe_morze, "misc", "rings_ice0", 256f, 2, Color.gray, 256f, 220f, 30f, Terrain.RING, "Grace of Lazurowe Morze");

        // Argon Gamma: Dead World
        //PlanetAPI arka = system.addPlanet("ui_Arka", aekiStar,"Arka","barren-bombarded",360f * (float) Math.random(),320f,3000f,41f);
        //arka.setCustomDescriptionId("ui-2"); //reference descriptions.csv
        //PlanetConditionGenerator.generateConditionsForPlanet(arka, StarAge.AVERAGE);



        system.autogenerateHyperspaceJumpPoints(true, true);

        //i lazy, let game design the rest for me
        float radiusAfter = StarSystemGenerator.addOrbitingEntities(system, pieklo, StarAge.YOUNG,
                3, 5, // min/max entities to add
                6000f, // radius to start adding at
                4, // name offset - next planet will be <system name> <roman numeral of this parameter + 1>
                false, // whether to use custom or system-name based names
                false); // whether to allow habitable worlds

        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 2f;

        float radius = system.getMaxRadiusInHyperspace();
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f);
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);
    }
}