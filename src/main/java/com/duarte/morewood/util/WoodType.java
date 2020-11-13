package com.duarte.morewood.util;

import com.duarte.morewood.MoreWood;
import com.duarte.morewood.config.Config;
import net.minecraft.util.LazyValue;
import net.minecraftforge.fml.ModList;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public enum WoodType {
    SPRUCE(Variables.SPRUCE),
    BIRCH(Variables.BIRCH),
    ACACIA(Variables.ACACIA),
    DARK_OAK(Variables.DARK_OAK),
    JUNGLE(Variables.JUNGLE),
    CRIMSON(Variables.CRIMSON),
    WARPED(Variables.WARPED);

    private final String name;
    private final String modId;
    private final Map<ObjectType, Properties> properties;
    private final LazyValue<Supplier<Double>> enchantingPowerBonus;

    WoodType(final String name) {
        this(name, Variables.MOD_ID);
    }

    WoodType(final String name, final String modId) {
        this.name = name;
        this.modId = modId;
        final Map<ObjectType, Properties> properties = new EnumMap<>(ObjectType.class);
        for (final ObjectType objectType : ObjectType.values()) {
            properties.put(objectType, new Properties(() -> Config.SERVER_CONFIG.BURN_TIME.get(name).get(objectType.toString())::get));
        }
        this.properties = Collections.unmodifiableMap(properties);
        this.enchantingPowerBonus = new LazyValue<>(() -> Config.SERVER_CONFIG.ENCHANTING_POWER_BONUS.get(name)::get);
    }

    public static Stream<WoodType> getLoadedValues() {
        try {
            final String dataModId = System.getProperty("ilikewood.datagen.modid");
            if (dataModId != null) {
                return Arrays.stream(values()).filter(woodType -> woodType.getModId().equals(dataModId));
            }
        } catch (NullPointerException | SecurityException | IllegalArgumentException e) {
            MoreWood.LOGGER.error(e.getMessage());
        }
        return Arrays.stream(values()).filter(woodType -> ModList.get().isLoaded(woodType.getModId()));
    }

    public Properties getProperties(final ObjectType objectType) {
        return this.properties.get(objectType);
    }

    public float getEnchantingPowerBonus() {
        return this.enchantingPowerBonus.getValue().get().floatValue();
    }

    public String getModId() {
        return this.modId;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static final class Properties {
        private final LazyValue<Supplier<Integer>> burnTime;

        public Properties(final Supplier<Supplier<Integer>> burnTime) {
            this.burnTime = new LazyValue<>(burnTime);
        }

        public int getBurnTime() {
            return this.burnTime.getValue().get();
        }
    }
}