package cz.wake.craftcore.services.prometheus;

import cz.wake.craftcore.Main;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.common.TextFormat;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MetricsController extends AbstractHandler {

    private final Main plugin;

    //TODO: ID SERVER
    private Gauge players = Gauge.build().name(Main.getInstance().getIdServer() + "_players_total").help("Online and offline players").labelNames("state").create().register();
    private Gauge loadedChunks = Gauge.build().name(Main.getInstance().getIdServer() + "_loaded_chunks_total").help("Chunks loaded per world").labelNames("world").create().register();
    private Gauge playersOnline = Gauge.build().name(Main.getInstance().getIdServer() + "_players_online_total").help("Players currently online per world").labelNames("world").create().register();
    private Gauge entities = Gauge.build().name(Main.getInstance().getIdServer() + "_entities_total").help("Entities loaded per world").labelNames("world").create().register();
    private Gauge livingEntities = Gauge.build().name(Main.getInstance().getIdServer() + "_living_entities_total").help("Living entities loaded per world").labelNames("world").create().register();
    private Gauge memory = Gauge.build().name(Main.getInstance().getIdServer() + "_jvm_memory").help("JVM memory usage").labelNames("type").create().register();
    private Gauge tps = Gauge.build().name(Main.getInstance().getIdServer() + "_tps").help("Server TPS (ticks per second)").create().register();
    private Gauge disk = Gauge.build().name(Main.getInstance().getIdServer() + "_disk").help("Space of disk").labelNames("type").create().register();
    private Gauge cpu = Gauge.build().name(Main.getInstance().getIdServer() + "_cpu").help("Processor").create().register();

    public MetricsController(Main exporter) {
        this.plugin = exporter;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (!target.equals("/metrics")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Future<Object> future = Main.getInstance().getServer().getScheduler().callSyncMethod(Main.getInstance(), new Callable<Object>() {
            public Object call() throws Exception {
                players.labels("online").set(Bukkit.getOnlinePlayers().size());
                players.labels("offline").set(Bukkit.getOfflinePlayers().length);

                // TPS
                //TODO: NMS
                //double d = MinecraftServer.getServer().recentTps[0];
                //tps.set(d);

                for (World world : Bukkit.getWorlds()) {
                    loadedChunks.labels(world.getName()).set(world.getLoadedChunks().length);
                    playersOnline.labels(world.getName()).set(world.getPlayers().size());
                    entities.labels(world.getName()).set(world.getEntities().size());
                    livingEntities.labels(world.getName()).set(world.getLivingEntities().size());
                }

                memory.labels("max").set(Runtime.getRuntime().totalMemory());
                memory.labels("free").set(Runtime.getRuntime().freeMemory());
                memory.labels("used").set(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());

                disk.labels("max").set(new File("/").getTotalSpace());
                disk.labels("used").set((new File("/").getTotalSpace()) - (new File("/").getUsableSpace()));

                cpu.set(getProcessCpuLoad());

                return null;
            }
        });

        try {
            future.get();

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html;charset=utf-8");


            TextFormat.write004(response.getWriter(), CollectorRegistry.defaultRegistry.metricFamilySamples());

            baseRequest.setHandled(true);
        } catch (InterruptedException | ExecutionException e) {
            //TODO: Log
            //Log.withPrefix(ChatColor.RED + "Nelze se pripojit na Prometheus server!");
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public static double getProcessCpuLoad() throws Exception {

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
        AttributeList list = mbs.getAttributes(name, new String[]{"ProcessCpuLoad"});

        if (list.isEmpty()) return Double.NaN;

        Attribute att = (Attribute) list.get(0);
        Double value = (Double) att.getValue();

        // usually takes a couple of seconds before we get real values
        if (value == -1.0) return Double.NaN;
        // returns a percentage value with 1 decimal point precision
        return ((int) (value * 1000) / 10.0);
    }
}
