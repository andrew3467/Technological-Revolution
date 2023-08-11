package net.iirc.techrevo;

import com.mojang.logging.LogUtils;
import net.iirc.techrevo.setup.ClientSetup;
import net.iirc.techrevo.setup.ModSetup;
import net.iirc.techrevo.setup.Registration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
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

       // DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> modEventBus.addListener(ClientSetup::init));

        MinecraftForge.EVENT_BUS.register(this);
    }
}
