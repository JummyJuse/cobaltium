
package com.jummyjuse.cobaltium;

import com.jummyjuse.cobaltium.armor.CobaltArmorHandler;
import com.jummyjuse.cobaltium.mixin.CobaltStealthOverlayMixin;
import com.simibubi.create.Create;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.bus.api.SubscribeEvent;

@Mod(value = cobaltium.MODID, dist = Dist.CLIENT)
public class cobaltiumClient {

    public cobaltiumClient(ModContainer container) {
        // Register config screen
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    static void onClientSetup(FMLClientSetupEvent event) {
        cobaltium.LOGGER.info("HELLO FROM CLIENT SETUP");
        cobaltium.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}