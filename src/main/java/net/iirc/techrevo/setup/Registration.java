package net.iirc.techrevo.setup;

import net.iirc.techrevo.blocks.CopperCable;
import net.iirc.techrevo.world.feature.ModConfiguredFeatures;
import net.iirc.techrevo.world.feature.ModPlacedFeatures;
import net.minecraft.world.inventory.MenuType;
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
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);


    public static final RegistryObject<Block> TEST_BLOCK = BLOCKS.register("test_block", () -> new Block(BLOCK_PROPERTIES));
    public static final RegistryObject<Block> TEST_ORE = BLOCKS.register("test_ore", () -> new Block(BLOCK_PROPERTIES));
    public static final RegistryObject<Block> COPPER_CABLE = BLOCKS.register("copper_cable", () -> new CopperCable(BLOCK_PROPERTIES));

//  Official ores

    public static final RegistryObject<Block> HEMATITE_ORE = BLOCKS.register("hematite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static RegistryObject<Item> HEMATITE_ORE_ITEM = fromBlock(HEMATITE_ORE);

    public static final RegistryObject<Block>  BAUXITE_ORE = BLOCKS.register("bauxite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static RegistryObject<Item> BAUXITE_ORE_ITEM = fromBlock(BAUXITE_ORE);

    public static final RegistryObject<Block> PYRITE_ORE = BLOCKS.register("pyrite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static RegistryObject<Item> PYRITE_ORE_ITEM = fromBlock(PYRITE_ORE);


    public static final RegistryObject<Block> MAGNETITE_ORE  = BLOCKS.register("magnetite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static RegistryObject<Item> MAGNETITE_ORE_ITEM = fromBlock(MAGNETITE_ORE);



    public static final RegistryObject<Block>  CHALCOPYRITE_ORE = BLOCKS.register("chalcopyrite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static RegistryObject<Item> CHALCOPYRITE_ORE_ITEM = fromBlock(CHALCOPYRITE_ORE);



    public static final RegistryObject<Block> MALECHITE_ORE = BLOCKS.register("malechite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static RegistryObject<Item> MALCHEITE_ORE_ITEM = fromBlock(MALECHITE_ORE);



    public static final RegistryObject<Block> SPHALERITE_ORE = BLOCKS.register("sphalerite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static RegistryObject<Item>  SPHALERITE_ORE_ITEM = fromBlock(SPHALERITE_ORE);



    public static final RegistryObject<Block> FLOURITE_ORE = BLOCKS.register("flourite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static RegistryObject<Item> FLOURITE_ORE_ITEM = fromBlock(FLOURITE_ORE);









    //

    public static RegistryObject<Item> TEST_BLOCK_ITEM = fromBlock(TEST_BLOCK);
    public static RegistryObject<Item> TEST_ORE_ITEM = fromBlock(TEST_ORE);

    public static final RegistryObject<Block> TEST_ORE_BLOCK = BLOCKS.register("test_ore_block", () -> new Block(BLOCK_PROPERTIES));
    public static RegistryObject<Item> KANYE_BLOCK_ITEM = fromBlock(TEST_ORE_BLOCK);
    public static RegistryObject<Item> COPPER_CABLE_ITEM = fromBlock(COPPER_CABLE);



    //GUI
    public static final RegistryObject<MenuType<IronFurnaceMenu>IRON_FURNACE_MENU = MENUS.


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
