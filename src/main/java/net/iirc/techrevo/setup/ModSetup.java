package net.iirc.techrevo.setup;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {
    public static final String TAB_NAME = "techrevo";


    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(TAB_NAME) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.TEST_BLOCK.get());
        }
    };

     public static void init(FMLCommonSetupEvent event){

     }
}
