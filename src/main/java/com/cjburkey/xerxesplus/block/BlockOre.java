package com.cjburkey.xerxesplus.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

class BlockOre extends Block {

    BlockOre(float hardness) {
        super(Material.GROUND);
        setSoundType(SoundType.METAL);
        setHardness(hardness);
        setHarvestLevel("pickaxe", 2);
    }

}
