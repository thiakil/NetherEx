/*
 * Copyright (C) 2016.  LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nex.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockThornBush extends BlockNetherEx
{
    private static final PropertyEnum<EnumBlockPart> PART = PropertyEnum.create("piece", EnumBlockPart.class);

    public BlockThornBush()
    {
        super("thorn_bush", Material.PLANTS, SoundType.PLANT, "piece", "top", "middle", "bottom");
        setHardness(0.0F);
    }

    @Override
    public boolean shouldDisplayVariant(int meta)
    {
        return meta == 1;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World world, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if(entity instanceof EntityLivingBase)
        {
            entity.attackEntityFrom(DamageSource.cactus, 1.0F);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block)
    {
        Block blockUp = world.getBlockState(pos.up()).getBlock();
        Block blockDown = world.getBlockState(pos.down()).getBlock();

        if(!((blockUp == Blocks.AIR || blockUp == this) && (blockDown.isBlockSolid(world, pos.down(), null) || blockDown == this)))
        {
            world.destroyBlock(pos, true);
        }
        else
        {
            if(blockDown != this && blockUp != this || blockDown == this && blockUp != this)
            {
                world.setBlockState(pos, state.withProperty(PART, EnumBlockPart.TOP), 2);
            }
            else if(blockDown != this && blockUp == this || blockDown == this && blockUp == this)
            {
                world.setBlockState(pos, state.withProperty(PART, EnumBlockPart.MIDDLE), 2);

                if(blockDown == this)
                {
                    world.setBlockState(pos.down(), state.withProperty(PART, EnumBlockPart.BOTTOM), 2);
                }
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        Block block = world.getBlockState(pos).getBlock();
        Block blockDown = world.getBlockState(pos.down()).getBlock();
        Block blockDown2 = world.getBlockState(pos.down(2)).getBlock();
        Block blockDown3 = world.getBlockState(pos.down(3)).getBlock();

        return !(blockDown == this && blockDown2 == this && blockDown3 == this) && block == Blocks.AIR && (blockDown == this || blockDown == Blocks.SAND || blockDown == Blocks.SOUL_SAND);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, PART);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(PART, EnumBlockPart.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(PART).ordinal();
    }

    public void generate(World world, Random rand, BlockPos pos)
    {
        int height = rand.nextInt(3) + 1;

        if(height == 1)
        {
            world.setBlockState(pos, getDefaultState().withProperty(PART, EnumBlockPart.TOP), 2);
        }
        else if(height == 2)
        {
            world.setBlockState(pos.up(), getDefaultState().withProperty(PART, EnumBlockPart.TOP), 2);
            world.setBlockState(pos, getDefaultState().withProperty(PART, EnumBlockPart.MIDDLE), 2);
        }
        else
        {
            world.setBlockState(pos.up(2), getDefaultState().withProperty(PART, EnumBlockPart.TOP), 2);
            world.setBlockState(pos.up(), getDefaultState().withProperty(PART, EnumBlockPart.MIDDLE), 2);
            world.setBlockState(pos, getDefaultState().withProperty(PART, EnumBlockPart.BOTTOM), 2);
        }
    }

    public enum EnumBlockPart implements IStringSerializable
    {
        TOP,
        MIDDLE,
        BOTTOM;

        public String getName()
        {
            return toString().toLowerCase();
        }
    }
}