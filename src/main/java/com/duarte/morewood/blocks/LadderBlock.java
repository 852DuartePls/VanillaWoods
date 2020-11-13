package com.duarte.morewood.blocks;

import com.duarte.morewood.util.WoodType;
import com.duarte.morewood.util.Woodwood;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;

public class LadderBlock extends net.minecraft.block.LadderBlock implements Woodwood {
    private final WoodType type;

    public LadderBlock(final WoodType type) {
        super(AbstractBlock.Properties.from(Blocks.LADDER));
        this.type = type;
    }

    @Override
    public WoodType getWoodType() {
        return this.type;
    }
}

