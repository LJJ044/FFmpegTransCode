package com.example.ffmpegtranscode.jni;

import android.util.Log;

import java.util.ArrayList;

public class FFmpegCmd {
    static {
        System.loadLibrary("avutil");
        System.loadLibrary("avcodec");
        System.loadLibrary("swresample");
        System.loadLibrary("avformat");
        System.loadLibrary("swscale");
        System.loadLibrary("avfilter");
        System.loadLibrary("ffmpeg-invoke");
        System.loadLibrary("ffmpeg-cmd");
    }

    private static native int run(int cmdLen, String[] cmd);
    public static int run(ArrayList<String> cmd) {
        String[] cmdArr = new String[cmd.size()];
        Log.d("FFmpegCmd", "run: " + cmd.toString());
        return run(cmd.size(), cmd.toArray(cmdArr));
    }
    public static int run(String[] cmd){
        return run(cmd.length,cmd);
    }
    public static void transcode(String srcPath, String outPath, int targetFPS, int bitrate, int width, int height, String presets) {
        ArrayList<String> cmd = new ArrayList<>();
        cmd.add("ffmpeg");
        //cmd.add("-d");
        cmd.add("-y");

        //当rotation为0时使用硬件解码器
        // rotation不为0时使用硬件解码视频画面可能会变绿
      /*  if (info.rotation==0){
            switch (info.videoCodec){
                case "h264":
                    cmd.add("-c:v");
                    cmd.add("h264_mediacodec");
                    break;
                case "hevc":
                    cmd.add("-c:v");
                    cmd.add("hevc_mediacodec");
                    break;
                case "mpeg4":
                    cmd.add("-c:v");
                    cmd.add("mpeg4");
                    break;
                case "vp9":
                   cmd.add("-c:v");
                   cmd.add("vp9_mediacodec");
                   break;
            }
        }*/
      /*  cmd.add("-ss");
        cmd.add("00:00:20");
        cmd.add("-t");
        cmd.add("00:00:08");*/

        cmd.add("-i");
        cmd.add(srcPath);
        cmd.add("-vcodec");
        cmd.add("copy");
        cmd.add("-vcodec");
        cmd.add("copy");
        cmd.add("-acodec");
        cmd.add("copy");
        //cmd.add("-preset");
        //cmd.add(presets);
        //cmd.add("-b:v");
        //cmd.add(bitrate + "k");
      /*  if (width > 0) {
            cmd.add("-s");
            cmd.add(width + "x" + height);
        }*/
        //cmd.add("-r");
        //cmd.add(String.valueOf(targetFPS));
        cmd.add(outPath);
        run(cmd);
    }
}

