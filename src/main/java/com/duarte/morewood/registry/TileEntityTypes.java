package com.duarte.morewood.registry;

import com.duarte.morewood.client.MyBarrelTileEntity;
import com.duarte.morewood.client.MyChestTileEntity;
import com.duarte.morewood.util.ObjectType;
import com.duarte.morewood.util.Util;
import com.duarte.morewood.util.Variables;
import com.duarte.morewood.util.WoodType;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public final class TileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Variables.MOD_ID);
    private static final Map<ObjectType, Map<WoodType, RegistryObject<TileEntityType<?>>>> REGISTRY_OBJECTS;

    static {
        final EnumMap<ObjectType, Map<WoodType, RegistryObject<TileEntityType<?>>>> registryObjects = new EnumMap<>(ObjectType.class);

        registryObjects.put(ObjectType.BARREL, registerSimpleTileEntityTypes(TileEntityTypes::registerBarrelTileEntityType));
        registryObjects.put(ObjectType.CHEST, registerSimpleTileEntityTypes(TileEntityTypes::registerChestTileEntityType));

        REGISTRY_OBJECTS = Collections.unmodifiableMap(registryObjects);
    }

    private TileEntityTypes() {
    }

    public static TileEntityType<?> getTileEntityType(final ObjectType objectType, final WoodType woodType) {
        return getRegistryObject(objectType, woodType).get();
    }

    public static Stream<TileEntityType<?>> getTileEntityTypes(final ObjectType objectType) {
        return REGISTRY_OBJECTS.get(objectType).values().stream().map(RegistryObject::get);
    }

    public static RegistryObject<TileEntityType<?>> getRegistryObject(final ObjectType objectType, final WoodType woodType) {
        return REGISTRY_OBJECTS.get(objectType).get(woodType);
    }

    private static Map<WoodType, RegistryObject<TileEntityType<?>>> registerSimpleTileEntityTypes(final Function<WoodType, RegistryObject<TileEntityType<?>>> function) {
        final Map<WoodType, RegistryObject<TileEntityType<?>>> tileEntityTypes = new EnumMap<>(WoodType.class);
        WoodType.getLoadedValues().forEach(woodType -> tileEntityTypes.put(woodType, function.apply(woodType)));
        return Collections.unmodifiableMap(tileEntityTypes);
    }

    private static RegistryObject<TileEntityType<?>> registerBarrelTileEntityType(final WoodType woodType) {
        final String name = Util.toRegistryName(woodType.toString(), ObjectType.BARREL.toString());
        return REGISTRY.register(name,
                () -> TileEntityType.Builder
                        .create(() -> new MyBarrelTileEntity(woodType, (TileEntityType<BarrelTileEntity>) REGISTRY_OBJECTS.get(ObjectType.BARREL).get(woodType).get()),
                                Blocks.getBlock(ObjectType.BARREL, woodType))
                        .build(net.minecraft.util.Util.attemptDataFix(TypeReferences.BLOCK_ENTITY, name)));
    }

    private static RegistryObject<TileEntityType<?>> registerChestTileEntityType(final WoodType woodType) {
        final String name = Util.toRegistryName(woodType.toString(), ObjectType.CHEST.toString());
        return REGISTRY.register(name,
                () -> TileEntityType.Builder
                        .create(() -> new MyChestTileEntity(woodType, REGISTRY_OBJECTS.get(ObjectType.CHEST).get(woodType).get()),
                                Blocks.getBlock(ObjectType.CHEST, woodType))
                        .build(net.minecraft.util.Util.attemptDataFix(TypeReferences.BLOCK_ENTITY, name)));
    }

}
