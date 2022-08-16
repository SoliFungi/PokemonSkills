package com.solifungi.pkmskills.common.init;

import com.solifungi.pkmskills.common.blocks.BlockBase;
import com.solifungi.pkmskills.common.blocks.BlockBlaze;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block BLOCK_BLAZE = new BlockBlaze("block_blaze");
}
