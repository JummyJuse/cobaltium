package com.jummyjuse.cobaltium.armor;

import com.jummyjuse.cobaltium.client.ModPartials;
import com.jummyjuse.cobaltium.cobaltium;
import com.google.common.cache.Cache;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.logistics.box.PackageRenderer;
import com.simibubi.create.foundation.utility.TickBasedCache;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.UUID;

/**
 * Client-side handler that renders the stealth crate with proper transparency and bobbing.
 */
@EventBusSubscriber(modid = cobaltium.MODID, value = Dist.CLIENT)
public class CobaltArmorHandlerClient {
    private static final Cache<UUID, Boolean> STEALTH_CACHE = new TickBasedCache<>(20, true);
    private static final PartialModel BOX = ModPartials.COBALT_HIDE;

    /** Keeps the stealth cache alive when in stealth */
    @SubscribeEvent
    public static void keepCacheAlive(PlayerTickEvent.Post evt) {
        Player p = evt.getEntity();
        if (CobaltArmorHandler.testForStealth(p)) {
            STEALTH_CACHE.put(p.getUUID(), true);
        }
    }

    /** Renders the crate overlay with transparency under stealth conditions */
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onRenderPlayer(RenderPlayerEvent.Pre evt) {
        Player player = evt.getEntity();
        if (!CobaltArmorHandler.testForStealth(player)) return;

        // Allow first-person hands to render normally
        if (player == Minecraft.getInstance().player
                && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
            return;
        }

        evt.setCanceled(true);
        var ms = evt.getPoseStack();
        ms.pushPose();

        // Apply vanilla render offset and bob animation
        Vec3 offset = evt.getRenderer().getRenderOffset((AbstractClientPlayer) player, evt.getPartialTick());
        ms.translate(0, -offset.y, 0);
        long gameTime = player.level().getGameTime();
        float rt = gameTime + evt.getPartialTick();
        float move = (float) player.position().subtract(player.xo, player.yo, player.zo).length();
        ms.translate(0, Math.min(Math.abs(Mth.cos((rt % 256) / 2f)) * -offset.y, move * 5), 0);

        // Rotate and scale
        float yaw = Mth.lerp(evt.getPartialTick(), player.yRotO, player.getYRot());
        float scale = player.getScale();
        ms.scale(scale, scale, scale);

        // Wrap the buffer source to render with cutout_mipped for transparency
        MultiBufferSource wrapped = wrapBuffer(evt.getMultiBufferSource());

        // Render the crate
        PackageRenderer.renderBox(
                player,
                yaw,
                ms,
                wrapped,
                evt.getPackedLight(),
                BOX
        );

        ms.popPose();
    }

    /**
     * Wraps the given buffer source to redirect solid layer to cutout_mipped,
     * ensuring true transparency for the partial’s texture.
     */
    private static MultiBufferSource wrapBuffer(MultiBufferSource orig) {
        return new MultiBufferSource() {
            @Override
            public VertexConsumer getBuffer(RenderType type) {
                // Redirect solid to cutout_mipped
                if (type == RenderType.solid()) {
                    return orig.getBuffer(RenderType.cutoutMipped());
                }
                return orig.getBuffer(type);
            }
        };
    }
}
