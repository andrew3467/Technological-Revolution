package net.iirc.techrevo.world.feature;

import static net.iirc.techrevo.TechnologicalRevolution.MODID;
import static net.iirc.techrevo.setup.Registration.*;

import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import com.google.common.base.Supplier;

import java.util.List;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, MODID);


    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_BAUXITE_ORE = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, BAUXITE_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_CHALCOPYRITE_ORE = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, CHALCOPYRITE_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_FLOURITE_ORE = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, FLOURITE_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_HEMATITE_ORE = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, HEMATITE_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_MAGNETITE_ORE = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, MAGNETITE_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_MALECHITE_ORE = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, MALACHITE_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_PYRITE_ORE = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, PYRITE_ORE.get().defaultBlockState())));





    public static final RegistryObject<ConfiguredFeature<?, ?>> BAUXITE_ORE_PLACED = CONFIGURED_FEATURES.register("bauxite_ore_placed",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_BAUXITE_ORE.get(), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CHALCOPYRITE_ORE_PLACED = CONFIGURED_FEATURES.register("chalcopyrite_ore_placed",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_CHALCOPYRITE_ORE.get(), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> FLOURITE_ORE_PLACED = CONFIGURED_FEATURES.register("flourite_ore_placed",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_FLOURITE_ORE.get(), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> HEMATITE_ORE_PLACED = CONFIGURED_FEATURES.register("hematite_ore_placed",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_BAUXITE_ORE.get(), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> MAGNETITE_ORE_PLACED = CONFIGURED_FEATURES.register("magnetite_ore_placed",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_MAGNETITE_ORE.get(), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> MALACHITE_ORE_PLACED = CONFIGURED_FEATURES.register("malachite_ore_placed",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_MALECHITE_ORE.get(), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> PYRITE_ORE_PLACED = CONFIGURED_FEATURES.register("pyrite_ore_placed",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_PYRITE_ORE.get(), 9)));





    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
    }
}
