package com.duarte.morewood.registry;

import com.duarte.morewood.blocks.BarrelBlock;
import com.duarte.morewood.blocks.BookShelfblock;
import com.duarte.morewood.blocks.ChestBlock;
import com.duarte.morewood.blocks.LadderBlock;
import com.duarte.morewood.util.ObjectType;
import com.duarte.morewood.util.Util;
import com.duarte.morewood.util.Variables;
import com.duarte.morewood.util.WoodType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class Blocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, Variables.MOD_ID);
    private static final Map<ObjectType, Map<WoodType, RegistryObject<Block>>> REGISTRY_OBJECTS;


    static {
        final Map<ObjectType, Map<WoodType, RegistryObject<Block>>> registryObjects = new EnumMap<>(ObjectType.class);

        registryObjects.put(ObjectType.BARREL, registerBlock(Blocks::registerBarrel));
        registryObjects.put(ObjectType.BOOKSHELF, registerBlock(Blocks::registerBookshelf));
        registryObjects.put(ObjectType.CHEST, registerBlock(Blocks::registerChest));
        registryObjects.put(ObjectType.LADDER, registerBlock(Blocks::registerLadder));

    final Map<WoodType, AbstractBlock.Properties> woodProperties = new EnumMap<>(WoodType.class);
        woodProperties.put(WoodType.SPRUCE, AbstractBlock.Properties.from(net.minecraft.block.Blocks.SPRUCE_PLANKS));
        woodProperties.put(WoodType.BIRCH, AbstractBlock.Properties.from(net.minecraft.block.Blocks.BIRCH_PLANKS));
        woodProperties.put(WoodType.ACACIA, AbstractBlock.Properties.from(net.minecraft.block.Blocks.ACACIA_PLANKS));
        woodProperties.put(WoodType.JUNGLE, AbstractBlock.Properties.from(net.minecraft.block.Blocks.JUNGLE_PLANKS));
        woodProperties.put(WoodType.DARK_OAK, AbstractBlock.Properties.from(net.minecraft.block.Blocks.DARK_OAK_PLANKS));
        woodProperties.put(WoodType.CRIMSON, AbstractBlock.Properties.from(net.minecraft.block.Blocks.CRIMSON_PLANKS));
        woodProperties.put(WoodType.WARPED, AbstractBlock.Properties.from(net.minecraft.block.Blocks.WARPED_PLANKS));

        REGISTRY_OBJECTS = Collections.unmodifiableMap(registryObjects);
    }
    private Blocks() {
    }

    public static Stream<Block> getBlocks(final ObjectType objectType) {
        return REGISTRY_OBJECTS.get(objectType).values().stream().map(RegistryObject::get);
    }

    public static Block getBlock(final ObjectType objectType, final  WoodType woodType) {
        return REGISTRY_OBJECTS.get(objectType).get(woodType).get();
    }

    public static Stream<Block> getBlocks(final ObjectType... objectTypes) {
        return Arrays.stream(objectTypes).flatMap(Blocks::getBlocks);
    }

    public static RegistryObject<Block> getRegistryObject(final ObjectType objectType, final WoodType woodType) {
        return REGISTRY_OBJECTS.get(objectType).get(woodType);
    }

    public static Stream<RegistryObject<Block>> getRegistryObjects(final ObjectType objectType) {
        return REGISTRY_OBJECTS.get(objectType).values().stream();
    }

    private static Map<WoodType, RegistryObject<Block>> registerBlock(final Function<WoodType, RegistryObject<Block>> function) {
        final Map<WoodType, RegistryObject<Block>> blocks = new EnumMap<>(WoodType.class);
        WoodType.getLoadedValues().forEach(woodType -> blocks.put(woodType, function.apply(woodType)));
        return Collections.unmodifiableMap(blocks);
    }

    private static RegistryObject<Block> registerBarrel(final WoodType woodType) {
        return REGISTRY.register(Util.toRegistryName(woodType.toString(), ObjectType.BARREL.toString()), () ->
                new BarrelBlock(woodType));
    }

    private static RegistryObject<Block> registerBookshelf(final WoodType woodType) {
        return REGISTRY.register(Util.toRegistryName(woodType.toString(), ObjectType.BOOKSHELF.toString()), () ->
                new BookShelfblock(woodType));
    }

    private static RegistryObject<Block> registerChest(final WoodType woodType) {
        return REGISTRY.register(Util.toRegistryName(woodType.toString(), ObjectType.CHEST.toString()), () ->
                new ChestBlock(woodType));
    }

    private static RegistryObject<Block> registerLadder(final WoodType woodType) {
        return REGISTRY.register(Util.toRegistryName(woodType.toString(), ObjectType.LADDER.toString()), () ->
                new LadderBlock(woodType));
    }
}
