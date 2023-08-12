package net.iirc.techrevo;

import com.mojang.logging.LogUtils;
import net.iirc.techrevo.block.ModBlocks;
import net.iirc.techrevo.item.ModItems;
import net.iirc.techrevo.setup.ModSetup;
import net.iirc.techrevo.setup.Registration;
import net.iirc.techrevo.world.feature.ModConfiguredFeatures;
import net.iirc.techrevo.world.feature.ModPlacedFeatures;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TechnologicalRevolution.MODID)
public class TechnologicalRevolution
{
    public static final String MODID = "techrevo";
    private static final Logger LOGGER = LogUtils.getLogger();

    public TechnologicalRevolution()
    {
        Registration.init();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(ModSetup::init);
        ModPlacedFeatures.register(modEventBus);
        ModConfiguredFeatures.register(modEventBus);

       // DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> modEventBus.addListener(ClientSetup::init));

        MinecraftForge.EVENT_BUS.register(this);
    }
}
