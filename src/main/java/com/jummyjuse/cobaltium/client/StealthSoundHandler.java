// src/main/java/com/jummyjuse/cobaltium/client/StealthSoundHandler.java
package com.jummyjuse.cobaltium.client;

import com.jummyjuse.cobaltium.armor.CobaltArmorHandler;
import com.jummyjuse.cobaltium.cobaltium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = cobaltium.MODID, value = Dist.CLIENT)
public class StealthSoundHandler {
    private static boolean wasStealthed = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post evt) {
        // get the local player
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        boolean nowStealthed = CobaltArmorHandler.testForStealth(player);

        if (nowStealthed && !wasStealthed) {
            player.playSound(SoundEvents.DECORATED_POT_PLACE);
        } else if (!nowStealthed && wasStealthed) {
            player.playSound(SoundEvents.DECORATED_POT_BREAK);
        }

        wasStealthed = nowStealthed;
    }
}
