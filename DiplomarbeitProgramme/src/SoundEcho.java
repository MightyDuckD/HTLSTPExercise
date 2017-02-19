
import com.sun.media.sound.WaveFileReader;
import com.sun.media.sound.WaveFileWriter;
import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Simon
 */
public class SoundEcho {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        InputStream input = null;
        OutputStream output = null;
        WaveFileReader reader = new WaveFileReader();
        AudioInputStream in = reader.getAudioInputStream(input);
        
        
        
        WaveFileWriter writer = new WaveFileWriter();
        writer.write(in, AudioFileFormat.Type.WAVE, output);
    }

}
