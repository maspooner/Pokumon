
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;


public class AudioPlayer{
	
	static Thread thread=new Thread();
	static boolean isPlaying=false;
	
	private static String getFile(int musicID){
		String newName="music/";
		switch(musicID){
		case 1: newName+="title"; break;
		case 2: newName+="battle"; break;
		case 3: newName+="win"; break;
		case 4: newName+="title2"; break;
		case 5: newName+="opening"; break;
		case 6: newName+="town"; break;
		case 7: newName+="oak"; break;
		case 8: newName+="center"; break;
		case 9: newName+="recovery"; break;
		case 10: newName+="shop"; break;
		case 11: newName+="route"; break;
		case 12: newName+="caught"; break;
		case 13: newName+="cong"; break;
		case 14: newName+="end"; break;
		}
		return newName+=".wav";
	}
	

	private static String getSFile(int soundID){
		//TODO: change to get resource after
		String newName="sounds/";
		switch(soundID){
		case 1: newName+="hit"; break;
		case 2: newName+="getPoke"; break;
		case 3: newName+="error"; break;
		case 4: newName+="heal"; break;
		case 5: newName+="get"; break;
		case 6: newName+="level"; break;
		case 7: newName+="potion"; break;
		}
		return newName+=".wav";
	}
	
	private static AudioInputStream createInputStream(String path) throws UnsupportedAudioFileException, IOException{
		if(Main.IS_TEST){
       	 return AudioSystem.getAudioInputStream(new File(path));
        }
       	return AudioSystem.getAudioInputStream(AudioPlayer.class.getResource("/" + path));
	}
	
	@SuppressWarnings("deprecation")
	public static void playm(final int musicID) {
		if(thread.isAlive())
			thread.stop();
		thread=new Thread(){
			 @Override
			 public void run(){
				 isPlaying=true;
				 SourceDataLine soundLine = null;
			      int BUFFER_SIZE = 64*1024;  // 64 KB
			      
			      try {
			         AudioInputStream audioInputStream = createInputStream(getFile(musicID));
			         AudioFormat audioFormat = audioInputStream.getFormat();
			         DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			         soundLine = (SourceDataLine) AudioSystem.getLine(info);
			         soundLine.open(audioFormat);
			         soundLine.start();
			         int nBytesRead = 0;
			         byte[] sampledData = new byte[BUFFER_SIZE];
			         while (nBytesRead != -1) {
			            nBytesRead = audioInputStream.read(sampledData, 0, sampledData.length);
			            if (nBytesRead >= 0) {
			               soundLine.write(sampledData, 0, nBytesRead);
			            }
			         }
			      isPlaying=false;
			      } catch (Exception e) {
			      }
			      finally {
			         soundLine.drain();
			         soundLine.close();
			      }
			 }
		 };
		 thread.start();
	 }
	
	
	public static void plays(final int soundID){
		new Thread(new Runnable(){
			public void run() {
				try{
					AudioInputStream ais = createInputStream(getSFile(soundID));
					Clip cl=AudioSystem.getClip();
					cl.open(ais);
					cl.start();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}
}