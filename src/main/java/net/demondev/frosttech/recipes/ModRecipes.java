package net.demondev.frosttech.recipes;


import net.demondev.frosttech.FrostTech;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FrostTech.MOD_ID);

    public static final RegistryObject<RecipeSerializer<OreFreezerRecipe>> ORE_FREEZER_SERIALIZER =
            SERIALIZERS.register("ore_freezing", () -> OreFreezerRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<OreCrushingRecipe>> ORE_CRUSHING_SERIALIZER =
            SERIALIZERS.register("ore_crushing", () -> OreCrushingRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}