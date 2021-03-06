package gregtech.loaders.load;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;

public class FuelLoader {

    public static void registerFuels() {
        RecipeMaps.DIESEL_GENERATOR_FUELS.recipeBuilder()
            .fluidInputs(Materials.Fuel.getFluid(1))
            .duration(200).EUt(-30)
            .buildAndRegister();
    }

}