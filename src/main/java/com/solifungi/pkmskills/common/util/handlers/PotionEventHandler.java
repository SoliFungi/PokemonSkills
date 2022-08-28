package com.solifungi.pkmskills.common.util.handlers;

import com.solifungi.pkmskills.common.init.ModStatusConditions;
import com.solifungi.pkmskills.common.potions.PotionStatus;
import com.solifungi.pkmskills.common.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class PotionEventHandler
{
    private static Potion locate_potion;

    /**
     * THIS PART IS CODED FOR MOD-ADDED <STATUS CONDITIONS> EFFECTS.
     * <Status Conditions> have their own trigger events.
     * <Status Conditions> are incompatible.
     * Vanilla POISON shall be replaced by mod ones.
     * Effects for <status conditions> generally include damage counted by %, (physical)attack fall, slowness and freezing.
     */

//    @SubscribeEvent(priority = EventPriority.HIGHEST)
//    public static void onPotionAdded(PotionEvent.PotionAddedEvent event)
//    {
//        //Get world, the potion & effect the event attempts to add, and the entity affected by the event.
//        World world = event.getEntity().getEntityWorld();
//        EntityLivingBase affectedOne = event.getEntityLiving();
//        PotionEffect eventPotionEffect = event.getPotionEffect();
//        Potion eventPotion = eventPotionEffect.getPotion();
//
//        //If the event-added potion is one of the <status conditions>
//        if(eventPotion == ModStatusConditions.BURN || eventPotion == ModStatusConditions.FREEZE || eventPotion == ModStatusConditions.PARALYSIS ||
//                eventPotion == ModStatusConditions.POISON || eventPotion == ModStatusConditions.BADLY_POISON)
//        {
//
//                //Set <status conditions> incompatible (Soli Exclusion Principle)
//            Map<Potion, Boolean> entityStatusMap = ModStatusConditions.getEntityStatusMap(affectedOne);
//            if(ModStatusConditions.statusCount(entityStatusMap) > 1)
//            {
//                if(!world.isRemote)
//                {
//                    affectedOne.addTag(eventPotion.getName() + ".readytoremove");
//                }
//            }
////        }
//
//    }


    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
    {
        World world = event.getEntity().getEntityWorld();
        EntityLivingBase entity = event.getEntityLiving();

        if(!world.isRemote) {
            PotionEffect vanillaPoison = entity.getActivePotionEffect(MobEffects.POISON);
            if (locate_potion!=null){
                entity.removePotionEffect(locate_potion);
            }
            if (vanillaPoison != null) {
                int duration = vanillaPoison.getDuration();
                int amplifier = vanillaPoison.getAmplifier();

                entity.removePotionEffect(MobEffects.POISON);

                if (amplifier < 1) {
                    if (!ModStatusConditions.isEntityStatused(entity) || entity.isPotionActive(ModStatusConditions.POISON)) {
                        entity.addPotionEffect(new PotionEffect(ModStatusConditions.POISON, duration));
                    }
                } else {
                    if (!ModStatusConditions.isEntityStatused(entity) || entity.isPotionActive(ModStatusConditions.BADLY_POISON)) {
                        entity.addPotionEffect(new PotionEffect(ModStatusConditions.BADLY_POISON, duration));
                    } else if (entity.isPotionActive(ModStatusConditions.POISON)) {
                        //Duration of modPoison is not considered
                        entity.removePotionEffect(ModStatusConditions.POISON);
                        entity.addPotionEffect(new PotionEffect(ModStatusConditions.BADLY_POISON, duration));
                    }
                }
            }
        }

//        Map<Potion, Boolean> entityStatusMap = ModStatusConditions.getEntityStatusMap(entity);
//        for(Potion potion : entityStatusMap.keySet())
//        {
//            if(entity.getTags().contains(potion.getName() + ".readytoremove"))
//            {
//                entity.removePotionEffect(potion);
//                entity.removeTag(potion.getName() + ".readytoremove");
//            }
//        }
    }


    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
        World world = event.getEntity().getEntityWorld();

        if(!world.isRemote)
        {
            EntityLivingBase victim = event.getEntityLiving();
            EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();

            if(attacker != null)
            {
                //Physical damage(non-magic) cuts in half if the attacker is BURNED
                if(attacker.isPotionActive(ModStatusConditions.BURN) && !event.getSource().isMagicDamage())
                {
                    event.setAmount(event.getAmount() * 0.5f);
                }
                //Magic damage cuts in half if the attacker is FROSTBITTEN
                if(ConfigHandler.isFreezeFrostbite)
                {
                    if(attacker.isPotionActive(ModStatusConditions.FREEZE) && event.getSource().isMagicDamage())
                    {
                        event.setAmount(event.getAmount() * 0.5f);
                    }
                }
                //Paralyzed entity have 25% change to miss a hit
                if(attacker.isPotionActive(ModStatusConditions.PARALYSIS) && attacker.getRNG().nextFloat() < 0.25)
                {
                    event.setCanceled(true);
                }
            }

            //Badly poison amplifier++ once it take effect
            if(event.getSource().getDamageType().equals("dmg_badly_poison"))
            {
                PotionEffect badlyPoison = victim.getActivePotionEffect(ModStatusConditions.BADLY_POISON);
                if(badlyPoison != null)
                {
                    int amplifier = badlyPoison.getAmplifier();
                    int duration = badlyPoison.getDuration();
                    if(amplifier < 7)
                    {
                        amplifier += 1;
                        victim.addPotionEffect(new PotionEffect(ModStatusConditions.BADLY_POISON, duration, amplifier));
                    }
                }
            }
        }

    }



    @SubscribeEvent
    public static void onPotionAdd(PotionEvent.PotionAddedEvent event) {
        modPotionTagUtil(event.getEntity(), event.getEntityLiving(), event.getPotionEffect(), event);
    }

    @SubscribeEvent
    public static void onPotionRemove(PotionEvent.PotionRemoveEvent event){
        modPotionTagUtil(event.getEntity(), event.getEntityLiving(), event.getPotionEffect(), event);
    }

    private static void modPotionTagUtil(Entity entity, EntityLivingBase entityLiving, PotionEffect potionEffect, PotionEvent event) {
        World world = entity.getEntityWorld();
        if (world.isRemote || potionEffect == null){
            return;
        }
        Potion eventPotion = potionEffect.getPotion();
        if(eventPotion == ModStatusConditions.BURN || eventPotion == ModStatusConditions.FREEZE || eventPotion == ModStatusConditions.PARALYSIS ||
                eventPotion == ModStatusConditions.POISON || eventPotion == ModStatusConditions.BADLY_POISON){

            Map<Potion,PotionEffect> map = entityLiving.getActivePotionMap();
            for (Map.Entry<Potion,PotionEffect> potionPotionEffectMap : map.entrySet()){
                if (potionPotionEffectMap.getKey().equals(ModStatusConditions.POISON) ||potionPotionEffectMap.getKey().equals(ModStatusConditions.BURN) || potionPotionEffectMap.getKey().equals(ModStatusConditions.FREEZE) ||
                        potionPotionEffectMap.getKey().equals(ModStatusConditions.PARALYSIS) || potionPotionEffectMap.getKey().equals(ModStatusConditions.BADLY_POISON)){
                    if (eventPotion != potionPotionEffectMap.getKey() && event instanceof PotionEvent.PotionAddedEvent){
                        locate_potion = eventPotion;
                    }
                    else if(eventPotion != potionPotionEffectMap.getKey() && event instanceof PotionEvent.PotionRemoveEvent){
                        locate_potion = null;
                    }
                }
            }
        }
    }



}
