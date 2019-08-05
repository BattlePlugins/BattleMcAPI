package mc.alk.nukkit;

import cn.nukkit.IPlayer;
import cn.nukkit.OfflinePlayer;
import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;

import mc.alk.mc.APIType;
import mc.alk.mc.MCLocation;
import mc.alk.mc.MCOfflinePlayer;
import mc.alk.mc.MCPlayer;
import mc.alk.mc.plugin.MCPlugin;
import mc.alk.mc.MCServer;
import mc.alk.mc.MCWorld;

import java.util.UUID;

public class NukkitServer extends MCServer {

	@Override
	public APIType getAPIType() {
		return APIType.NUKKIT;
	}

	@Override
	public MCLocation getMCLocation(String world, int x, int y, int z) {
		return new NukkitLocation(world, x, y, z);
	}

	@Override
	public MCWorld getMCWorld(String world) {
		return new NukkitWorld(Server.getInstance().getLevelByName(world));
	}

	@Override
	public long scheduleSyncTask(MCPlugin plugin, Runnable runnable, long millis) {
		return Server.getInstance().getScheduler().scheduleDelayedTask((Plugin) plugin, runnable, (int) millis/50).getTaskId();
	}

	@Override
	public MCPlayer getMCPlayer(String name) {
		return new NukkitPlayer(Server.getInstance().getPlayer(name));
	}

	@Override
	public MCOfflinePlayer getMCOfflinePlayer(String name) {
		IPlayer player = Server.getInstance().getOfflinePlayer(name);
		if (player instanceof OfflinePlayer)
			return new NukkitOfflinePlayer((OfflinePlayer) player);

		return null;
	}

	@Override
	public MCOfflinePlayer getMCOfflinePlayer(UUID uuid) {
		IPlayer player = Server.getInstance().getOfflinePlayer(uuid);
		if (player instanceof OfflinePlayer)
			return new NukkitOfflinePlayer((OfflinePlayer) player);

		return null;
	}

	@Override
	public boolean cancelMCTask(long id) {
		Server.getInstance().getScheduler().cancelTask((int) id);
		return true;
	}
}
