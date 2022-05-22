package org.example.io.zip;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void zipFileWithoutBuffer(String zipFile, Iterable<String> files) throws IOException {
        if (files == null) throw new IllegalArgumentException("no file to zip");
        File zip = new File(zipFile);
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip))) {
            for (String file : files) {
                try (InputStream in = new FileInputStream(new File(file))) {
                    out.putNextEntry(new ZipEntry(file));
                    int tmp = 0;
                    while ((tmp = in.read()) != -1) {
                        out.write(tmp);
                    }
                }
            }
        }
    }

    public static void zipFileWithBuffer(String zipFile, Iterable<String> files) throws IOException {
        if (files == null) throw new IllegalArgumentException("no file to zip");
        File zip = new File(zipFile);
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
             BufferedOutputStream bufferOut = new BufferedOutputStream(out)) {
            for (String file : files) {
                try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(file)))) {
                    out.putNextEntry(new ZipEntry(file));
                    int tmp = 0;
                    while ((tmp = in.read()) != -1) {
                        bufferOut.write(tmp);
                    }
                }
            }
        }
    }

    public static void zipFileWithChannel(String zipFile, Iterable<String> files) throws IOException {
        if (files == null) throw new IllegalArgumentException("no file to zip");
        File zip = new File(zipFile);
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
             WritableByteChannel writableByteChannel = Channels.newChannel(out)) {
            for (String file : files) {
                File f = new File(file);
                try (FileChannel fileChannel = new FileInputStream(f).getChannel()) {
                    out.putNextEntry(new ZipEntry(file));
                    fileChannel.transferTo(0, f.length(), writableByteChannel);
                }
            }
        }
    }

    public static void zipFileWithMappedByteBuffer(String zipFile, Iterable<String> files) throws IOException {
        if (files == null) throw new IllegalArgumentException("no file to zip");
        File zip = new File(zipFile);
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
             WritableByteChannel writableByteChannel = Channels.newChannel(out)) {
            for (String file : files) {
                out.putNextEntry(new ZipEntry(file));
                File f = new File(file);
                final MappedByteBuffer mappedByteBuffer = new RandomAccessFile(f, "r").getChannel().map(FileChannel.MapMode.READ_ONLY, 0, f.length());
                writableByteChannel.write(mappedByteBuffer);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String zipFile = "/Users/albert/Downloads/thunder.zip";
        Set<String> files = new HashSet<>();
        files.add("/Users/albert/Downloads/thunder_3.4.1.4368.dmg");
        long start = System.currentTimeMillis();
        long end = start;
//        zipFileWithoutBuffer(zipFile, files); // 36s
//        zipFileWithBuffer(zipFile, files);  // 1.7s
//        zipFileWithChannel(zipFile, files);  // 926ms
//        zipFileWithMappedByteBuffer(zipFile, files); // 916ms
        end = System.currentTimeMillis();
        System.out.println((end - start) + " ms");


    }
}
