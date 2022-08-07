package com.solifungi.pkmskills.common.potions;

import com.solifungi.pkmskills.common.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class PotionStatus extends PotionBase {

    public PotionStatus(String name, boolean isBadEffect, int Color, int iconIndexX, int iconIndexY) {
        super(name, isBadEffect, Color, iconIndexX, iconIndexY);
        setPotionName("status_" + name);
        setIconIndex(iconIndexX, iconIndexY);
    }

    @Override
    public boolean hasStatusIcon()
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID + "textures/gui/status_conditions.png"));
        return true;
    }

}
