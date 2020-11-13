package com.duarte.morewood.proxy;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public interface Proxys {
    void onFMLClientSetup(final FMLClientSetupEvent event);

    void onFMLCommonSetup(final FMLCommonSetupEvent event);
}
