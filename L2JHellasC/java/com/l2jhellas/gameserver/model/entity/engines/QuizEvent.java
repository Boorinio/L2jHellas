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
package com.l2jhellas.gameserver.model.entity.engines;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.Announcements;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.util.database.L2DatabaseFactory;

/**
 * @author Pauler
 */
public class QuizEvent
{
	/** Milliseconds until event starts again **/
	private static int _delay = Config.QUIZ_MINUTES_UNTIL_EVENT_STARTS_AGAIN * 60 * 1000;

	/** Milliseconds until event first start **/
	private static int _initial = 30000;

	/** Milliseconds to answer the question **/
	private static int _questionDuration = Config.QUIZ_MINUTES_TO_ANSWER * 60 * 1000;

	private static int _rewardId = Config.QUIZ_REWARD_ID;
	private static int _rewardQuantity = Config.QUIZ_REWARD_QUANTITY;

	private static int _lastQuestionId = 0;
	private static int _questions = 0;

	private static String _answer1 = "";
	private static String _answer2 = "";
	private static String _answer3 = "";

	private static String _rightAnswer = "";

	private static boolean _isRunning = false;

	private static boolean _isRecievingAnswers = true;
	private static boolean _isStoppedForever = false;

	private static boolean _hasAlreadyStartedOnce = false;

	private static L2PcInstance _winner = null;

	public static void getInstance()
	{
		System.out.println("Quiz Event has started.");
		ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new Runnable()
		{
			@Override
			public void run()
			{
				if (_lastQuestionId >= _questions && _hasAlreadyStartedOnce)
					_isStoppedForever = true;

				if (!_isStoppedForever)
					startEvent();
			}
		}, _initial, _delay);
	}

	private static void startEvent()
	{
		_isRunning = true;

		if (!_hasAlreadyStartedOnce)
			_hasAlreadyStartedOnce = true;

		Announcements.getInstance().gameAnnounceToAll("Quiz event just started.");
		askQuestion();
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				if (!_isStoppedForever)
					endEvent();
			}
		}, _questionDuration);
	}

	private static void endEvent()
	{
		_isRunning = false;

		if (_winner != null)
		{
			rewardWinner();
			Announcements.getInstance().gameAnnounceToAll(_winner.getName() + " Is The Winner of the Quiz Event.");
		}

		Announcements.getInstance().gameAnnounceToAll("Quiz Event just ended.");
		clearEverything();
	}

	private static void clearEverything()
	{
		_winner = null;

		_answer1 = "";
		_answer2 = "";
		_answer3 = "";
		_rightAnswer = "";

		_isRecievingAnswers = true;
	}

	private static void setWinner(L2PcInstance activeChar)
	{
		if (_isRecievingAnswers)
		{
			_winner = activeChar;
			_isRecievingAnswers = false;
		}
	}

	private static void rewardWinner()
	{
		_winner.sendMessage("You are the winner! Here's your reward.");
		_winner.addItem("Reward", _rewardId, _rewardQuantity, _winner, true);
	}

	public static void checkAnswer(String answer, L2PcInstance player)
	{
		if (answer.equalsIgnoreCase(_rightAnswer))
		{
			setWinner(player);
		}
	}

	private static void askQuestion()
	{
		String question = "";
		if (_questions == 0)
		{
			try (Connection con = L2DatabaseFactory.getInstance().getConnection())
			{
				PreparedStatement statement = con.prepareStatement("SELECT * FROM questions");
				ResultSet result = statement.executeQuery();
				while (result.next())
				{
					_questions++;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT * FROM questions WHERE `id`=? ORDER BY id DESC");
			_lastQuestionId++;
			statement.setInt(1, _lastQuestionId);
			ResultSet result = statement.executeQuery();
			while (result.next())
			{
				question = result.getString("question");

				_answer1 = result.getString("answer1");
				_answer2 = result.getString("answer2");
				_answer3 = result.getString("answer3");

				_rightAnswer = result.getString("right_answer");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		Announcements.getInstance().gameAnnounceToAll("Type .quiz and then type your answer! The first one who will answer correctly wins.");
		Announcements.getInstance().gameAnnounceToAll("Question: " + question);

		if ((_answer1 != null) && !_answer1.equalsIgnoreCase(""))
			Announcements.getInstance().gameAnnounceToAll("1. " + _answer1);
		if ((_answer2 != null) && !_answer2.equalsIgnoreCase(""))
			Announcements.getInstance().gameAnnounceToAll("2. " + _answer2);
		if ((_answer3 != null) && !_answer3.equalsIgnoreCase(""))
			Announcements.getInstance().gameAnnounceToAll("3. " + _answer3);
	}

	public static L2PcInstance getWinner()
	{
		return _winner;
	}

	public static boolean isRunning()
	{
		return _isRunning;
	}

	public static boolean isRecievingAnswers()
	{
		return _isRecievingAnswers;
	}
}