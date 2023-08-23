package net.iirc.techrevo.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.iirc.techrevo.TechnologicalRevolution;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class IronFurnaceRecipe implements Recipe<SimpleContainer> {
    public static final String RECIPE_TYPE = "iron_furnace_smelting";


    private static final int SLOT_COUNT = 2;
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;


    private final ResourceLocation ID;
    private final ItemStack output;
    private final NonNullList<Ingredient> inputs;


    public IronFurnaceRecipe(ResourceLocation ID, ItemStack output, NonNullList<Ingredient> inputs){
        this.ID = ID;
        this.output = output;
        this.inputs = inputs;

        System.out.println(output);
    }



    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return !pLevel.isClientSide && inputs.get(0).test(pContainer.getItem(INPUT_SLOT));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputs;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<IronFurnaceRecipe>{
        public static final Type INSTANCE = new Type();
        public static final String ID = RECIPE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<IronFurnaceRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(TechnologicalRevolution.MODID, RECIPE_TYPE);

        @Override
        public IronFurnaceRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < ingredients.size(); i++){
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new IronFurnaceRecipe(pRecipeId, output, inputs);
        }

        @Override
        public @Nullable IronFurnaceRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++){
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();

            return new IronFurnaceRecipe(pRecipeId, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, IronFurnaceRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());

            for(Ingredient ing : pRecipe.getIngredients()){
                ing.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(), false);
        }
    }
}
