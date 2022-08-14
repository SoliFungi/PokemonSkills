package com.solifungi.pkmskills.common.potions;

import com.solifungi.pkmskills.common.init.ModStatusConditions;
import com.solifungi.pkmskills.common.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Objects;

public class PotionStatus extends PotionBase
{
    public PotionStatus(String name, boolean isBadEffect, int Color, int iconIndexX, int iconIndexY) {
        super(name, isBadEffect, Color, iconIndexX, iconIndexY);
        setPotionName("status_" + name);

        if(name.equals("freeze")) {
            this.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "c28c1955-14a1-49fb-9444-5fea9d83c75e", -1.0D, 2);
        }
        else if(name.equals("paralysis")) {
            this.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "c28c1955-14a1-49fb-9444-5fea9d83c75e", -0.5D, 2);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon()
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID + ":textures/gui/status_conditions.png"));
        return true;
    }


    @Override
    public void performEffect(@Nonnull EntityLivingBase entity, int amplifier)
    {
        if(this == ModStatusConditions.BURN)
        {
            entity.attackEntityFrom(DamageSource.MAGIC, entity.getMaxHealth() * 0.0625f);
        }
        else if(this == ModStatusConditions.POISON)
        {
            entity.attackEntityFrom(DamageSource.MAGIC, entity.getMaxHealth() * 0.125f);
        }
        else if(this == ModStatusConditions.BADLY_POISON)
        {
            entity.attackEntityFrom(ModStatusConditions.BAD_POISON,entity.getMaxHealth() * 0.0625f);
        }
        else if(this == ModStatusConditions.FREEZE)
        {
            //Lock visual angles
            //Codes from mod "thebetweenlands"
            NBTTagCompound nbt = entity.getEntityData();
            if(nbt.getInteger("pkmskills.freeze.ticks") != entity.ticksExisted - 1)
            {
                nbt.setFloat("pkmskills.freeze.yaw", entity.rotationYaw);
                nbt.setFloat("pkmskills.freeze.yawHead", entity.rotationYawHead);
                nbt.setFloat("pkmskills.freeze.pitch", entity.rotationPitch);
            }
            nbt.setInteger("pkmskills.freeze.ticks", entity.ticksExisted);
            entity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, nbt.getFloat("pkmskills.freeze.yaw"), nbt.getFloat("thebetweenlands.petrify.pitch"));
            entity.rotationYawHead = nbt.getFloat("pkmskills.freeze.yawHead");
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        if (this == ModStatusConditions.BURN || this == ModStatusConditions.POISON)
        {
            //Burn & poison does damage every 2 seconds
            int k = 40;
            return duration % k == 0;
        }
        else if (this == ModStatusConditions.BADLY_POISON)
        {
            int j;

            if (amplifier < 3)
            {
                j = 40;
            }
            else if (amplifier > 6)
            {
                j = 10;
            }
            else
            {
                j = 20;
            }

            return duration % j == 0;
        }
        else
        {
            return this != MobEffects.POISON;
        }
    }

}
