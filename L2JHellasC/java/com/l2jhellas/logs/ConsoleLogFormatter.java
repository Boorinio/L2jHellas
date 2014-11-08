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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ConsoleLogFormatter extends Formatter
{
	// private static final String _ = " ";
	private static final String CRLF = "\r\n";

	@Override
	public String format(LogRecord record)
	{
		StringBuilder output = new StringBuilder();
		// output.append(record.getLevel().getName());
		// output.append(_);
		// output.append(record.getLoggerName());
		// output.append(_);
		output.append(record.getMessage());
		output.append(CRLF);
		if (record.getThrown() != null)
		{
			try (StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw))
			{
				record.getThrown().printStackTrace(pw);
				output.append(sw.toString());
				output.append(CRLF);
			}
			catch (Exception ex)
			{
				//
			}
		}
		return output.toString();
	}
}