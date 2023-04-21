package me.lyric.infinity.manager.client;

import event.bus.EventState;
import me.lyric.infinity.api.event.events.network.PacketEvent;
import me.lyric.infinity.api.event.events.player.UpdateWalkingPlayerEvent;
import me.lyric.infinity.api.event.events.render.RenderLivingEntityEvent;
import me.lyric.infinity.api.util.minecraft.IGlobals;
import me.lyric.infinity.api.util.minecraft.rotation.Rotation;
import event.bus.EventListener;
import me.lyric.infinity.mixin.mixins.accessors.IEntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


public class RotationManager implements IGlobals {
    private static float yaw;
    private static float pitch;
    private float headPitch = -1;
    private Rotation serverRotation = new Rotation(0, 0, Rotation.Rotate.NONE);

    public void init() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void updateRotations() {
        if (mc.player == null)
        {
            return;
        }
        yaw = mc.player.rotationYaw;
        pitch = mc.player.rotationPitch;
    }
    public static void resetRotations() {
        if (mc.player == null)
        {
            return;
        }

        mc.player.rotationYaw = yaw;
        mc.player.rotationYawHead = yaw;
        mc.player.rotationPitch = pitch;
    }
    public static void lookAtVec3dPacket(Vec3d vec, boolean normalize, boolean update) {
        float[] angle = getAngle(vec);
        mc.player.connection.sendPacket(new CPacketPlayer.Rotation(angle[0], normalize ? (float) MathHelper.normalizeAngle((int) angle[1], 360) : angle[1], mc.player.onGround));

        if (update) {
            ((IEntityPlayerSP) mc.player).setLastReportedYaw(angle[0]);
            ((IEntityPlayerSP) mc.player).setLastReportedPitch(angle[1]);
        }
    }
    public static float[] getAngle(Vec3d vec) {
        Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + (double) mc.player.getEyeHeight(), mc.player.posZ);
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{ mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch)};
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player != null && mc.world != null) {
            headPitch = -1;
        }
    }
    @EventListener
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
        if (mc.player == null) return;

        if (event.getEventState() == EventState.PRE) {
            RotationManager.updateRotations();
        }
        if (event.getEventState() == EventState.POST) {
            RotationManager.resetRotations();
        }
    }
    public static void resetRotationsPacket() {
        if (mc.player == null)
        {
            return;
        }
        float[] angle = new float[]{mc.player.rotationYaw, mc.player.rotationPitch};
        mc.player.connection.sendPacket(new CPacketPlayer.Rotation(angle[0], angle[1], mc.player.onGround));
    }

    @EventListener
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer)
            serverRotation = new Rotation(((CPacketPlayer) event.getPacket()).getYaw(0), ((CPacketPlayer) event.getPacket()).getPitch(0), Rotation.Rotate.NONE);
    }

    @SubscribeEvent
    public void onRenderLivingEntity(RenderLivingEntityEvent event) {
        if (event.getEntityLivingBase().equals(mc.player)) {
            event.setCanceled(true);
            event.getModelBase().render(event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), headPitch == -1 ? mc.player.rotationPitch : headPitch, event.getScaleFactor());
        }
    }

    public void setHeadPitch(float in) {
        headPitch = in;
    }

    public Rotation getServerRotation() {
        return this.serverRotation;
    }
}