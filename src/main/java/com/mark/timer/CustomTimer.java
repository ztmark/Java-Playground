package com.mark.timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Author: Mark
 * Date  : 2017/3/21
 */
public class CustomTimer {


    /*
    大量定时任务，需要超时处理

    方案：
    使用环形队列：
    每个 slot 中是一个 Set，Set 中放入了任务，
    定义一个定时器，每隔一段时间走一个 slot，slot 中的任务就超时了

    比如：维护用户的长连接任务需要 60 秒超时

    1. 设置一个长度为60的环形队列（数组）,每个槽中都是一个 Set<Long> uids
    2. 设置一个 Map<Long,Integer> 表示 uid -> slot 的对应关系，用户在哪个槽中
    3. 设置一个 index 表示当前的 slot
    4. 设置一个定时任务，一秒中触发一次
    5. 触发的定时任务对 index 的 slot 中的 uid 进行超时处理（这些 uid 都是超时的）
    6. 如果有用户有新的请求过来，将通过 Map 找出对应的 uid 在哪个槽里，将 uid 从那个槽里删除，然后加入到 index - 1 的槽里（定时任务过60秒才会走到）

    netty 的 HashedWheelTimer
     */

    private Set<Long>[] queue;
    private int index = 0;
    private Map<Long, Integer> mapping = new HashMap<>();
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    CustomTimer(int timeout) {
        //noinspection unchecked
        queue = new Set[timeout];
        // 环形队列
        for (int i = 0; i < timeout; i++) {
            queue[i] = new HashSet<>();
        }
        executorService.scheduleAtFixedRate(() -> {
            queue[index].clear();
            next();
        }, 1, 1, TimeUnit.SECONDS);
    }

    private void next() {
        index = index == queue.length - 1 ? 0 : index + 1;
    }

    private int pre() {
        return index == 0 ? queue.length - 1 : index - 1;
    }

    public void update(Long uid) {
        final Integer integer = mapping.get(uid);
        if (integer != null) {
            queue[integer].remove(uid);
            final int pre = pre();
            queue[pre].add(uid);
            mapping.remove(uid);
            mapping.put(uid, pre);
        }
    }

    public static void main(String[] args) {

        CustomTimer customTimer = new CustomTimer(60);

        customTimer.update(1234L);

        customTimer.update(12345L);



    }

}
