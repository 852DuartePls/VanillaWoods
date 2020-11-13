package com.duarte.morewood.provider;

import com.duarte.morewood.registry.Blocks;
import com.duarte.morewood.util.*;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(final DataGenerator generator, final ExistingFileHelper helper) {
        super(generator, Variables.MOD_ID, helper);
    }

    private void blockItem(final Block block, final String path) {
        final String woodType = ((Woodwood) block).getWoodType().toString();
        this.getBuilder(Objects.requireNonNull(block.getRegistryName(), "Registry name was null.").getPath())
                .parent(new ModelFile.UncheckedModelFile(modLoc(Util.toPath(BLOCK_FOLDER, path, woodType))));
    }

    @Override
    protected void registerModels() {
        Blocks.getBlocks(ObjectType.BARREL).forEach(block -> this.blockItem(block, ObjectType.BARREL.toString()));
        Blocks.getBlocks(ObjectType.BOOKSHELF).forEach(block -> this.blockItem(block, ObjectType.BOOKSHELF.toString()));
        Blocks.getBlocks(ObjectType.CHEST).forEach(block -> {
            final WoodType woodType = ((Woodwood) block).getWoodType();
            this.getBuilder(Objects.requireNonNull(block.getRegistryName(), "Registry is null").getPath())
                    .parent(new ModelFile.UncheckedModelFile(mcLoc(Util.toPath("builtin", "entity"))))
                    .texture("particle", Util.getPlanks(woodType))
                    .transforms()
                    .transform(ModelBuilder.Perspective.GUI)
                    .rotation(30, 45, 0)
                    .scale(0.625F)
                    .end()
                    .transform(ModelBuilder.Perspective.GROUND)
                    .translation(0, 3, 0)
                    .scale(0.25F)
                    .end()
                    .transform(ModelBuilder.Perspective.HEAD)
                    .rotation(0, 180, 0)
                    .scale(1.f)
                    .end()
                    .transform(ModelBuilder.Perspective.FIXED)
                    .rotation(0, 180, 0)
                    .scale(0.5F)
                    .end()
                    .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT)
                    .rotation(75, 315, 0)
                    .translation(0.F, 2.5F, 0.F)
                    .scale(0.375F)
                    .end()
                    .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT)
                    .rotation(0, 315, 0)
                    .scale(0.4F)
                    .end()
                    .end();
        });
        Blocks.getBlocks(ObjectType.LADDER).forEach(block -> {
            final String woodType = ((Woodwood) block).getWoodType().toString();
            this.getBuilder(Objects.requireNonNull(block.getRegistryName(), "Registry is null").getPath())
                    .parent(new ModelFile.UncheckedModelFile(mcLoc(Util.toPath(ITEM_FOLDER, "generated"))))
                    .texture("layer0", modLoc(Util.toPath(BLOCK_FOLDER, ObjectType.LADDER.toString(), woodType)));
        });

    }
}
