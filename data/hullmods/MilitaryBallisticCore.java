package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.combat.ShipAPI;

public class MilitaryBallisticCore extends BaseHullMod {

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

	public static final float PROJ_SPEED_BONUS = 25f;
	public static float RANGE_BONUS = 45f;
	public static float TURRET_SPEED_BONUS = 10f;
	//public static float AUTOFIRE_AIM = 0.5f;

	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int)Math.round(RANGE_BONUS) + "%";
		if (index == 1) return "" + (int)Math.round(PROJ_SPEED_BONUS) + "%";
		if (index == 2) return "" + new Float(VISION_BONUS).intValue() + "su";
		if (index == 2) return "" + n(int)Math.round(TURRET_SPEED_BONUS) + "su";
		return null;
	}

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		// modify weapon range by a set percent
		stats.getBallisticWeaponRangeBonus().modifyPercent(id, RANGE_BONUS);
		stats.getEnergyWeaponRangeBonus().modifyPercent(id, RANGE_BONUS);
		stats.getBeamWeaponRangeBonus().modifyPercent(id, RANGE_BONUS);
		// modify pd weapon range
		stats.getNonBeamPDWeaponRangeBonus().modifyPercent(id, RANGE_BONUS);
		stats.getBeamPDWeaponRangeBonus().modifyPercent(id, RANGE_BONUS);
		// Modify turret rotation speed by a percent
		stats.getWeaponTurnRateBonus().modifyPercent(id, TURRET_SPEED_BONUS);
		stats.getBeamWeaponTurnRateBonus().modifyPercent(id, TURRET_SPEED_BONUS);
		// modify projectile speed by a percent
		stats.getProjectileSpeedMult().modifyPercent(id, PROJ_SPEED_BONUS);
	}

	
}
