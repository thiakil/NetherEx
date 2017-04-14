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

package nex.trade;

import com.google.common.collect.Lists;

import java.util.List;

public class TradeList
{
    private String name;
    private List<TradeProfession> professions;

    public TradeList(String nameIn, TradeProfession[] professionsIn)
    {
        name = nameIn;
        professions = Lists.newArrayList(professionsIn);
    }

    public String getName()
    {
        return name;
    }

    public List<TradeProfession> getProfessions()
    {
        return professions;
    }
}