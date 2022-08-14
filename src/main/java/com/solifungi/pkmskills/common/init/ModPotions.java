package com.solifungi.pkmskills.common.init;

import com.solifungi.pkmskills.common.potions.PotionBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModPotions {
//    public static final Potion NEW_POTION_EFFECT = new PotionBase("new_potion",false,13791173,0,0);
//
//    public static final PotionType NEW_POTION = new PotionType("new_potion",
//            new PotionEffect[]{new PotionEffect(NEW_POTION_EFFECT,2400)}).setRegistryName("new_potion");
//    public static final PotionType LONG_NEW_POTION = new PotionType("new_potion",
//            new PotionEffect[]{new PotionEffect(NEW_POTION_EFFECT,4800)}).setRegistryName("long_new_potion");

    private static Potion getRegisteredMobEffect(String id)
    {
        Potion potion = Potion.REGISTRY.getObject(new ResourceLocation(id));

        if (potion == null)
        {
            throw new IllegalStateException("Invalid MobEffect requested: " + id);
        }
        else
        {
            return potion;
        }
    }

    public static void registerPotions()
    {
        //registerPotion(NEW_POTION, LONG_NEW_POTION, NEW_POTION_EFFECT);
    }

    private static void registerPotion(PotionType defaultPotion, PotionType longPotion, Potion effect)
    {
        ForgeRegistries.POTIONS.register(effect);
        ForgeRegistries.POTION_TYPES.register(defaultPotion);
        ForgeRegistries.POTION_TYPES.register(longPotion);
    }
}
