package com.duarte.morewood;

import com.duarte.morewood.config.Config;
import com.duarte.morewood.items.Items;
import com.duarte.morewood.provider.BlockStateProvider;
import com.duarte.morewood.provider.ItemModelProvider;
import com.duarte.morewood.provider.ProviderLootTable;
import com.duarte.morewood.proxy.Client;
import com.duarte.morewood.proxy.Common;
import com.duarte.morewood.proxy.Proxys;
import com.duarte.morewood.registry.Blocks;
import com.duarte.morewood.registry.TileEntityTypes;
import com.duarte.morewood.util.Variables;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Variables.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class MoreWood {
    public static final Logger LOGGER = LogManager.getLogger(MoreWood.class);
    private static final Proxys proxy = DistExecutor.safeRunForDist(() -> Client::new, () -> Common::new);

    public MoreWood() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(proxy::onFMLCommonSetup);
        modEventBus.addListener(proxy::onFMLClientSetup);
        Blocks.REGISTRY.register(modEventBus);
        Items.REGISTRY.register(modEventBus);
        TileEntityTypes.REGISTRY.register(modEventBus);
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent event) {
        final DataGenerator generator = event.getGenerator();

        if (event.includeServer()) {
            generator.addProvider(new ProviderLootTable(generator));
        }

        if (event.includeClient()) {
            final ExistingFileHelper helper = event.getExistingFileHelper();

            generator.addProvider(new BlockStateProvider(generator, helper));
            generator.addProvider(new ItemModelProvider(generator, helper));
        }
    }
}
