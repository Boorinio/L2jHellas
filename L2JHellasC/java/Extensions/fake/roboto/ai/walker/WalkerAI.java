package Extensions.fake.roboto.ai.walker;

import java.util.LinkedList;
import java.util.Queue;

import Extensions.fake.roboto.FakePlayer;
import Extensions.fake.roboto.ai.FakePlayerAI;
import Extensions.fake.roboto.model.WalkNode;
import Extensions.fake.roboto.model.WalkerType;

import com.l2jhellas.util.Rnd;

public abstract class WalkerAI extends FakePlayerAI
{
	
	protected Queue<WalkNode> _walkNodes;
	private WalkNode _currentWalkNode;
	private int currentStayIterations = 0;
	protected boolean isWalking = false;
	
	public WalkerAI(FakePlayer character)
	{
		super(character);
	}
	
	public Queue<WalkNode> getWalkNodes()
	{
		return _walkNodes;
	}
	
	protected void addWalkNode(WalkNode walkNode)
	{
		_walkNodes.add(walkNode);
	}
	
	@Override
	public void setup()
	{
		super.setup();
		_walkNodes = new LinkedList<>();
		setWalkNodes();
	}
	
	@Override
	public void thinkAndAct()
	{
		setBusyThinking(true);
		handleDeath();
		
		if (_walkNodes.isEmpty())
			return;
		
		if (isWalking)
		{
			if (userReachedDestination(_currentWalkNode))
			{
				if (currentStayIterations < _currentWalkNode.getStayIterations())
				{
					currentStayIterations++;
					setBusyThinking(false);
					return;
				}
				_currentWalkNode = null;
				currentStayIterations = 0;
			}
		}
		else
		{
			switch (getWalkerType())
			{
				case RANDOM:
					_currentWalkNode = (WalkNode) getWalkNodes().toArray()[Rnd.get(0, getWalkNodes().size() - 1)];
					break;
				case LINEAR:
					_currentWalkNode = getWalkNodes().poll();
					_walkNodes.add(_currentWalkNode);
					break;
			}
			_fakePlayer.getFakeAi().moveTo(_currentWalkNode.getX(), _currentWalkNode.getY(), _currentWalkNode.getZ());
			isWalking = true;
		}
		
		setBusyThinking(false);
	}
	
	@Override
	protected int[][] getBuffs()
	{
		return new int[0][0];
	}
	
	protected boolean userReachedDestination(WalkNode targetWalkNode)
	{
		if (_fakePlayer.isInsideRadius(targetWalkNode.getX(), targetWalkNode.getY(), targetWalkNode.getZ(), 5, false, false))
		{
			isWalking = false;
			return true;
		}
		
		return false;
	}
	
	protected abstract WalkerType getWalkerType();
	
	protected abstract void setWalkNodes();
}
