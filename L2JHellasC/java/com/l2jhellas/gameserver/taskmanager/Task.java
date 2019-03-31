package com.l2jhellas.gameserver.taskmanager;

import com.l2jhellas.gameserver.taskmanager.TaskManager.ExecutedTask;

import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

public abstract class Task
{
	private static Logger _log = Logger.getLogger(Task.class.getName());
	
	public void initializate()
	{
		_log.info("Task " + getName() + " initialized.");
	}
	
	public ScheduledFuture<?> launchSpecial(ExecutedTask instance)
	{
		return null;
	}
	
	public abstract String getName();
	
	public abstract void onTimeElapsed(ExecutedTask task);
	
	public void onDestroy()
	{
	}
}