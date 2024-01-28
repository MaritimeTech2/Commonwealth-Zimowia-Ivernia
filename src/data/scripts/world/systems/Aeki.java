package data.scripts.world.systems;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;

import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.PlanetConditionGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;
import com.fs.starfarer.api.campaign.SectorEntityToken;


import static data.scripts.world.utilities.MarketCreateHelper.addMarketplace;

public class Aeki {
    public void generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Aeki");
        system.getLocation().set(28000, 10000); //Northeast of core

        system.setBackgroundTextureFilename("graphics/backgrounds/background3.jpg");
        // create the star and generate the hyperspace anchor for this system
        PlanetAPI aekiStar = system.initStar("Aeki", // unique id for this star
                "star_orange", // id in planets.json
                750f, // radius (in pixels at default zoom)
                250); // corona radius, from star edge
        system.setLightColor(new Color(255, 119, 51)); // light color in entire system, affects all entities
        //add first stable loc
        SectorEntityToken stableLoc1 = system.addCustomEntity("aeki_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(aekiStar, MathUtils.getRandomNumberInRange(0f, 360f), 4000f, 220);
        //add second stable loc
        SectorEntityToken stableLoc2 = system.addCustomEntity("aeki_stableloc_2", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc2.setCircularOrbit(aekiStar, MathUtils.getRandomNumberInRange(0f, 360f), 8000f, 520);
        //Comm Array
        //SectorEntityToken AekiSensorArray = system.addCustomEntity("ui_aeki_sensor", "Domain Sensor Array", "domain_sensor_array", "ui");
        //AekiSensorArray.setCircularOrbitPointingDown(aekiStar, 240.0F, 6300.0F, 200.0F);
        //asteroid belt1 ring
        float asteroidBelt1Dist = 6000f;
        system.addAsteroidBelt(aekiStar, 1000, asteroidBelt1Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Inner Band");
        system.addRingBand(aekiStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt1Dist - 200, 250f);
        system.addRingBand(aekiStar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt1Dist, 350f);
        system.addRingBand(aekiStar, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, asteroidBelt1Dist + 200, 400f);
        // Argon Gamma: Dead World
        PlanetAPI arka = system.addPlanet("ui_Arka", aekiStar,"Arka","barren-bombarded",360f * (float) Math.random(),320f,3000f,41f);
        //arka.setCustomDescriptionId("ui-2"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(arka, StarAge.AVERAGE);

        // Lumyra: Desertic industry world
        PlanetAPI lumyra = system.addPlanet("ui_Lumyra",
                aekiStar,
                "Lumyra",
                "desert",
                360f * (float) Math.random(),
                200f,
                5000f,
                420f);

        system.addRingBand(lumyra, "misc", "ui_rings_lava0", 256f, 0, Color.gray, 256f, 230, 30f);

        lumyra.setCustomDescriptionId("Lumyra_planet"); //reference descriptions.csv
        lumyra.setInteractionImage("illustrations", "urban00");
        MarketAPI lumyra_market = addMarketplace("ui", lumyra, null,
                "Lumyra",
                6,
                Arrays.asList(
                        Conditions.POPULATION_6,
                        Conditions.ORE_SPARSE,
                        Conditions.RARE_ORE_SPARSE,
                        Conditions.NO_ATMOSPHERE,
                        Conditions.DESERT,
                        Conditions.INDUSTRIAL_POLITY

                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_OPEN,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.MEGAPORT,
                        Industries.FUELPROD,
                        Industries.BATTLESTATION_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.HIGHCOMMAND,
                        Industries.WAYSTATION
                        //Industries.ORBITALWORKS

                ),
                0.06f,
                true,
                true);


        // Icylla: icyllan homeworld
        PlanetAPI icylla = system.addPlanet("ui_Icylla",
                aekiStar,
                "Icylla",
                "terran",
                360f * (float) Math.random(),
                220f,
                8000f,
                320f);

        icylla.setCustomDescriptionId("icylla_planet"); //reference descriptions.csv
        icylla.setInteractionImage("illustrations", "city_from_above");
        MarketAPI icylla_market = addMarketplace("ui", icylla, null,
                "Icylla",
                7,
                Arrays.asList(
                        Conditions.POPULATION_7,
                        Conditions.ORE_RICH,
                        Conditions.FARMLAND_RICH,
                        Conditions.ORGANICS_ABUNDANT,
                        Conditions.HABITABLE,
                        Conditions.TERRAN,
                        Conditions.REGIONAL_CAPITAL

                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_OPEN,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.MEGAPORT,
                        Industries.FARMING,
                        Industries.STARFORTRESS_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.HIGHCOMMAND,
                        Industries.WAYSTATION,
                        Industries.ORBITALWORKS

                ),
                0.06f,
                false,
                true);

        // Aela: terran moon of icylla, home of terrafroming burreu
        PlanetAPI aela = system.addPlanet("ui_Aela",
                icylla,
                "Aela",
                "terran-eccentric",
                360f * (float) Math.random(),
                80f,
                1000f,
                100f);

        aela.setCustomDescriptionId("Aela_planet"); //reference descriptions.csv
        aela.setInteractionImage("illustrations", "urban02");
        MarketAPI aela_market = addMarketplace("ui", aela, null,
                "Aela",
                6,
                Arrays.asList(
                        Conditions.POPULATION_6,
                        Conditions.ORE_SPARSE,
                        Conditions.HABITABLE,
                        Conditions.TERRAN,
                        Conditions.URBANIZED_POLITY,
                        Conditions.FARMLAND_ADEQUATE

                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_OPEN,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.MEGAPORT,
                        Industries.LIGHTINDUSTRY,
                        Industries.BATTLESTATION_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.HIGHCOMMAND,
                        Industries.WAYSTATION,
                        Industries.FARMING

                ),
                0.06f,
                true,
                true);

        lumyra_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList(Items.PRISTINE_NANOFORGE)); //couldn't find another way to add w/ forge!
        icylla_market.getIndustry(Industries.HIGHCOMMAND).setAICoreId(Commodities.ALPHA_CORE);
        lumyra_market.getIndustry(Industries.HIGHCOMMAND).setAICoreId(Commodities.ALPHA_CORE);
        lumyra_market.getIndustry(Industries.ORBITALWORKS).setAICoreId(Commodities.ALPHA_CORE);
        icylla_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        icylla_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.GAMMA_CORE);
        aela_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        //icylla_market.getIndustry(Industries.ORBITALWORKS).setSpecialItem(Items.PRISTINE_NANOFORGE);
        //icylla_market.getIndustry(Industries.FARMING).setSpecialItem(Items.SOIL_NANITES);

        // Kaukus Gas Giant
        PlanetAPI kaukus = system.addPlanet("ui_Kaukus", aekiStar,"Kaukus","ice_giant",360f * (float) Math.random(),420f,12000f,2421f);
        //arka.setCustomDescriptionId("ui-2"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(kaukus, StarAge.AVERAGE);

        // ilkus Gas Giant moon
        PlanetAPI ilkus = system.addPlanet("ui_Ilkus",kaukus,"Ilkus","frozen",360f * (float) Math.random(),80f,1500f,121f);
        //arka.setCustomDescriptionId("ui-2"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(ilkus, StarAge.AVERAGE);

        // saikus Gas Giant moon
        PlanetAPI saikus = system.addPlanet("ui_Saikus",kaukus,"Saikus","cryovolcanic",360f * (float) Math.random(),120f,2200f,421f);
        //arka.setCustomDescriptionId("ui-2"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(saikus, StarAge.AVERAGE);

        system.autogenerateHyperspaceJumpPoints(true, true);

        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 2f;

        float radius = system.getMaxRadiusInHyperspace();
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f);
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);
    }
}