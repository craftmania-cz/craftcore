package cz.craftmania.craftcore.tasks;

public class TpsPollerTask implements Runnable {

    private static int TICK_COUNT = 0;
    private static long[] TICKS = new long[600];
    private static long LAST_TICK = 0L;

    /**
     * Returns server TPS in last 5 seconds
     *
     * @return TPS
     */
    public static double getTPS() {
        return getTPS(100);
    }

    /**
     * Returns server TPS for custom value
     *
     * @param ticks Amount of ticks that to be counted
     * @return TPS
     */
    public static double getTPS(int ticks) {
        if (TICK_COUNT < ticks) {
            return 20.0D;
        }
        int target = (TICK_COUNT - 1 - ticks) % TICKS.length;
        long elapsed = System.currentTimeMillis() - TICKS[target];

        return ticks / (elapsed / 1000.0D);
    }

    /**
     * Returns amount of ticks that elapsed from start of counting
     *
     * @param tickID Ticks IDs
     * @return Time in miliseconds
     */
    public static long getElapsed(int tickID) {
        if (TICK_COUNT - tickID >= TICKS.length) {
        }

        long time = TICKS[(tickID % TICKS.length)];
        return System.currentTimeMillis() - time;
    }

    /* Starter for main process */
    public void run() {
        TICKS[(TICK_COUNT % TICKS.length)] = System.currentTimeMillis();

        TICK_COUNT += 1;
    }
}
