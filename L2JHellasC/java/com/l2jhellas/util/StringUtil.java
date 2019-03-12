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
package com.l2jhellas.util;

import java.util.logging.Logger;

public final class StringUtil
{

	private static final Logger LOG = Logger.getLogger(StringUtil.class.getSimpleName());
	
	
	private StringUtil()
	{
	}

	/**
	 * Concatenates strings.
	 * 
	 * @param strings
	 *        strings to be concatenated
	 * @return concatenated string
	 * @see StringUtil
	 */
	public static String concat(final String... strings)
	{
		final StringBuilder sbString = new StringBuilder(getLength(strings));

		for (final String string : strings)
		{
			sbString.append(string);
		}

		return sbString.toString();
	}

	/**
	 * Creates new string builder with size initializated to <code>sizeHint</code>, unless total length of strings is greater than <code>sizeHint</code>.
	 * 
	 * @param sizeHint
	 *        hint for string builder size allocation
	 * @param strings
	 *        strings to be appended
	 * @return created string builder
	 * @see StringUtil
	 */
	public static StringBuilder startAppend(final int sizeHint, final String... strings)
	{
		final int length = getLength(strings);
		final StringBuilder sbString = new StringBuilder(sizeHint > length ? sizeHint : length);

		for (final String string : strings)
		{
			sbString.append(string);
		}

		return sbString;
	}

	public static void append(StringBuilder sb, Object... content)
	{
		for (Object obj : content)
			sb.append((obj == null) ? null : obj.toString());
	}
	
	/**
	 * Counts total length of all the strings.
	 * 
	 * @param strings
	 *        array of strings
	 * @return total length of all the strings
	 */
	private static int getLength(final String[] strings)
	{
		int length = 0;

		for (final String string : strings)
		{
			length += string.length();
		}

		return length;
	}

	/**
	 * @param text : the String to check.
	 * @return true if the String contains only numbers, false otherwise.
	 */
	public static boolean isDigit(String text)
	{
		if (text == null)
			return false;
		
		return text.matches("[0-9]+");
	}

	/**
	 * Format a given text to fit with logging "title" criterias, and send it.
	 * @param text : the String to format.
	 */
	public static void printSection(String text)
	{
		final StringBuilder sb = new StringBuilder(80);
		for (int i = 0; i < (73 - text.length()); i++)
			sb.append("-");
		
		StringUtil.append(sb, "=[ ", text, " ]");
		
		LOG.info(sb.toString());
	}
}