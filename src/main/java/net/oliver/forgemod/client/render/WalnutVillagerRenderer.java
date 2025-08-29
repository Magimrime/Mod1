package net.oliver.forgemod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.VillagerProfessionLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import net.oliver.forgemod.ForgeMod;
import net.oliver.forgemod.client.render.layer.ItemInVillagerHandLayer;
import net.oliver.forgemod.client.render.layer.VillagerOverlayLayer;
import net.oliver.forgemod.client.render.model.WalnutVillagerModel;

public class WalnutVillagerRenderer extends MobRenderer<Villager, WalnutVillagerModel<Villager>> {
    private final ResourceLocation textureLocation;

    public WalnutVillagerRenderer(EntityRendererProvider.Context context, String texture) {
        super(context, new WalnutVillagerModel<>(context.bakeLayer(WalnutVillagerModel.MAIN_LAYER_LOCATION)), 0.5F);
        this.textureLocation = ResourceLocation.fromNamespaceAndPath(ForgeMod.MOD_ID, "textures/entity/villager" + texture + ".png");
        addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        addLayer(new VillagerProfessionLayer<>(this, context.getResourceManager(), "villager"));
        addLayer(new ItemInVillagerHandLayer<>(this, context.getItemInHandRenderer()));
        addLayer(new VillagerOverlayLayer<>(this, new WalnutVillagerModel<>(context.bakeLayer(WalnutVillagerModel.OVERLAY_LAYER_LOCATION))));
    }

    @Override
    public ResourceLocation getTextureLocation(Villager villager) {
        return textureLocation;
    }

    @Override
    protected void scale(Villager villager, PoseStack poseStack, float partialTicks) {
        var defaultScale = 0.9375F;
        shadowRadius = 0.5F;

        if (villager.isBaby()) {
            defaultScale *= 0.5F;
            shadowRadius *= 0.5F;
        }

        poseStack.scale(defaultScale, defaultScale, defaultScale);
    }
}