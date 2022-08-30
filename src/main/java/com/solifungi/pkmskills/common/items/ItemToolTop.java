package com.solifungi.pkmskills.common.items;

public class ItemToolTop extends ItemBase
{
    public ItemToolTop(String name, ToolMaterial material)
    {
        super(name);
        setMaxStackSize(1);
        setMaxDamage(material.getMaxUses());
    }
}
