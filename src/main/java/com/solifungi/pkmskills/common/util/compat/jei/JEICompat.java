package com.solifungi.pkmskills.common.util.compat.jei;

import com.solifungi.pkmskills.common.util.Reference;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.Loader;

import java.util.IllegalFormatException;

@JEIPlugin
public class JEICompat implements IModPlugin
{
    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {}

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {}

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
//        final IJeiHelpers helpers = registry.getJeiHelpers();
//        final IGuiHelper gui = helpers.getGuiHelper();
    }

    @Override
    public void register(IModRegistry registry)
    {
        if(!Loader.isModLoaded(Reference.MODID)) return;

//        final IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
//        final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
//        IRecipeTransferRegistry recipeTransfer = registry.getRecipeTransferRegistry();
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {}

    public static String translateToLocal(String key)
    {
        if(I18n.canTranslate(key)) return I18n.translateToLocal(key);
        else return I18n.translateToFallback(key);
    }

    public static String translateToLocalFormatted(String key, Object... format)
    {
        String s = translateToLocal(key);
        try
        {
            return String.format(s,format);
        }catch (IllegalFormatException e)
        {
            return "Format error:" + s;
        }
    }
}
