package com.solifungi.pkmskills.common.init;

import com.solifungi.pkmskills.common.items.ItemBase;
import com.solifungi.pkmskills.common.items.ItemElementCrystal;
import com.solifungi.pkmskills.common.items.tools.*;
import com.solifungi.pkmskills.common.util.handlers.EnumHandler;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class ModItems
{
    public static final Map<Item,Item> disassembleMap = new HashMap<>();

    public static final List<Item> ITEMS = new ArrayList<Item>();

    /*****Miscs*****/
    public static final Item PLATE_IRON = new ItemBase("plate_iron");
    public static final Item ROD_IRON = new ItemBase("rod_iron");
    public static final Item ROD_PRISMARINE = new ItemBase("rod_prismarine");
    public static final Item STICK_MOSSY = new ItemBase("stick_mossy");

    public static final Item ELEMENT_FRAGMENT = new ItemElementCrystal("element_fragment","fragment");
    public static final Item ELEMENT_CRYSTAL = new ItemElementCrystal("element_crystal","crystal").setMaxStackSize(16);

    public static final Item PICKAXE_TOP_FIRE = new ItemBase("pickaxe_top_fire");
    public static final Item AXE_TOP_FIRE = new ItemBase("axe_top_fire");
    public static final Item SHOVEL_TOP_FIRE = new ItemBase("shovel_top_fire");
    public static final Item SWORD_TOP_FIRE = new ItemBase("sword_top_fire");
    public static final Item PICKAXE_TOP_WATER = new ItemBase("pickaxe_top_water");
    public static final Item AXE_TOP_WATER = new ItemBase("axe_top_water");
    public static final Item SHOVEL_TOP_WATER = new ItemBase("shovel_top_water");
    public static final Item SWORD_TOP_WATER = new ItemBase("sword_top_water");
    public static final Item PICKAXE_TOP_GRASS = new ItemBase("pickaxe_top_grass");
    public static final Item AXE_TOP_GRASS = new ItemBase("axe_top_grass");
    public static final Item SHOVEL_TOP_GRASS = new ItemBase("shovel_top_grass");
    public static final Item SWORD_TOP_GRASS = new ItemBase("sword_top_grass");


    /*****Materials*****/
    //Single-Element Materials
    public static final Item.ToolMaterial TOOL_FIRE = EnumHelper.addToolMaterial("tool_fire",1, 250, 8.0F, 4.0F, 10);
    public static final Item.ToolMaterial TOOL_WATER = EnumHelper.addToolMaterial("tool_water",3, 250, 10.0F, 2.0F, 14);
    public static final Item.ToolMaterial TOOL_GRASS = EnumHelper.addToolMaterial("tool_grass",3, 200, 6.0F, 1.0F, 22);

    public static final Item.ToolMaterial TOOL_BLAZE = EnumHelper.addToolMaterial("tool_blaze",2, 800, 10.0F, 5.0F, 14);
    public static final Item.ToolMaterial TOOL_TORRENT = EnumHelper.addToolMaterial("tool_torrent",4, 800, 12.0F, 3.0F, 18);
    public static final Item.ToolMaterial TOOL_OVERGROW = EnumHelper.addToolMaterial("tool_overgrow",4, 700, 8.0F, 2.0F, 26);


    /*****Tools*****/
    public static final Item DISASSEMBLING_HAMMER = new ItemDisassemblingHammer("disassembling_hammer");

    //Single-Element Starter Tools
    public static final Item PICKAXE_FIRE = new ItemPickaxePS("pickaxe_fire",TOOL_FIRE).setDisassembleTo(PICKAXE_TOP_FIRE);
    public static final Item AXE_FIRE = new ItemAxePS("axe_fire",TOOL_FIRE,9.0f,-3.0f).setDisassembleTo(AXE_TOP_FIRE);
    public static final Item SHOVEL_FIRE = new ItemShovelPS("shovel_fire",TOOL_FIRE).setDisassembleTo(SHOVEL_TOP_FIRE);
    public static final Item SWORD_FIRE = new ItemSwordPS("sword_fire",TOOL_FIRE).setDisassembleTo(SWORD_TOP_FIRE);
    public static final Item PICKAXE_WATER = new ItemPickaxePS("pickaxe_water",TOOL_WATER).setDisassembleTo(PICKAXE_TOP_WATER);
    public static final Item AXE_WATER = new ItemAxePS("axe_water",TOOL_WATER,7.0f,-3.0f).setDisassembleTo(AXE_TOP_WATER);
    public static final Item SHOVEL_WATER = new ItemShovelPS("shovel_water",TOOL_WATER).setDisassembleTo(SHOVEL_TOP_WATER);
    public static final Item SWORD_WATER = new ItemSwordPS("sword_water",TOOL_WATER).setDisassembleTo(SWORD_TOP_WATER);
    public static final Item PICKAXE_GRASS = new ItemPickaxePS("pickaxe_grass",TOOL_GRASS).setDisassembleTo(PICKAXE_TOP_GRASS);
    public static final Item AXE_GRASS = new ItemAxePS("axe_grass",TOOL_GRASS,6.0f,-3.0f).setDisassembleTo(AXE_TOP_GRASS);
    public static final Item SHOVEL_GRASS = new ItemShovelPS("shovel_grass",TOOL_GRASS).setDisassembleTo(SHOVEL_TOP_GRASS);
    public static final Item SWORD_GRASS = new ItemSwordPS("sword_grass",TOOL_GRASS).setDisassembleTo(SWORD_TOP_GRASS);

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


    /*****Armors*****/
    //Single-Element Starter Armors
    //Single-Element Upgrade Armors



    /*****Functions*****/
    //Sets the repair items for each armor/tool material
    public static void setRepairItems()
    {
        TOOL_FIRE.setRepairItem(new ItemStack(ModItems.ELEMENT_CRYSTAL,1, EnumHandler.EnumElementType.FIRE.getMeta()));
        TOOL_WATER.setRepairItem(new ItemStack(ModItems.ELEMENT_CRYSTAL,1,EnumHandler.EnumElementType.WATER.getMeta()));
        TOOL_GRASS.setRepairItem(new ItemStack(ModItems.ELEMENT_CRYSTAL,1,EnumHandler.EnumElementType.GRASS.getMeta()));
    }

    //Put item pairs in disassembleMap
    public static void putItemInDisassembleMap(Map<Item,Item> map, Item tool, Item toolTop)
    {
        map.put(tool,toolTop);
    }

    public static ItemStack getHandle(Item tool)
    {
        if(tool instanceof ItemTool)
        {
            switch (((ItemTool)tool).getToolMaterialName())
            {
                case "tool_blaze": return new ItemStack(Items.BLAZE_ROD,2);
                case "tool_torrent": return new ItemStack(ModItems.ROD_PRISMARINE,2);
                case "tool_overgrow": return new ItemStack(ModItems.STICK_MOSSY,2);
                default: return new ItemStack(Items.STICK,2);
            }
        }
        else if(tool instanceof ItemSword)
        {
            switch (((ItemSword)tool).getToolMaterialName())
            {
                case "tool_blaze": return new ItemStack(Items.BLAZE_ROD);
                case "tool_torrent": return new ItemStack(ModItems.ROD_PRISMARINE);
                case "tool_overgrow": return new ItemStack(ModItems.STICK_MOSSY);
                default: return new ItemStack(Items.STICK);
            }
        }
        else return ItemStack.EMPTY;
    }
}
