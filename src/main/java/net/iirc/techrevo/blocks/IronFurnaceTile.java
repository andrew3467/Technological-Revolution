package net.iirc.techrevo.blocks;

import net.iirc.techrevo.TechnologicalRevolution;
import net.iirc.techrevo.recipes.IronFurnaceRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import oshi.jna.platform.windows.NtDll;

import java.util.Optional;

import static net.iirc.techrevo.setup.Registration.IRON_FURNACE_TILE;
import static net.minecraft.world.item.Items.IRON_INGOT;
import static net.minecraft.world.item.Items.RAW_IRON;

public class IronFurnaceTile extends BlockEntity {

    private int progress;
    private static final int MAX_PROGRESS = 100;
    private static final int SLOT_COUNT = 2;

    private IronFurnaceRecipe currentRecipe;

    private final ItemStackHandler inventory = new ItemStackHandler(SLOT_COUNT);
    private final LazyOptional<IItemHandlerModifiable> optional = LazyOptional.of(() -> this.inventory);

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex){
                case 0 -> IronFurnaceTile.this.progress;
                case 1 -> IronFurnaceTile.this.MAX_PROGRESS;
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex){
                case 0 -> IronFurnaceTile.this.progress = pValue;
                default -> {}
            };
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public static final Component TITLE = Component.translatable("container." + TechnologicalRevolution.MODID + ".iron_furnace");


    public IronFurnaceTile(BlockPos pPos, BlockState pBlockState) {
        super(IRON_FURNACE_TILE.get(), pPos, pBlockState);
    }


    public static void tick(Level level, BlockPos pos, BlockState state, IronFurnaceTile pEntity){
        if(!level.isClientSide()){
            return;
        }


        if(hasRecipe(pEntity)){
            pEntity.progress++;
            setChanged(level, pos, state);

            if(pEntity.progress > MAX_PROGRESS){
                System.out.println("Craft Item");
                craftItem(pEntity);

            }
        }else{
            System.out.println("Progress Reset");
            pEntity.progress = 0;
            setChanged(level, pos, state);
        }
    }

    private static void craftItem(IronFurnaceTile pEntity) {
        SimpleContainer inventory = new SimpleContainer(pEntity.inventory.getSlots());
        for (int i = 0; i < pEntity.inventory.getSlots(); i++) {
            inventory.setItem(i, pEntity.inventory.getStackInSlot(i));
        }

        Optional<IronFurnaceRecipe> recipe = pEntity.level.getRecipeManager()
                .getRecipeFor(IronFurnaceRecipe.Type.INSTANCE, inventory, pEntity.level);

        if(hasRecipe(pEntity)){
            removeInputItem(pEntity, 1);
            insertOutputItem(pEntity, recipe.get().getResultItem().getItem(), 2);

            pEntity.progress = 0;
        }
    }

    private static void insertOutputItem(IronFurnaceTile pEntity, Item item, int count) {
        //Can insert both items
        if (pEntity.inventory.getStackInSlot(1).getCount() < item.getMaxStackSize() - 1) {
            pEntity.inventory.setStackInSlot(1, new ItemStack(item,
                    pEntity.inventory.getStackInSlot(1).getCount() + count));
        }
    }

    private static void removeInputItem(IronFurnaceTile pEntity, int count) {
        if (pEntity.inventory.getStackInSlot(0).getCount() > 0) {
            pEntity.inventory.extractItem(0, count, false);
        }
    }

    private static boolean hasRecipe(IronFurnaceTile pEntity) {
        SimpleContainer inv = new SimpleContainer(pEntity.inventory.getSlots());
        for (int i = 0; i < pEntity.inventory.getSlots(); i++) {
            inv.setItem(i, pEntity.inventory.getStackInSlot(i));
        }


        Optional<IronFurnaceRecipe> recipe = pEntity.level.getRecipeManager().getRecipeFor(IronFurnaceRecipe.Type.INSTANCE, inv, pEntity.level);
        System.out.println(recipe);

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inv) &&
                canInsertItemIntoOutputSlot(inv, recipe.get().getResultItem());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack resultItem) {
        return inventory.getItem(1).getItem() == resultItem.getItem() || inventory.getItem(1).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(5).getMaxStackSize() - 2 > inventory.getItem(5).getCount();
    }

    public void drops(){
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); i++){
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.progress = nbt.getInt("progress");
        this.inventory.deserializeNBT(nbt.getCompound("inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("progress", this.progress);
        nbt.put("inventory", this.inventory.serializeNBT());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ForgeCapabilities.ITEM_HANDLER ? this.optional.cast() : super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        this.optional.invalidate();
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public ContainerData getContainerData() {
        return this.data;
    }

    public IronFurnaceRecipe getCurrentRecipe() {
        return currentRecipe;
    }
}
