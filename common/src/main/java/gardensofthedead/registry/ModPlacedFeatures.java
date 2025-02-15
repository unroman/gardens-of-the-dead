package gardensofthedead.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import gardensofthedead.GardensOfTheDead;
import gardensofthedead.placementmodifier.CountOnEveryCeilingPlacement;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class ModPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(GardensOfTheDead.MOD_ID, Registry.PLACED_FEATURE_REGISTRY);

    public static final RegistrySupplier<PlacedFeature> SOULBLIGHT_FUNGI = register("soulblight_fungi", ModConfiguredFeatures.SOULBLIGHT_FUNGUS,
            CountOnEveryLayerPlacement.of(3),
            BlockPredicateFilter.forPredicate(BlockPredicate.not(BlockPredicate.matchesFluids(Fluids.LAVA))),
            BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Vec3i.ZERO.below(), Blocks.SOUL_SOIL)),
            BiomeFilter.biome()
    );

    public static final RegistrySupplier<PlacedFeature> SOULBLIGHT_FOREST_VEGETATION = register(
            "soulblight_forest_vegetation",
            ModConfiguredFeatures.SOULBLIGHT_FOREST_VEGETATION,
            CountOnEveryLayerPlacement.of(2),
            BiomeFilter.biome()
    );

    public static final RegistrySupplier<PlacedFeature> SHORT_STANDING_SOUL_SPORE_PATCH = register(
            "short_standing_soul_spore_patch",
            ModConfiguredFeatures.SHORT_STANDING_SOUL_SPORE_PATCH,
            CountOnEveryLayerPlacement.of(4),
            BiomeFilter.biome()
    );

    public static final RegistrySupplier<PlacedFeature> LONG_STANDING_SOUL_SPORE_PATCH = register(
            "long_standing_soul_spore_patch",
            ModConfiguredFeatures.LONG_STANDING_SOUL_SPORE_PATCH,
            CountOnEveryLayerPlacement.of(12),
            BiomeFilter.biome()
    );

    public static final RegistrySupplier<PlacedFeature> SHORT_HANGING_SOUL_SPORE_PATCH = register(
            "short_hanging_soul_spore_patch",
            ModConfiguredFeatures.SHORT_HANGING_SOUL_SPORE_PATCH,
            CountOnEveryCeilingPlacement.of(4),
            BiomeFilter.biome()
    );

    public static final RegistrySupplier<PlacedFeature> LONG_HANGING_SOUL_SPORE_PATCH = register(
            "long_hanging_soul_spore_patch",
            ModConfiguredFeatures.LONG_HANGING_SOUL_SPORE_PATCH,
            CountOnEveryCeilingPlacement.of(8),
            BiomeFilter.biome()
    );

    public static final RegistrySupplier<PlacedFeature> NOISY_CRIMSON_FUNGI = register(
            "noisy_crimson_fungi",
            TreeFeatures.CRIMSON_FUNGUS::value,
            NoiseBasedCountPlacement.of(6, 180, 0),
            CountOnEveryLayerPlacement.of(1),
            BiomeFilter.biome()
    );

    public static final RegistrySupplier<PlacedFeature> DENSE_WEEPING_VINES = register(
            "dense_weeping_vines",
            NetherFeatures.WEEPING_VINES::value,
            CountPlacement.of(40),
            InSquarePlacement.spread(),
            PlacementUtils.FULL_RANGE,
            BiomeFilter.biome()
    );

    public static final RegistrySupplier<PlacedFeature> WHISTLECANE_COLUMN = register(
            "whistlecane_column",
            ModConfiguredFeatures.WHISTLECANE_COLUMN,
            () -> List.of(
                    CountOnEveryLayerPlacement.of(20),
                    PlacementUtils.filteredByBlockSurvival(ModBlocks.WHISTLECANE.get()),
                    BiomeFilter.biome()
            )
    );

    public static final RegistrySupplier<PlacedFeature> WHISTLING_WOODS_VEGETATION = register(
            "whistling_woods_vegetation",
            ModConfiguredFeatures.WHISTLING_WOODS_VEGETATION,
            CountOnEveryLayerPlacement.of(10),
            BiomeFilter.biome()
    );

    public static final RegistrySupplier<PlacedFeature> TALL_BLISTERCROWN_PATCH = register(
            "tall_blistercrown_patch",
            ModConfiguredFeatures.TALL_BLISTERCROWN_PATCH,
            CountOnEveryLayerPlacement.of(1),
            BiomeFilter.biome()
    );

    public static final RegistrySupplier<PlacedFeature> NETHER_WART_BLOCK_PILE = register(
            "nether_wart_block_pile",
            ModConfiguredFeatures.NETHER_WART_BLOCK_PILE,
            CountOnEveryLayerPlacement.of(1),
            RarityFilter.onAverageOnceEvery(2),
            BiomeFilter.biome()
    );

    private static <T extends FeatureConfiguration> RegistrySupplier<PlacedFeature> register(String name, Supplier<ConfiguredFeature<T, ?>> feature, PlacementModifier... modifiers) {
        return register(name, feature, () -> List.of(modifiers));
    }

    private static <T extends FeatureConfiguration> RegistrySupplier<PlacedFeature> register(String name, Supplier<ConfiguredFeature<T, ?>> feature, Supplier<List<PlacementModifier>> modifiers) {
        return PLACED_FEATURES.register(name, () -> new PlacedFeature(getHolder(feature.get()), modifiers.get()));
    }

    private static Holder<ConfiguredFeature<?, ?>> getHolder(ConfiguredFeature<?, ?> feature) {
        return getHolder(BuiltinRegistries.CONFIGURED_FEATURE, feature);
    }

    public static Holder<PlacedFeature> getHolder(PlacedFeature feature) {
        return getHolder(BuiltinRegistries.PLACED_FEATURE, feature);
    }

    private static <T> Holder<T> getHolder(Registry<T> registry, T object) {
        return registry.getHolderOrThrow(registry.getResourceKey(object).orElseThrow());
    }
}
