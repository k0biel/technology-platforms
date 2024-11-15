package org.example;

import org.apache.commons.lang3.tuple.Pair;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.concurrent.ForkJoinPool;


public class Main {
    public static void main(String[] args)  {
        for (int i = 1; i <= 8; i++) {
            ForkJoinPool ThreadPool = new ForkJoinPool(i);
            long time = System.currentTimeMillis();

            Path source = Paths.get("input");
            Path destination = Paths.get("output");

            List<Path> files;
            try (Stream<Path> stream = Files.list(source)) {
                files = stream.collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                ThreadPool.submit(() ->
                        files.parallelStream()
                                .map(Main::loadImage)
                                .map(Main::processImage)
                                .forEach(pair -> saveImage(destination, pair.getLeft(), pair.getRight()))
                ).get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            System.out.println("Execution time of the program using " + i + " threads: " + (System.currentTimeMillis() - time) / 1000.0);
        }        
    }

    private static Pair<String, BufferedImage> loadImage(Path path) {
        try {
            BufferedImage image = ImageIO.read(path.toFile());
            String name = path.getFileName().toString();
            return Pair.of(name, image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Pair<String, BufferedImage> processImage(Pair<String, BufferedImage> pair) {
        BufferedImage original = pair.getRight();
        BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                int rgb = original.getRGB(i, j);
                Color color = new Color(rgb);
                int gray = (int) ((color.getRed() * 0.299) + (color.getGreen() * 0.587) + (color.getBlue() * 0.114));
                Color outColor = new Color(gray, gray, gray);
                int outRgb = outColor.getRGB();
                image.setRGB(i, j, outRgb);
            }
        }

        return Pair.of(pair.getLeft(), image);
    }

    private static void saveImage(Path destination, String name, BufferedImage image) {
        try {
            ImageIO.write(image, "jpg", destination.resolve(name).toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}