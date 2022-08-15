package com.solifungi.pkmskills.common.init;

import com.solifungi.pkmskills.common.items.ItemElementCrystal;
import com.solifungi.pkmskills.common.items.tools.ItemAxePS;
import com.solifungi.pkmskills.common.items.tools.ItemPickaxePS;
import com.solifungi.pkmskills.common.items.tools.ItemShovelPS;
import com.solifungi.pkmskills.common.items.tools.ItemSwordPS;
import com.solifungi.pkmskills.common.util.handlers.EnumHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<Item>();

    //Misc
    public static final Item ELEMENT_FRAGMENT = new ItemElementCrystal("element_fragment","fragment");
    public static final Item ELEMENT_CRYSTAL = new ItemElementCrystal("element_crystal","crystal").setMaxStackSize(16);


    //Material
    //Single-Element Materials
    public static final Item.ToolMaterial TOOL_FIRE = EnumHelper.addToolMaterial("tool_fire",1, 250, 8.0F, 4.0F, 10);
    public static final Item.ToolMaterial TOOL_WATER = EnumHelper.addToolMaterial("tool_water",3, 250, 10.0F, 2.0F, 14);
    public static final Item.ToolMaterial TOOL_GRASS = EnumHelper.addToolMaterial("tool_grass",3, 200, 6.0F, 1.0F, 22);

    public static final Item.ToolMaterial TOOL_BLAZE = EnumHelper.addToolMaterial("tool_blaze",2, 800, 10.0F, 5.0F, 14);
    public static final Item.ToolMaterial TOOL_TORRENT = EnumHelper.addToolMaterial("tool_torrent",4, 800, 12.0F, 3.0F, 18);
    public static final Item.ToolMaterial TOOL_OVERGROW = EnumHelper.addToolMaterial("tool_overgrow",4, 700, 8.0F, 2.0F, 26);


    //Tools
    //Single-Element Starter Tools
    public static final Item PICKAXE_FIRE = new ItemPickaxePS("pickaxe_fire",TOOL_FIRE);
    public static final Item AXE_FIRE = new ItemAxePS("axe_fire",TOOL_FIRE,9.0f,-3.0f);
    public static final Item SHOVEL_FIRE = new ItemShovelPS("shovel_fire",TOOL_FIRE);
    public static final Item SWORD_FIRE = new ItemSwordPS("sword_fire",TOOL_FIRE);

    public static final Item PICKAXE_WATER = new ItemPickaxePS("pickaxe_water",TOOL_WATER);
    public static final Item AXE_WATER = new ItemAxePS("axe_water",TOOL_WATER,7.0f,-3.0f);
    public static final Item SHOVEL_WATER = new ItemShovelPS("shovel_water",TOOL_WATER);
    public static final Item SWORD_WATER = new ItemSwordPS("sword_water",TOOL_WATER);

    public static final Item PICKAXE_GRASS = new ItemPickaxePS("pickaxe_grass",TOOL_GRASS);
    public static final Item AXE_GRASS = new ItemAxePS("axe_grass",TOOL_GRASS,6.0f,-3.0f);
    public static final Item SHOVEL_GRASS = new ItemShovelPS("shovel_grass",TOOL_GRASS);
    public static final Item SWORD_GRASS = new ItemSwordPS("sword_grass",TOOL_GRASS);

    //Single-Element Upgrade Tools
    public static final Item PICKAXE_BLAZE = new ItemPickaxePS("pickaxe_blaze",TOOL_BLAZE);
    public static final Item AXE_BLAZE = new ItemAxePS("axe_blaze",TOOL_BLAZE,10.0f,-2.9f);
    public static final Item SHOVEL_BLAZE = new ItemShovelPS("shovel_blaze",TOOL_BLAZE);
    public static final Item SWORD_BLAZE = new ItemSwordPS("sword_blaze",TOOL_BLAZE);

    public static final Item PICKAXE_TORRENT = new ItemPickaxePS("pickaxe_torrent",TOOL_TORRENT);
    public static final Item AXE_TORRENT = new ItemAxePS("axe_torrent",TOOL_TORRENT,8.0f,-2.9f);
    public static final Item SHOVEL_TORRENT = new ItemShovelPS("shovel_torrent",TOOL_TORRENT);
    public static final Item SWORD_TORRENT = new ItemSwordPS("sword_torrent",TOOL_TORRENT);

    public static final Item PICKAXE_OVERGROW = new ItemPickaxePS("pickaxe_overgrow",TOOL_OVERGROW);
    public static final Item AXE_OVERGROW = new ItemAxePS("axe_overgrow",TOOL_OVERGROW,7.0f,-2.9f);
    public static final Item SHOVEL_OVERGROW = new ItemShovelPS("shovel_overgrow",TOOL_OVERGROW);
    public static final Item SWORD_OVERGROW = new ItemSwordPS("sword_overgrow",TOOL_OVERGROW);


    //Armors


    /**
     * Sets the repair items for each armor/tool material
     */
    public static void setRepairItems()
    {
        TOOL_FIRE.setRepairItem(new ItemStack(ModItems.ELEMENT_CRYSTAL,1, EnumHandler.EnumElementType.FIRE.getMeta()));
        TOOL_WATER.setRepairItem(new ItemStack(ModItems.ELEMENT_CRYSTAL,1,EnumHandler.EnumElementType.WATER.getMeta()));
        TOOL_GRASS.setRepairItem(new ItemStack(ModItems.ELEMENT_CRYSTAL,1,EnumHandler.EnumElementType.GRASS.getMeta()));
    }
}
