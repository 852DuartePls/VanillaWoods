package com.duarte.morewood.client;

import com.duarte.morewood.blocks.ChestBlock;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ChestItemStackTileEntityRenderer extends ItemStackTileEntityRenderer {
    private MyChestTileEntity chestTileEntity = null;

    @Override
    public void func_239207_a_(final ItemStack itemStack, final ItemCameraTransforms.TransformType transformType, final MatrixStack matrixStack, final IRenderTypeBuffer buffer, final int combinedLight, final int combinedOverlay) {
        final Item item = itemStack.getItem();
        if (item instanceof BlockItem) {
            final Block block = ((BlockItem) item).getBlock();
            if (block instanceof ChestBlock) {
                if (this.chestTileEntity == null) {
                    this.chestTileEntity = (MyChestTileEntity) ((ChestBlock) block).getTileEntityType().create();
                }
                assert chestTileEntity != null;
                TileEntityRendererDispatcher.instance.renderItem(this.chestTileEntity, matrixStack, buffer, combinedLight, combinedOverlay);
            }
        }
    }
}

