package data.scripts.world.systems;

import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.PlanetConditionGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
//import org.lazywizard.lazylib.MathUtils;

import java.awt.*;
import java.util.Arrays;

import static data.scripts.world.utilities.MarketCreateHelper.addMarketplace;

public class Kaeus {
    public void generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Kaeus");
        system.getLocation().set(27000, 11000); //Northeast of core

        system.setBackgroundTextureFilename("graphics/backgrounds/background3.jpg");
        // create the star and generate the hyperspace anchor for this system
        PlanetAPI kaeusstar = system.initStar("Kaeus", // unique id for this star
                "star_yellow", // id in planets.json
                1350f, // radius (in pixels at default zoom)
                750); // corona radius, from star edge
        system.setLightColor(new Color(249, 248, 245)); // light color in entire system, affects all entities
        //add first stable loc
        SectorEntityToken stableLoc1 = system.addCustomEntity("kaeus_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(kaeusstar, (float) Math.random() * 360f, 4000f, 220);
        //add second stable loc
        SectorEntityToken stableLoc2 = system.addCustomEntity("kaeus_stableloc_2", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc2.setCircularOrbit(kaeusstar, (float) Math.random() * 360f, 8000f, 520);
        //Comm Array
        //SectorEntityToken AekiSensorArray = system.addCustomEntity("ui_kaeus_sensor", "Domain Sensor Array", "domain_sensor_array", "ui");
        //AekiSensorArray.setCircularOrbitPointingDown(kaeusstar, 240.0F, 6300.0F, 200.0F);
        //asteroid belt1 ring
        float asteroidBelt1Dist = 7000f;
        system.addAsteroidBelt(kaeusstar, 1000, asteroidBelt1Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Inner Band");
        system.addRingBand(kaeusstar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt1Dist - 200, 250f);
        system.addRingBand(kaeusstar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt1Dist, 350f);
        system.addRingBand(kaeusstar, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, asteroidBelt1Dist + 200, 400f);
        // Argon Gamma: Dead World
        PlanetAPI Erka = system.addPlanet("ui_Erka",kaeusstar,"Erka","desert",360f * (float) Math.random(),320f,4500f,141f);
        //arka.setCustomDescriptionId("ui-2"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(Erka, StarAge.AVERAGE);

        // Lumyra: Desertic industry world
        PlanetAPI narxxia = system.addPlanet("ui_Narxxia",
                kaeusstar,
                "Narxxia",
                "lava",
                360f * (float) Math.random(),
                250f,
                3500f,
                42f);

        narxxia.setCustomDescriptionId("Narxxia_planet"); //reference descriptions.csv
        narxxia.setInteractionImage("illustrations", "urban00");
        MarketAPI narxxia_market = addMarketplace("ui", narxxia, null,
                "Lumyra",
                4,
                Arrays.asList(
                        Conditions.POPULATION_4,
                        Conditions.ORE_ULTRARICH,
                        Conditions.RARE_ORE_RICH,
                        Conditions.TECTONIC_ACTIVITY,
                        Conditions.VERY_HOT

                ),
                Arrays.asList(
                        Submarkets.SUBMARKET_OPEN,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.MEGAPORT,
                        Industries.MINING,
                        Industries.ORBITALSTATION_MID,
                        Industries.HEAVYBATTERIES,
                        Industries.REFINING,
                        Industries.WAYSTATION

                ),
                0.06f,
                true,
                true);


        // Icylla: icyllan homeworld
        PlanetAPI merelia = system.addPlanet("ui_merelia",
                kaeusstar,
                "Merelia",
                "water",
                360f * (float) Math.random(),
                420f,
                11000f,
                320f);

        merelia.setCustomDescriptionId("merelia_planet"); //reference descriptions.csv
        merelia.setInteractionImage("illustrations", "city_from_above");
        MarketAPI merelia_market = addMarketplace("ui", merelia, null,
                "Merelia",
                5,
                Arrays.asList(
                        Conditions.POPULATION_5,
                        Conditions.ORE_RICH,
                        Conditions.FARMLAND_ADEQUATE,
                        Conditions.ORGANICS_ABUNDANT,
                        Conditions.HABITABLE,
                        Conditions.WATER_SURFACE,
                        Conditions.VOLATILES_ABUNDANT

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
                        Industries.STARFORTRESS_MID,
                        Industries.HEAVYBATTERIES,
                        Industries.MILITARYBASE,
                        Industries.WAYSTATION,
                        Industries.MINING

                ),
                0.06f,
                false,
                true);

        // Aela: terran moon of icylla, home of terrafroming burreu
        PlanetAPI virca = system.addPlanet("ui_Virca",
                merelia,
                "Virca",
                "jungle",
                360f * (float) Math.random(),
                80f,
                1500f,
                100f);

        virca.setCustomDescriptionId("virca_planet"); //reference descriptions.csv
        virca.setInteractionImage("illustrations", "urban02");
        MarketAPI virca_market = addMarketplace("ui", virca, null,
                "virca",
                5,
                Arrays.asList(
                        Conditions.POPULATION_5,
                        Conditions.ORE_SPARSE,
                        Conditions.ORGANICS_PLENTIFUL,
                        Conditions.JUNGLE,
                        Conditions.HABITABLE,
                        Conditions.MILD_CLIMATE,
                        Conditions.RURAL_POLITY,
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
                        Industries.MINING,
                        Industries.BATTLESTATION_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.MILITARYBASE,
                        Industries.WAYSTATION,
                        Industries.FARMING

                ),
                0.06f,
                true,
                true);

        // Aela: terran moon of icylla, home of terrafroming burreu
        PlanetAPI orlia = system.addPlanet("ui_Orlia",
                merelia,
                "Orlia",
                "frozen",
                360f * (float) Math.random(),
                100f,
                2500f,
                300f);

        orlia.setCustomDescriptionId("Orlia_planet"); //reference descriptions.csv
        orlia.setInteractionImage("illustrations", "urban02");
        MarketAPI orlia_market = addMarketplace("ui", orlia, null,
                "Orlia",
                4,
                Arrays.asList(
                        Conditions.POPULATION_4,
                        Conditions.ORE_SPARSE,
                        Conditions.COLD,
                        Conditions.INDUSTRIAL_POLITY

                ),
                Arrays.asList(
                        Submarkets.SUBMARKET_OPEN,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.MEGAPORT,
                        Industries.FUELPROD,
                        Industries.ORBITALSTATION_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.WAYSTATION,
                        Industries.REFINING

                ),
                0.06f,
                true,
                true);


        //icylla_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList(Items.PRISTINE_NANOFORGE)); //couldn't find another way to add w/ forge!
        merelia_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        narxxia_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        orlia_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        virca_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        //icylla_market.getIndustry(Industries.ORBITALWORKS).setSpecialItem(Items.PRISTINE_NANOFORGE);
        //icylla_market.getIndustry(Industries.FARMING).setSpecialItem(Items.SOIL_NANITES);

        // Kaukus Gas Giant
        PlanetAPI irtuk = system.addPlanet("ui_Irtuk",kaeusstar,"Irtuk","gas_giant",360f * (float) Math.random(),420f,8000f,2421f);
        //arka.setCustomDescriptionId("ui-2"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(irtuk, StarAge.AVERAGE);

        // ilkus Gas Giant moon
        PlanetAPI ilta = system.addPlanet("ui_Ilta",irtuk,"Ilta","barren",360f * (float) Math.random(),80f,1500f,121f);
        //arka.setCustomDescriptionId("ui-2"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(ilta, StarAge.AVERAGE);

        // saikus Gas Giant moon
        PlanetAPI saitaus = system.addPlanet("ui_Saitaus",irtuk,"Saitaus","barren",360f * (float) Math.random(),120f,2200f,421f);
        //arka.setCustomDescriptionId("ui-2"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(saitaus, StarAge.AVERAGE);

        system.autogenerateHyperspaceJumpPoints(true, true);

        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 2f;

        float radius = system.getMaxRadiusInHyperspace();
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f);
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);
    }
}
