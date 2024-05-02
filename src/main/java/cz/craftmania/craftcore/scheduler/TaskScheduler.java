package cz.craftmania.craftcore.scheduler;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class TaskScheduler {

    protected Runnable runnable;
    protected boolean stopped;
    protected Thread thread;

    public TaskScheduler(Runnable runnable) {
        this.runnable = runnable;
        this.stopped = false;
    }

    /**
     * Runs this scheduler
     */
    public abstract void run();

    /**
     * Stops this scheduler
     */
    public void stop() {
        this.stopped = true;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass() == this.getClass()) {
            TaskScheduler d = (TaskScheduler) o;
            return new EqualsBuilder().append(d.runnable, this.runnable).build();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(27, 19)
                .append(runnable).toHashCode();
    }
}
