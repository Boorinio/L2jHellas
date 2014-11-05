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
package com.l2jhellas.gameserver.model.actor.knownlist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javolution.util.FastMap;

import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.L2Playable;
import com.l2jhellas.util.Util;

public class ObjectKnownList
{
	protected final L2Object _activeObject;
	protected Map<Integer, L2Object> _knownObjects;

	public ObjectKnownList(L2Object activeObject)
	{
		_activeObject = activeObject;
	}

	public boolean addKnownObject(L2Object object)
	{
		return addKnownObject(object, null);
	}

	public boolean addKnownObject(L2Object object, L2Character dropper)
	{
		if ((object == null) || (object == getActiveObject()))
			return false;

		// Check if already know object
		if (knowsObject(object))
		{

			if (!object.isVisible())
				removeKnownObject(object);
			return false;
		}

		// Check if object is not inside distance to watch object
		if (!Util.checkIfInRange(getDistanceToWatchObject(object), getActiveObject(), object, true))
			return false;

		return (getKnownObjects().put(object.getObjectId(), object) == null);
	}

	public final boolean knowsObject(L2Object object)
	{
		return getActiveObject() == object || getKnownObjects().containsKey(object.getObjectId());
	}

	/** Remove all L2Object from _knownObjects */
	public void removeAllKnownObjects()
	{
		getKnownObjects().clear();
	}

	public boolean removeKnownObject(L2Object object)
	{
		if (object == null)
			return false;
		return (getKnownObjects().remove(object.getObjectId()) != null);
	}

	/**
	 * Update the _knownObject and _knowPlayers of the L2Character and of its already known L2Object.<BR>
	 * <BR>
	 * <B><U> Actions</U> :</B><BR>
	 * <BR>
	 * <li>Remove invisible and too far L2Object from _knowObject and if necessary from _knownPlayers of the L2Character</li> <li>Add visible L2Object near the L2Character to
	 * _knowObject and if necessary to _knownPlayers of the L2Character</li> <li>Add L2Character to _knowObject and if necessary to _knownPlayers of L2Object alreday known by the
	 * L2Character</li><BR>
	 * <BR>
	 */
	public final synchronized void updateKnownObjects()
	{
		// Only bother updating known objects for L2Character; don't for L2Object
		if (getActiveObject() instanceof L2Character)
		{
			findCloseObjects();
			forgetObjects();
		}
	}

	private final void findCloseObjects()
	{
		boolean isActiveObjectPlayable = (getActiveObject() instanceof L2Playable);

		if (isActiveObjectPlayable)
		{
			Collection<L2Object> objects = L2World.getVisibleObjects(getActiveObject());
			if (objects == null)
				return;

			// Go through all visible L2Object near the L2Character
			for (L2Object object : objects)
			{
				if (object == null)
					continue;

				// Try to add object to active object's known objects
				// L2PlayableInstance sees everything
				addKnownObject(object);

				// Try to add active object to object's known objects
				// Only if object is a L2Character and active object is a L2PlayableInstance
				if (object instanceof L2Character)
					object.getKnownList().addKnownObject(getActiveObject());
			}
		}
		else
		{
			Collection<L2Playable> playables = L2World.getVisiblePlayable(getActiveObject());
			if (playables == null)
				return;

			// Go through all visible L2Object near the L2Character
			for (L2Object playable : playables)
			{
				if (playable == null)
					continue;

				// Try to add object to active object's known objects
				// L2Character only needs to see visible L2PcInstance and L2PlayableInstance,
				// when moving. Other l2characters are currently only known from initial spawn area.
				// Possibly look into getDistanceToForgetObject values before modifying this approach...
				addKnownObject(playable);
			}
		}
	}

	private final void forgetObjects()
	{
		// Go through knownObjects
		final Collection<L2Object> knownObjects = getKnownObjects().values();

		if ((knownObjects == null) || (knownObjects.size() == 0))
			return;

		for (L2Object object : knownObjects)
		{
			if (object == null)
				continue;
			
			if (!object.isVisible() || !Util.checkIfInRange(getDistanceToForgetObject(object), _activeObject, object, true))
				removeKnownObject(object);
		}
	}

	public L2Object getActiveObject()
	{
		return _activeObject;
	}

	public int getDistanceToForgetObject(L2Object object)
	{
		return 0;
	}

	public int getDistanceToWatchObject(L2Object object)
	{
		return 0;
	}

	/** Return the _knownObjects containing all L2Object known by the L2Character. */
	public final Map<Integer, L2Object> getKnownObjects()
	{
		if (_knownObjects == null)
			_knownObjects = new FastMap<Integer, L2Object>().setShared(true);
		return _knownObjects;
	}

	@SuppressWarnings("unchecked")
	public final <A> Collection<A> getKnownTypeInRadius(Class<A> type, int radius)
	{
		List<A> result = new ArrayList<>();
		
		for (L2Object obj : _knownObjects.values())
		{
			if (type.isAssignableFrom(obj.getClass()) && Util.checkIfInRange(radius, getActiveObject(), obj, true))
				result.add((A) obj);
		}
		return result;
	}
	
	public static class KnownListAsynchronousUpdateTask implements Runnable
	{
		private final L2Object _obj;

		public KnownListAsynchronousUpdateTask(L2Object obj)
		{
			_obj = obj;
		}

		@Override
		public void run()
		{
			if (_obj != null)
				_obj.getKnownList().updateKnownObjects();
		}
	}
}