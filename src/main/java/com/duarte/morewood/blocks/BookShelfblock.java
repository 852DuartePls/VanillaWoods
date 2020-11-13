package com.duarte.morewood.blocks;


import com.duarte.morewood.util.WoodType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public final class BookShelfblock extends WoodBlocks {
    public BookShelfblock(final WoodType type) {
        super(type, AbstractBlock.Properties.from(Blocks.BOOKSHELF));
    }

    @Override
    public float getEnchantPowerBonus(final BlockState state, final IWorldReader world, final BlockPos pos) {
        return this.getWoodType().getEnchantingPowerBonus();
    }
}
