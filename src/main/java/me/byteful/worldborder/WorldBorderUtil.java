package me.byteful.worldborder;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.event.impl.PacketPlaySendEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.utils.player.PlayerUtils;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class WorldBorderUtil {
  // Should world border packets be cancelled after a player's world border is manually set?
  private static final boolean LOCK_PACKETS = true;
  private static final Map<UUID, WorldBorderData> DATA = new HashMap<>();

  static {
    if (LOCK_PACKETS) {
      PacketEvents.get()
          .registerListener(
              new PacketListenerAbstract(PacketListenerPriority.HIGHEST) {
                final Byte[] packetIds = new Byte[]{PacketType.Play.Server.SET_BORDER_CENTER, PacketType.Play.Server.SET_BORDER_SIZE, PacketType.Play.Server.SET_BORDER_LERP_SIZE, PacketType.Play.Server.SET_BORDER_WARNING_DELAY, PacketType.Play.Server.SET_BORDER_WARNING_DISTANCE, PacketType.Play.Server.WORLD_BORDER, PacketType.Play.Server.INITIALIZE_BORDER};

                @Override
                public void onPacketPlaySend(PacketPlaySendEvent event) {
                  if (DATA.containsKey(event.getPlayer().getUniqueId()) && Arrays.stream(packetIds).noneMatch(id -> event.getPacketId() == id) && LOCK_PACKETS) {
                    return;
                  }

                  event.setCancelled(true);
                }
              });
    }
  }

  public static void setWorldBorder(Player player, WorldBorderData data) {
    DATA.put(player.getUniqueId(), data);
  }

  private static void send1_17() {

  }

  private static void send1_8() {
    final PlayerUtils u = PacketEvents.get().getPlayerUtils();

  }

  private static final class WorldBorderData {
    private double centerX = 0, centerZ = 0;
    private double oldDiameter = 0, newDiameter = 0;
    private long speed = 0;
    private double size = 60000000;
    private int warningTime = 0, warningBlocks = 0;

    public WorldBorderData(double centerX, double centerZ, double oldDiameter, double newDiameter, long speed, double size, int warningTime, int warningBlocks) {
      this.centerX = centerX;
      this.centerZ = centerZ;
      this.oldDiameter = oldDiameter;
      this.newDiameter = newDiameter;
      this.speed = speed;
      this.size = size;
      this.warningTime = warningTime;
      this.warningBlocks = warningBlocks;
    }

    public double getCenterX() {
      return centerX;
    }

    public void setCenterX(double centerX) {
      this.centerX = centerX;
    }

    public double getCenterZ() {
      return centerZ;
    }

    public void setCenterZ(double centerZ) {
      this.centerZ = centerZ;
    }

    public double getOldDiameter() {
      return oldDiameter;
    }

    public void setOldDiameter(double oldDiameter) {
      this.oldDiameter = oldDiameter;
    }

    public double getNewDiameter() {
      return newDiameter;
    }

    public void setNewDiameter(double newDiameter) {
      this.newDiameter = newDiameter;
    }

    public long getSpeed() {
      return speed;
    }

    public void setSpeed(long speed) {
      this.speed = speed;
    }

    public double getSize() {
      return size;
    }

    public void setSize(double size) {
      this.size = size;
    }

    public int getWarningTime() {
      return warningTime;
    }

    public void setWarningTime(int warningTime) {
      this.warningTime = warningTime;
    }

    public int getWarningBlocks() {
      return warningBlocks;
    }

    public void setWarningBlocks(int warningBlocks) {
      this.warningBlocks = warningBlocks;
    }
  }
}
