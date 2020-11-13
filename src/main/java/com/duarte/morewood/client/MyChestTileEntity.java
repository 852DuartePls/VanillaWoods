package com.duarte.morewood.client;

import com.duarte.morewood.util.ObjectType;
import com.duarte.morewood.util.Variables;
import com.duarte.morewood.util.WoodType;
import com.duarte.morewood.util.Woodwood;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.StringUtils;

public class MyChestTileEntity extends ChestTileEntity implements Woodwood {
    private final WoodType woodType;
    private final TranslationTextComponent defaultName;

    public MyChestTileEntity(final WoodType woodType, final TileEntityType<?> type) {
        super(type);
        this.woodType = woodType;
        this.defaultName = new TranslationTextComponent(
                StringUtils.joinWith(".", "container", Variables.MOD_ID,
                        this.getWoodType().toString() + "_" + ObjectType.CHEST.toString()));
    }

    @Override
    protected ITextComponent getDefaultName() {
        return this.defaultName;
    }

    @Override
    public WoodType getWoodType() {
        return this.woodType;
    }
}

