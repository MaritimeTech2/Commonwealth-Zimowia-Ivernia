package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.combat.ShipAPI;

public class MilitarySensorCore extends BaseHullMod {

	@Override
	public boolean isApplicableToShip(ShipAPI ship) {
		return true;
	}
	public String getUnapplicableReason(ShipAPI ship) {
		if (ship != null && ship.getHullSize() != HullSize.CAPITAL_SHIP && ship.getHullSize() != HullSize.CRUISER) {
			return "Can only be installed on cruisers and capital ships";
		}
		if (ship.getVariant().getHullMods().contains("targetingunit")) {
			return "Incompatible with Integrated Targeting Unit";
		}
		if (ship.getVariant().getHullMods().contains("advancedcore")) {
			return "Incompatible with Advanced Targeting Core";
		}
		if (ship.getVariant().getHullMods().contains(HullMods.DISTRIBUTED_FIRE_CONTROL)) {
			return "Incompatible with Distributed Fire Control";
		}

		return null;
	}

	public static final float RECOIL_BONUS = 50f;
	public static final float PROJ_SPEED_BONUS = 30f;
	public static float RANGE_BONUS = 20f;
	public static float PD_MINUS = 190f;
	public static float VISION_BONUS = 1000f;
	//public static float AUTOFIRE_AIM = 0.5f;
	public static final float PROJ_SPEED_BONUS = 50f;
	public static float RANGE_MULT = 80f;
	public static float VISION_BONUS = 1500f;
	public static float TURRET_SPEED_BONUS = 40f;
	public static float ECM_BONUS = 3f;
	public static float ECCM_CHANCE = 0.5f;
	public static float GUIDANCE_IMPROVEMENT = 1f;
	public static float EW_PENALTY_MULT = 0.25f;
	public static float SENSOR_BONUS = 50f;
	public static float AUTOFIRE_AIM = 0.2f;



	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int)Math.round(RANGE_BONUS) + "%";
		if (index == 0) return "" + (int)Math.round((1f - RANGE_MULT) * 100f) + "%";
		if (index == 1) return "" + (int)Math.round(PROJ_SPEED_BONUS) + "%";
		//if (index == 0) return "" + (int)RANGE_THRESHOLD;
		if (index == 1) return "" + new Float(VISION_BONUS).intValue();
		if (index == 2) return "" + new Float(VISION_BONUS).intValue();
		if (index == 3) return "" + new Float(SENSOR_BONUS).intValue();
		if (index == 4) return "" + (int)Math.round(TURRET_SPEED_BONUS) + "%";
		if (index == 5) return "" + (int)Math.round(ECM_BONUS) + "%";
		if (index == 6) return "" + (int)Math.round(EW_PENALTY_MULT) + "%";


		return null;
	}

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getBallisticWeaponRangeBonus().modifyPercent(id, RANGE_BONUS);
		stats.getEnergyWeaponRangeBonus().modifyPercent(id, RANGE_BONUS);
		stats.getBeamWeaponRangeBonus().modifyPercent(id, RANGE_BONUS);

		stats.getNonBeamPDWeaponRangeBonus().modifyPercent(id, RANGE_BONUS);
		stats.getBeamPDWeaponRangeBonus().modifyPercent(id, RANGE_BONUS);
		// modify all weapon type range by a percent
		stats.getBallisticWeaponRangeBonus().modifyMult(id, RANGE_MULT);
		stats.getEnergyWeaponRangeBonus().modifyMult(id, RANGE_MULT);
		stats.getBeamWeaponRangeBonus().modifyMult(id, RANGE_MULT);
		stats.getNonBeamPDWeaponRangeBonus().modifyMult(id, RANGE_MULT);
		stats.getBeamPDWeaponRangeBonus().modifyMult(id, RANGE_MULT);

		stats.getSightRadiusMod().modifyFlat(id, VISION_BONUS);
		//stats.getAutofireAimAccuracy().modifyFlat(id, AUTOFIRE_AIM);

		//stats.getMaxRecoilMult().modifyMult(id, 1f - (0.01f * RECOIL_BONUS));
		//stats.getRecoilPerShotMult().modifyMult(id, 1f - (0.01f * RECOIL_BONUS));
		// modify projectile speed by a percent
		stats.getProjectileSpeedMult().modifyPercent(id, PROJ_SPEED_BONUS);

		// modify vision range by a flat value
		stats.getSightRadiusMod().modifyFlat(id, VISION_BONUS);

		// increase autofire aiming (may not work)
		stats.getAutofireAimAccuracy().modifyFlat(id, AUTOFIRE_AIM);

		// Modify turret rotation speed by a percent
		stats.getWeaponTurnRateBonus().modifyPercent(id, TURRET_SPEED_BONUS);
		stats.getBeamWeaponTurnRateBonus().modifyPercent(id, TURRET_SPEED_BONUS);

		// modify the ecm bonus
		stats.getDynamic().getMod(Stats.ELECTRONIC_WARFARE_FLAT).modifyFlat(id, ECM_BONUS);

		// modify missile aiming
		stats.getEccmChance().modifyFlat(id, ECCM_CHANCE);
		stats.getMissileGuidance().modifyFlat(id, GUIDANCE_IMPROVEMENT);

		// reduce enemy ecm bonus by a percent
		stats.getDynamic().getMod(Stats.ELECTRONIC_WARFARE_PENALTY_MOD).modifyMult(id, EW_PENALTY_MULT);

		// increase sensor range in campaign map
		stats.getDynamic().getMod(Stats.HRS_SENSOR_RANGE_MOD).modifyFlat(id, SENSOR_BONUS);

	}

	
}
