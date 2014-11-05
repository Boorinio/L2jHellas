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
package com.l2jhellas.logs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import javolution.text.TextBuilder;

import com.l2jhellas.Config;

/**
 * @author Nightwolf
 */
public class FileLogFormatter extends Formatter
{
	private static final String CRLF = "\r\n";
	private static final String tab = "\t";
	private final SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	@Override
	public String format(LogRecord record)
	{
		TextBuilder output = new TextBuilder();

		output.append("L2jHellas");
		output.append(tab);
		output.append(dateFmt.format(new Date(record.getMillis())));
		output.append(tab);
		output.append(record.getLevel().getName());
		if (Config.DEBUG_LOGGER)
		{
			output.append(tab);
			output.append(record.getThreadID());
			output.append(tab);
			output.append(record.getLoggerName());
		}
		output.append(tab);
		output.append(record.getMessage());

		output.append(CRLF);

		return output.toString();
	}
}