//package com.jummyjuse.cobaltium.client;
//
//import com.jummyjuse.cobaltium.item.CobaltAlloyArmorItem;
//import com.jummyjuse.cobaltium.cobaltium;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.entity.layers.RenderLayer;
//import net.minecraft.client.renderer.entity.RenderLayerParent;
//import net.minecraft.client.renderer.entity.player.PlayerRenderer;
//import net.minecraft.client.model.HumanoidModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.model.geom.builders.LayerDefinition;
//import net.minecraft.client.renderer.texture.OverlayTexture;
//import net.minecraft.client.model.geom.ModelLayers;
//import net.minecraft.client.model.geom.EntityModelSet;
//import net.minecraft.client.player.AbstractClientPlayer;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.EquipmentSlot;
//
//public class CobaltArmorGlowLayer
//        extends RenderLayer<AbstractClientPlayer, HumanoidModel<AbstractClientPlayer>> {
//
//    private final HumanoidModel<AbstractClientPlayer> innerArmor;
//    private final HumanoidModel<AbstractClientPlayer> outerArmor;
//
////    public CobaltArmorGlowLayer(PlayerRenderer parent, EntityModelSet models) {
////        super(parent);
////        // Bake the armor layers from vanilla ModelLayers:
////        ModelPart innerRoot = models.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR);
////        ModelPart outerRoot = models.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR);
////
////        // RenderType.entityCutoutNoCull is vanilla’s armor RT—eyes() just needs the texture
////        this.innerArmor = new HumanoidModel<>(innerRoot, RenderType::entityCutoutNoCull);
////        this.outerArmor = new HumanoidModel<>(outerRoot, RenderType::entityCutoutNoCull);
////    }
//
//    @Override
//    public void render(PoseStack ms,
//                       MultiBufferSource buffers,
//                       int packedLight,
//                       AbstractClientPlayer player,
//                       float limbSwing,
//                       float limbSwingAmount,
//                       float partialTicks,
//                       float ageInTicks,
//                       float netHeadYaw,
//                       float headPitch) {
//        for (EquipmentSlot slot : EquipmentSlot.values()) {
//            if (!(player.getItemBySlot(slot).getItem() instanceof CobaltAlloyArmorItem))
//                continue;
//
//            ResourceLocation tex = ResourceLocation.fromNamespaceAndPath(
//                    cobaltium.MODID,
//                    slot == EquipmentSlot.LEGS
//                            ? "textures/models/armor/cobalt_alloy_layer_2_emissive.png"
//                            : "textures/models/armor/cobalt_alloy_layer_1_emissive.png"
//            );
//
//            // pick the right armor model
//            HumanoidModel<AbstractClientPlayer> armorModel =
//                    slot == EquipmentSlot.LEGS ? innerArmor : outerArmor;
//
//            // copy pose + animations
//            HumanoidModel<AbstractClientPlayer> parentModel = getParentModel();
//            parentModel.copyPropertiesTo(armorModel);
//            armorModel.prepareMobModel(player, limbSwing, limbSwingAmount, partialTicks);
//            armorModel.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
//
//            // draw the glow
//            VertexConsumer vb = buffers.getBuffer(RenderType.eyes(tex));
//            armorModel.renderToBuffer(
//                    ms,
//                    vb,
//                    0xF000F0,                  // full-bright lightmap
//                    OverlayTexture.NO_OVERLAY
//            );
//        }
//    }
//}
//
//
