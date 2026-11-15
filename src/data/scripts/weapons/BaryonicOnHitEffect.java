package data.scripts.weapons;

import java.awt.Color;

import org.lwjgl.util.vector.Vector2f;

import com.fs.starfarer.api.combat.BeamAPI;
import com.fs.starfarer.api.combat.BeamEffectPlugin;
import com.fs.starfarer.api.combat.CollisionClass;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.loading.DamagingExplosionSpec;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.util.IntervalUtil;

public class BaryonicOnHitEffect implements BeamEffectPlugin {

    //would make it 0 sec, but im scared of players getting an endless exlposion
    private IntervalUtil fireInterval = new IntervalUtil(0.25f, 2f);
    private boolean wasZero = true;


    public void advance(float amount, CombatEngineAPI engine, BeamAPI beam) {
        CombatEntityAPI target = beam.getDamageTarget();
        if (target instanceof ShipAPI && beam.getBrightness() >= 1f) {
            float dur = beam.getDamage().getDpsDuration();
            // needed because when the ship is in fast-time, dpsDuration will not be reset every frame as it should be
            if (!wasZero) dur = 0;
            wasZero = beam.getDamage().getDpsDuration() <= 0;
            fireInterval.advance(dur);

            //at some random point in the interval set above, will run script to see if you get an explosion.
            if (fireInterval.intervalElapsed()) {
                //if ((float) Math.random() > 0.75f && !shieldHit && target instanceof ShipAPI)
                ShipAPI ship = (ShipAPI) target;
                boolean hitShield = target.getShield() != null && target.getShield().isWithinArc(beam.getRayEndPrevFrame());

                //if hit hull and roll well, then make explosion
                if (!hitShield && (float) Math.random() > 0.5f) {
                    Vector2f point = beam.getRayEndPrevFrame();
                    DamagingProjectileAPI e = engine.spawnDamagingExplosion(createExplosionSpec(), beam.getSource(), point);
                    e.addDamagedAlready(target);
                }
            }
        }
    }
    //shamelessly stolen from paladin explosion specs
    public DamagingExplosionSpec createExplosionSpec() {
        float damage = 1000f;
        DamagingExplosionSpec spec = new DamagingExplosionSpec(
                0.1f, // duration
                75f, // radius
                50f, // coreRadius
                damage, // maxDamage
                damage / 2f, // minDamage
                CollisionClass.PROJECTILE_FF, // collisionClass
                CollisionClass.PROJECTILE_FIGHTER, // collisionClassByFighter
                3f, // particleSizeMin
                3f, // particleSizeRange
                0.5f, // particleDuration
                150, // particleCount
                new Color(255,255,255,255), // particleColor
                new Color(255,100,100,75)  // explosionColor
        );

        spec.setDamageType(DamageType.FRAGMENTATION);
        spec.setUseDetailedExplosion(false);
        spec.setSoundSetId("explosion_guardian");
        return spec;
    }
}