package com.jummyjuse.cobaltium.client;

import com.jummyjuse.cobaltium.block.ModBlocks;
import com.jummyjuse.cobaltium.cobaltium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = cobaltium.MODID, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent evt) {
        // 1) block render layer for cobalt_hide
        ItemBlockRenderTypes.setRenderLayer(
                ModBlocks.COBALT_HIDE.get(),
                RenderType.cutoutMipped()
        );

        // 2) armor emissive glow layer
        // defer to the render thread via enqueueWork

        // inside onClientSetup():
        evt.enqueueWork(() -> {
            var mc = Minecraft.getInstance();
            var dispatcher = mc.getEntityRenderDispatcher();
            var entityModels = mc.getEntityModels();
            var player = mc.player;
            if (player == null) return;
            // lookup via actual instance
            PlayerRenderer renderer = (PlayerRenderer) dispatcher.getRenderer(player);
            // pass the EntityModelSet so the layer can bake its armor parts
            //renderer.addLayer(new CobaltArmorGlowLayer(renderer, entityModels));
        });

    }
}
