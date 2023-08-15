package net.iirc.techrevo.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;

import static net.iirc.techrevo.TechnologicalRevolution.MODID;
import static net.iirc.techrevo.setup.Registration.*;


public class IronFurnaceRecipe implements Recipe<SimpleContainer> {
    public static final String ID = "iron_furnace_smelting";

    public final ResourceLocation id;
    private Ingredient input;
    private final ItemStack output;
    private int outputCount;



    public IronFurnaceRecipe(ResourceLocation id, ItemStack output, int outputCount, Ingredient input){
        this.id = id;
        this.output = output;
        this.outputCount = outputCount;
        this.input = input;
    }

    public Ingredient getInput(){
        return input;
    }

    public int getOutputCount(){
        return outputCount;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return !pLevel.isClientSide && this.input.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return getResultItem();
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
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }




    public static class Type implements RecipeType<IronFurnaceRecipe> {

        private Type() {

        }

        public static final Type INSTANCE = new Type();
        @Override
        public String toString() {
            return IronFurnaceRecipe.ID;
        }
    }

    public static class Serializer implements RecipeSerializer<IronFurnaceRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public static final ResourceLocation ID =
                new ResourceLocation(MODID, "iron_furnace_smelting");

        @Override
        public IronFurnaceRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input"));

            return new IronFurnaceRecipe(pRecipeId, output, 2, input);
        }

        @Override
        public @Nullable IronFurnaceRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            ItemStack output = buf.readItem();
            Ingredient input = Ingredient.fromNetwork(buf);
            int count = buf.readInt();
            return new IronFurnaceRecipe(id, output, count, input);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, IronFurnaceRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            recipe.getInput().toNetwork(buf);
            buf.writeInt(recipe.getOutputCount());
        }
    }
}
