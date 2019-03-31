package Extensions.fake.roboto.task;

import Extensions.fake.roboto.FakePlayerTaskManager;

import com.l2jhellas.gameserver.ThreadPoolManager;

import java.util.List;

public class AITaskRunner implements Runnable
{
	@Override
	public void run()
	{
		FakePlayerTaskManager.INSTANCE.adjustTaskSize();
		List<AITask> aiTasks = FakePlayerTaskManager.INSTANCE.getAITasks();
		aiTasks.forEach(aiTask -> ThreadPoolManager.getInstance().executeAi(aiTask));
	}
}