package org.example.common.helper;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * ZIP 压缩解压工具类
 */
@Slf4j
public class ZipHelper {

    /**
     * 压缩多个文件
     */
    public static void zip(Iterable<File> srcfile, File zipfile) {
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));) {
            byte[] buf = new byte[1024];
            for (File file : srcfile) {
                try (FileInputStream in = new FileInputStream(file)) {
                    out.putNextEntry(new ZipEntry(file.getName()));
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    out.closeEntry();
                } catch (Exception e) {
                    log.error("zip error", e);
                }
            }
        } catch (Exception e) {
            log.error("zip error", e);
        }
    }

    /**
     * 解压文件
     */
    public static void unZip(File zipfile, String descDir) {
        try {
            ZipFile zf = new ZipFile(zipfile);
            File desc = new File(descDir);
            if (!desc.isDirectory()) {
                log.error("{} must be a directory", descDir);
                return;
            }

            if (!desc.exists()) {
                desc.mkdirs();
            }

            byte[] buf1 = new byte[1024];
            for (Enumeration entries = zf.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zf.getInputStream(entry);
                OutputStream out = new FileOutputStream(descDir + zipEntryName);
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }
        } catch (Exception e) {
            log.error("unZip error", e);
        }
    }

    /**
     * 功能:
     *
     * @param args
     */
    public static void main(String[] args) {
        //2个源文件
        File f1 = new File("/Users/albert/Desktop/tmp临时库top20表格式转换.xls");
        File f2 = new File("/Users/albert/Desktop/微信_基础数据_20180811-20190211.xlsx");
        List<File> srcfile = new ArrayList<>();
        srcfile.add(f1);
        srcfile.add(f2);

        //压缩后的文件
        File zipfile = new File("/Users/albert/Desktop/3.zip");
        zip(srcfile, zipfile);

        //需要解压缩的文件
        File file = new File("/Users/albert/Desktop/3.zip");
        //解压后的目标目录
        String dir = "/Users/albert/Desktop/1/";
        unZip(file, dir);
    }
}

