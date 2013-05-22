/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jhellas.gameserver.script;

import org.apache.bsf.BSFManager;

public class Expression
{
	private final BSFManager _context;
	private final String _lang;
	private final String _code;

	public static Object eval(String lang, String code)
	{
		try
		{
			return new BSFManager().eval(lang, "eval", 0, 0, code);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static Object eval(BSFManager context, String lang, String code)
	{
		try
		{
			return context.eval(lang, "eval", 0, 0, code);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static Expression create(BSFManager context, String lang, String code)
	{
		try
		{
			return new Expression(context, lang, code);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private Expression(BSFManager pContext, String pLang, String pCode)
	{
		_context = pContext;
		_lang = pLang;
		_code = pCode;
	}

	public <T> void addDynamicVariable(String name, T value, Class<T> type)
	{
		try
		{
			_context.declareBean(name, value, type);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void removeDynamicVariable(String name)
	{
		try
		{
			_context.undeclareBean(name);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}