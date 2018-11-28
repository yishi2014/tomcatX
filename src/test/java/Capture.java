//import javazoom.spi.mpeg.sampled.file.MpegAudioFileFormat;
//import javazoom.spi.mpeg.sampled.file.MpegEncoding;
//import javazoom.spi.mpeg.sampled.file.MpegFileFormatType;
//import org.tritonus.share.sampled.AudioFileTypes;
//
//import javax.sound.sampled.*;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//import java.util.Scanner;
//
///**
// * Title:        VoiceChat
// * Description: 音频捕捉（录音程序）
// * Copyright:    Copyright (c) 2001
// * Company:
// *
// * @author 网络
// * @version 1.0
// */
//
//class Capture implements Runnable {
//    public static void main(String[] args) {
//        System.out.println(11);
//        new Capture().start();
//    }
//
//    TargetDataLine line;
//    Thread thread;
//    Socket s;
//    BufferedOutputStream captrueOutputStream;
//
//    Capture(Socket s) {//构造器 取得socket以获得网络输出流
//        this.s = s;
//    }
//    Capture() {//构造器 取得socket以获得网络输出流
//    }
//
//    public void start() {
//
////        thread = new Thread(this);
////        thread.setName("Capture");
////        thread.start();
//         new theRecorder();
//    }
//
//    public void stop() {
//        thread = null;
//    }
//
//    public void run() {
////        String filename="C:\\Users\\yishi\\Desktop\\test.mp3";
////        AudioInputStream audioInputStream = null;
////        File SoundFile;
////
////        SoundFile = new File(filename);
////        try {
////            audioInputStream = AudioSystem.getAudioInputStream(SoundFile);
////        } catch (Exception e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////            return;
////        }
////        AudioFormat format = audioInputStream.getFormat();
//
//        try {
//            File outfile=new File("audio.mp3");
//            FileOutputStream fos=new FileOutputStream(outfile);
////            captrueOutputStream = new BufferedOutputStream(s.getOutputStream());//建立输出流 此处可以加套压缩流用来压缩数据
//            captrueOutputStream = new BufferedOutputStream(fos);//建立输出流 此处可以加套压缩流用来压缩数据
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return;
//        }
//
//        AudioFormat format = new AudioFormat(MpegEncoding.MPEG1L3,48000, 16, 2, ((16 + 7) / 8) * 2,48000, false);//AudioFormat(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian）
////        AudioFormat format = new AudioFormat(48000, 16, 2, true,false);//AudioFormat(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian）
////        AudioFormat format = new AudioFormat();//AudioFormat(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian）
//        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
//
//        try {
//            Line.Info lineInfo = null;
//
//            Mixer mixer=AudioSystem.getMixer(AudioSystem.getMixerInfo()[7]);
//            mixer.open();
//            line= (TargetDataLine) AudioSystem.getLine(mixer.getTargetLineInfo()[0]);
////            line = (TargetDataLine) AudioSystem.getLine(info);
//            line.open(format, line.getBufferSize());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return;
//        }
//
//        byte[] data = new byte[4096];//此处的1024可以情况进行调整，应跟下面的1024应保持一致
//        int numBytesRead = 0;
//        line.start();
//        int i=0;
//        while (thread != null&&++i<500) {
//                numBytesRead = line.read(data, 0, data.length);//取数据（1024）的大小直接关系到传输的速度，一般越小越快，
////                System.out.println(Arrays.toString(data));
//                try {
//                    captrueOutputStream.write(data, 0, numBytesRead);//写入网络流
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    break;
//                }
//        }
//
//        line.stop();
//        line.close();
//        line = null;
//
//        try {
//            captrueOutputStream.flush();
//            captrueOutputStream.close();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//    }
//
//    /**
//     *
//     * @author admin_70
//     */
//     class theRecorder {
//        private static final long serialVersionUID = 1L;
//        AudioFormat audioFormat;
//        TargetDataLine targetDataLine;
//
////        public static void main(String args[]) {
////            new theRecorder();
////        }
//
//        public theRecorder() {
//            System.out.println("y开始n结束");
//            Scanner input = new Scanner(System.in);
//            String Sinput = input.next();
//            long testtime = System.currentTimeMillis();
//            if(Sinput.equals("y")){
//                captureAudio();//调用录音方法
//            }
//            Scanner input_2 = new Scanner(System.in);
//            String Sinput_2 = input_2.next();
//            if(Sinput_2.equals("n")){
//                targetDataLine.stop();
//                targetDataLine.close();
//            }
//            System.out.println("录音了"+(System.currentTimeMillis()-testtime)/1000+"秒！");
//        }
//
//        public void captureAudio(){
//            try {
////
////                //audioFormat = getAudioFormat();//构造具有线性 PCM 编码和给定参数的 AudioFormat。
////                audioFormat=new AudioFormat(MpegEncoding.MPEG1L3,48000, 16, 2, ((16 + 7) / 8) * 2,48000, false);//AudioFormat(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian）
////
////                DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
////                //根据指定信息构造数据行的信息对象，这些信息包括单个音频格式。此构造方法通常由应用程序用于描述所需的行。
////                //lineClass - 该信息对象所描述的数据行的类
////                //format - 所需的格式
////                targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
////                audioFormat = new AudioFormat(MpegEncoding.MPEG1L3,48000, 16, 2, ((16 + 7) / 8) * 2,48000, false);//AudioFormat(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian）
//                audioFormat = getAudioFormat();
//                DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
//
//                try {
//                    Line.Info lineInfo = null;
//
//                    Mixer mixer=AudioSystem.getMixer(AudioSystem.getMixerInfo()[7]);
//                    mixer.open();
//                    targetDataLine= (TargetDataLine) AudioSystem.getLine(mixer.getTargetLineInfo()[0]);
////                    line.open(format, line.getBufferSize());
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    return;
//                }
//
//                //如果请求 DataLine，且 info 是 DataLine.Info 的实例（至少指定一种完全限定的音频格式），
//                //上一个数据行将用作返回的 DataLine 的默认格式。
//                new CaptureThread().start();
//                //开启线程
//            } catch (Exception e){
//                e.printStackTrace();
//                System.exit(0);
//            }
//        }
//
//        private AudioFormat getAudioFormat() {
//            float sampleRate = 44100;
//            // 8000,11025,16000,22050,44100 采样率
//            int sampleSizeInBits = 16;
//            // 8,16 每个样本中的位数
//            int channels = 2;
//            // 1,2 信道数（单声道为 1，立体声为 2，等等）
//            boolean signed = true;
//            // true,false
//            boolean bigEndian = false;
//            // true,false 指示是以 big-endian 顺序还是以 little-endian 顺序存储音频数据。
//            return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
//                    bigEndian);//构造具有线性 PCM 编码和给定参数的 AudioFormat。
//        }
//
//        class CaptureThread extends Thread {
//            public void run() {
//                AudioFileFormat.Type fileType = null;
//                //指定的文件类型
//                File audioFile = null;
//                //设置文件类型和文件扩展名
//                //根据选择的单选按钮。
////                fileType = AudioFileFormat.Type.WAVE;
//                fileType = AudioFileFormat.Type.WAVE;
//                audioFile = new File("test.mp3");
//                try {
//                    targetDataLine.open(audioFormat);
//                    //format - 所需音频格式
//                    targetDataLine.start();
//                    //当开始音频捕获或回放时，生成 START 事件。
//                    AudioSystem.write(new AudioInputStream(targetDataLine),fileType, audioFile);
//                    //new AudioInputStream(TargetDataLine line):构造从指示的目标数据行读取数据的音频输入流。该流的格式与目标数据行的格式相同,line - 此流从中获得数据的目标数据行。
//                    //stream - 包含要写入文件的音频数据的音频输入流
//                    //fileType - 要写入的音频文件的种类
//                    //out - 应将文件数据写入其中的外部文件
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//}
