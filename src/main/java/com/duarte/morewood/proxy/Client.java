package com.duarte.morewood.proxy;

import com.duarte.morewood.client.renderer.tileentity.ChestTileEntityRenderer;
import com.duarte.morewood.registry.Blocks;
import com.duarte.morewood.registry.TileEntityTypes;
import com.duarte.morewood.util.ObjectType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class Client implements Proxys {
    @SuppressWarnings("unchecked")
    @Override
    public void onFMLClientSetup(final FMLClientSetupEvent event) {
        TileEntityTypes.getTileEntityTypes(ObjectType.CHEST)
                .forEach(type -> ClientRegistry.bindTileEntityRenderer(type, ChestTileEntityRenderer::new));

        Blocks.getBlocks(ObjectType.LADDER).forEach(block -> RenderTypeLookup.setRenderLayer(block, RenderType.getCutout()));

    }
    @Override
    public void onFMLCommonSetup(final FMLCommonSetupEvent event) {
    }
}
