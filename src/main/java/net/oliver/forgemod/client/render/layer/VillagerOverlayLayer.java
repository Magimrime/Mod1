package net.oliver.forgemod.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.oliver.forgemod.ForgeMod;
import net.oliver.forgemod.client.render.model.WalnutVillagerModel;

public class VillagerOverlayLayer<T extends AbstractVillager> extends RenderLayer<T, WalnutVillagerModel<T>> {
    private static final ResourceLocation OVERLAY_TEXTURE = ResourceLocation.fromNamespaceAndPath(ForgeMod.MOD_ID, "textures/entity/villager/type/walnut_biome.png");
    private final WalnutVillagerModel<T> overlayModel;

    public VillagerOverlayLayer(RenderLayerParent<T, WalnutVillagerModel<T>> parent, WalnutVillagerModel<T> hairModel) {
        super(parent);
        this.overlayModel = hairModel;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float entityAge, float netHeadYaw, float headPitch) {
        var renderType = overlayModel.renderType(OVERLAY_TEXTURE);
        getParentModel().copyPropertiesTo(overlayModel);
        overlayModel.renderToBuffer(poseStack, bufferSource.getBuffer(renderType), packedLight, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
    }
}