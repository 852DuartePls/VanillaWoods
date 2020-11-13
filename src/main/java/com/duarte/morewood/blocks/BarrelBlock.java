package com.duarte.morewood.blocks;

import com.duarte.morewood.registry.TileEntityTypes;
import com.duarte.morewood.util.ObjectType;
import com.duarte.morewood.util.WoodType;
import com.duarte.morewood.util.Woodwood;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.LazyValue;
import net.minecraft.world.IBlockReader;

@SuppressWarnings("NullableProblems")
public final class BarrelBlock extends net.minecraft.block.BarrelBlock implements Woodwood {
    private final WoodType woodType;
    private final LazyValue<TileEntityType<?>> tileEntityType;

    @SuppressWarnings("unchecked")
    public BarrelBlock(final WoodType woodType) {
        super(AbstractBlock.Properties.from(Blocks.BARREL));
        this.woodType = woodType;
        this.tileEntityType = new LazyValue<>(TileEntityTypes.getRegistryObject(ObjectType.BARREL, woodType));
    }

    @Override
    public TileEntity createNewTileEntity(final IBlockReader reader) {
        return this.tileEntityType.getValue().create();
    }

    @Override
    public WoodType getWoodType() {
        return this.woodType;
    }
}