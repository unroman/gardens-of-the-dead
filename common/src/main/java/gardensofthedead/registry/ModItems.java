package gardensofthedead.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import gardensofthedead.GardensOfTheDead;
import net.minecraft.core.Registry;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(GardensOfTheDead.MOD_ID, Registry.ITEM_REGISTRY);

    public static final RegistrySupplier<Item> SOUL_SPORE = blockItem(ModBlocks.SOUL_SPORE);
    public static final RegistrySupplier<Item> GLOWING_SOUL_SPORE = blockItem(ModBlocks.GLOWING_SOUL_SPORE);
    public static final RegistrySupplier<Item> SOULBLIGHT_FUNGUS = blockItem(ModBlocks.SOULBLIGHT_FUNGUS);
    public static final RegistrySupplier<Item> SOULBLIGHT_SPROUTS = blockItem(ModBlocks.SOULBLIGHT_SPROUTS);
    public static final RegistrySupplier<Item> BLISTERCROWN = blockItem(ModBlocks.BLISTERCROWN);
    public static final RegistrySupplier<Item> TALL_BLISTERCROWN = blockItem(ModBlocks.TALL_BLISTERCROWN);
    public static final RegistrySupplier<Item> WHISTLECANE = blockItem(ModBlocks.WHISTLECANE);

    public static final RegistrySupplier<Item> SOULBLIGHT_STEM = blockItem(ModBlocks.SOULBLIGHT_STEM);
    public static final RegistrySupplier<Item> STRIPPED_SOULBLIGHT_STEM = blockItem(ModBlocks.STRIPPED_SOULBLIGHT_STEM);
    public static final RegistrySupplier<Item> SOULBLIGHT_HYPHAE = blockItem(ModBlocks.SOULBLIGHT_HYPHAE);
    public static final RegistrySupplier<Item> STRIPPED_SOULBLIGHT_HYPHAE = blockItem(ModBlocks.STRIPPED_SOULBLIGHT_HYPHAE);
    public static final RegistrySupplier<Item> BLIGHTWART_BLOCK = blockItem(ModBlocks.BLIGHTWART_BLOCK);

    public static final RegistrySupplier<Item> SOULBLIGHT_PLANKS = blockItem(ModBlocks.SOULBLIGHT_PLANKS);
    public static final RegistrySupplier<Item> SOULBLIGHT_SLAB = blockItem(ModBlocks.SOULBLIGHT_SLAB);
    public static final RegistrySupplier<Item> SOULBLIGHT_STAIRS = blockItem(ModBlocks.SOULBLIGHT_STAIRS);
    public static final RegistrySupplier<Item> SOULBLIGHT_FENCE = blockItem(ModBlocks.SOULBLIGHT_FENCE);
    public static final RegistrySupplier<Item> SOULBLIGHT_FENCE_GATE = blockItem(ModBlocks.SOULBLIGHT_FENCE_GATE);
    public static final RegistrySupplier<Item> SOULBLIGHT_BUTTON = blockItem(ModBlocks.SOULBLIGHT_BUTTON);
    public static final RegistrySupplier<Item> SOULBLIGHT_PRESSURE_PLATE = blockItem(ModBlocks.SOULBLIGHT_PRESSURE_PLATE);
    public static final RegistrySupplier<Item> SOULBLIGHT_DOOR = ITEMS.register("soulblight_door", () -> new DoubleHighBlockItem(ModBlocks.SOULBLIGHT_DOOR.get(), properties()));
    public static final RegistrySupplier<Item> SOULBLIGHT_TRAPDOOR = blockItem(ModBlocks.SOULBLIGHT_TRAPDOOR);
    public static final RegistrySupplier<Item> SOULBLIGHT_SIGN = ITEMS.register("soulblight_sign", () -> new SignItem(properties().stacksTo(16), ModBlocks.SOULBLIGHT_SIGN.get(), ModBlocks.SOULBLIGHT_WALL_SIGN.get()));

    public static final RegistrySupplier<Item> WHISTLECANE_BLOCK = blockItem(ModBlocks.WHISTLECANE_BLOCK);
    public static final RegistrySupplier<Item> WHISTLECANE_SLAB = blockItem(ModBlocks.WHISTLECANE_SLAB);
    public static final RegistrySupplier<Item> WHISTLECANE_STAIRS = blockItem(ModBlocks.WHISTLECANE_STAIRS);
    public static final RegistrySupplier<Item> WHISTLECANE_FENCE = blockItem(ModBlocks.WHISTLECANE_FENCE);
    public static final RegistrySupplier<Item> WHISTLECANE_FENCE_GATE = blockItem(ModBlocks.WHISTLECANE_FENCE_GATE);
    public static final RegistrySupplier<Item> WHISTLECANE_BUTTON = blockItem(ModBlocks.WHISTLECANE_BUTTON);
    public static final RegistrySupplier<Item> WHISTLECANE_PRESSURE_PLATE = blockItem(ModBlocks.WHISTLECANE_PRESSURE_PLATE);
    public static final RegistrySupplier<Item> WHISTLECANE_DOOR = ITEMS.register("whistlecane_door", () -> new DoubleHighBlockItem(ModBlocks.WHISTLECANE_DOOR.get(), properties()));
    public static final RegistrySupplier<Item> WHISTLECANE_TRAPDOOR = blockItem(ModBlocks.WHISTLECANE_TRAPDOOR);
    public static final RegistrySupplier<Item> WHISTLECANE_SIGN = ITEMS.register("whistlecane_sign", () -> new SignItem(properties().stacksTo(16), ModBlocks.WHISTLECANE_SIGN.get(), ModBlocks.WHISTLECANE_WALL_SIGN.get()));

    public static final CreativeModeTab CREATIVE_TAB = CreativeTabRegistry.create(GardensOfTheDead.id("main"), () -> new ItemStack(GLOWING_SOUL_SPORE.get()));

    private static RegistrySupplier<Item> blockItem(RegistrySupplier<? extends Block> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), properties()));
    }

    private static Item.Properties properties() {
        return new Item.Properties().tab(CREATIVE_TAB);
    }

    public static void addCompostables(BiConsumer<ItemLike, Float> consumer) {
        addCompostables(
                consumer,
                0.3F,
                SOUL_SPORE.get(),
                SOULBLIGHT_SPROUTS.get(),
                BLISTERCROWN.get(),
                TALL_BLISTERCROWN.get()
        );
        addCompostables(
                consumer,
                0.5F,
                WHISTLECANE.get()
        );
        addCompostables(
                consumer,
                0.65F,
                SOULBLIGHT_FUNGUS.get()
        );
        addCompostables(
                consumer,
                0.85F,
                GLOWING_SOUL_SPORE.get(),
                BLIGHTWART_BLOCK.get()
        );
    }

    private static void addCompostables(BiConsumer<ItemLike, Float> consumer, double chance, ItemLike... items) {
        for (ItemLike item : items) {
            consumer.accept(item, (float) chance);
        }
    }
}
