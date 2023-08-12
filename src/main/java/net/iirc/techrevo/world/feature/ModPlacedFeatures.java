package net.iirc.techrevo.world.feature;
import static net.iirc.techrevo.TechnologicalRevolution.MODID;


import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;


public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, MODID);

    public static final RegistryObject<PlacedFeature> KANYE_BLOCK_PLACED = PLACED_FEATURES.register("kanye_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.OVERWORLD_KANYE_BLOCK.getHolder().get(),
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