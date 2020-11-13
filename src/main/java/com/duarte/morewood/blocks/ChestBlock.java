package com.duarte.morewood.blocks;

import com.duarte.morewood.registry.TileEntityTypes;
import com.duarte.morewood.util.ObjectType;
import com.duarte.morewood.util.WoodType;
import com.duarte.morewood.util.Woodwood;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.LazyValue;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.function.BiPredicate;

public class ChestBlock extends net.minecraft.block.ChestBlock implements Woodwood {
    private final WoodType woodType;
    private final LazyValue<TileEntityType<? extends ChestTileEntity>> tileEntityType;

    @SuppressWarnings("unchecked")
    public ChestBlock(final WoodType woodType) {
        super(AbstractBlock.Properties.from(Blocks.CHEST),
                () -> (TileEntityType<? extends ChestTileEntity>) TileEntityTypes.getTileEntityType(ObjectType.CHEST, woodType));
        this.woodType = woodType;
        this.tileEntityType = new LazyValue<>(() -> (TileEntityType<? extends ChestTileEntity>) TileEntityTypes.getRegistryObject(ObjectType.CHEST, woodType).get());
    }

    public TileEntityType<? extends ChestTileEntity> getTileEntityType() {
        return tileEntityType.getValue();
    }

    @Override
    public TileEntity createNewTileEntity(final IBlockReader reader) {
        return tileEntityType.getValue().create();
    }

    @Override
    public TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> combine(final BlockState blockState, final World world, final BlockPos blockPos, final boolean isHopper) {
        final BiPredicate<IWorld, BlockPos> predicate;
        if (isHopper) {
            predicate = (w, p) -> false;
        } else {
            predicate = ChestBlock::isBlocked;
        }

        return TileEntityMerger.func_226924_a_(this.tileEntityType.getValue(), ChestBlock::getChestMergerType, ChestBlock::getDirectionToAttached, FACING, blockState, world, blockPos, predicate);
    }

    @Override
    public WoodType getWoodType() {
        return this.woodType;
    }
}

