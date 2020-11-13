package com.duarte.morewood.provider;

import com.duarte.morewood.blocks.pos.StrippedPostBlock;
import com.duarte.morewood.registry.Blocks;
import com.duarte.morewood.util.*;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SixWayBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public BlockStateProvider(final DataGenerator generator, final ExistingFileHelper helper) {
        super(generator, Variables.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        Blocks.getBlocks(ObjectType.BARREL).forEach(block -> {
            final String path = Util.toPath(ModelProvider.BLOCK_FOLDER, ObjectType.BARREL.toString(), "%s", ((Woodwood) block).getWoodType().toString());
            this.directionalBlock(block,
                    state -> {
                        final boolean open = state.get(BarrelBlock.PROPERTY_OPEN);
                        return this.models().cubeBottomTop(
                                String.format(path, (open ? "open" : "")),
                                modLoc(String.format(path, "side")),
                                modLoc(String.format(path, "bottom")),
                                modLoc(String.format(path, "top" + (open ? "/open" : "")))
                        );
                    }
            );
        });

        Blocks.getBlocks(ObjectType.BOOKSHELF).forEach(block -> {
            final WoodType woodType = ((Woodwood) block).getWoodType();
            final String name = ((Woodwood) block).getWoodType().toString();
            final String path = Util.toPath(ModelProvider.BLOCK_FOLDER, ObjectType.BOOKSHELF.toString(), name);
            final ResourceLocation planks = Util.getPlanks(woodType);

            this.simpleBlock(block, this.models().cubeColumn(path, modLoc(path), planks));
        });

        Blocks.getBlocks(ObjectType.CHEST).forEach(block -> {
            final WoodType woodType = ((Woodwood) block).getWoodType();
            final String name = ((Woodwood) block).getWoodType().toString();
            final String path = Util.toPath(ModelProvider.BLOCK_FOLDER, ObjectType.CHEST.toString(), name);
            final ResourceLocation planks = Util.getPlanks(woodType);

            this.simpleBlock(block, this.models().getBuilder(path)
                    .texture("particle", planks));
        });

        Blocks.getBlocks(ObjectType.LADDER).forEach(block -> {
            final String woodType = ((Woodwood) block).getWoodType().toString();
            final String path = Util.toPath(ModelProvider.BLOCK_FOLDER, ObjectType.LADDER.toString());
            final ModelFile template = this.models()
                    .singleTexture(Util.toPath(path, woodType), modLoc(Util.toPath(path, "template")), "ladder", modLoc(Util.toPath(path, woodType)));

            this.horizontalBlock(block, template);
        });

    }

    private ModelFile templateWithPlanks(final Block block, final String nested, final ObjectType... objectTypes) {
        final WoodType woodType = ((Woodwood) block).getWoodType();
        final String name = ((Woodwood) block).getWoodType().toString();
        final String path = Util.toPath(ModelProvider.BLOCK_FOLDER, Arrays.stream(objectTypes).map(Objects::toString).collect(Collectors.joining("/")));
        final ResourceLocation planks = Util.getPlanks(woodType);

        return this.models().singleTexture(Util.toPath(path + nested, name), modLoc(Util.toPath(path + nested, "template")), "planks", planks);
    }

    private void postBlock(final MultiPartBlockStateBuilder builder, final ModelFile post, final ModelFile side) {
        builder.part()
                .modelFile(post)
                .addModel()
                .condition(StrippedPostBlock.AXIS, Direction.Axis.Y)
                .end()
                .part()
                .modelFile(post)
                .rotationY(90)
                .rotationX(90)
                .addModel()
                .condition(StrippedPostBlock.AXIS, Direction.Axis.X)
                .end()
                .part()
                .modelFile(post)
                .rotationX(90)
                .addModel()
                .condition(StrippedPostBlock.AXIS, Direction.Axis.Z)
                .end();

        SixWayBlock.FACING_TO_PROPERTY_MAP.forEach((direction, property) -> {
            if (direction.getAxis().isHorizontal()) {
                builder.part()
                        .modelFile(side)
                        .rotationY((((int) direction.getHorizontalAngle()) + 180) % 360)
                        .uvLock(false)
                        .addModel()
                        .condition(property, true);
            } else {
                builder.part()
                        .modelFile(side)
                        .rotationX(direction == Direction.UP ? 270 : 90)
                        .uvLock(false)
                        .addModel()
                        .condition(property, true);
            }
        });
    }

    @Override
    public String getName() {
        return "MoreWood Blockstates";
    }
}
