package com.solifungi.pkmskills.common.init;

import com.solifungi.pkmskills.common.potions.PotionStatus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ModStatusConditions {

    private static final Map<Potion, Boolean> entityStatusMap = new HashMap<>();
    public static final DamageSource DMG_BADLY_POISON = (new DamageSource("dmg_badly_poison")).setDamageBypassesArmor().setMagicDamage();
    public static final DamageSource DMG_POISON = (new DamageSource("dmg_poison")).setDamageBypassesArmor().setMagicDamage();
    public static final DamageSource DMG_BURN = (new DamageSource("dmg_burn")).setDamageBypassesArmor().setMagicDamage();
    public static final DamageSource DMG_FROSTBITE = (new DamageSource("dmg_frostbite")).setDamageBypassesArmor().setMagicDamage();

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


    public static void initEntityStatusMap()
    {
        entityStatusMap.put(ModStatusConditions.BURN, false);
        entityStatusMap.put(ModStatusConditions.FREEZE, false);
        entityStatusMap.put(ModStatusConditions.PARALYSIS, false);
        entityStatusMap.put(ModStatusConditions.POISON, false);
        entityStatusMap.put(ModStatusConditions.BADLY_POISON, false);
    }

    public static Map<Potion,Boolean> getEntityStatusMap(EntityLivingBase entityLiving)
    {
        //Put/Set all false in entityStatusMap
        initEntityStatusMap();

        if (entityLiving.isPotionActive(ModStatusConditions.BURN)) {entityStatusMap.replace(ModStatusConditions.BURN, true);}
        if (entityLiving.isPotionActive(ModStatusConditions.FREEZE)) {entityStatusMap.replace(ModStatusConditions.FREEZE, true);}
        if (entityLiving.isPotionActive(ModStatusConditions.PARALYSIS)) {entityStatusMap.replace(ModStatusConditions.PARALYSIS, true);}
        if (entityLiving.isPotionActive(ModStatusConditions.POISON)) {entityStatusMap.replace(ModStatusConditions.POISON, true);}
        if (entityLiving.isPotionActive(ModStatusConditions.BADLY_POISON)) {entityStatusMap.replace(ModStatusConditions.BADLY_POISON, true);}

        return entityStatusMap;
    }

    public static int statusCount(Map<Potion,Boolean> map)
    {
        int trues = 0;
        for(Potion potion : map.keySet())
        {
            if(map.get(potion))
            {
                trues += 1;
            }
        }
        return trues;
    }

    public static boolean isEntityStatused(EntityLivingBase entity) {
        return entity.isPotionActive(ModStatusConditions.BURN) || entity.isPotionActive(ModStatusConditions.FREEZE) || entity.isPotionActive(ModStatusConditions.PARALYSIS) ||
                entity.isPotionActive(ModStatusConditions.POISON) || entity.isPotionActive(ModStatusConditions.BADLY_POISON);
    }

}
