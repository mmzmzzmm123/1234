package com.onethinker.common.webp.exc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yangyouqi
 * @date 2024/5/16
 */
public class WebpEncodeUtil {
    private static final Logger logger = LoggerFactory.getLogger(WebpEncodeUtil.class);

    /**
     * 使用默认参数将原始图片转换为webp文件(转换尺寸)
     *
     * @param source 原始图片
     * @param target webp文件
     * @param width  width
     * @param height height
     */
    public static void toWebp(Path source, Path target, Integer width, Integer height) throws IOException {
        toWebp(source, target, 6, 50, false, width, height);
    }

    /**
     * 使用默认参数将原始图片转换为webp文件(不转换尺寸)
     *
     * @param source 原始图片
     * @param target webp文件
     */
    public static void toWebp(Path source, Path target) throws IOException {
        toWebp(source, target, 6, 50, false, null, null);
    }

    /**
     * 转换为webp文件
     *
     * @param input    输入文件
     * @param target   输出文件
     * @param m        制定压缩模式。这个参数控制了编码的速度，压缩文件大小，质量。值范围为0-6. 默认4.
     *                 值越大，花费时间越大用来检查额外的编码能力和质量。小的值会产生快的压缩速度，大的文件和低的压缩质量。
     * @param q        为RGB通道指定压缩参数0-100， 默认值75
     *                 如果是在有损压缩中（默认为有损压缩）参数越小，图片质量越低，文件越小。最好的质量参数是100
     *                 如是是在无损压缩中（特别是指定了 -lossless 参数），小的参数会获得快的压缩速度，但是生产出较大的文件。最大的压缩值为100.
     * @param lossless 对图像进行无损编码
     *                 encode image losslessly, default=off
     * @param width    width
     * @param height   height
     * @throws IOException IO异常
     */
    public static void toWebp(Path input, Path target, int m, int q, boolean lossless, Integer width, Integer height) throws IOException {
        List<String> commands = new ArrayList<>();
        commands.add("cwebp");
        if (m >= 0) {
            commands.add("-m");
            commands.add(m + "");
        }
        if (q >= 0) {
            commands.add("-q");
            commands.add(q + "");
        }
        if (lossless) {
            commands.add("-lossless");
        }
        if (width != null || height != null) {
            commands.add("-resize");
            commands.add(String.valueOf(width == null ? 0 : width));
            commands.add(String.valueOf(height == null ? 0 : height));
        }
        commands.add("-mt");
        commands.add(input.toAbsolutePath().toString());
        commands.add("-o");
        commands.add(target.toAbsolutePath().toString());

        runProcess(commands);
    }

    private static void runProcess(List<String> commands) throws IOException {
        logger.debug("Run Process:{}", String.join(" ", commands));
        Path stdout = Files.createTempFile("stdout", "webp");
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.redirectErrorStream(true);
        builder.redirectOutput(stdout.toFile());
        Process process = builder.start();
        try {
            process.waitFor(5, TimeUnit.MINUTES);
            int exitStatus = process.exitValue();
            if (exitStatus != 0) {
                List<String> error = Files.readAllLines(stdout);
                throw new IOException(error.toString());
            }
        } catch (InterruptedException e) {
            throw new IOException(e);
        } finally {
            process.destroy();
            Files.delete(stdout);
        }
    }
}
