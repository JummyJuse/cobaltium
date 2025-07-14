package com.jummyjuse.cobaltium.mixin;

import com.jummyjuse.cobaltium.armor.CobaltArmorHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.gui.Gui")
public class CobaltStealthOverlayMixin {
    private static final ResourceLocation BLUR_TEXTURE = ResourceLocation.fromNamespaceAndPath("cobaltium", "textures/misc/cobalt_blur.png");

    @Inject(method = "renderCameraOverlays", at = @At("TAIL"))
    private void cobaltium_renderStealthOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || !CobaltArmorHandler.testForStealth(player)) return;
        if (!mc.options.getCameraType().isFirstPerson()) return;

        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();

        guiGraphics.pose().pushPose();
        guiGraphics.setColor(1f, 1f, 1f, 1f);

        guiGraphics.blit(
                BLUR_TEXTURE,
                0, 0, 0, 0.0f, 0.0f,
                width, height,
                width, height
        );

        guiGraphics.setColor(1f, 1f, 1f, 1f);
        guiGraphics.pose().popPose();

        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }
}
