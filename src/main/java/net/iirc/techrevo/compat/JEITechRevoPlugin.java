package net.iirc.techrevo.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.iirc.techrevo.TechnologicalRevolution;
import net.iirc.techrevo.recipes.IronFurnaceRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

public class JEITechRevoPlugin implements IModPlugin {
    public static RecipeType<IronFurnaceRecipe> IRON_FURNACE_TYPE =
            new RecipeType<>(IronFurnaceRecipeCategory.UID, IronFurnaceRecipe.class);


    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(TechnologicalRevolution.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new IronFurnaceRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<IronFurnaceRecipe> ironFurnaceSmelting = rm.getAllRecipesFor(IronFurnaceRecipe.Type.INSTANCE);
        registration.addRecipes(IRON_FURNACE_TYPE, ironFurnaceSmelting);
    }
}
