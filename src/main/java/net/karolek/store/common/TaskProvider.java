package net.karolek.store.common;

import java.util.logging.Logger;

public interface TaskProvider {

    Logger getLogger();

    void runTask(Runnable runnable);

    void runTaskTimer(Runnable runnable, long period);

}
