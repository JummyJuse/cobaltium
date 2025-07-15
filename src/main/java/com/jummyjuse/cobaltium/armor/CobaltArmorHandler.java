// CobaltArmorHandler.java
package com.jummyjuse.cobaltium.armor;

import com.jummyjuse.cobaltium.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.UUID;

@EventBusSubscriber
public class CobaltArmorHandler {

    // 1) Invulnerability
    @SubscribeEvent
    public static void onInvulnerabilityCheck(EntityInvulnerabilityCheckEvent evt) {
        if (evt.getEntity() instanceof Player p && testForStealth(p))
            evt.setInvulnerable(true);
    }

    // 2) Cancel incoming damage
    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent evt) {
        if (evt.getEntity() instanceof Player p && testForStealth(p))
            evt.setCanceled(true);
    }

    // 3) Shrink hitbox & eye height
    @SubscribeEvent
    public static void onSize(EntityEvent.Size evt) {
        Entity e = evt.getEntity();
        if (e instanceof Player p && p.isAddedToLevel() && testForStealth(p)) {
            float s = p.getScale();
            evt.setNewSize(
                    EntityDimensions
                            .fixed(0.6F * s, 0.8F * s)
                            .withEyeHeight(0.6F * s)
            );
        }
    }

    // 4) Invisible to mobs
    @SubscribeEvent
    public static void onVisibility(LivingEvent.LivingVisibilityEvent evt) {
        if (evt.getEntity() instanceof Player p && testForStealth(p))
            evt.modifyVisibility(0.0);
    }
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post evt) {
        Player player = evt.getEntity();

        // only when fully armored and in “crawl” pose on land
        if (testForStealth(player)
                && player.getPose() == Pose.SWIMMING
                && !player.isInWater()) {
            player.setPose(Pose.CROUCHING);
        }
    }
    // 5) Clear mob targets every 16 ticks
    @SubscribeEvent
    public static void onMobTick(EntityTickEvent.Pre evt) {
        Entity ent = evt.getEntity();
        if (!(ent instanceof Mob mob) || mob.tickCount % 16 != 0) return;

        // clear current target
        if (mob.getTarget() instanceof Player t && testForStealth(t)) {
            mob.setTarget(null);
            if (mob.targetSelector != null) {
                for (WrappedGoal wg : mob.targetSelector.getAvailableGoals()) {
                    if (wg.isRunning() && wg.getGoal() instanceof TargetGoal tg)
                        tg.stop();
                }
            }
        }

        // clear persistent anger
        if (mob instanceof NeutralMob nm && mob.level() instanceof ServerLevel sl) {
            UUID anger = nm.getPersistentAngerTarget();
            if (anger != null) {
                Entity e2 = sl.getEntity(anger);
                if (e2 instanceof Player t2 && testForStealth(t2))
                    nm.stopBeingAngry();
            }
        }

        // clear revenge
        if (mob.getLastHurtByMob() instanceof Player t3 && testForStealth(t3)) {
            mob.setLastHurtByMob(null);
            mob.setLastHurtByPlayer(null);
        }
    }

    // → No PlayerTickEvent here: we let Minecraft switch you to SWIMMING under blocks,
    //    which automatically gives you its crawl‐speed movement.
    @SubscribeEvent
    public static void onPlayerTickPre(PlayerTickEvent.Pre evt) {
        Player p = evt.getEntity();
        if (testForStealth(p)) {
            p.setPose(Pose.CROUCHING);
        }
    }
    /** full-set + crouch or crawl check */
    public static boolean testForStealth(Player p) {
        Pose pose = p.getPose();
        if (pose != Pose.CROUCHING && pose != Pose.SWIMMING) return false;
        if (p.getAbilities().flying) return false;

        return
                p.getItemBySlot(EquipmentSlot.HEAD).getItem()  == ModItems.COBALT_ALLOY_HELMET.get()     &&
                        p.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.COBALT_ALLOY_CHESTPLATE.get() &&
                        p.getItemBySlot(EquipmentSlot.LEGS).getItem()  == ModItems.COBALT_ALLOY_LEGGINGS.get()   &&
                        p.getItemBySlot(EquipmentSlot.FEET).getItem()  == ModItems.COBALT_ALLOY_BOOTS.get();
    }
}
