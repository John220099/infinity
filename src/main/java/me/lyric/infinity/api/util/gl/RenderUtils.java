package me.lyric.infinity.api.util.gl;

import me.lyric.infinity.api.util.metadata.FileUtils;
import me.lyric.infinity.api.util.minecraft.IGlobals;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;

import static net.minecraft.client.renderer.GlStateManager.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author lyric :)))
 */

public class RenderUtils implements IGlobals {
    private final static Tessellator tessellator = Tessellator.getInstance();
    private final static BufferBuilder bufferBuilder = tessellator.getBuffer();

    public static void color(Color color)
    {
        glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    public static void prepareScissor(int x, int y, int width, int height) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(524288);
        scissor(x, y, x + width, y + height);
        GL11.glEnable(3089);
    }

    public static void releaseScissor() {
        GL11.glDisable(3089);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void scissor(int x, int y, int x2, int y2) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        GL11.glScissor(x * scaledResolution.getScaleFactor(), (scaledResolution.getScaledHeight() - y2) * scaledResolution.getScaleFactor(), (x2 - x) * scaledResolution.getScaleFactor(), (y2 - y) * scaledResolution.getScaleFactor());
    }

    public static void drawBBSlab(AxisAlignedBB bb, float height, Color color) {
        final int r = color.getRed();
        final int g = color.getGreen();
        final int b = color.getBlue();
        final int a = color.getAlpha();
        double minX = bb.minX;
        double minY = bb.minY;
        double minZ = bb.minZ;
        double maxX = bb.maxX;
        double maxY = bb.maxY + (double) height;
        double maxZ = bb.maxZ;
        pushMatrix();

        disableTexture2D();
        enableBlend();
        disableAlpha();
        glDisable(GL_DEPTH_TEST);
        tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
        disableCull();
        shadeModel(GL_SMOOTH);

        bufferBuilder.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        vertex(minX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, minZ).color(0, 0, 0, 0).endVertex();
        vertex(minX, maxY, maxZ).color(0, 0, 0, 0).endVertex();
        vertex(maxX, maxY, maxZ).color(0, 0, 0, 0).endVertex();
        vertex(maxX, maxY, minZ).color(0, 0, 0, 0).endVertex();
        vertex(minX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, minZ).color(0, 0, 0, 0).endVertex();
        vertex(maxX, maxY, minZ).color(0, 0, 0, 0).endVertex();
        vertex(maxX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, minZ).color(0, 0, 0, 0).endVertex();
        vertex(maxX, maxY, maxZ).color(0, 0, 0, 0).endVertex();
        vertex(maxX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, maxZ).color(0, 0, 0, 0).endVertex();
        vertex(minX, maxY, maxZ).color(0, 0, 0, 0).endVertex();
        vertex(minX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, maxZ).color(0, 0, 0, 0).endVertex();
        vertex(minX, maxY, minZ).color(0, 0, 0, 0).endVertex();
        tessellator.draw();

        glEnable(GL_DEPTH_TEST);
        shadeModel(GL_FLAT);
        disableBlend();
        enableCull();
        enableAlpha();
        enableTexture2D();
        popMatrix();
    }

    public static void drawBBOutline(AxisAlignedBB bb, float width, Color start, Color end) {
        disableTexture2D();
        enableBlend();
        disableAlpha();
        disableDepth();
        tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
        shadeModel(GL_SMOOTH);
        width(width);

        final int r = start.getRed();
        final int b = start.getBlue();
        final int g = start.getGreen();
        final int a = start.getAlpha();

        final int r0 = end.getRed();
        final int b0 = end.getBlue();
        final int g0 = end.getGreen();
        final int a0 = end.getAlpha();

        bufferBuilder.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        vertex(bb.minX, bb.minY, bb.minZ, r, g, b, a);
        vertex(bb.minX, bb.minY, bb.maxZ, r, g, b, a);
        vertex(bb.maxX, bb.minY, bb.maxZ, r, g, b, a);
        vertex(bb.maxX, bb.minY, bb.minZ, r, g, b, a);
        vertex(bb.minX, bb.minY, bb.minZ, r, g, b, a);
        vertex(bb.minX, bb.maxY, bb.minZ, r0, g0, b0, a0);
        vertex(bb.minX, bb.maxY, bb.maxZ, r0, g0, b0, a0);
        vertex(bb.minX, bb.minY, bb.maxZ, r, g, b, a);
        vertex(bb.maxX, bb.minY, bb.maxZ, r, g, b, a);
        vertex(bb.maxX, bb.maxY, bb.maxZ, r0, g0, b0, a0);
        vertex(bb.minX, bb.maxY, bb.maxZ, r0, g0, b0, a0);
        vertex(bb.maxX, bb.maxY, bb.maxZ, r0, g0, b0, a0);
        vertex(bb.maxX, bb.maxY, bb.minZ, r0, g0, b0, a0);
        vertex(bb.maxX, bb.minY, bb.minZ, r, g, b, a);
        vertex(bb.maxX, bb.maxY, bb.minZ, r0, g0, b0, a0);
        vertex(bb.minX, bb.maxY, bb.minZ, r0, g0, b0, a0);
        tessellator.draw();

        enableDepth();
        shadeModel(GL_FLAT);
        disableBlend();
        enableAlpha();
        enableTexture2D();
    }

    public static void drawBBFill(AxisAlignedBB bb, Color color, Color bottom) {
        pushMatrix();
        disableTexture2D();
        enableBlend();
        disableAlpha();
        disableCull();
        disableDepth();
        tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
        shadeModel(GL_SMOOTH);
        width(1);

        final int r = color.getRed();
        final int b = color.getBlue();
        final int g = color.getGreen();
        final int a = color.getAlpha();
        final int r0 = bottom.getRed();
        final int b0 = bottom.getBlue();
        final int g0 = bottom.getGreen();
        final int a0 = bottom.getAlpha();

        bufferBuilder.begin(GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        double minX = bb.minX;
        double minY = bb.minY;
        double minZ = bb.minZ;
        double maxX = bb.maxX;
        double maxY = bb.maxY;
        double maxZ = bb.maxZ;
        vertex(minX, minY, minZ).color(r0, g0, b0, a0).endVertex();
        vertex(minX, minY, minZ).color(r0, g0, b0, a0).endVertex();
        vertex(minX, minY, minZ).color(r0, g0, b0, a0).endVertex();
        vertex(minX, minY, maxZ).color(r0, g0, b0, a0).endVertex();
        vertex(minX, maxY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, maxZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, maxZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, maxZ).color(r0, g0, b0, a0).endVertex();
        vertex(maxX, maxY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, maxZ).color(r0, g0, b0, a0).endVertex();
        vertex(maxX, minY, maxZ).color(r0, g0, b0, a0).endVertex();
        vertex(maxX, minY, minZ).color(r0, g0, b0, a0).endVertex();
        vertex(maxX, maxY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, minZ).color(r0, g0, b0, a0).endVertex();
        vertex(minX, maxY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, minZ).color(r0, g0, b0, a0).endVertex();
        vertex(minX, minY, minZ).color(r0, g0, b0, a0).endVertex();
        vertex(maxX, minY, minZ).color(r0, g0, b0, a0).endVertex();
        vertex(minX, minY, maxZ).color(r0, g0, b0, a0).endVertex();
        vertex(maxX, minY, maxZ).color(r0, g0, b0, a0).endVertex();
        vertex(maxX, minY, maxZ).color(r0, g0, b0, a0).endVertex();
        vertex(minX, maxY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, maxZ).color(r, g, b, a).endVertex();
        tessellator.draw();

        enableDepth();
        shadeModel(GL_FLAT);
        disableBlend();
        enableCull();
        enableAlpha();
        enableTexture2D();
        popMatrix();
    }

    public static void drawBBClaw(AxisAlignedBB bb, float width, float height, Color color) {
        pushMatrix();
        start();
        width(width);

        double minX = bb.minX;
        double minY = bb.minY;
        double minZ = bb.minZ;
        double maxX = bb.maxX;
        double maxY = bb.maxY;
        double maxZ = bb.maxZ;

        bufferBuilder.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        vertex(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(minX, minY, minZ + height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(minX, minY, maxZ - height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(maxX, minY, minZ + height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(maxX, minY, maxZ - height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(minX + height, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(minX + height, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(maxX - height, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(maxX - height, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(minX, minY + height, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(minX, minY + height, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(maxX, minY + height, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(maxX, minY + height, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(minX, maxY, minZ + height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(minX, maxY, maxZ - height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(maxX, maxY, minZ + height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(maxX, maxY, maxZ - height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(minX + height, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(minX + height, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(maxX - height, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(maxX - height, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(minX, maxY - height, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(minX, maxY - height, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(maxX, maxY - height, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
        vertex(maxX, maxY - height, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        tessellator.draw();

        end();
        popMatrix();
    }

    public static void drawRect(float startX, float startY, float endX, float endY, int color) {
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(startX, endY, 0.0D).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(endX, endY, 0.0D).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(endX, startY, 0.0D).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(startX, startY, 0.0D).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawBorderedRect(float x, float y, float x2, float y2, float lineSize, int color, int borderColor) {
        drawRect(x, y, x2, y2, color);
        drawRect(x, y, x + lineSize, y2, borderColor);
        drawRect(x2 - lineSize, y, x2, y2, borderColor);
        drawRect(x, y2 - lineSize, x2, y2, borderColor);
        drawRect(x, y, x2, y + lineSize, borderColor);
    }

    public static void end() {
        glDisable(GL_LINE_SMOOTH);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void start() {
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ZERO, GL_ONE);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
    }

    private static BufferBuilder vertex(double x, double y, double z) {
        return bufferBuilder.pos(x - mc.getRenderManager().viewerPosX, y - mc.getRenderManager().viewerPosY, z - mc.getRenderManager().viewerPosZ);
    }

    private static void vertex(double x, double y, double z, int r, int g, int b, int a) {
        bufferBuilder.pos(x - mc.getRenderManager().viewerPosX, y - mc.getRenderManager().viewerPosY, z - mc.getRenderManager().viewerPosZ).color(r, g, b, a).endVertex();
    }

    private static void width(float width) {
        GlStateManager.glLineWidth(width);
    }
    public static void renderArmorNew() {
            GL11.glColor4f(1.f, 1.f, 1.f, 1.f);
            ScaledResolution res = new ScaledResolution(mc);
            int x = 15;
            RenderHelper.enableGUIStandardItemLighting();
            for (int i = 3; i >= 0; i--) {
                ItemStack stack = mc.player.inventory.armorInventory.get(i);
                if (!stack.isEmpty()) {
                    int y = getArmorY();
                    final float percent = getPercent(stack) / 100.0f;
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(0.625F, 0.625F, 0.625F);
                    GlStateManager.disableDepth();
                    mc.fontRenderer.drawStringWithShadow(((int) (percent * 100.0f)) + "%", (((res.getScaledWidth() >> 1) + x + 1) * 1.6F), (res.getScaledHeight() - y - 3) * 1.6F, ColorUtils.toColor(percent * 120.0f, 100.0f, 50.0f, 1.0f).getRGB());
                    GlStateManager.enableDepth();
                    GlStateManager.scale(1.0f, 1.0f, 1.0f);
                    GlStateManager.popMatrix();
                    GlStateManager.pushMatrix();
                    mc.getRenderItem().renderItemIntoGUI(stack, res.getScaledWidth() / 2 + x, res.getScaledHeight() - y);
                    mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, res.getScaledWidth() / 2 + x, res.getScaledHeight() - y);
                    GlStateManager.popMatrix();
                    x += 18;
                }
            }
            RenderHelper.disableStandardItemLighting();
    }
    public static int getDamage(ItemStack stack)
    {
        return stack.getMaxDamage() - stack.getItemDamage();
    }
    public static float getPercent(ItemStack stack)
    {
        return (getDamage(stack) / (float) stack.getMaxDamage()) * 100.0f;
    }
    public static int getArmorY() {
        int y;
        if (mc.player.isInsideOfMaterial(Material.WATER)
                && mc.player.getAir() > 0
                && !mc.player.capabilities.isCreativeMode) {
            y = 65;
        } else if (mc.player.getRidingEntity() != null
                && !mc.player.capabilities.isCreativeMode) {
            if (mc.player.getRidingEntity()
                    instanceof EntityLivingBase) {
                EntityLivingBase entity =
                        (EntityLivingBase) mc.player.getRidingEntity();
                y = (int) (45
                        + Math.ceil((entity.getMaxHealth()
                        - 1.0F)
                        / 20.0F)
                        * 10);
            } else {
                y = 45;
            }
        } else if (mc.player.capabilities.isCreativeMode) {
            y = mc.player.isRidingHorse() ? 45 : 38;
        } else {
            y = 55;
        }
        return y;
    }

    public static void drawCustomBB(Color color, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        AxisAlignedBB bb1 = new AxisAlignedBB(minX - mc.getRenderManager().viewerPosX, minY - mc.getRenderManager().viewerPosY, minZ - mc.getRenderManager().viewerPosZ, maxX - mc.getRenderManager().viewerPosX, maxY - mc.getRenderManager().viewerPosY, maxZ - mc.getRenderManager().viewerPosZ);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        RenderGlobal.renderFilledBox(bb1, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawBlockOutlineBBWithHeight(AxisAlignedBB bb, Color color, float linewidth, float height) {
        Vec3d interp = interpolateEntity(mc.player, mc.getRenderPartialTicks());
        RenderUtils.drawBlockOutlineWithHeight(bb.grow(0.002f).offset(-interp.x, -interp.y, -interp.z), color, linewidth, height);
    }
    public static void drawBlockOutlineWithHeight(AxisAlignedBB bb, Color color, float linewidth, float height) {
        float red = (float) color.getRed() / 255.0f;
        float green = (float) color.getGreen() / 255.0f;
        float blue = (float) color.getBlue() / 255.0f;
        float alpha = (float) color.getAlpha() / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(linewidth);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY - 1 + height, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY - 1 + height, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY - 1 + height, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY - 1 + height, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY - 1 + height, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY - 1 + height, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY - 1 + height, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY - 1 + height, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    public static void drawBoxWithHeight(AxisAlignedBB bb, Color color, float height) {
        AxisAlignedBB bb1 = new AxisAlignedBB(bb.minX - mc.getRenderManager().viewerPosX, bb.minY - mc.getRenderManager().viewerPosY, bb.minZ - mc.getRenderManager().viewerPosZ, bb.maxX - mc.getRenderManager().viewerPosX, bb.maxY - 1 + height - mc.getRenderManager().viewerPosY, bb.maxZ - mc.getRenderManager().viewerPosZ);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        RenderGlobal.renderFilledBox(bb1, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void rectangle(float x, float y, float width, float height, int color) {
        float alpha = (color >> 24 & 0xFF) / 255.0f;
        float red = (color >> 16 & 0xFF) / 255.0f;
        float green = (color >> 8 & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, height, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(width, height, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(width, y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(x, y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void outline(float x, float y, float width, float height, final Color color, final float lineWidth) {
        if (x < width) {
            final double i = x;
            x = width;
            width = (float) i;
        }
        if (y < height) {
            final double j = y;
            y = height;
            height = (float)j;
        }
        float f3 = (color.getRGB() >> 24 & 0xFF) / 255.0f;
        float f4 = (color.getRGB() >> 16 & 0xFF) / 255.0f;
        float f5 = (color.getRGB() >> 8 & 0xFF) / 255.0f;
        float f6 = (color.getRGB() & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GL11.glPolygonMode(1032, 6913);
        GL11.glLineWidth(lineWidth);
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f4, f5, f6, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(x, height, 0.0).endVertex();
        bufferbuilder.pos(width, height, 0.0).endVertex();
        bufferbuilder.pos(width, y, 0.0).endVertex();
        bufferbuilder.pos(x, y, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glPolygonMode(1032, 6914);
    }
    public static void drawArrow(float x, float y, float size, float widthDiv, float heightDiv, float outlineWidth) {
        final boolean blend = GL11.glIsEnabled(3042);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glLineWidth(outlineWidth);
        GL11.glBegin(2);
        GL11.glColor4f(255.0f, 255.0f, 255.0f, 255.0f);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d((x - size / widthDiv), (y - size));
        GL11.glVertex2d(x, (y - size / heightDiv));
        GL11.glVertex2d((x + size / widthDiv), (y - size));
        GL11.glVertex2d(x, y);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        if (!blend) {
            GL11.glDisable(3042);
        }
        GL11.glDisable(2848);
    }

    public static int loadGlTexture(ResourceLocation resource) {
        try {
            BufferedImage bufferedImage = ImageIO.read(FileUtils.getFile(resource.getPath()));
            return loadGlTexture(bufferedImage);
        } catch (Throwable e) {
            return 0;
        }
    }

    public static int loadGlTexture(BufferedImage bufferedImage) {
        try {
            int textureId = GL11.glGenTextures();

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, bufferedImage.getWidth(), bufferedImage.getHeight(),
                    0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, ImageUtils.readImageToBuffer(bufferedImage));

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

            return textureId;
        } catch (Throwable e) {
            return 0;
        }
    }
    public static void drawRoundedRect(double x, double y, double x2, double y2, final double radius, final int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        float f = (color >> 24 & 0xFF) / 255.0f;
        float f2 = (color >> 16 & 0xFF) / 255.0f;
        float f3 = (color >> 8 & 0xFF) / 255.0f;
        float f4 = (color & 0xFF) / 255.0f;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0;
        y *= 2.0;
        x2 *= 2.0;
        y2 *= 2.0;
        GL11.glDisable(3553);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glEnable(2848);
        GL11.glBegin(9);
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0), y + radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0));
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0), y2 - radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0));
        }
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x2 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y2 - radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x2 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y + radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glPopAttrib();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }




    public static Vec3d interpolateEntity(Entity entity, float time) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) time);
    }
    public static boolean validScreen() {
        return !(mc.currentScreen instanceof GuiContainer) || mc.currentScreen instanceof GuiInventory;
    }

}
