import mods.mekanism.api.ingredient.ChemicalStackIngredient.GasStackIngredient;

//Adds a Purifying Recipe that uses 200 mB of Oxygen (1 mB per tick) Basalt into Polished Basalt.

// <recipetype:mekanism:purifying>.addRecipe(name as string, itemInput as IIngredientWithAmount, chemicalInput as GasStackIngredient, output as IItemStack)

<recipetype:mekanism:purifying>.addRecipe("purify_basalt", <item:minecraft:basalt>, GasStackIngredient.from(<gas:mekanism:oxygen>), <item:minecraft:polished_basalt>);
//An alternate implementation of the above recipe are shown commented below. This implementation makes use of implicit casting to allow easier calling:
// <recipetype:mekanism:purifying>.addRecipe("purify_basalt", <item:minecraft:basalt>, <gas:mekanism:oxygen>, <item:minecraft:polished_basalt>);


//Removes the Purifying Recipe that creates Gold Clumps from Gold Ore.

// <recipetype:mekanism:purifying>.removeByName(name as string)

<recipetype:mekanism:purifying>.removeByName("mekanism:processing/gold/clump/from_ore");