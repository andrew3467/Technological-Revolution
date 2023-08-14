package net.iirc.techrevo.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static net.iirc.techrevo.setup.Registration.IRON_FURNACE;
import static net.iirc.techrevo.setup.Registration.IRON_FURNACE_TILE;

public class IronFurnaceTile extends BlockEntity {

    private int progress;
    private static final int MAX_PROGRESS = 100;

    public IronFurnaceTile(BlockPos pPos, BlockState pBlockState) {
        super(IRON_FURNACE_TILE.get(), pPos, pBlockState);
    }


    public void tick(){
        progress++;
        if(progress > MAX_PROGRESS){
            progress = 0;
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.progress = nbt.getInt("progress");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("progress", progress);
    }
}
