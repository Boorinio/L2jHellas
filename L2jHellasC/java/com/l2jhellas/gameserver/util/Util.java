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
package com.l2jhellas.gameserver.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.model.L2Character;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;


/**
 * General Utility functions related to Gameserver
 *
 * @version $Revision: 1.2 $ $Date: 2004/06/27 08:12:59 $
 */
public final class Util
{
	private final static Log _log = LogFactory.getLog(Util.class.getName());
	
    public static void handleIllegalPlayerAction(L2PcInstance actor, String message, int punishment)
    {
    	ThreadPoolManager.getInstance().scheduleGeneral(new IllegalPlayerAction(actor,message, punishment), 5000);
    }

    public static String getRelativePath(File base,File file)
    {
        return file.toURI().getPath().substring(base.toURI().getPath().length());
    }

    /** Return degree value of object 2 to the horizontal line with object 1 being the origin */
    public static double calculateAngleFrom(L2Object obj1, L2Object obj2)
    {
    	return calculateAngleFrom(obj1.getX(), obj1.getY(), obj2.getX(), obj2.getY());
    }

    /** Return degree value of object 2 to the horizontal line with object 1 being the origin */
    public final static double calculateAngleFrom(int obj1X, int obj1Y, int obj2X, int obj2Y)
	{
		double angleTarget = Math.toDegrees(Math.atan2(obj2Y - obj1Y, obj2X - obj1X));
		if (angleTarget < 0)
			angleTarget = 360 + angleTarget;
		return angleTarget;
	}

    public static double calculateDistance(int x1, int y1, int z1, int x2, int y2) { return calculateDistance(x1, y1, 0, x2, y2, 0, false); }

    public static double calculateDistance(int x1, int y1, int z1, int x2, int y2, int z2, boolean includeZAxis)
    {
        double dx = (double)x1 - x2;
        double dy = (double)y1 - y2;

        if (includeZAxis)
        {
            double dz = z1 - z2;
            return Math.sqrt((dx*dx) + (dy*dy) + (dz*dz));
        }
        else
            return Math.sqrt((dx*dx) + (dy*dy));
    }

    public static double calculateDistance(L2Object obj1, L2Object obj2, boolean includeZAxis)
    {
        if (obj1 == null || obj2 == null) return 1000000;
        return calculateDistance(obj1.getPosition().getX(), obj1.getPosition().getY(), obj1.getPosition().getZ(), obj2.getPosition().getX(), obj2.getPosition().getY(), obj2.getPosition().getZ(), includeZAxis);
    }

    /**
     * Capitalizes the first letter of a string, and returns the result.<BR>
     * (Based on ucfirst() function of PHP)
     *
     * @param String str
     * @return String containing the modified string.
     */
    public static String capitalizeFirst(String str)
    {
        str = str.trim();

        if (str.length() > 0 && Character.isLetter(str.charAt(0)))
            return str.substring(0, 1).toUpperCase() + str.substring(1);

        return str;
    }
    
    public static void printSection(String s)  
    {  
     int maxlength = 79;  
      s = "-[ " + s + " ]";  
     int slen = s.length();  
      if (slen > maxlength)  
    {  
      System.out.println(s);  
      return;  
    }  
     int i;  
      for (i = 0; i < (maxlength - slen); i++)  
      s = "=" + s;  
      System.out.println(s);  
   }

     /**
     * Capitalizes the first letter of every "word" in a string.<BR>
     * (Based on ucwords() function of PHP)
     *
     * @param String str
     * @return String containing the modified string.
     */
    public static String capitalizeWords(String str)
    {
        char[] charArray = str.toCharArray();
        String result = "";

        // Capitalize the first letter in the given string!
        charArray[0] = Character.toUpperCase(charArray[0]);

        for (int i = 0; i < charArray.length; i++)
        {
            if (Character.isWhitespace(charArray[i]))
                charArray[i + 1] = Character.toUpperCase(charArray[i + 1]);

            result += Character.toString(charArray[i]);
        }

        return result;
    }

    // Micht: Removed this because UNUSED
    /*
    public static boolean checkIfInRange(int range, int x1, int y1, int x2, int y2)
    {
        return checkIfInRange(range, x1, y1, 0, x2, y2, 0, false);
    }

    public static boolean checkIfInRange(int range, int x1, int y1, int z1, int x2, int y2, int z2, boolean includeZAxis)
    {

        if (includeZAxis)
        {
            return ((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2) + (z1 - z2)*(z1 - z2)) <= range * range;
        }
        else
        {
            return ((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2)) <= range * range;
        }
    }

    public static boolean checkIfInRange(int range, L2Object obj1, L2Object obj2, boolean includeZAxis)
    {
        if (obj1 == null || obj2 == null) return false;

        return checkIfInRange(range, obj1.getPosition().getX(), obj1.getPosition().getY(), obj1.getPosition().getZ(), obj2.getPosition().getX(), obj2.getPosition().getY(), obj2.getPosition().getZ(), includeZAxis);
    }
    */
    public static boolean checkIfInRange(int range, L2Object obj1, L2Object obj2, boolean includeZAxis)
    {
        if (obj1 == null || obj2 == null) return false;
        if (range == -1) return true; // not limited

        int rad = 0;
        if(obj1 instanceof L2Character)
        	rad += ((L2Character)obj1).getTemplate().collisionRadius;
        if(obj2 instanceof L2Character)
        	rad += ((L2Character)obj2).getTemplate().collisionRadius;

        double dx = obj1.getX() - obj2.getX();
        double dy = obj1.getY() - obj2.getY();

        if (includeZAxis)
        {
        	double dz = obj1.getZ() - obj2.getZ();
        	double d = dx*dx + dy*dy +dz*dz;

            return d <= range*range + 2*range*rad + rad*rad;
        }
        else
        {
        	double d = dx*dx + dy*dy;

            return d <= range*range + 2*range*rad +rad*rad;
        }
    }
    public static double convertHeadingToDegree(int heading)
    {
    	double degree = heading / 182.044444444;
		return degree;
    }

    public final static int calculateHeadingFrom(L2Object obj1, L2Object obj2)
	{
		return calculateHeadingFrom(obj1.getX(), obj1.getY(), obj2.getX(), obj2.getY());
	}
	
	public final static int calculateHeadingFrom(int obj1X, int obj1Y, int obj2X, int obj2Y)
	{
		double angleTarget = Math.toDegrees(Math.atan2(obj2Y - obj1Y, obj2X - obj1X));
		if (angleTarget < 0)
			angleTarget = 360 + angleTarget;
		return (int) (angleTarget * 182.044444444);
	}
	
	public final static int calculateHeadingFrom(double dx, double dy)
	{
		double angleTarget = Math.toDegrees(Math.atan2(dy, dx));
		if (angleTarget < 0)
			angleTarget = 360 + angleTarget;
		return (int) (angleTarget * 182.044444444);
	}
    
    /**
     * Returns the number of "words" in a given string.
     *
     * @param String str
     * @return int numWords
     */
    public static int countWords(String str)
    {
        return str.trim().split(" ").length;
    }

    /**
     * Returns a delimited string for an given array of string elements.<BR>
     * (Based on implode() in PHP)
     *
     * @param String[] strArray
     * @param String strDelim
     * @return String implodedString
     */
    public static String implodeString(String[] strArray, String strDelim)
    {
        String result = "";

        for (String strValue : strArray)
            result += strValue + strDelim;

        return result;
    }

    /**
     * Returns a delimited string for an given collection of string elements.<BR>
     * (Based on implode() in PHP)
     *
     * @param Collection&lt;String&gt; strCollection
     * @param String strDelim
     * @return String implodedString
     */
    public static String implodeString(Collection<String> strCollection, String strDelim)
    {
        return implodeString(strCollection.toArray(new String[strCollection.size()]), strDelim);
    }

    /**
     * Returns the rounded value of val to specified number of digits
     * after the decimal point.<BR>
     * (Based on round() in PHP)
     *
     * @param float val
     * @param int numPlaces
     * @return float roundedVal
     */
    public static float roundTo(float val, int numPlaces)
    {
        if (numPlaces <= 1)
            return Math.round(val);

        float exponent = (float) Math.pow(10, numPlaces);

        return (Math.round(val * exponent) / exponent);
    }

	public static boolean isAlphaNumeric(String text)
	{
		if (text == null) return false;
		boolean result = true;
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (!Character.isLetterOrDigit(chars[i]))
			{
				result = false;
				break;
			}
		}
		return result;
	}

	/**
     * Return amount of adena formatted with "," delimiter
     * @param amount
     * @return String formatted adena amount
     */
    public static String formatAdena(int amount) {
    	String s = "";
    	int rem = amount % 1000;
    	s = Integer.toString(rem);
    	amount = (amount - rem)/1000;
    	while (amount > 0) {
    		if (rem < 99)
    			s = '0' + s;
    		if (rem < 9)
    			s = '0' + s;
    		rem = amount % 1000;
        	s = Integer.toString(rem) + "," + s;
        	amount = (amount - rem)/1000;
    	}
    	return s;
    }
    
    /** 
     * returns how many processors are installed on this system. 
     */ 
    private static void printCpuInfo() 
    { 
    	  _log.info("Avaible CPU(s): " + Runtime.getRuntime().availableProcessors()); 
          _log.info("Processor(s) Identifier: " + System.getenv("PROCESSOR_IDENTIFIER")); 
          _log.info(".................................................."); 
          _log.info(".................................................."); 
    } 
    	 	 
    /** 
     * returns the operational system server is running on it. 
     */ 
    private static void printOSInfo() 
    { 
    	  _log.info("OS: " + System.getProperty("os.name") + " Build: " + System.getProperty("os.version")); 
    	  _log.info("OS Arch: " + System.getProperty("os.arch")); 
    	  _log.info(".................................................."); 
    	  _log.info(".................................................."); 
    } 
    	 	 
    /** 
     * returns JAVA Runtime Environment properties 
     */ 
    private static void printJreInfo() 
    { 
    	  _log.info("Java Platform Information"); 
    	  _log.info("Java Runtime  Name: " + System.getProperty("java.runtime.name")); 
    	  _log.info("Java Version: " + System.getProperty("java.version")); 
    	  _log.info("Java Class Version: " + System.getProperty("java.class.version")); 
    	  _log.info(".................................................."); 
          _log.info(".................................................."); 
    } 
    	 	 
    /** 
     * returns general info related to machine 
     */ 
    private static void printRuntimeInfo() 
    { 
       _log.info("Runtime Information"); 
       _log.info("Current Free Heap Size: " + (Runtime.getRuntime().freeMemory() / 1024 / 1024) + " mb"); 
       _log.info("Current Heap Size: " + (Runtime.getRuntime().totalMemory() / 1024 / 1024) + " mb"); 
       _log.info("Maximum Heap Size: " + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + " mb"); 
       _log.info(".................................................."); 
       _log.info(".................................................."); 
    	 	 
    } 
    	 	 
    /** 
     * calls time service to get system time. 
     */ 
    private static void printSystemTime() 
    { 
    	 // Instantiates Date Object 
    	 Date dateInfo = new Date(); 
    	 	 
    	 //generates a simple date format 
    	 SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa"); 
    	 	 
    	 //generates String that will get the formatter info with values 
    	 String dayInfo = df.format(dateInfo); 
    	 	 
    	 _log.info(".................................................."); 
    	_log.info("System Time: " + dayInfo); 
    	_log.info(".................................................."); 
    } 
    	 	 
    /** 
     * gets system JVM properties. 
     */ 
    private static void printJvmInfo() 
    { 
    	_log.info("Virtual Machine Information (JVM)"); 
    	_log.info("JVM Name: " + System.getProperty("java.vm.name")); 
    	_log.info("JVM installation directory: " + System.getProperty("java.home")); 
    	_log.info("JVM version: " + System.getProperty("java.vm.version")); 
    	_log.info("JVM Vendor: " + System.getProperty("java.vm.vendor")); 
    	_log.info("JVM Info: " + System.getProperty("java.vm.info")); 
    	_log.info(".................................................."); 
    	_log.info(".................................................."); 
    } 
    	 	 
    /** 
     * prints all other methods. 
     */ 
    public static void printGeneralSystemInfo() 
     { 
    	 printSystemTime(); 
    	 printOSInfo(); 
    	 printCpuInfo(); 
    	 printRuntimeInfo(); 
    	 printJreInfo(); 
    	 printJvmInfo(); 
     }
    
	public static String reverseColor(String color)
	{
		char[] ch1 = color.toCharArray();
		char[] ch2 = new char[6];
		ch2[0] = ch1[4];
		ch2[1] = ch1[5];
		ch2[2] = ch1[2];
		ch2[3] = ch1[3];
		ch2[4] = ch1[0];
		ch2[5] = ch1[1];

		return new String(ch2);
	}
	/**
	 * converts a given time from minutes -> milliseconds
	 * @param string
	 * @return
	 */
	public static int convertMinutesToMiliseconds(int minutesToConvert)
	{
		return minutesToConvert * 60000;
	}
}