package org.battleplugins.api.sponge;

import mc.euro.version.Version;

import org.battleplugins.api.Platform;
import org.battleplugins.api.PlatformType;
import org.battleplugins.api.PlatformTypes;
import org.battleplugins.api.entity.living.player.OfflinePlayer;
import org.battleplugins.api.entity.living.player.Player;
import org.battleplugins.api.message.Message;
import org.battleplugins.api.plugin.Plugin;
import org.battleplugins.api.plugin.service.ServicePriority;
import org.battleplugins.api.sponge.entity.living.player.SpongeOfflinePlayer;
import org.battleplugins.api.sponge.entity.living.player.SpongePlayer;
import org.battleplugins.api.sponge.inventory.item.SpongeItemStack;
import org.battleplugins.api.sponge.message.SpongeMessage;
import org.battleplugins.api.sponge.world.SpongeWorld;
import org.battleplugins.api.world.World;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.ProviderRegistration;
import org.spongepowered.api.service.user.UserStorageService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SpongePlatform extends Platform {

    private SpongeRegistry registry;

    public SpongePlatform() {
        this.registry = new SpongeRegistry();
    }

    @Override
    public PlatformType getType() {
        return PlatformTypes.SPONGE;
    }

    @Override
    public Optional<World> getWorld(String world) {
        return Sponge.getServer().getWorld(world).map(SpongeWorld::new);
    }

    @Override
    public long scheduleSyncTask(Plugin plugin, Runnable runnable, long millis) {
        Sponge.getScheduler().createTaskBuilder().delay(millis, TimeUnit.MILLISECONDS).execute(runnable).submit(plugin.getPlatformPlugin());
        return Sponge.getScheduler().getScheduledTasks().size(); // Should work for now....
    }

    @Override
    public long scheduleRepeatingTask(Plugin plugin, Runnable runnable, long millis) {
        Sponge.getScheduler().createTaskBuilder().interval(millis, TimeUnit.MILLISECONDS).execute(runnable).submit(plugin.getPlatformPlugin());
        return Sponge.getScheduler().getScheduledTasks().size(); // Should work for now....
    }

    @Override
    public boolean cancelTask(long id) {
        return false; // No support for sponge task ids
    }

    @Override
    public Optional<Player> getPlayer(String name) {
        return Sponge.getServer().getPlayer(name).map(SpongePlayer::new);
    }

    @Override
    public Optional<Player> getPlayer(UUID uuid) {
        return Sponge.getServer().getPlayer(uuid).map(SpongePlayer::new);
    }

    @Override
    public Optional<OfflinePlayer> getOfflinePlayer(String name) {
        Optional<UserStorageService> userStorageService = Sponge.getServiceManager().provide(UserStorageService.class);
        if (!userStorageService.isPresent())
            return Optional.empty();

        return userStorageService.get().get(name).map(SpongeOfflinePlayer::new);
    }

    @Override
    public Optional<OfflinePlayer> getOfflinePlayer(UUID uuid) {
        Optional<UserStorageService> userStorageService = Sponge.getServiceManager().provide(UserStorageService.class);
        if (!userStorageService.isPresent())
            return Optional.empty();

        return userStorageService.get().get(uuid).map(SpongeOfflinePlayer::new);
    }

    @Override
    public Collection<Player> getOnlinePlayers() {
        List<Player> playerList = new ArrayList<>();
        for (org.spongepowered.api.entity.living.player.Player player : Sponge.getServer().getOnlinePlayers()) {
            playerList.add(new SpongePlayer(player));
        }

        return playerList;
    }

    @Override
    public Collection<OfflinePlayer> getOfflinePlayers() {
        Collection<OfflinePlayer> players = new ArrayList<>();
        // TODO: Find a way to do this
        return players;
    }

    @Override
    public boolean isMainThread() {
        return Sponge.getServer().isMainThread();
    }

    @Override
    public boolean isOnlineMode() {
        return Sponge.getServer().getOnlineMode();
    }

    @Override
    public Version<Platform> getVersion() {
        return new Version<>(Sponge.getGame().getPlatform().getMinecraftVersion().getName());
    }

    @Override
    public Message getDefaultPlatformMessage() {
        return new SpongeMessage();
    }

    @Override
    public SpongeItemStack getDefaultPlatformItemStack() {
        return new SpongeItemStack(ItemStack.of(ItemTypes.AIR));
    }

    @Override
    public <T> void registerService(Class<T> clazz, T service, Plugin plugin, ServicePriority priority) {
        Sponge.getServiceManager().setProvider(plugin.getPlatformPlugin(), clazz, service);
    }

    @Override
    public <T> Optional<T> getService(Class<T> clazz) {
        return Sponge.getServiceManager().getRegistration(clazz).map(ProviderRegistration::getProvider);
    }

    @Override
    public SpongeRegistry getRegistry() {
        return registry;
    }
}
