package com.solifungi.pkmskills.common.blocks.pokeanvil;

import com.solifungi.pkmskills.common.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiPokeAnvil extends GuiContainer
{
    private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/poke_anvil.png");
    private final InventoryPlayer player;
    private final TileEntityPokeAnvil tileEntity;

    public GuiPokeAnvil(InventoryPlayer player, TileEntityPokeAnvil tileEntity)
    {
        super(new ContainerPokeAnvil(player, tileEntity));
        this.player = player;
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        //String tileName = this.tileEntity.getDisplayName().getUnformattedText();
        //this.fontRenderer.drawString(tileName,(this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) + 3, 8,4210752);
        //this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(),122, this.ySize - 96 + 2,4210752);
        String title = new TextComponentTranslation("gui.poke_anvil.title").getUnformattedText();
        this.fontRenderer.drawString(title,(this.xSize + 50) / 2 - this.fontRenderer.getStringWidth(title) / 2,20,3826382);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0f,1.0f,1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop,0,0, this.xSize, this.ySize);

        int l = this.getWorkProgressScaled(24);
        this.drawTexturedModalRect(this.guiLeft + 101,this.guiTop + 48,176,0,l + 1,16);
    }

    private int getWorkProgressScaled(int pixels)
    {
        int i = this.tileEntity.getField(0);
        int j = this.tileEntity.getField(1);
        return i != 0 && j != 0 ? i * pixels / j : 0;
    }
}
