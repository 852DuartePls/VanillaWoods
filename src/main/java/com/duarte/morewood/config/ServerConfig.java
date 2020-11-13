package com.duarte.morewood.config;

import com.duarte.morewood.util.ObjectType;
import com.duarte.morewood.util.WoodType;
import com.google.common.collect.ImmutableMap;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.StringUtils;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class ServerConfig {
    public final Map<String, Map<String, ForgeConfigSpec.IntValue>> BURN_TIME;
    public final Map<String, ForgeConfigSpec.DoubleValue> ENCHANTING_POWER_BONUS;

    protected ServerConfig(final ForgeConfigSpec.Builder builder) {
        builder.push("enchanting_power_bonus");
        ENCHANTING_POWER_BONUS = buildConfigValues(path -> builder.defineInRange(path, 1.0D, 0.0D, 15.0D));
        builder.pop();

        builder.push("burn_time");
        final ImmutableMap.Builder<String, Map<String, ForgeConfigSpec.IntValue>> burnTime = new ImmutableMap.Builder<>();
        final ImmutableMap.Builder<String, Map<String, ForgeConfigSpec.IntValue>> tieredBurnTime = new ImmutableMap.Builder<>();
        WoodType.getLoadedValues().forEach(woodType -> {
            buildBurnTimes(woodType, builder, burnTime);
        });

        BURN_TIME = burnTime.build();


    }

    private static <T extends ForgeConfigSpec.ConfigValue> ImmutableMap<String, T> buildConfigValues(final Function<String, T> functor) {
        return WoodType.getLoadedValues().map(WoodType::toString).collect(ImmutableMap.toImmutableMap(Function.identity(), functor));
    }

    private static void buildBurnTimes(final WoodType woodType, final ForgeConfigSpec.Builder spec, final ImmutableMap.Builder<String, Map<String, ForgeConfigSpec.IntValue>> burnTimes) {
        final ImmutableMap.Builder<String, ForgeConfigSpec.IntValue> burnTime = new ImmutableMap.Builder<>();

        final String name = woodType.toString();

        spec.push(StringUtils.lowerCase(name));

        Stream.of(ObjectType.BARREL, ObjectType.CHEST,
                ObjectType.BOOKSHELF,  ObjectType.LADDER)

                .map(ObjectType::toString)
                .forEach(path -> burnTime.put(path, spec.defineInRange(path, 300, -1, Integer.MAX_VALUE)));

        spec.pop();

        burnTimes.put(name, burnTime.build());
    }

}
