package com.springboot.eilieili.demo;

import com.springboot.eilieili.demo.videoTransfer.CmdExcuter;
import com.springboot.eilieili.demo.videoTransfer.Transcode;
import com.springboot.eilieili.demo.videoTransfer.TranscodeTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.springboot.eilieili.demo.common.Constants.UPLOAD_TMP_VIDEO_PATH;

@SpringBootTest
@Slf4j
class SpringBootEiliEiliApplicationTests {

    private Boolean CmdExecutor(List<String> command) {


        try {

            ProcessBuilder builder = new ProcessBuilder(command);
            builder.command(command);
            Process process = builder.start();
            TranscodeTask.PrintStream errorStream = new TranscodeTask.PrintStream(process.getErrorStream());
            TranscodeTask.PrintStream inputStream = new TranscodeTask.PrintStream(process.getInputStream());
            errorStream.start();
            inputStream.start();

            process.waitFor();

            return process.exitValue() == 0;

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }


    }


    @Test
    void contextLoads() {


        String pattern = "Video: (.*?) kb/s";
        String info = "Stream #0:0: Video: h264 (High), yuv420p(progressive), 1280x720, 1326 kb/s, 30.03 fps, 30 tbr, 1k tbn, 60 tbc";
        String pixel = null;
        String bitrate = null;

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(info);

        if (m.find()) {

            pixel = m.group(1).split(", ")[2];
            bitrate = m.group(1).split(", ")[3];


        }

        log.error(pixel,bitrate);

//        TranscodeTask transcodeTask = new TranscodeTask("panda.mp4");

//        try {
//
//            String encode = PasswordUtil.Encrypt("1234");
//            log.error(encode);
//            log.error(PasswordUtil.Decrypt(encode));
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

}
