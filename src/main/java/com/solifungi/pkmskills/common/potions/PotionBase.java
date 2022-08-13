package com.solifungi.pkmskills.common.potions;

import com.solifungi.pkmskills.common.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionBase extends Potion {

    public PotionBase(String name, boolean isBadEffect, int Color, int iconIndexX, int iconIndexY) {
        super(isBadEffect, Color);
        setPotionName("effect_" + name);
        setIconIndex(iconIndexX,iconIndexY);
        setRegistryName(new ResourceLocation(Reference.MODID + ":" + name));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon()
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID + ":textures/gui/potion_effects.png"));
        return true;
    }

}
