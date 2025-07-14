package com.jummyjuse.cobaltium.armor;

import java.util.Iterator;
import java.util.UUID;

import com.jummyjuse.cobaltium.item.ModItems; // ← adjust to your registry class

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

/**
 * Stealth handler for the Cobalt‑Alloy armor set.
 * <p>When the full set is worn and the player crouches, their hit‑box shrinks,
 * they become invisible to mobs, and hostile targets are cleared.</p>
 */
@EventBusSubscriber
public class CobaltArmorHandler {
    // … existing code …
    @SubscribeEvent
    public static void onInvulnerabilityCheck(EntityInvulnerabilityCheckEvent event) {
        if (testForStealth(event.getEntity())) {
            event.setInvulnerable(true);
        }
    }

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (testForStealth(event.getEntity())) {
            event.setCanceled(true);
        }
    }
    /* ───────────────────────────────── Size / Eye‑height ───────────────────────────────── */
    @SubscribeEvent
    public static void onSize(EntityEvent.Size event) {
        Entity entity = event.getEntity();
        if (!entity.isAddedToLevel() || !testForStealth(entity))
            return;

        // Preserve scale if the mod or datapack changes it
        float scale = entity instanceof LivingEntity le ? le.getScale() : 1.0F;

        // In 1.21 use width + height + eyeHeight in one call
        event.setNewSize(EntityDimensions.fixed(0.6F * scale, 0.8F * scale).withEyeHeight(0.6F * scale));
        }

    /* ───────────────────────────────── Visibility ───────────────────────────────── */

    @SubscribeEvent
    public static void onVisibility(LivingEvent.LivingVisibilityEvent event) {
        if (testForStealth(event.getEntity()))
            event.modifyVisibility(0.0);
    }

    /* ───────────────────────────────── Mob AI reset every 16 ticks ───────────────────────────────── */

    @SubscribeEvent
    public static void onMobTick(EntityTickEvent.Pre event) {
        Entity ent = event.getEntity();
        if (!(ent instanceof LivingEntity living) || living.tickCount % 16 != 0)
            return;

        if (!(living instanceof Mob mob))
            return;

        // 1. Clear current target if the target is stealthed
        if (testForStealth(mob.getTarget())) {
            mob.setTarget(null);
            if (mob.targetSelector != null) {
                for (WrappedGoal wrapped : mob.targetSelector.getAvailableGoals()) {
                    if (wrapped.isRunning() && wrapped.getGoal() instanceof TargetGoal tg)
                        tg.stop();
                }
            }
        }

        // 2. Clear persistent anger for NeutralMob
        if (mob instanceof NeutralMob nMob && mob.level() instanceof ServerLevel sl) {
            UUID anger = nMob.getPersistentAngerTarget();
            if (anger != null && testForStealth(sl.getEntity(anger)))
                nMob.stopBeingAngry();
        }

        // 3. Clear revenge target
        if (testForStealth(mob.getLastHurtByMob())) {
            mob.setLastHurtByMob(null);
            mob.setLastHurtByPlayer(null);
        }
    }

    /* ───────────────────────────────── Utility ───────────────────────────────── */

    /** Returns {@code true} if entity is crouching & wearing full Cobalt‑Alloy armor. */
    public static boolean testForStealth(Entity entityIn) {
        if (entityIn instanceof LivingEntity entity) {
            if (entity.getPose() != Pose.CROUCHING) {
                return false;
            } else {
                if (entity instanceof Player) {
                    Player player = (Player)entity;
                    if (player.getAbilities().flying) {
                        return false;
                    }
                }

                return ModItems.COBALT_ALLOY_HELMET.get()     .equals(entity.getItemBySlot(EquipmentSlot.HEAD ).getItem()) &&
                        ModItems.COBALT_ALLOY_CHESTPLATE.get() .equals(entity.getItemBySlot(EquipmentSlot.CHEST).getItem()) &&
                        ModItems.COBALT_ALLOY_LEGGINGS.get()   .equals(entity.getItemBySlot(EquipmentSlot.LEGS ).getItem()) &&
                        ModItems.COBALT_ALLOY_BOOTS.get()      .equals(entity.getItemBySlot(EquipmentSlot.FEET ).getItem());
            }
        } else {
            return false;
        }
    }

}

