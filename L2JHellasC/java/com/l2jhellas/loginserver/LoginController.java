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
package com.l2jhellas.loginserver;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAKeyGenParameterSpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.crypto.Cipher;

import javolution.util.FastCollection.Record;
import javolution.util.FastSet;

import com.l2jhellas.Config;
import com.l2jhellas.loginserver.GameServerTable.GameServerInfo;
import com.l2jhellas.loginserver.gameserverpackets.ServerStatus;
import com.l2jhellas.loginserver.serverpackets.LoginFail.LoginFailReason;
import com.l2jhellas.logs.LogRecorder;
import com.l2jhellas.util.Rnd;
import com.l2jhellas.util.crypt.ScrambledKeyPair;
import com.l2jhellas.util.database.L2DatabaseFactory;

public class LoginController
{
	protected static final Logger _log = Logger.getLogger(LoginController.class.getName());

	private static final String UPDATE_ACC_LS = "UPDATE accounts SET lastServer=? WHERE login=?";
	private static final String UPDATE_ACC_AL = "UPDATE accounts SET access_level=? WHERE login=?";
	private static final String SELECT_ACC_AL = "SELECT access_level FROM accounts WHERE login=?";
	private static final String SELECT_ACCOUNT = "SELECT password, access_level, lastServer FROM accounts WHERE login=?";
	private static final String INSERT_ACCOUNT = "INSERT INTO accounts (login,password,lastactive,access_level,lastIP) values(?,?,?,?,?)";
	private static final String UPDATE_ACTIVITY = "UPDATE accounts SET lastactive=?, lastIP=? WHERE login=?";
	private static final String SELECT_ACCESS = "SELECT access_level FROM accounts WHERE login=?";
	
	private static LoginController _instance;

	/** Time before kicking the client if he didnt logged yet */
	private final static int LOGIN_TIMEOUT = 60 * 1000;

	/** Clients that are on the LS but arent assocated with a account yet */
	protected FastSet<L2LoginClient> _clients = new FastSet<L2LoginClient>();

	/** Authed Clients on LoginServer */
	protected Map<String, L2LoginClient> _loginServerClients = new ConcurrentHashMap<>();

	private final Map<InetAddress, BanInfo> _bannedIps = new ConcurrentHashMap<InetAddress, BanInfo>();

	private final Map<InetAddress, FailedLoginAttempt> _hackProtection;

	protected ScrambledKeyPair[] _keyPairs;

	protected byte[][] _blowfishKeys;
	private static final int BLOWFISH_KEYS = 20;

	public static void load() throws GeneralSecurityException
	{
		if (_instance == null)
		{
			_instance = new LoginController();
		}
		else
		{
			throw new IllegalStateException("LoginController can only be loaded a single time.");
		}
	}

	public static LoginController getInstance()
	{
		return _instance;
	}

	private LoginController() throws GeneralSecurityException
	{
		_log.info("Loading LoginContoller...");

		_hackProtection = new HashMap<InetAddress, FailedLoginAttempt>();

		_keyPairs = new ScrambledKeyPair[10];

		KeyPairGenerator keygen = null;

		keygen = KeyPairGenerator.getInstance("RSA");
		RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4);
		keygen.initialize(spec);

		// generate the initial set of keys
		for (int i = 0; i < 10; i++)
		{
			_keyPairs[i] = new ScrambledKeyPair(keygen.generateKeyPair());
		}
		_log.info("Cached 10 KeyPairs for RSA communication");

		testCipher((RSAPrivateKey) _keyPairs[0]._pair.getPrivate());

		// Store keys for blowfish communication
		generateBlowFishKeys();
	}

	/**
	 * This is mostly to force the initialization of the Crypto Implementation, avoiding it being done on runtime when its first needed.<BR>
	 * In short it avoids the worst-case execution time on runtime by doing it on loading.
	 * 
	 * @param key
	 *        Any private RSA Key just for testing purposes.
	 * @throws GeneralSecurityException
	 *         if a underlying exception was thrown by the Cipher
	 */
	private void testCipher(RSAPrivateKey key) throws GeneralSecurityException
	{
		// avoid worst-case execution, KenM
		Cipher rsaCipher = Cipher.getInstance("RSA/ECB/nopadding");
		rsaCipher.init(Cipher.DECRYPT_MODE, key);
	}

	private void generateBlowFishKeys()
	{
		_blowfishKeys = new byte[BLOWFISH_KEYS][16];

		for (int i = 0; i < BLOWFISH_KEYS; i++)
		{
			for (int j = 0; j < _blowfishKeys[i].length; j++)
			{
				_blowfishKeys[i][j] = (byte) (Rnd.nextInt(255) + 1);
			}
		}
		_log.info("Stored " + _blowfishKeys.length + " keys for Blowfish communication.");
	}

	/**
	 * @return Returns a random key
	 */
	public byte[] getBlowfishKey()
	{
		return _blowfishKeys[(int) (Math.random() * BLOWFISH_KEYS)];
	}

	public void addLoginClient(L2LoginClient client)
	{
		synchronized (_clients)
		{
			_clients.add(client);
		}
	}

	public void removeLoginClient(L2LoginClient client)
	{
		synchronized (_clients)
		{
			_clients.remove(client);
		}
	}

	public SessionKey assignSessionKeyToClient(String account, L2LoginClient client)
	{
		SessionKey key;

		key = new SessionKey(Rnd.nextInt(), Rnd.nextInt(), Rnd.nextInt(), Rnd.nextInt());
		_loginServerClients.put(account, client);
		return key;
	}

	public void removeAuthedLoginClient(String account)
	{
		_loginServerClients.remove(account);
	}

	public boolean isAccountInLoginServer(String account)
	{
		return _loginServerClients.containsKey(account);
	}

	public L2LoginClient getAuthedClient(String account)
	{
		return _loginServerClients.get(account);
	}

	public static enum AuthLoginResult
	{
		INVALID_PASSWORD, ACCOUNT_BANNED, ALREADY_ON_LS, ALREADY_ON_GS, AUTH_SUCCESS
	};

	public AuthLoginResult tryAuthLogin(String account, String password, L2LoginClient client) throws HackingException, NoSuchAlgorithmException
	{
		AuthLoginResult ret = AuthLoginResult.INVALID_PASSWORD;
		if (loginValid(account, password, client))
		{
			// login was successful, verify presence on Gameservers
			ret = AuthLoginResult.ALREADY_ON_GS;
			if (!isAccountInAnyGameServer(account))
			{
				// account isnt on any GS verify LS itself
				ret = AuthLoginResult.ALREADY_ON_LS;

				// dont allow 2 simultaneous login
				synchronized (_loginServerClients)
				{
					if (!_loginServerClients.containsKey(account))
					{
						_loginServerClients.put(account, client);
						ret = AuthLoginResult.AUTH_SUCCESS;

						// remove him from the non-authed list
						removeLoginClient(client);
					}
				}
			}
		}
		else
		{
			if (client.getAccessLevel() < 0)
			{
				ret = AuthLoginResult.ACCOUNT_BANNED;
			}
		}
		return ret;
	}

	/**
	 * Adds the address to the ban list of the login server, with the given duration.
	 * 
	 * @param address
	 *        The Address to be banned.
	 * @param expiration
	 *        Timestamp in milliseconds when this ban expires
	 * @throws UnknownHostException
	 *         if the address is invalid.
	 */
	public void addBanForAddress(String address, long expiration) throws UnknownHostException
	{
		InetAddress netAddress = InetAddress.getByName(address);
		_bannedIps.put(netAddress, new BanInfo(netAddress, expiration));
	}

	/**
	 * Adds the address to the ban list of the login server, with the given duration.
	 * 
	 * @param address
	 *        The Address to be banned.
	 * @param duration
	 *        is milliseconds
	 */
	public void addBanForAddress(InetAddress address, long duration)
	{
		_bannedIps.put(address, new BanInfo(address, System.currentTimeMillis() + duration));
	}

	public boolean isBannedAddress(InetAddress address)
	{
		BanInfo bi = _bannedIps.get(address);
		if (bi != null)
		{
			if (bi.hasExpired())
			{
				_bannedIps.remove(address);
				return false;
			}
			else
			{
				return true;
			}
		}
		return false;
	}

	public Map<InetAddress, BanInfo> getBannedIps()
	{
		return _bannedIps;
	}

	/**
	 * Remove the specified address from the ban list
	 * 
	 * @param address
	 *        The address to be removed from the ban list
	 * @return true if the ban was removed, false if there was no ban for this ip
	 */
	public boolean removeBanForAddress(InetAddress address)
	{
		return _bannedIps.remove(address) != null;
	}

	/**
	 * Remove the specified address from the ban list
	 * 
	 * @param address
	 *        The address to be removed from the ban list
	 * @return true if the ban was removed, false if there was no ban for this ip or the address was invalid.
	 */
	public boolean removeBanForAddress(String address)
	{
		try
		{
			return this.removeBanForAddress(InetAddress.getByName(address));
		}
		catch (UnknownHostException e)
		{
			return false;
		}
	}

	public SessionKey getKeyForAccount(String account)
	{
		L2LoginClient client = _loginServerClients.get(account);
		if (client != null)
		{
			return client.getSessionKey();
		}
		return null;
	}

	public int getOnlinePlayerCount(int serverId)
	{
		GameServerInfo gsi = GameServerTable.getInstance().getRegisteredGameServerById(serverId);
		if (gsi != null && gsi.isAuthed())
		{
			return gsi.getCurrentPlayerCount();
		}
		return 0;
	}

	public boolean isAccountInAnyGameServer(String account)
	{
		Collection<GameServerInfo> serverList = GameServerTable.getInstance().getRegisteredGameServers().values();
		for (GameServerInfo gsi : serverList)
		{
			GameServerThread gst = gsi.getGameServerThread();
			if (gst != null && gst.hasAccountOnGameServer(account))
			{
				return true;
			}
		}
		return false;
	}

	public GameServerInfo getAccountOnGameServer(String account)
	{
		Collection<GameServerInfo> serverList = GameServerTable.getInstance().getRegisteredGameServers().values();
		for (GameServerInfo gsi : serverList)
		{
			GameServerThread gst = gsi.getGameServerThread();
			if (gst != null && gst.hasAccountOnGameServer(account))
			{
				return gsi;
			}
		}
		return null;
	}

	public int getTotalOnlinePlayerCount()
	{
		int total = 0;
		Collection<GameServerInfo> serverList = GameServerTable.getInstance().getRegisteredGameServers().values();
		for (GameServerInfo gsi : serverList)
		{
			if (gsi.isAuthed())
			{
				total += gsi.getCurrentPlayerCount();
			}
		}
		return total;
	}

	public int getMaxAllowedOnlinePlayers(int id)
	{
		GameServerInfo gsi = GameServerTable.getInstance().getRegisteredGameServerById(id);
		if (gsi != null)
		{
			return gsi.getMaxPlayers();
		}
		return 0;
	}

	/**
	 * @return
	 */
	public boolean isLoginPossible(L2LoginClient client, int serverId)
	{
		GameServerInfo gsi = GameServerTable.getInstance().getRegisteredGameServerById(serverId);
		int access = client.getAccessLevel();
		if (gsi != null && gsi.isAuthed())
		{
			boolean loginOk = (gsi.getCurrentPlayerCount() < gsi.getMaxPlayers() && gsi.getStatus() != ServerStatus.STATUS_GM_ONLY) || access >= 0;

			if (loginOk && client.getLastServer() != serverId)
			{
				try (Connection con = L2DatabaseFactory.getInstance().getConnection())
				{
					PreparedStatement statement = con.prepareStatement(UPDATE_ACC_LS);
					statement.setInt(1, serverId);
					statement.setString(2, client.getAccount());
					statement.executeUpdate();
					statement.close();
				}
				catch (SQLException e)
				{
					_log.warning(LoginController.class.getName() + ": WARNING: Could not set lastServer: ");
					if (Config.DEVELOPER)
						e.printStackTrace();
				}
			}
			return loginOk;
		}
		return false;
	}

	public void setAccountAccessLevel(String account, int banLevel)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement(UPDATE_ACC_AL);
			statement.setInt(1, banLevel);
			statement.setString(2, account);
			statement.executeUpdate();
			statement.close();
		}
		catch (SQLException e)
		{
			_log.warning(LoginController.class.getName() + ": WARNING: Could not set accessLevel: ");
			if (Config.DEVELOPER)
				e.printStackTrace();
		}
	}

	public static boolean isGM(String user)
	{
		boolean ok = false;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement(SELECT_ACC_AL);
			statement.setString(1, user);
			ResultSet rset = statement.executeQuery();
			if (rset.next())
			{
				int accessLevel = rset.getInt(1);
				if (accessLevel > 0)
				{
					ok = true;
				}
			}
			rset.close();
			statement.close();
		}
		catch (SQLException e)
		{
			_log.warning(LoginController.class.getSimpleName() + ":   Could not check GM state: ");
			if (Config.DEVELOPER)
				e.printStackTrace();
			ok = false;
		}
		return ok;
	}

	/**
	 * <p>
	 * This method returns one of the cached {@link ScrambledKeyPair ScrambledKeyPairs} for communication with Login Clients.
	 * </p>
	 * 
	 * @return a scrambled keypair
	 */
	public ScrambledKeyPair getScrambledRSAKeyPair()
	{
		return _keyPairs[Rnd.nextInt(10)];
	}

	/**
	 * user name is not case sensitive any more
	 * 
	 * @param user
	 * @param password
	 * @param address
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	
	public boolean loginValid(String user, String password, L2LoginClient client)
	{
		boolean ok = false;
		InetAddress address = client.getConnection().getInetAddress();
		// log it anyway
		LogRecorder.add("'" + (user == null ? "null" : user) + "' " + (address == null ? "null" : address.getHostAddress()), "logins_ip");

		// player disconnected meanwhile
		if (address == null)
		{
			return false;
		}

		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			MessageDigest md = null;
			try
			{
				md = MessageDigest.getInstance("SHA");
			}
			catch (NoSuchAlgorithmException e1)
			{
				if (Config.DEVELOPER)
					e1.printStackTrace();
			}
			byte[] raw = null;
			try
			{
				raw = password.getBytes("UTF-8");
			}
			catch (UnsupportedEncodingException e)
			{
				if (Config.DEVELOPER)
					e.printStackTrace();
			}
			byte[] hash = md.digest(raw);

			byte[] expected = null;
			int access = 0;
			int lastServer = 1;

			PreparedStatement statement = con.prepareStatement(SELECT_ACCOUNT);
			statement.setString(1, user);
			ResultSet rset = statement.executeQuery();
			if (rset.next())
			{
				expected = Base64.getDecoder().decode(rset.getString("password"));
				access = rset.getInt("access_level");
				lastServer = rset.getInt("lastServer");
				if (lastServer <= 0)
					lastServer = 1; // minServerId is 1 in Interlude
				if (Config.DEBUG)
					_log.fine("account exists");
			}
			rset.close();
			statement.close();

			// if account doesn't exists
			if (expected == null)
			{
				if (Config.AUTO_CREATE_ACCOUNTS)
				{
					if ((user.length() >= 2) && (user.length() <= 14))
					{
						statement = con.prepareStatement(INSERT_ACCOUNT);
						statement.setString(1, user);
						statement.setString(2, Base64.getEncoder().encodeToString(hash));
						statement.setLong(3, System.currentTimeMillis());
						statement.setInt(4, 0);
						statement.setString(5, address.getHostAddress());
						statement.execute();
						statement.close();

						_log.info("Created new account for " + user);
						return true;

					}
					_log.warning(LoginController.class.getName() + ": Invalid username creation/use attempt: " + user);
					return false;
				}
				_log.warning(LoginController.class.getName() + ": WARNING: Account missing for user " + user);
				return false;
			}
			else
			{
				// is this account banned?
				if (access < 0)
				{
					client.setAccessLevel(access);
					return false;
				}

				// check password hash
				ok = true;
				for (int i = 0; i < expected.length; i++)
				{
					if (hash[i] != expected[i])
					{
						ok = false;
						break;
					}
				}
			}

			if (ok)
			{
				client.setAccessLevel(access);
				client.setLastServer(lastServer);
				PreparedStatement statement2 = con.prepareStatement(UPDATE_ACTIVITY);
				statement2.setLong(1, System.currentTimeMillis());
				statement2.setString(2, address.getHostAddress());
				statement2.setString(3, user);
				statement2.execute();
				statement2.close();
			}
		}
		catch (SQLException e)
		{
			_log.warning(LoginController.class.getName() + ": WARNING: Could not check password: ");
			if (Config.DEVELOPER)
				e.printStackTrace();
			ok = false;
		}

		if (!ok)
		{
			LogRecorder.add("'" + user + "' " + address.getHostAddress(), "logins_ip_fails");

			FailedLoginAttempt failedAttempt = _hackProtection.get(address);
			int failedCount;
			if (failedAttempt == null)
			{
				_hackProtection.put(address, new FailedLoginAttempt(address, password));
				failedCount = 1;
			}
			else
			{
				failedAttempt.increaseCounter(password);
				failedCount = failedAttempt.getCount();
			}

			if (failedCount >= Config.LOGIN_TRY_BEFORE_BAN)
			{
				_log.info("Banning '" + address.getHostAddress() + "' for " + Config.LOGIN_BLOCK_AFTER_BAN + " seconds due to " + failedCount + " invalid user/pass attempts");
				this.addBanForAddress(address, Config.LOGIN_BLOCK_AFTER_BAN * 1000);
			}
		}
		else
		{
			_hackProtection.remove(address);
			LogRecorder.add("'" + user + "' " + address.getHostAddress(), "logins_ip");
		}

		return ok;
	}

	public boolean loginBanned(String user)
	{
		boolean ok = false;

		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement(SELECT_ACCESS);
			statement.setString(1, user);
			ResultSet rset = statement.executeQuery();
			if (rset.next())
			{
				int accessLevel = rset.getInt(1);
				if (accessLevel < 0)
					ok = true;
			}
			rset.close();
			statement.close();
		}
		catch (SQLException e)
		{
			_log.warning(LoginController.class.getName() + ": WARNING: Could not check ban state: ");
			if (Config.DEVELOPER)
				e.printStackTrace();
			ok = false;
		}

		return ok;
	}

	class FailedLoginAttempt
	{
		// private InetAddress _ipAddress;
		private int _count;
		private long _lastAttempTime;
		private String _lastPassword;

		public FailedLoginAttempt(InetAddress address, String lastPassword)
		{
			// _ipAddress = address;
			_count = 1;
			_lastAttempTime = System.currentTimeMillis();
			_lastPassword = lastPassword;
		}

		public void increaseCounter(String password)
		{
			if (!_lastPassword.equals(password))
			{
				// check if theres a long time since last wrong try
				if (System.currentTimeMillis() - _lastAttempTime < 300 * 1000)
				{
					_count++;
				}
				else
				{
					// restart the status
					_count = 1;

				}
				_lastPassword = password;
				_lastAttempTime = System.currentTimeMillis();
			}
			else
			// trying the same password is not brute force
			{
				_lastAttempTime = System.currentTimeMillis();
			}
		}

		public int getCount()
		{
			return _count;
		}
	}

	class BanInfo
	{
		private final InetAddress _ipAddress;
		// Expiration
		private final long _expiration;

		public BanInfo(InetAddress ipAddress, long expiration)
		{
			_ipAddress = ipAddress;
			_expiration = expiration;
		}

		public InetAddress getAddress()
		{
			return _ipAddress;
		}

		public boolean hasExpired()
		{
			return System.currentTimeMillis() > _expiration && _expiration > 0;
		}
	}

	class PurgeThread extends Thread
	{
		@Override
		public void run()
		{
			for (;;)
			{
				synchronized (_clients)
				{
					for (Record e = _clients.head(), end = _clients.tail(); (e = e.getNext()) != end;)
					{
						L2LoginClient client = _clients.valueOf(e);
						
						if ((client.getConnectionStartTime() + LOGIN_TIMEOUT) < System.currentTimeMillis())
							client.close(LoginFailReason.REASON_ACCESS_FAILED);
					}
				}

				for (L2LoginClient client : _loginServerClients.values())
				{
					if (client == null)
						continue;
					
					if ((client.getConnectionStartTime() + LOGIN_TIMEOUT) < System.currentTimeMillis())
						client.close(LoginFailReason.REASON_ACCESS_FAILED);
				}

				try
				{
					wait(2 * LOGIN_TIMEOUT);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}