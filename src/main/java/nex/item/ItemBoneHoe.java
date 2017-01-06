/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

package nex.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import nex.init.NetherExMaterials;

public class ItemBoneHoe extends ItemNetherExHoe
{
    public ItemBoneHoe()
    {
        super("tool_hoe_bone", NetherExMaterials.TOOL_BONE);

        addPropertyOverride(new ResourceLocation("variant"), (stack, worldIn, entityIn) -> isVariant(stack) ? 1.0F : 0.0F);
    }

    private static boolean isVariant(ItemStack stack)
    {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey("Variant");
    }
}
