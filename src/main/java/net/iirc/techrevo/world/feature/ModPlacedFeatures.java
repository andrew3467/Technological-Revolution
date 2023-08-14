package net.iirc.techrevo.world.feature;
import static net.iirc.techrevo.TechnologicalRevolution.MODID;


import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

//incase wolfy does it again and .getHolder() unavailable check newPlacedFeature(ModConfiguredFeatures for ORE_PLACED not OVERWORLD.
public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, MODID);


    public static final RegistryObject<PlacedFeature> BAUXITE_ORE_PLACED = PLACED_FEATURES.register("bauxite_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.BAUXITE_ORE_PLACED.getHolder().get(),
                    commonOrePlacement(8, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static final RegistryObject<PlacedFeature> CHALCOPYRITE_ORE_PLACED = PLACED_FEATURES.register("chalcopyrite_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.CHALCOPYRITE_ORE_PLACED.getHolder().get(),
                    commonOrePlacement(8, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static final RegistryObject<PlacedFeature> FLOURITE_ORE_PLACED = PLACED_FEATURES.register("flourite_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.FLOURITE_ORE_PLACED.getHolder().get(),
                    commonOrePlacement(8, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static final RegistryObject<PlacedFeature> HEMATITE_ORE_PLACED = PLACED_FEATURES.register("hematite_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.HEMATITE_ORE_PLACED.getHolder().get(),
                    commonOrePlacement(8, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static final RegistryObject<PlacedFeature> MAGNETITE_ORE_PLACED = PLACED_FEATURES.register("magnetite_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.MAGNETITE_ORE_PLACED.getHolder().get(),
                    commonOrePlacement(8, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));


    public static final RegistryObject<PlacedFeature> MALACHITE_ORE_PLACED = PLACED_FEATURES.register("malachite_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.MALACHITE_ORE_PLACED.getHolder().get(),
                    commonOrePlacement(8, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));


    public static final RegistryObject<PlacedFeature> PYRITE_ORE_PLACED = PLACED_FEATURES.register("pyrite_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.PYRITE_ORE_PLACED.getHolder().get(),
                    commonOrePlacement(8, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static final RegistryObject<PlacedFeature> SPHALERITE_ORE_PLACED = PLACED_FEATURES.register("sphalerite_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures. SPHALERITE_ORE_PLACED.getHolder().get(),
                    commonOrePlacement(8, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));



    public static List<PlacementModifier> orePlacement(PlacementModifier placementModifier, PlacementModifier placementModifier1) {
        return List.of(placementModifier, InSquarePlacement.spread(), placementModifier1, BiomeFilter.biome());
    }


    public static List<PlacementModifier> commonOrePlacement(int amount, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(amount), placementModifier);
    }

    public static List<PlacementModifier> rareOrePlacement(int amount, PlacementModifier placementModifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(amount), placementModifier);
    }


    public static void register(IEventBus eventbus) {
        PLACED_FEATURES.register(eventbus);
    }
}