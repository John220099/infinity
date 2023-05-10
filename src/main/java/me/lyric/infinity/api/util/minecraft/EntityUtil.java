package me.lyric.infinity.api.util.minecraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityUtil implements IGlobals {
    public static boolean isLiving(Entity entity) {
        return entity instanceof EntityLivingBase;
    }
    public static boolean isInLiquid() {
        return EntityUtil.mc.player.isInWater() || EntityUtil.mc.player.isInLava();
    }
    public static int getPing(EntityPlayer p) {
        int ping = 0;
        try {
            ping = mc.getConnection().getPlayerInfo(p.getUniqueID()).getResponseTime();
        } catch (NullPointerException np) {}
        return ping;
    }
    public static float getHealth(Entity entity) {
        if (EntityUtil.isLiving(entity)) {
            EntityLivingBase livingBase = (EntityLivingBase) entity;
            return livingBase.getHealth() + livingBase.getAbsorptionAmount();
        }
        return 0.0f;
    }
}