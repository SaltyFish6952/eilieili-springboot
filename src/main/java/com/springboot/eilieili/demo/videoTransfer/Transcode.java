package com.springboot.eilieili.demo.videoTransfer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class Transcode {

    HashMap<String, TranscodeTask> taskMap = new HashMap<>();

    public void newTask(TranscodeTask task, String uuid) {

        taskMap.put(uuid, task);

    }

    private void removeTask(String uuid) {
        taskMap.remove(uuid);
    }

    public int isTaskFinished(String uuid) {

        try {

            if (taskMap.get(uuid).isFinished) {

                removeTask(uuid);
                return 1;
            } else
                return 0;

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return -1;
        }


    }

}
