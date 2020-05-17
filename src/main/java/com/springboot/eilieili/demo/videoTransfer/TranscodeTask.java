package com.springboot.eilieili.demo.videoTransfer;

import com.springboot.eilieili.demo.mapper.VideoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import sun.java2d.cmm.CMSManager;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.springboot.eilieili.demo.common.Constants.*;

@Slf4j
public class TranscodeTask {


    private static final float[] BITRATES = new float[]{500, 2000, 4000};
    private static final String[] PIXELS = new String[]{"640x360", "1280x720", "1920x1080"};

    private String videoPath;


    public TranscodeTask(String fileName, VideoMapper mapper) {


        String originalFilePath = UPLOAD_TMP_VIDEO_PATH + fileName;
        videoPath = VIDEO_RESOURCE_PATH + fileName.split("\\.")[0] + "/";
        String mp4 = fileName.split("\\.")[0] + ".mp4";
        String uuid = fileName.split("\\.")[0];


        File videoPathDir = new File(videoPath);

        if (!videoPathDir.exists()) {

            log.error("目录:" + videoPath + " 不存在！将创建目录...");

            if (!(videoPathDir.mkdir())) {
                log.error("ffmpeg 创建目录失败");
                return;
            }
        }


        //获取分辨率
        String pattern = "Video: (.*?) kb/s";
        String info = getVideoInfo(originalFilePath);
        String pixel = null;
        String bitrate = null;

        if (info != null) {

            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(info);

            if (m.find()) {

                pixel = m.group(1).split(", ")[2];
                bitrate = m.group(1).split(", ")[3];

                if (pixel.contains(" ")) {
                    pixel = pixel.split(" ")[0];
                }
            }
        }

        log.error("pixel is : " + pixel);
        log.error("bitrate is : " + bitrate);

        int i;

        for (i = 2; i >= 0; i--) {

            if (PIXELS[i].equals(pixel)) {

                if (BITRATES[i] <= Float.parseFloat(bitrate)) {
                    BITRATES[i] = Float.parseFloat(bitrate);
                }
                break;
            }
        }

        ArrayList<String> fNames = new ArrayList<>();

        log.error(Arrays.toString(BITRATES));
        log.error("i is " + i);

        if (i == -1) {
            i = 2;
            pixel = PIXELS[2];

        }

        mapper.updateVideoStatus(uuid, 6);

        try {

            int finalI = i;

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int j = finalI; j >= 0; j--) {

                        mapper.updateVideoStatus(uuid, j + 3);

                        String name = "video_" + PIXELS[j] + "_" + mp4;


                        transcode(name, originalFilePath, PIXELS[j], String.valueOf(BITRATES[j]));

                        bento4_fragment(name, "f_" + name);

                        fNames.add(videoPath + "f_" + name);


                    }

                    mapper.updateVideoStatus(uuid, 2);

                    splitAudio("audio_" + mp4, originalFilePath);
                    bento4_fragment("audio_" + mp4, "f_audio_" + mp4);



                    fNames.add(videoPath + "f_audio_" + mp4);

                    bento4_dash(fNames);

                    mvFiles(fileName);

                }
            });

            thread.start();
            thread.join();

            log.info(uuid + " done!");


            mapper.updateVideoStatus(uuid, 1);
            mapper.postVideoQuality(uuid, pixel);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            mapper.updateVideoStatus(uuid, -1);
        }


    }

    private void splitAudio(String fileName, String originalFilePath) {

        List<String> command = new ArrayList<String>();
        command.add(FFMPEG_PATH);
        command.add("-i");
        command.add(originalFilePath);
        command.add("-c:a");
        command.add("aac");
        command.add("-vn");
        command.add(videoPath + fileName);
        command.add("-y");

        CmdExecutor(command);

    }

    private void transcode(String fileName, String originalFilePath, String pixel, String bitrate) {

        List<String> command = new ArrayList<String>();
        command.add(FFMPEG_PATH);
        command.add("-i");
        command.add(originalFilePath);
        command.add("-s");
        command.add(pixel);
        command.add("-c:v");
        command.add("h264_qsv");
        command.add("-b:v");
        command.add(bitrate + "k");
        command.add("-keyint_min");
        command.add("15");
        command.add("-g");
        command.add("15");
        command.add("-sc_threshold");
        command.add("0");
        command.add("-an");
        command.add(videoPath + fileName);
        command.add("-y");

        CmdExecutor(command);
    }

    public void mvFiles(String fileName) {

        try {
            File afile = new File(UPLOAD_TMP_VIDEO_PATH + fileName);
            File bfile = new File(videoPath + fileName);//定义移动后的文件路径
            bfile.createNewFile();//新建文件
            FileInputStream c = new FileInputStream(afile);
            FileOutputStream d = new FileOutputStream(bfile);
            byte[] date = new byte[512];//定义byte数组
            int i = 0;
            while ((i = c.read(date)) > 0) {//判断是否读到文件末尾
                d.write(date);//写数据
            }
            c.close();//关闭流
            d.close();//关闭流
            afile.delete();//删除原文件
            System.out.println("文件移动成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void bento4_fragment(String fileName, String frag_fileName) {

        List<String> command = new ArrayList<>();
        command.add(BENTO4_FRM_PATH);
        command.add(videoPath + fileName);
        command.add(videoPath + frag_fileName);

        CmdExecutor(command);
    }

    private void bento4_dash(ArrayList<String> fileNames) {

        List<String> command = new ArrayList<>();
        command.add(BENTO4_DASH_PATH);
        command.addAll(fileNames);
        command.add("-o");
        command.add(videoPath + "dash");

        CmdExecutor(command);

    }


    private Boolean CmdExecutor(List<String> command) {


        try {

            ProcessBuilder builder = new ProcessBuilder(command);
            builder.command(command);
            Process process = builder.start();
            PrintStream errorStream = new PrintStream(process.getErrorStream());
            PrintStream inputStream = new PrintStream(process.getInputStream());
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


    public static class PrintStream extends Thread {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();

        public PrintStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            try {
                if (null == inputStream) {
                    log.error("--- 读取输出流出错！因为当前输出流为空！---");
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    log.info(line);
                    stringBuffer.append(line);
                }
            } catch (Exception e) {
                log.error("--- 读取输入流出错了！--- 错误信息：" + e.getMessage());
            } finally {
                try {
                    if (null != bufferedReader) {
                        bufferedReader.close();
                    }
                    if (null != inputStream) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    log.error("--- 调用PrintStream读取输出流后，关闭流时出错！---");
                }
            }
        }
    }

    private static String getVideoInfo(String path) {

        List<String> command = new java.util.ArrayList<String>();
        command.add("H:\\IDEA_project\\EiliEiliDemo\\exe\\ffmpeg.exe");
        command.add("-i");
        command.add(path);


        try {


            ProcessBuilder builder = new ProcessBuilder();
            builder.command(command);
            builder.redirectErrorStream(true);
            Process p = builder.start();

            BufferedReader buf = null;
            String line = null;

            buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

            StringBuilder sb = new StringBuilder();
            while ((line = buf.readLine()) != null) {
                log.info(line);
                sb.append(line);
            }
            int ret = p.waitFor();//这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
            //1. end
            return sb.toString();


        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }


    }


}
