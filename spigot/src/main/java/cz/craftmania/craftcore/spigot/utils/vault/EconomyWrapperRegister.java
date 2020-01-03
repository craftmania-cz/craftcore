package cz.craftmania.craftcore.spigot.utils.vault;

import cz.craftmania.craftcore.spigot.Main;
import cz.craftmania.craftcore.spigot.events.vault.EconomyEventsAvailableEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

import java.util.Optional;

public class EconomyWrapperRegister {

    public void register() {
        Optional<Economy> economy = getEconomy();
        if (economy.isPresent()) {
            this.registerWrapper(economy.get());
        } else {
            this.tryAgainOnStart();
        }
    }

    private void tryAgainOnStart() {
        Main.getInstance().getServer().getScheduler().runTaskLater(Main.getInstance(),
                () -> getEconomy().ifPresent(this::registerWrapper),
                0); // Run when server has done loading
    }

    private Optional<Economy> getEconomy() {
        ServicesManager services = Main.getInstance().getServer().getServicesManager();
        RegisteredServiceProvider<Economy> economyService = services.getRegistration(Economy.class);
        if (economyService == null) return Optional.empty();
        Economy economy = economyService.getProvider();
        return Optional.ofNullable(economy);
    }

    private void registerWrapper(Economy original) {
        // Don't wrap Economy twice in case of reloads.
        Economy wrappedEco = original instanceof EconomyWrapper ? original : new EconomyWrapper(original);
        Main.getInstance().getServer().getServicesManager().register(Economy.class, wrappedEco, Main.getInstance(), ServicePriority.Highest);
        Main.getCoreLogger().info("Vault Eventy registrovany.");
        Bukkit.getPluginManager().callEvent(new EconomyEventsAvailableEvent(wrappedEco));
    }
}
