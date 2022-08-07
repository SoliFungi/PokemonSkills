package com.solifungi.pkmskills.common.util.handlers;

import com.solifungi.pkmskills.common.init.ModStatusConditions;
import com.solifungi.pkmskills.common.util.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static java.lang.Math.max;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class PotionEventHandler
{

    /**
     * THIS PART IS CODED FOR MOD-ADDED <STATUS CONDITIONS></STATUS> EFFECTS.
     * <Status Conditions></Status> have their own trigger event.
     * <Status Conditions></Status> are incompatible.
     * <Status Conditions></Status> with an amplifier of 2 and higher have no difference with those with level 1.
     * Vanilla POISON shall be replaced by mod ones.
     * Effects for <status conditions></status> generally include damage counted by %, (physical)attack fall, slowness and freezing.
     */

    public static boolean isEntityStatused = false;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPotionAdded(PotionEvent.PotionAddedEvent event)
    {
        //Get <world>, the <potion effect> the event attempts to add, and the <entity> affected by the event.
        World world = event.getEntity().getEntityWorld();
        EntityLivingBase affectedOne = event.getEntityLiving();
        PotionEffect eventPotionEffect = event.getPotionEffect();

        //If the event-added potion is one of the <status conditions>
        //Add exclusive Tag for <status conditions>，and set them incompatible (Soli Exclusion Principle)
        if(eventPotionEffect.getPotion() == ModStatusConditions.BURN || eventPotionEffect.getPotion() == ModStatusConditions.FREEZE || eventPotionEffect.getPotion() == ModStatusConditions.PARALYSIS ||
                eventPotionEffect.getPotion() == ModStatusConditions.POISON || eventPotionEffect.getPotion() == ModStatusConditions.BADLY_POISON)
        {
            if(isEntityStatused)
            {
                if(!affectedOne.isPotionActive(eventPotionEffect.getPotion()))
                {
                    affectedOne.removePotionEffect(eventPotionEffect.getPotion());
                }
            }
            else
            {
                isEntityStatused = true;
            }

            //<Status conditions> can be added with amplifier 0 only.
            if(eventPotionEffect.getAmplifier() > 0)
            {
                int duration = eventPotionEffect.getDuration();
                affectedOne.removePotionEffect(eventPotionEffect.getPotion());
                affectedOne.addPotionEffect(new PotionEffect(eventPotionEffect.getPotion(),duration,0));
            }
        }

        //If the event-added potion is vanilla POISON
        else if(eventPotionEffect.getPotion() == MobEffects.POISON)
        {
            //Cancel the vanilla POISON adding event
            int dur_vanilla = eventPotionEffect.getDuration();
            int amplifier = eventPotionEffect.getAmplifier();
            affectedOne.removePotionEffect(eventPotionEffect.getPotion());

            if(isEntityStatused)
            {
                //Status POISON + vanilla POISON(lvl.n) ---> n = 1: Status POISON，n > 1: Status BADLY_POISON
                //Set duration: the longer one of the two
                if(affectedOne.isPotionActive(ModStatusConditions.POISON))
                {
                    int dur_poison = affectedOne.getActivePotionEffect(ModStatusConditions.POISON).getDuration();
                    if(amplifier == 0)
                    {
                        affectedOne.addPotionEffect(new PotionEffect(ModStatusConditions.POISON, dur_vanilla));
                    }
                    else
                    {
                        affectedOne.removePotionEffect(ModStatusConditions.POISON);
                        affectedOne.addPotionEffect(new PotionEffect(ModStatusConditions.BADLY_POISON, max(dur_vanilla, dur_poison)));
                    }
                }
                //Status BADLY_POISON + vanilla POISON ---> Status BADLY_POISON
                //Set duration: the longer one of the two
                else if(affectedOne.isPotionActive(ModStatusConditions.BADLY_POISON))
                {
                    affectedOne.addPotionEffect(new PotionEffect(ModStatusConditions.BADLY_POISON, dur_vanilla));
                }
                //Other three status conditions: No effect adding
            }
            //No effects or have other irrelevant effects ---> n = 1: Status POISON，n > 1: Status BADLY_POISON
            else
            {
                if(amplifier == 0)
                {
                    affectedOne.addPotionEffect(new PotionEffect(ModStatusConditions.POISON, dur_vanilla));
                }
                else
                {
                    affectedOne.addPotionEffect(new PotionEffect(ModStatusConditions.BADLY_POISON, dur_vanilla));
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPotionRemoved(PotionEvent.PotionRemoveEvent event)
    {
        if(event.getPotion() == ModStatusConditions.BURN || event.getPotion() == ModStatusConditions.FREEZE || event.getPotion() == ModStatusConditions.PARALYSIS ||
                event.getPotion() == ModStatusConditions.POISON || event.getPotion() == ModStatusConditions.BADLY_POISON)
        {
            //This event is cancelable.
            if(!event.isCanceled())
            {
                isEntityStatused = false;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPotionExpired(PotionEvent.PotionExpiryEvent event)
    {
        if(event.getPotionEffect().getPotion() == ModStatusConditions.BURN || event.getPotionEffect().getPotion() == ModStatusConditions.FREEZE || event.getPotionEffect().getPotion() == ModStatusConditions.PARALYSIS ||
                event.getPotionEffect().getPotion() == ModStatusConditions.POISON || event.getPotionEffect().getPotion() == ModStatusConditions.BADLY_POISON)
        {
            isEntityStatused = false;
        }
    }


}
