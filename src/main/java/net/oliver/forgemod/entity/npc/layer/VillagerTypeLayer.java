package net.oliver.forgemod.entity.npc.layer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.oliver.forgemod.ForgeMod;
import net.oliver.forgemod.entity.client.ModVillagerModel;

public class VillagerTypeLayer<T extends AbstractVillager> extends RenderLayer<T, ModVillagerModel<T>> {
    private static final ResourceLocation WALNUT_LAYER = ResourceLocation.fromNamespaceAndPath(ForgeMod.MOD_ID, "textures/entity/villager/type/walnut.png");
    private final ModVillagerModel<T> walnutLayer;

    public VillagerTypeLayer(RenderLayerParent<T, ModVillagerModel<T>> parent, ModVillagerModel<T> hairModel) {
        super(parent);
        this.walnutLayer = hairModel;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float entityAge, float netHeadYaw, float headPitch) {
        var renderType = walnutLayer.renderType(WALNUT_LAYER);
        getParentModel().copyPropertiesTo(walnutLayer);
        walnutLayer.renderToBuffer(poseStack, bufferSource.getBuffer(renderType), packedLight, OverlayTexture.NO_OVERLAY);
    }
}
