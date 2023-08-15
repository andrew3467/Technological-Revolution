package net.iirc.techrevo.setup;

import net.iirc.techrevo.blocks.IronFurnaceTile;
import net.iirc.techrevo.menus.IronFurnaceMenu;
import net.iirc.techrevo.recipes.IronFurnaceRecipe;
import net.iirc.techrevo.screens.IronFurnaceScreen;
import net.minecraft.client.gui.screens.MenuScreens;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.iirc.techrevo.blocks.IronFurnace;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

import static net.iirc.techrevo.TechnologicalRevolution.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registration {
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(ModSetup.ITEM_GROUP);
    public static final BlockBehaviour.Properties BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(2.0f);


    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MODID);


    public static final RegistryObject<Block> IRON_FURNACE = BLOCKS.register("iron_furnace", () -> new IronFurnace(BLOCK_PROPERTIES));

//  Official ores
    public static final RegistryObject<Block> HEMATITE_ORE = BLOCKS.register("hematite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static final RegistryObject<Block> BAUXITE_ORE = BLOCKS.register("bauxite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static final RegistryObject<Block> PYRITE_ORE = BLOCKS.register("pyrite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static final RegistryObject<Block> MAGNETITE_ORE = BLOCKS.register("magnetite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static final RegistryObject<Block> CHALCOPYRITE_ORE = BLOCKS.register("chalcopyrite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static final RegistryObject<Block> MALACHITE_ORE = BLOCKS.register("malachite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static final RegistryObject<Block> SPHALERITE_ORE = BLOCKS.register("sphalerite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static final RegistryObject<Block> FLOURITE_ORE = BLOCKS.register("flourite_ore", () -> new Block(BLOCK_PROPERTIES));
    public static final RegistryObject<Block> WOLFRAMITE_ORE = BLOCKS.register("wolframite_ore", () -> new Block(BLOCK_PROPERTIES));



    //ITEMS
    public static final RegistryObject<Item> WOLFRAMITE_INGOT = ITEMS.register("wolframite_ingot", () -> new Item(ITEM_PROPERTIES));



    public static final RegistryObject<BlockEntityType<IronFurnaceTile>> IRON_FURNACE_TILE = BLOCK_ENTITIES.register("iron_furnace", () ->
            BlockEntityType.Builder.of(IronFurnaceTile::new, IRON_FURNACE.get()).build(null));


    //GUI
    public static final RegistryObject<MenuType<IronFurnaceMenu>> IRON_FURNACE_MENU = registerMenuType(IronFurnaceMenu::new, "iron_furnace_menu");


    //RECIPE SERIALIZERS
    public static final RegistryObject<RecipeSerializer<IronFurnaceRecipe>> IRON_FURNACE_SERIALIZER =
            RECIPE_SERIALIZERS.register("iron_furnace_smelting", () -> IronFurnaceRecipe.Serializer.INSTANCE);


    //RECIPE TYPES
    public static final RegistryObject<RecipeType<IronFurnaceRecipe>> IRON_FURNACE_TYPE =
            RECIPE_TYPES.register("iron_furnace", () -> IronFurnaceRecipe.Type.INSTANCE);





    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        MENUS.register(bus);
    }

    @SubscribeEvent
    public static void onRegisterItems(final RegisterEvent event) {
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS)) {
            BLOCKS.getEntries().forEach((blockRegistryObject) -> {
                Block block = blockRegistryObject.get();
                Supplier<Item> blockItemFactory = () -> new BlockItem(block, ITEM_PROPERTIES);
                event.register(ForgeRegistries.Keys.ITEMS, blockRegistryObject.getId(), blockItemFactory);
            });
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            //Block GUIs
            MenuScreens.register(IRON_FURNACE_MENU.get(), IronFurnaceScreen::new);
        }
    }
}
