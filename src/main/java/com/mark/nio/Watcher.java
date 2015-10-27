package com.mark.nio;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * Author: Mark
 * Date  : 2015/3/20
 * Time  : 15:25
 */
public class Watcher {

    public static void main(String[] args) {
        Path this_dir = Paths.get(".");
        System.out.println("Now watching the current directory ..." + this_dir.toAbsolutePath().toString());

        try {
            WatchService watcher = this_dir.getFileSystem().newWatchService();
            this_dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey watchKey = watcher.take();

            List<WatchEvent<?>> events = watchKey.pollEvents();

            for (WatchEvent event : events) {
                System.out.println("Someone just created the file '" + event.context().toString() + "'.");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.toString());
        }
    }

}
