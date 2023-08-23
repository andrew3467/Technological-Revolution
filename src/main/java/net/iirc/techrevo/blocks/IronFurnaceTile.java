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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
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

import java.util.Optional;

import static net.iirc.techrevo.setup.Registration.IRON_FURNACE_TILE;

public class IronFurnaceTile extends BlockEntity {

    private int progress;
    private static final int MAX_PROGRESS = 10;
    private static final int INPUT_SLOTS = 1;
    private static final int OUTPUT_SLOTS = 1;
    private static final int SLOT_COUNT = INPUT_SLOTS + OUTPUT_SLOTS;

    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT);


    private final LazyOptional<IItemHandlerModifiable> optional = LazyOptional.of(() -> this.itemHandler);

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

        var recipes = level.getRecipeManager().getRecipes();
        for(var i : recipes){
            System.out.println(i.getType());
        }

        if(hasRecipe(pEntity)){
            pEntity.progress++;
            setChanged(level, pos, state);

            if(pEntity.progress > MAX_PROGRESS){
                craftItem(pEntity);
            }
        }else{
            pEntity.progress = 0;
            setChanged(level, pos, state);
        }
    }

    private static void craftItem(IronFurnaceTile pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<IronFurnaceRecipe> recipe = level.getRecipeManager().getRecipeFor(IronFurnaceRecipe.Type.INSTANCE, inventory, level);

        if(hasRecipe(pEntity)) {
            pEntity.itemHandler.extractItem(0, 1, false);
            pEntity.itemHandler.setStackInSlot(SLOT_COUNT - 1, new ItemStack(recipe.get().getResultItem().getItem(),
                    pEntity.itemHandler.getStackInSlot(SLOT_COUNT - 1).getCount() + recipe.get().getResultItem().getCount()));

            pEntity.progress = 0;
        }
    }

    private static boolean hasRecipe(IronFurnaceTile pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<IronFurnaceRecipe> recipe = level.getRecipeManager().getRecipeFor(IronFurnaceRecipe.Type.INSTANCE, inventory, level);


        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack resultItem) {
        return inventory.getItem(SLOT_COUNT - 1).getItem() == resultItem.getItem() || inventory.getItem(SLOT_COUNT - 1).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(SLOT_COUNT - 1).getMaxStackSize() > inventory.getItem(SLOT_COUNT - 1).getCount();
    }

    public void drops(){
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.progress = nbt.getInt("iron_furnace.progress");
        this.itemHandler.deserializeNBT(nbt.getCompound("iron_furnace.inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("iron_furnace.progress", this.progress);
        nbt.put("iron_furnace.inventory", this.itemHandler.serializeNBT());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ForgeCapabilities.ITEM_HANDLER ? this.optional.cast() : super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        this.optional.invalidate();
    }

    public ItemStackHandler getInputHandler() {
        return itemHandler;
    }

    public ContainerData getContainerData() {
        return this.data;
    }
}
