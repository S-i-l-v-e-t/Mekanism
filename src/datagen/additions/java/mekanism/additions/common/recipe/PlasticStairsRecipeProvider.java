package mekanism.additions.common.recipe;

import java.util.Map;
import mekanism.additions.common.AdditionsTags;
import mekanism.additions.common.MekanismAdditions;
import mekanism.additions.common.registries.AdditionsBlocks;
import mekanism.api.providers.IItemProvider;
import mekanism.api.text.EnumColor;
import mekanism.common.recipe.ISubRecipeProvider;
import mekanism.common.recipe.builder.ExtendedShapedRecipeBuilder;
import mekanism.common.recipe.pattern.Pattern;
import mekanism.common.recipe.pattern.RecipePattern;
import mekanism.common.recipe.pattern.RecipePattern.TripleLine;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class PlasticStairsRecipeProvider implements ISubRecipeProvider {

    private static final RecipePattern PLASTIC_STAIRS = RecipePattern.createPattern(
          TripleLine.of(Pattern.CONSTANT, Pattern.EMPTY, Pattern.EMPTY),
          TripleLine.of(Pattern.CONSTANT, Pattern.CONSTANT, Pattern.EMPTY),
          TripleLine.of(Pattern.CONSTANT, Pattern.CONSTANT, Pattern.CONSTANT));

    @Override
    public void addRecipes(RecipeOutput consumer, HolderLookup.Provider registries) {
        String basePath = "plastic/stairs/";
        registerPlasticStairs(consumer, AdditionsBlocks.PLASTIC_STAIRS, AdditionsBlocks.PLASTIC_BLOCKS, AdditionsTags.Items.STAIRS_PLASTIC, false, basePath);
        registerPlasticStairs(consumer, AdditionsBlocks.TRANSPARENT_PLASTIC_STAIRS, AdditionsBlocks.TRANSPARENT_PLASTIC_BLOCKS,
              AdditionsTags.Items.STAIRS_PLASTIC_TRANSPARENT, true, basePath + "transparent/");
        registerPlasticStairs(consumer, AdditionsBlocks.PLASTIC_GLOW_STAIRS, AdditionsBlocks.PLASTIC_GLOW_BLOCKS, AdditionsTags.Items.STAIRS_PLASTIC_GLOW, false,
              basePath + "glow/");
    }

    private void registerPlasticStairs(RecipeOutput consumer, Map<EnumColor, ? extends IItemProvider> blocks, Map<EnumColor, ? extends IItemProvider> plastic,
          TagKey<Item> blockType, boolean transparent, String basePath) {
        for (Map.Entry<EnumColor, ? extends IItemProvider> entry : blocks.entrySet()) {
            EnumColor color = entry.getKey();
            registerPlasticStairs(consumer, color, entry.getValue(), plastic.get(color), blockType, transparent, basePath);
        }
    }

    private void registerPlasticStairs(RecipeOutput consumer, EnumColor color, IItemProvider result, IItemProvider plastic, TagKey<Item> blockType,
          boolean transparent, String basePath) {
        ExtendedShapedRecipeBuilder.shapedRecipe(result, 4)
              .pattern(PLASTIC_STAIRS)
              .key(Pattern.CONSTANT, plastic)
              .category(RecipeCategory.BUILDING_BLOCKS)
              .build(consumer, MekanismAdditions.rl(basePath + color.getRegistryPrefix()));
        if (transparent) {
            PlasticBlockRecipeProvider.registerTransparentRecolor(consumer, result, blockType, color, basePath);
        } else {
            PlasticBlockRecipeProvider.registerRecolor(consumer, result, blockType, color, basePath);
        }
    }
}