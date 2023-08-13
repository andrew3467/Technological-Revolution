package net.iirc.techrevo.setup;

import net.iirc.techrevo.blocks.CopperCable;
import net.iirc.techrevo.world.feature.ModConfiguredFeatures;
import net.iirc.techrevo.world.feature.ModPlacedFeatures;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.iirc.techrevo.TechnologicalRevolution.MODID;

public class Registration {
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(ModSetup.ITEM_GROUP);
    public static final BlockBehaviour.Properties BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(2.0f);


    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);


    public static final RegistryObject<Block> TEST_BLOCK = BLOCKS.register("test_block", () -> new Block(BLOCK_PROPERTIES));
    public static final RegistryObject<Block> TEST_ORE = BLOCKS.register("test_ore", () -> new Block(BLOCK_PROPERTIES));
    public static final RegistryObject<Block> COPPER_CABLE = BLOCKS.register("copper_cable", () -> new CopperCable(BLOCK_PROPERTIES));



    public static RegistryObject<Item> TEST_BLOCK_ITEM = fromBlock(TEST_BLOCK);
    public static RegistryObject<Item> TEST_ORE_ITEM = fromBlock(TEST_ORE);

    public static final RegistryObject<Block> TEST_ORE_BLOCK = BLOCKS.register("test_ore_block", () -> new Block(BLOCK_PROPERTIES));
    public static RegistryObject<Item> KANYE_BLOCK_ITEM = fromBlock(TEST_ORE_BLOCK);
    public static RegistryObject<Item> COPPER_CABLE_ITEM = fromBlock(COPPER_CABLE);


    public static void init(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(bus);
        ITEMS.register(bus);

    }

    private static void createBlockItems(){

    }

    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block){
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPERTIES));
    }
}
