package net.iirc.techrevo.menus;

import net.iirc.techrevo.blocks.IronFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;
import org.jetbrains.annotations.Nullable;

import static net.iirc.techrevo.setup.Registration.IRON_FURNACE;
import static net.iirc.techrevo.setup.Registration.IRON_FURNACE_MENU;

public class IronFurnaceMenu extends AbstractContainerMenu {

    private final IronFurnaceTile blockEntity;
    private final Level level;
    private final ContainerData data;


    private static int SLOT_COUNT = 2;


    public IronFurnaceMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(SLOT_COUNT));
    }

    public IronFurnaceMenu(int id, Inventory playerInv, BlockEntity entity, ContainerData data) {
        super(IRON_FURNACE_MENU.get(), id);
        this.blockEntity = (IronFurnaceTile) entity;
        this.level = entity.getLevel();
        this.data = data;

        //Add slots
        //Add Container inventory before player inventory
        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            //Input
            this.addSlot(new SlotItemHandler(handler, 0, 56, 35));

            //Output
            addSlot(new SlotWithRestriction(handler, 1, 117, 35));
        });

        //Player Inventory
        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);

        this.addDataSlots(this.data);
    }

    public BlockEntity getBlockEntity() {
        return blockEntity;
    }

    public static MenuConstructor getServerMenu(IronFurnaceTile blockEntity, BlockPos pos){
        return (id ,playerInv, player) -> new IronFurnaceMenu(id, playerInv, blockEntity, blockEntity.getContainerData());
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 26; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        System.out.println(data.get(0));
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack currentItem = slot.getItem();
            itemStack = currentItem.copy();
            final int SLOT_COUNT = 2;
            if (pIndex < SLOT_COUNT) {
                if (!this.moveItemStackTo(currentItem, SLOT_COUNT, this.slots.size(), true)) ;
                {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(currentItem, 0, SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }

            if (currentItem.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, IRON_FURNACE.get());
    }

    public ContainerData getData() {
        return data;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }
    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
