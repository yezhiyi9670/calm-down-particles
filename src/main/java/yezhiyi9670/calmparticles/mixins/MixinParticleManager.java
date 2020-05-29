package yezhiyi9670.calmparticles.mixins;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.particle.ItemPickupParticle;

import java.util.Map;
import java.util.Queue;

@Mixin(ParticleManager.class)
abstract public class MixinParticleManager {
    @Shadow @Final
    private Map<IParticleRenderType, Queue<Particle>> byType = Maps.newIdentityHashMap();

    @Shadow @Final
    private TextureManager renderer;

    @Overwrite
    public void renderParticles(MatrixStack matrixStackIn, IRenderTypeBuffer.Impl bufferIn, LightTexture lightTextureIn, ActiveRenderInfo activeRenderInfoIn, float partialTicks) {
        lightTextureIn.enableLightmap();
        Runnable enable = () -> {
            RenderSystem.enableAlphaTest();
            RenderSystem.defaultAlphaFunc();
            RenderSystem.enableDepthTest();
            RenderSystem.enableFog();
        };
        RenderSystem.pushMatrix();
        RenderSystem.multMatrix(matrixStackIn.getLast().getMatrix());

        for(IParticleRenderType iparticlerendertype : this.byType.keySet()) { // Forge: allow custom IParticleRenderType's
            if (iparticlerendertype == IParticleRenderType.NO_RENDER) continue;
            enable.run(); //Forge: MC-168672 Make sure all render types have the correct GL state.
            lightTextureIn.enableLightmap(); //Forge is right, but made a mistake.
            Iterable<Particle> iterable = this.byType.get(iparticlerendertype);
            if (iterable != null) {
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                iparticlerendertype.beginRender(bufferbuilder, this.renderer);

                for(Particle particle : iterable) {
                    try {
                        particle.renderParticle(bufferbuilder, activeRenderInfoIn, partialTicks);
                    } catch (Throwable throwable) {
                        CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Particle");
                        CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being rendered");
                        crashreportcategory.addDetail("Particle", particle::toString);
                        crashreportcategory.addDetail("Particle Type", iparticlerendertype::toString);
                        throw new ReportedException(crashreport);
                    }
                }

                iparticlerendertype.finishRender(tessellator);
            }
        }

        RenderSystem.popMatrix();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.defaultAlphaFunc();
        lightTextureIn.disableLightmap();
        RenderSystem.disableFog();
    }
}
