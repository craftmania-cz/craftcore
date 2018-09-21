package cz.craftmania.craftcore.spigot.internal.thread;

public class FileThread {

    /**
     * The instance.
     */
    static FileThread instance = new FileThread();

    /**
     * Gets the single instance of Thread.
     *
     * @return single instance of Thread
     */
    public static FileThread getInstance() {
        return instance;
    }

    /**
     * The thread.
     */
    private ReadThread thread;

    /**
     * Instantiates a new thread.
     */
    private FileThread() {
    }

    /**
     * @return the thread
     */
    public ReadThread getThread() {
        if (thread == null || !thread.isAlive()) {
            loadThread();
        }
        return thread;
    }

    /**
     * Load thread.
     */
    public void loadThread() {
        thread = new ReadThread();
        thread.start();
    }

    /**
     * Run.
     *
     * @param run the run
     */
    public void run(Runnable run) {
        getThread().run(run);
    }

    /**
     * The Class ReadThread.
     */
    public class ReadThread extends java.lang.Thread {

        @Override
        public void run() {
        }

        /**
         * Run.
         *
         * @param run the run
         */
        public void run(Runnable run) {
            synchronized (FileThread.getInstance()) {
                run.run();
            }

        }
    }
}
