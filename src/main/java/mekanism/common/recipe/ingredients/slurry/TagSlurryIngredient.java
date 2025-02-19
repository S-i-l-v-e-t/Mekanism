package mekanism.common.recipe.ingredients.slurry;

import com.mojang.serialization.MapCodec;
import mekanism.api.MekanismAPI;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.recipes.ingredients.chemical.ISlurryIngredient;
import mekanism.api.recipes.ingredients.chemical.TagChemicalIngredient;
import mekanism.common.registries.MekanismSlurryIngredientTypes;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;

@NothingNullByDefault
public final class TagSlurryIngredient extends TagChemicalIngredient<Slurry, ISlurryIngredient> implements ISlurryIngredient {

    public static final MapCodec<TagSlurryIngredient> CODEC = codec(MekanismAPI.SLURRY_REGISTRY_NAME, TagSlurryIngredient::new);

    TagSlurryIngredient(TagKey<Slurry> tag) {
        super(tag);
    }

    @Override
    public MapCodec<TagSlurryIngredient> codec() {
        return MekanismSlurryIngredientTypes.TAG.value();
    }

    @Override
    protected Registry<Slurry> registry() {
        return MekanismAPI.SLURRY_REGISTRY;
    }
}
