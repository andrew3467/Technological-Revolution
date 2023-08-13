package net.iirc.techrevo.screen;

import net.iirc.techrevo.blocks.IronFurnaceTile;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.Nullable;

import static net.iirc.techrevo.setup.Registration.IRON_FURNACE_MENU;
import static net.minecraft.world.inventory.AbstractFurnaceMenu.SLOT_COUNT;

public class IronFurnaceMenu extends AbstractContainerMenu {

    public final IronFurnaceTile blockEntity;
    public final Level level;
    public final ContainerData data;

    public IronFurnaceMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(SLOT_COUNT));
    }

    public IronFurnaceMenu(int id, Inventory inv, BlockEntity entity, ContainerData data){
        super(IRON_FURNACE_MENU.get(), id);
        checkContainerSize(inv, SLOT_COUNT);
        this.blockEntity = (IronFurnaceTile)entity;
        this.level = inv.player.level;
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            //Input Slots

            //Output Slots
        });

    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }
    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}
