package com.duarte.morewood.items;

import com.duarte.morewood.client.ChestItemStackTileEntityRenderer;
import com.duarte.morewood.registry.Blocks;
import com.duarte.morewood.util.ObjectType;
import com.duarte.morewood.util.Variables;
import com.duarte.morewood.util.WoodType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public final class Items {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Variables.MOD_ID);
    private static final Map<ObjectType, Map<WoodType, RegistryObject<Item>>> REGISTRY_OBJECTS;


    static {
        final Map<ObjectType, Map<WoodType, RegistryObject<Item>>> registryObjects = new EnumMap<>(ObjectType.class);

        final BiFunction<ObjectType, RegistryObject<Block>, Item> BuildingItem = registerBlock((new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS.BUILDING_BLOCKS));
        final BiFunction<ObjectType, RegistryObject<Block>, Item> DecorationItem = registerBlock((new Item.Properties()).group(ItemGroup.DECORATIONS));

        registryObjects.put(ObjectType.BARREL, registerBlockItem(ObjectType.BARREL, DecorationItem));
        registryObjects.put(ObjectType.BOOKSHELF, registerBlockItem(ObjectType.BOOKSHELF, BuildingItem));
        registryObjects.put(ObjectType.CHEST, registerBlockItem(ObjectType.CHEST, DecorationItem((new Item.Properties()).group(ItemGroup.DECORATIONS).setISTER(() -> ChestItemStackTileEntityRenderer::new))));
        registryObjects.put(ObjectType.LADDER, registerBlockItem(ObjectType.LADDER, DecorationItem));

        REGISTRY_OBJECTS = Collections.unmodifiableMap(registryObjects);


    }


    private Items() {

    }
    public static Item getItem(final ObjectType objectType, final WoodType woodType) {
        return REGISTRY_OBJECTS.get(objectType).get(woodType).get();
    }

    public static Stream<Item> getItems(final ObjectType objectType) {
        return REGISTRY_OBJECTS.get(objectType).values().stream().map(RegistryObject::get);
    }

    public static Stream<Item> getItems(final ObjectType... objectTypes) {
        return Arrays.stream(objectTypes).flatMap(Items::getItems);
    }
    public static RegistryObject<Item> getRegistryObject(final ObjectType objectType, final WoodType woodType) {
        return REGISTRY_OBJECTS.get(objectType).get(woodType);
    }

    private static Map<WoodType, RegistryObject<Item>> registerBlockItem(final ObjectType objectType, final BiFunction<ObjectType, RegistryObject<Block>, Item> function) {
        final Map<WoodType, RegistryObject<Item>> items = new EnumMap<>(WoodType.class);
        WoodType.getLoadedValues().forEach(woodType -> {
            final RegistryObject<Block> block = Blocks.getRegistryObject(objectType, woodType);
            items.put(woodType, REGISTRY.register(block.getId().getPath(), () -> function.apply(objectType, block)));
        });
        return Collections.unmodifiableMap(items);
    }

    private static BiFunction<ObjectType, RegistryObject<Block>, Item> registerBlock(final Item.Properties properties) {
        return (objectType, block) -> new BlockItems(objectType, block.get(), properties);
    }

    private static BiFunction<ObjectType, RegistryObject<Block>, Item> DecorationItem(final Item.Properties properties) {
        return (objectType, block) -> new BlockItems(objectType, block.get(), properties);
    }


}
