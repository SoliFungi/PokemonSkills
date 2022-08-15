package com.solifungi.pkmskills.common.util.handlers;

import com.solifungi.pkmskills.common.init.ModStatusConditions;
import com.solifungi.pkmskills.common.util.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

import static java.lang.Math.max;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class PotionEventHandler
{

    /**
     * THIS PART IS CODED FOR MOD-ADDED <STATUS CONDITIONS> EFFECTS.
     * <Status Conditions> have their own trigger events.
     * <Status Conditions> are incompatible.
     * Vanilla POISON shall be replaced by mod ones.
     * Effects for <status conditions> generally include damage counted by %, (physical)attack fall, slowness and freezing.
     */

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPotionAdded(PotionEvent.PotionAddedEvent event)
    {
        //Get world, the potion & effect the event attempts to add, and the entity affected by the event.
        World world = event.getEntity().getEntityWorld();
        EntityLivingBase affectedOne = event.getEntityLiving();
        PotionEffect eventPotionEffect = event.getPotionEffect();
        Potion eventPotion = eventPotionEffect.getPotion();

        //If the event-added potion is one of the <status conditions>
        if(eventPotion == ModStatusConditions.BURN || eventPotion == ModStatusConditions.FREEZE || eventPotion == ModStatusConditions.PARALYSIS ||
                eventPotion == ModStatusConditions.POISON || eventPotion == ModStatusConditions.BADLY_POISON)
        {
            //Set <status conditions> incompatible (Soli Exclusion Principle)
            Map<Potion, Boolean> entityStatusMap = ModStatusConditions.getEntityStatusMap(affectedOne);
            if(ModStatusConditions.statusCount(entityStatusMap) > 1)
            {
                if(!world.isRemote){
                    affectedOne.removePotionEffect(eventPotion);
                }
            }
        }

        //If the event-added potion is vanilla POISON
        else if(eventPotionEffect.getPotion() == MobEffects.POISON)
        {
            int dur_vanilla = eventPotionEffect.getDuration();
            int amplifier = eventPotionEffect.getAmplifier();

            if(ModStatusConditions.isEntityStatused(affectedOne))
            {
                //Status POISON + vanilla POISON(lvl.n) ---> n = 1: Status POISON，n > 1: Status BADLY_POISON
                //Set duration: the longer one of the two
                PotionEffect modPoison = affectedOne.getActivePotionEffect(ModStatusConditions.POISON);
                if(modPoison != null)
                {
                    int dur_poison = modPoison.getDuration();
                    if(amplifier == 0)
                    {
                        affectedOne.addPotionEffect(new PotionEffect(ModStatusConditions.POISON, dur_vanilla));
                    }
                    else
                    {
                        affectedOne.addPotionEffect(new PotionEffect(ModStatusConditions.BADLY_POISON, max(dur_vanilla, dur_poison)));
                    }
                }
                //Status BADLY_POISON + vanilla POISON ---> Status BADLY_POISON
                //Set duration: the longer one of the two
                else if(affectedOne.isPotionActive(ModStatusConditions.BADLY_POISON))
                {
                    affectedOne.addPotionEffect(new PotionEffect(ModStatusConditions.BADLY_POISON, dur_vanilla));
                }
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


    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
        World world = event.getEntity().getEntityWorld();

        if(!world.isRemote)
        {
            EntityLivingBase victim = event.getEntityLiving();
            EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();

            //Physical damage(non-magic) cuts in half if the attacker is BURNED

            if(attacker != null)
            {
                if(attacker.isPotionActive(ModStatusConditions.BURN) && !event.getSource().isMagicDamage())
                {
                    event.setAmount(event.getAmount() * 0.5f);
                }
                if(attacker.isPotionActive(ModStatusConditions.PARALYSIS) && attacker.getRNG().nextFloat() < 0.25)
                {
                    event.setCanceled(true);
                }
            }

            //Badly poison amplifier++ once it take effect
            if(event.getSource().getDamageType().equals("bad_poison"))
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

}
