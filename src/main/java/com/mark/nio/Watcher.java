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
        Path this_dir = Paths.get("").toAbsolutePath();
        System.out.println("Now watching the current directory " + this_dir.toString());

        try {
            WatchService watcher = this_dir.getFileSystem().newWatchService();
            this_dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

            while (true) {

                WatchKey watchKey = watcher.take();

                List<WatchEvent<?>> events = watchKey.pollEvents();

                for (WatchEvent event : events) {
                    Path context = (Path) event.context(); // 返回的是相对于监视目录的相对路径
                    context = this_dir.resolve(context); // 转成绝对路径
                    System.out.println("Someone just created the file " + context.toString());
                    System.out.println(Files.size(context));
                }

                watchKey.reset(); // 不重置的话,之后相同的事件就无法被监视到
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.toString());
        }
    }

}
