package com.duarte.morewood.blocks;

import com.duarte.morewood.util.WoodType;
import com.duarte.morewood.util.Woodwood;
import net.minecraft.block.Block;

public class WoodBlocks extends Block implements Woodwood {
    private final WoodType type;

    public WoodBlocks(final WoodType type, final Block.Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public WoodType getWoodType() {
        return this.type;
    }
}

