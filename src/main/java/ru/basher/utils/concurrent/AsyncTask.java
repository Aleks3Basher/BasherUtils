package ru.basher.utils.concurrent;

import java.util.logging.Level;
import org.bukkit.plugin.Plugin;

public abstract class AsyncTask implements Runnable {

    private final Plugin plugin;
    private Thread thread;

    public AsyncTask(Plugin plugin) {
        this.plugin = plugin;
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public AsyncTask.SyncTask sync(Runnable runnable) {
        return new SyncTask(runnable);
    }

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to sleep", e);
        }
    }

    public boolean isActive() {
        return !thread.isInterrupted();
    }

    public void stop() {
        thread.interrupt();
    }

    public class SyncTask {
        private final Runnable runnable;
        private volatile boolean isDone;

        public SyncTask(Runnable runnable) {
            this.runnable = runnable;
        }

        public AsyncTask.SyncTask start() {
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                try {
                    runnable.run();
                } finally {
                    synchronized(this) {
                        isDone = true;
                        notifyAll();
                    }
                }
            });
            return this;
        }

        public void join() {
            synchronized(this) {
                if (!isDone) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        plugin.getLogger().log(Level.SEVERE, "Failed to join!", e);
                    }
                }
            }
        }
    }
}
