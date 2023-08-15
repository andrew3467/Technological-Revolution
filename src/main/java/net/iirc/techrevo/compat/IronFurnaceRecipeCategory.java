package net.iirc.techrevo.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.iirc.techrevo.recipes.IronFurnaceRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static net.iirc.techrevo.TechnologicalRevolution.MODID;
import static net.iirc.techrevo.setup.Registration.IRON_FURNACE;

public class IronFurnaceRecipeCategory implements IRecipeCategory<IronFurnaceRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(MODID, "iron_furnace");
    public final static ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/gui/iron_furnace.png");

    private final IDrawable background;
    private final IDrawable icon;


    public IronFurnaceRecipeCategory(IGuiHelper helper){
        this.background = helper.createDrawable(TEXTURE, 0, 0, 175, 79);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(IRON_FURNACE.get()));
    }

    @Override
    public RecipeType<IronFurnaceRecipe> getRecipeType() {
        return JEITechRevoPlugin.IRON_FURNACE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Iron Furnace");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IronFurnaceRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 56, 35).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 117, 35).addItemStack(recipe.getResultItem());
    }
}
