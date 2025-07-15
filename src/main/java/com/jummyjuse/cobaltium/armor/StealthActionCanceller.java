package com.jummyjuse.cobaltium.armor;

import com.jummyjuse.cobaltium.cobaltium;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import net.neoforged.neoforge.event.entity.player.ArrowNockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerDestroyItemEvent;

/**
 * Cancels combat, block interactions, item use, and other actions while in stealth.
 */
@EventBusSubscriber(modid = cobaltium.MODID)
public class StealthActionCanceller {

    // 1) Cancel entity attacks
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onAttackEntity(AttackEntityEvent evt) {
        Player player = evt.getEntity();
        if (CobaltArmorHandler.testForStealth(player)) {
            evt.setCanceled(true);
        }
    }


    // 4) Cancel item use (eating, bows, etc.)
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onRightClickItem(RightClickItem evt) {
        Player player = evt.getEntity();
        if (CobaltArmorHandler.testForStealth(player)) {
            evt.setCanceled(true);
        }
    }

    // 7) Cancel ranged weapon use
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onArrowLoose(ArrowLooseEvent evt) {
        Player player = evt.getEntity();
        if (CobaltArmorHandler.testForStealth(player)) {
            evt.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onArrowNock(ArrowNockEvent evt) {
        Player player = evt.getEntity();
        if (CobaltArmorHandler.testForStealth(player)) {
            evt.setCanceled(true);
        }
    }
}
