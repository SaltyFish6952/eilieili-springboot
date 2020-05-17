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

//    private void removeTask(String uuid) {
//        taskMap.remove(uuid);
//    }
//
//    public int isTaskFinished(String uuid) {
//
//        try {
//
//            TranscodeTask task = taskMap.get(uuid);
//
//            if(task == null){
//                return -2;              //HashMap中不存在task
//            }
//
//            if (task.isFinished) {
//                removeTask(uuid);
//                return 1;               //HashMap中存在task, 且已完成
//            } else
//                return 0;               //HashMap中存在task, 正在转码
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            return -1;                  //出现错误
//        }
//
//
//    }

}
