package com.jummyjuse.cobaltium.armor;

import com.jummyjuse.cobaltium.client.ModPartials;          // ← your COBALT_HIDE partial
import com.jummyjuse.cobaltium.armor.CobaltArmorHandler;    // server-side testForStealth

import com.jummyjuse.cobaltium.cobaltium;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.simibubi.create.content.logistics.box.PackageRenderer;
import com.google.common.cache.Cache;
import com.simibubi.create.foundation.utility.TickBasedCache;

import net.minecraft.client.Minecraft;
import net.minecraft.client.CameraType;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** Client-side render overlay that turns a sneaking, fully-armoured player into a Cobalt Hide crate. */
@EventBusSubscriber(
        modid = cobaltium.MODID,
        value = Dist.CLIENT
)
public class CobaltArmorHandlerClient {

    /** Stays at size 1 but keeps cache semantics identical to Create’s code. */
    private static final Cache<UUID, Boolean> STEALTH_CACHE = new TickBasedCache<>(20, true);
    private static final PartialModel BOX = ModPartials.COBALT_HIDE;

    /* keep cache alive so the check isn't re-evaluated every tick */
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post e) {
        Player p = e.getEntity();
        if (CobaltArmorHandler.testForStealth(p))
            STEALTH_CACHE.put(p.getUUID(), Boolean.TRUE);
    }

    /* replace player model with the crate while crouching & armoured */
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onRenderPlayer(RenderPlayerEvent.Pre e) {
        Player player = e.getEntity();
        if (!CobaltArmorHandler.testForStealth(player)) return;

        /* keep hands visible in first-person */
        if (player == Minecraft.getInstance().player
                && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON)
            return;

        e.setCanceled(true);

        var ms = e.getPoseStack();
        ms.pushPose();

        /* align with vanilla renderer’s offset */
        Vec3 offset = e.getRenderer().getRenderOffset((AbstractClientPlayer) player, e.getPartialTick());
        ms.translate(0, -offset.y, 0);

        /* subtle bob */
        long gt = player.level().getGameTime();
        float rt = gt + e.getPartialTick();
        float moveLen = (float) player.position().subtract(player.xo, player.yo, player.zo).length();
        if (player.onGround())
            ms.translate(0,
                    Math.min(Math.abs(Mth.cos((rt % 256) / 2f)) * -offset.y, moveLen * 5), 0);

        /* rotate & scale */
        float yaw = Mth.lerp(e.getPartialTick(), player.yRotO, player.getYRot());
        float scale = player.getScale();
        ms.scale(scale, scale, scale);

        /* draw the crate */
        PackageRenderer.renderBox(
                player,
                yaw,
                ms,
                e.getMultiBufferSource(),
                e.getPackedLight(),
                BOX
        );

        ms.popPose();
    }
}
