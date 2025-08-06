package net.oliver.forgemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.oliver.forgemod.ForgeMod;
import net.oliver.forgemod.entity.custom.SnailEntity;

public class SnailModel<T extends SnailEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ForgeMod.MOD_ID, "dirtsnail"), "main");
    private final ModelPart FullBody;
    private final ModelPart Middle;

    private final ModelPart Tail;
    private final ModelPart FullHead;
    private final ModelPart Head;
    private final ModelPart Antenni;
    private final ModelPart Antenna2;
    private final ModelPart Antenna1;
    private final ModelPart Shell;

    public SnailModel(ModelPart root) {
        this.FullBody = root.getChild("FullBody");
        this.Middle = this.FullBody.getChild("Middle");
        this.Tail = this.FullBody.getChild("Tail");
        this.FullHead = this.FullBody.getChild("FullHead");
        this.Head = this.FullHead.getChild("Head");
        this.Antenni = this.FullHead.getChild("Antenni");
        this.Antenna2 = this.Antenni.getChild("Antenna2");
        this.Antenna1 = this.Antenni.getChild("Antenna1");
        this.Shell = this.FullBody.getChild("Shell");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition FullBody = partdefinition.addOrReplaceChild("FullBody", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 1.0F));

        PartDefinition Middle = FullBody.addOrReplaceChild("Middle", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -1.0F));

        PartDefinition Tail = FullBody.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(6, 6).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -1.0F));

        PartDefinition FullHead = FullBody.addOrReplaceChild("FullHead", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, -1.0F));

        PartDefinition Head = FullHead.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(16, 11).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Antenni = FullHead.addOrReplaceChild("Antenni", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -4.0F));

        PartDefinition Antenna2 = Antenni.addOrReplaceChild("Antenna2", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 4.0F));

        PartDefinition cube_r1 = Antenna2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(15, 15).addBox(0.5F, -2.0F, -0.9F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -3.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition Antenna1 = Antenni.addOrReplaceChild("Antenna1", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 4.0F));

        PartDefinition cube_r2 = Antenna1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(15, 17).addBox(0.6F, -2.0F, -0.9F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -2.0F, -3.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition Shell = FullBody.addOrReplaceChild("Shell", CubeListBuilder.create().texOffs(0, 11).addBox(-1.5F, -3.0F, -2.5F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(SnailEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        
        // Only apply head rotation if not curled
        if (!entity.isPanicked()) {
            this.applyHeadRotation(netHeadYaw, headPitch);
        }

        // Only play walking animation if not curled
        if (!entity.isPanicked()) {
            this.animateWalk(SnailAnimations.CRAWLING, limbSwing, limbSwingAmount, 2f, 2.5f);
        }
        
        // Handle curl animation
        this.animate(entity.curlAnimationState, SnailAnimations.CURL, ageInTicks, 1f);
        
        // Handle peek animation
        this.animate(entity.peekAnimationState, SnailAnimations.PEEK, ageInTicks, 1f);
        
        // Start uncurl animation when peek finishes (peek animation is 1 second = 20 ticks)
        if (entity.peekAnimationState.isStarted() && entity.peekAnimationState.getAccumulatedTime() >= 20f) {
            entity.peekAnimationState.stop();
            entity.uncurlAnimationState.start(entity.tickCount);
        }
        
        // Handle uncurl animation
        this.animate(entity.uncurlAnimationState, SnailAnimations.UNCURL, ageInTicks, 1f);
        
        // Only play idle animation if not curled and no other animations are playing
        if (!entity.isPanicked() && !entity.curlAnimationState.isStarted() && 
            !entity.peekAnimationState.isStarted() && !entity.uncurlAnimationState.isStarted()) {
            this.animate(entity.idleAnimationState, SnailAnimations.IDLE, ageInTicks, 1f);
        }
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.FullHead.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.FullHead.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        FullBody.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return FullBody;
    }
}