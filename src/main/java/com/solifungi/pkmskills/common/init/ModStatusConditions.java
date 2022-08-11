package com.solifungi.pkmskills.common.init;

import com.solifungi.pkmskills.common.potions.PotionStatus;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModStatusConditions {
    public static final Potion BURN = new PotionStatus("burn",true,16745257,0,0);
    public static final Potion FREEZE = new PotionStatus("freeze",true,65497,1,0);
    public static final Potion PARALYSIS = new PotionStatus("paralysis",true,16764160,2,0);
    public static final Potion POISON = new PotionStatus("poison",true,13789951,3,0);
    public static final Potion BADLY_POISON = new PotionStatus("badly_poison",true,8596223,4,0);

    public static void registerStatusConditions()
    {
        registerStatusCondition(BURN);
        registerStatusCondition(FREEZE);
        registerStatusCondition(PARALYSIS);
        registerStatusCondition(POISON);
        registerStatusCondition(BADLY_POISON);
    }

    public static void registerStatusCondition(Potion effect)
    {
        ForgeRegistries.POTIONS.register(effect);
    }
}
