package net.demondev.frosttech.networking.packet;

import net.demondev.frosttech.block.entity.FrozenOreCrusherBlockEntity;
import net.demondev.frosttech.block.entity.OreFreezerBlockEntity;
import net.demondev.frosttech.screen.FrozenOreCrusherMenu;
import net.demondev.frosttech.screen.OreFreezerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EnergySyncS2CPacket {
    private final int energy;
    private final BlockPos pos;

    // Constructor to initialize the energy and position
    public EnergySyncS2CPacket(int energy, BlockPos pos) {
        this.energy = energy;
        this.pos = pos;
    }

    // Constructor for reading data from the network buffer
    public EnergySyncS2CPacket(FriendlyByteBuf buf) {
        this.energy = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    // Writing data to the network buffer
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(energy);
        buf.writeBlockPos(pos);
    }

    // Handling the packet when received on the client
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Check if Minecraft instance and level are valid
            if (Minecraft.getInstance().level != null) {
                // Handle OreFreezerBlockEntity
                if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof OreFreezerBlockEntity blockEntity) {
                    blockEntity.setEnergyLevel(energy);

                    if (Minecraft.getInstance().player != null &&
                            Minecraft.getInstance().player.containerMenu instanceof OreFreezerMenu menu &&
                            menu.getBlockEntity().getBlockPos().equals(pos)) {
                        menu.getBlockEntity().setEnergyLevel(energy);
                    }
                }

                // Handle FrozenOreCrusherBlockEntity
                if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof FrozenOreCrusherBlockEntity blockEntity) {
                    blockEntity.setEnergyLevel(energy);

                    if (Minecraft.getInstance().player != null &&
                            Minecraft.getInstance().player.containerMenu instanceof FrozenOreCrusherMenu menu &&
                            menu.getBlockEntity().getBlockPos().equals(pos)) {
                        menu.getBlockEntity().setEnergyLevel(energy);
                    }
                }
            }
        });
        // Mark the packet as handled
        context.setPacketHandled(true);
        return true;
    }
}
