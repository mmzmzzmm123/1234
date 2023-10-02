package com.ruoyi.common.utils;


import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author LAM
 * @date 2023/10/2 18:03
 */
public class AudioUtils {

    /**
     * 动态获取文件的音频时长
     *
     * @param audioFile 文件
     * @return 结果
     * */
    public static int getAudioDurationInSeconds(MultipartFile audioFile) throws IOException, UnsupportedAudioFileException, JavaLayerException {
        String filename = audioFile.getOriginalFilename();
        if (filename != null) {
            String lowerCaseFilename = filename.toLowerCase();
            if (lowerCaseFilename.endsWith(".mp3")) {
                return getMp3DurationInSeconds(audioFile);
            } else if (lowerCaseFilename.endsWith(".m4a")) {
                return getM4aDurationInSeconds(audioFile);
            }
        }
        throw new UnsupportedAudioFileException("Unsupported audio format");
    }

    /**
     * 获取mp3的音频时长
     *
     * @param mp3File 文件
     * @return 结果
     * */
    private static int getMp3DurationInSeconds(MultipartFile mp3File) throws JavaLayerException, IOException {
        byte[] mp3Bytes = mp3File.getBytes();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mp3Bytes);
        Bitstream bitstream = new Bitstream(byteArrayInputStream);
        int frameCount = bitstream.readFrame().max_number_of_frames(10000);
        return (int) (bitstream.readFrame().total_ms(frameCount) / 1000);
    }

    /**
     * 获取m4a的音频时长
     *
     * @param m4aFile 文件
     * @return 结果
     * */
    private static int getM4aDurationInSeconds(MultipartFile m4aFile) throws UnsupportedAudioFileException, IOException {
        byte[] m4aBytes = m4aFile.getBytes();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(m4aBytes);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(audioInputStream);

        Map<String, Object> properties = fileFormat.properties();
        Long durationMicroseconds = (Long) properties.get("duration");
        // 转换成秒
        return (int) (durationMicroseconds / 1_000_000);
    }
}
