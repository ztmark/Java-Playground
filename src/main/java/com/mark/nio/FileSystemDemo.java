package com.mark.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Author: Mark
 * Date  : 15/12/24.
 */
public class FileSystemDemo {

    public static void main(String[] args) throws IOException {
        File testZip = new File("归档.zip");
        File fileToAdd = new File("demo.txt");
        if (testZip.exists() && fileToAdd.exists()) {
//            addFile(testZip, fileToAdd);
            addFileToZip(testZip, fileToAdd);
        }
    }

    /*
    先创建一个tmp文件,然后将原压缩文件中的数据写入tmp文件,再把要加入的文件数据写入tmp文件
    最后删了原压缩文件,并把tmp文件改名为原压缩文件
     */
    private static void addFile(File zip, File file) throws IOException {
        File tmp = File.createTempFile(zip.getName(), null);
        int len = -1;
        try (ZipInputStream input = new ZipInputStream(new FileInputStream(zip));
            ZipOutputStream output = new ZipOutputStream(new FileOutputStream(tmp))) {
            ZipEntry entry = input.getNextEntry();
            byte[] buf = new byte[10240];
            while (entry != null) {
                String name = entry.getName();
                if (!file.getName().equals(name)) {
                    output.putNextEntry(new ZipEntry(name));
                    while ((len = input.read(buf)) != -1) {
                        output.write(buf, 0, len);
                    }
                    output.closeEntry();
                }
                entry = input.getNextEntry();
            }
            try (FileInputStream newFileInput = new FileInputStream(file)) {
                output.putNextEntry(new ZipEntry(file.getName()));
                System.out.println(newFileInput.available());
                while ((len = newFileInput.read(buf)) != -1) {
                    output.write(buf, 0, len);
                }
                output.closeEntry();
            }
            zip.delete();
            tmp.renameTo(zip);
        }
    }

    private static void addFileToZip(File zip, File file) throws IOException {
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        // 这里的URI.create的参数是固定的 jar:+文件的URI
        // jar是jar文件系统的Scheme
        // 在操作压缩文件时,需要通过Paths.get(URI)方法来得到压缩文件
        try (FileSystem fs = FileSystems.newFileSystem(URI.create("jar:" + zip.toURI()), env)) {
            Path path = file.toPath();
            Path pathInZip = fs.getPath("/test/" + file.getName()); // 指定压缩文件中的位置,这里是压缩文件中的test文件夹下
            Files.copy(path, pathInZip, StandardCopyOption.REPLACE_EXISTING);
        }
    }

}
