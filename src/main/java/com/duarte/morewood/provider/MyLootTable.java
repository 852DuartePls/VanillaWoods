package com.duarte.morewood.provider;

import com.duarte.morewood.registry.Blocks;
import com.duarte.morewood.util.ObjectType;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

import java.util.stream.Collectors;

public class MyLootTable extends BlockLootTables {

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Blocks.REGISTRY.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
    }

    @Override
    protected void addTables() {
        Blocks.getBlocks(ObjectType.LADDER )
                .forEach(this::registerDropSelfLootTable);

        Blocks.getBlocks(ObjectType.BARREL, ObjectType.CHEST)
                .forEach(block -> this.registerLootTable(block, BlockLootTables::droppingWithName));

        Blocks.getBlocks(ObjectType.BOOKSHELF)
                .forEach(block -> this.registerLootTable(block, b -> droppingWithSilkTouchOrRandomly(b, Items.BOOK, ConstantRange.of(3))));
    }
}

