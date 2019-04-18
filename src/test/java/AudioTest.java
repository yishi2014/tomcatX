import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class AudioTest {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File SoundFile;
        String filename="E:\\FFOutput\\test.wav";
        AudioInputStream audioInputStream = null;
        SourceDataLine auline = null;
        TargetDataLine line = null;

        SoundFile = new File(filename);
        try {
            audioInputStream = AudioSystem.getAudioInputStream(SoundFile);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        AudioFormat format = audioInputStream.getFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        AudioFormat format1 = new AudioFormat(8000, 16, 2, true, true);//AudioFormat(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian）

        DataLine.Info lineinfo = new DataLine.Info(TargetDataLine.class, format1);
//        try{
////            line = (TargetDataLine) AudioSystem.getLine(lineinfo);
//            for(Mixer.Info mixerInfo:AudioSystem.getMixerInfo()){
//                Mixer mixer=AudioSystem.getMixer(mixerInfo);
//                mixer.open();
//                System.out.println(mixer.isOpen());
//                try{
//                    if(mixer.getTargetLineInfo().length>0)
//                        lineinfo= (DataLine.Info) mixer.getTargetLineInfo()[0];
//                    System.out.println(mixer.getLine(lineinfo));
////                    System.out.println(Arrays.toString(mixer.getTargetLines()));
//                }catch (Exception e1){
//                    e1.printStackTrace();
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        System.out.println(line);
//
//        if(1==1)System.exit(0);
        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        auline.start();
        int nByteRead = 0;
        byte[] abData = new byte[512];
        try {
            while (nByteRead != -1) {
                nByteRead = audioInputStream.read(abData, 0, abData.length);
                if (nByteRead >= 0) {
                    auline.write(abData, 0, nByteRead);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            auline.drain();
            auline.close();
        }


    }

 void a(){

 }
}
