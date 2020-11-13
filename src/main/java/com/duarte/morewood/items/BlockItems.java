package com.duarte.morewood.items;

import com.duarte.morewood.util.ObjectType;
import com.duarte.morewood.util.WoodType;
import com.duarte.morewood.util.Woodwood;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockItems extends BlockItem implements Woodwood {
    private final ObjectType objectType;

    public BlockItems(final ObjectType objectType, final Block block, final Item.Properties properties) {
        super(block, properties);
        this.objectType = objectType;
    }

    public ObjectType getObjectType() {
        return this.objectType;
    }

    @Override
    public WoodType getWoodType() {
        assert this.getBlock() instanceof Woodwood;
        return ((Woodwood) this.getBlock()).getWoodType();
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return this.getWoodType().getProperties(this.getObjectType()).getBurnTime();
    }
}

