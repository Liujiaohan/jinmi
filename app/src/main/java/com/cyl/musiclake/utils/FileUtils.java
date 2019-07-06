package com.cyl.musiclake.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class FileUtils {

    private static String getAppDir() {
        return Environment.getExternalStorageDirectory() + "/musicLake/";
    }

    public static String getMusicDir() {
        String dir = getAppDir() + "Music/";
        return mkdirs(dir);
    }


    public static String getMusicCacheDir() {
        String dir = getAppDir() + "MusicCache/";
        return mkdirs(dir);
    }

    public static String getImageDir() {
        String dir = getAppDir() + "cache/";
        return mkdirs(dir);
    }

    public static String getLrcDir() {
        String dir = getAppDir() + "Lyric/";
        return mkdirs(dir);
    }

    public static String getLogDir() {
        String dir = getAppDir() + "Log/";
        return mkdirs(dir);
    }

    public static String getSplashDir(Context context) {
        String dir = context.getFilesDir() + "/splash/";
        return mkdirs(dir);
    }

    public static String getRelativeMusicDir() {
        String dir = "hkMusic/Music/";
        return mkdirs(dir);
    }

    private static String mkdirs(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }



    public static boolean isSDcardAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }


    public static boolean mkDir(String dirPath) {
        String[] dirArray = dirPath.split("/");
        String pathTemp = "";
        boolean mkdir = false;
        for (int i = 0; i < dirArray.length; i++) {
            pathTemp = pathTemp + "/" + dirArray[i];
            File newF = new File(dirArray[0] + pathTemp);
            if (!newF.exists()) {
                mkdir = newF.mkdir();
            }
        }
        return mkdir;
    }



    public static boolean creatFile(String dirPath, String fileName) {
        File file = new File(dirPath, fileName);
        boolean newFile = false;
        if (!file.exists()) {
            try {
                newFile = file.createNewFile();
            } catch (IOException e) {
                newFile = false;
            }
        }
        return newFile;
    }


    public static boolean creatFile(String filePath) {
        File file = new File(filePath);
        boolean newFile = false;
        if (!file.exists()) {
            try {
                newFile = file.createNewFile();
            } catch (IOException e) {
                newFile = false;
            }
        }
        return newFile;
    }


    public static boolean creatFile(File file) {
        boolean newFile = false;
        if (!file.exists()) {
            try {
                newFile = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                newFile = false;
            }
        }
        return newFile;
    }


    public static boolean delFile(String dirpath, String fileName) {
        File file = new File(dirpath, fileName);
        boolean delete = false;
        if (file == null || !file.exists() || file.isDirectory()) {
            delete = false;
        } else {
            delete = file.delete();
        }
        return delete;
    }


    public static boolean delFile(String filepath) {
        File file = new File(filepath);
        boolean delete = false;
        if (file == null || !file.exists() || file.isDirectory()) {
            delete = false;
        } else {
            delete = file.delete();
        }
        return delete;
    }


    public static boolean delFile(File filepath) {
        boolean delete = false;
        if (filepath == null || !filepath.exists() || filepath.isDirectory()) {
            delete = false;
        } else {
            delete = filepath.delete();
        }
        return delete;
    }


    public static boolean delDir(String dirpath) {
        File dir = new File(dirpath);
        return deleteDirWihtFile(dir);
    }

    public static boolean deleteDirWihtFile(File dir) {
        boolean delete = false;
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            delete = false;
        } else {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                } else if (files[i].isDirectory()) {
                    deleteDirWihtFile(files[i]);
                }
                delete = dir.delete();// 删除目录本身
            }
        }
        return delete;
    }



    public static boolean renameFile(String oldFilePath, String newFilePath) {
        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);
        return oldFile.renameTo(newFile);
    }

    public static boolean copyFileTo(String srcFile, String destFile) {
        return copyFileTo(new File(srcFile), new File(destFile));

    }


    public static boolean copyFileTo(File srcFile, File destFile) {
        boolean copyFile = false;
        if (!srcFile.exists() || srcFile.isDirectory() || destFile.isDirectory()) {
            copyFile = false;
        } else {
            FileInputStream is = null;
            FileOutputStream os = null;
            try {
                is = new FileInputStream(srcFile);
                os = new FileOutputStream(destFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                copyFile = true;
            } catch (Exception e) {
                copyFile = false;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return copyFile;
    }


    public static boolean copyFilesTo(File srcDir, File destDir) {
        if (!srcDir.exists() || !srcDir.isDirectory() || !destDir.isDirectory()) {
            return false;
        }
        File[] srcFiles = srcDir.listFiles();

        for (int i = 0; i < srcFiles.length; i++) {
            if (srcFiles[i].isFile()) {
                File destFile = new File(destDir.getAbsolutePath(), srcFiles[i].getName());
                copyFileTo(srcFiles[i], destFile);
            } else {
                File theDestDir = new File(destDir.getAbsolutePath(), srcFiles[i].getName());
                copyFilesTo(srcFiles[i], theDestDir);
            }

        }
        return true;
    }


    public static boolean moveFileTo(File srcFile, File destFile) {
        if (!srcFile.exists() || srcFile.isDirectory() || destFile.isDirectory()) {
            return false;
        }
        boolean iscopy = copyFileTo(srcFile, destFile);
        if (!iscopy) {
            return false;
        } else {
            delFile(srcFile);
            return true;
        }
    }


    public static boolean moveFilesTo(File srcDir, File destDir) {
        if (!srcDir.exists() || !srcDir.isDirectory() || !destDir.isDirectory()) {
            return false;
        }

        File[] srcDirFiles = srcDir.listFiles();
        for (int i = 0; i < srcDirFiles.length; i++) {
            if (srcDirFiles[i].isFile()) {
                File oneDestFile = new File(destDir.getAbsolutePath(), srcDirFiles[i].getName());
                moveFileTo(srcDirFiles[i], oneDestFile);
            } else {
                File oneDestFile = new File(destDir.getAbsolutePath(), srcDirFiles[i].getName());
                moveFilesTo(srcDirFiles[i], oneDestFile);
            }
        }
        return true;
    }



    public static byte[] file2byte(File file) throws IOException {
        byte[] bytes = null;
        if (file != null) {
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            if (length > Integer.MAX_VALUE) {// 当文件的长度超过了int的最大值
                System.out.println("this file is max ");
                is.close();
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            is.close();
            // 如果得到的字节长度和file实际的长度不一致就可能出错了
            if (offset < bytes.length) {
                System.out.println("file length is error");
                return null;
            }
        }
        return bytes;
    }



    public static String readFile(File filePath) {

        BufferedReader bufferedReader = null;
        StringBuilder fileStr = new StringBuilder();
        if (!filePath.exists() || filePath.isDirectory()) {
            return null;
        }
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String tempFileStr = "";

            while ((tempFileStr = bufferedReader.readLine()) != null) {
                fileStr.append(tempFileStr);
                fileStr.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileStr.toString();

    }


    public static String readFile(String strPath) {
        return readFile(new File(strPath));
    }


    public static String readInp(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            int len1;
            while ((len1 = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len1);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException var5) {
        }

        return outputStream.toString();
    }


    public static byte[] inputStreamToByteArray(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            int len1;
            while ((len1 = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len1);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException var5) {
        }

        return outputStream.toByteArray();
    }


    public static String readBuff(BufferedReader bufferedReader) {
        String readerstr = "";
        try {
            String tempstr = "";
            while ((tempstr = bufferedReader.readLine()) != null) {
                readerstr += tempstr;
            }
            return readerstr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static boolean inputStreamToFile(InputStream inputStream, String absPath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(absPath, false);
            fos.write(inputStreamToByteArray(inputStream));
            return true;
        } catch (IOException var7) {
            var7.printStackTrace();
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static InputStream file2Inp(String absPath) {
        File file = new File(absPath);
//        FLogUtils.getInstance().e(file.length());
        if (!file.exists()) {
            return null;
        }
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            return is;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }


    }


    public static boolean writeText(File filePath, String content) {
        creatFile(filePath);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static boolean writeText(String filePath, String content) {
        return writeText(new File(filePath), content);
    }



    public static boolean writeByteArrayToFile(byte[] content, String file_path) {
        try {
            File file = new File(file_path);
            FileOutputStream fileW = new FileOutputStream(file.getCanonicalPath());
            fileW.write(content);
            fileW.close();
        } catch (Exception var4) {
            return false;
        }
        return true;
    }



    public static boolean appendText(File filePath, String content) {
        creatFile(filePath);
        FileWriter writer = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(filePath, true);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return true;
    }



    public static void appendText(String filePath, String content, boolean header) {
        RandomAccessFile raf = null;
        FileOutputStream tmpOut = null;
        FileInputStream tmpIn = null;
        try {
            File tmp = File.createTempFile("tmp", null);
            tmp.deleteOnExit();//在JVM退出时删除

            raf = new RandomAccessFile(filePath, "rw");
            //创建一个临时文件夹来保存插入点后的数据
            tmpOut = new FileOutputStream(tmp);
            tmpIn = new FileInputStream(tmp);
            long fileLength = 0;
            if (!header) {
                fileLength = raf.length();
            }
            raf.seek(fileLength);


            byte[] buff = new byte[1024];
            //用于保存临时读取的字节数
            int hasRead = 0;
            //循环读取插入点后的内容
            while ((hasRead = raf.read(buff)) > 0) {
                // 将读取的数据写入临时文件中
                tmpOut.write(buff, 0, hasRead);
            }
            //插入需要指定添加的数据
            raf.seek(fileLength);//返回原来的插入处
            //追加需要追加的内容
            raf.write(content.getBytes());
            //最后追加临时文件中的内容
            while ((hasRead = tmpIn.read(buff)) > 0) {
                raf.write(buff, 0, hasRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tmpOut != null) {
                try {
                    tmpOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (tmpIn != null) {
                try {
                    tmpIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static long getLength(File filePath) {
        if (!filePath.exists()) {
            return -1;
        } else {
            return filePath.length();
        }
    }


    public static long getLength(String filePath) {
        return getLength(new File(filePath));
    }


    public static String getFileName(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        return file.getName();

    }


    public static boolean exists(String filePath) {
        if (new File(filePath).exists()) {
            return true;
        }
        return false;
    }



    public static File[] orderByDate(File fliePath, boolean desc) {
        File[] fs = fliePath.listFiles();
        Arrays.sort(fs, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;
            }

            public boolean equals(Object obj) {
                return true;
            }

        });
        if (desc) {
            File[] nfs = new File[fs.length];
            for (int i = fs.length - 1; i > -1; i--) {
                nfs[fs.length - 1 - i] = fs[i];
            }
            return nfs;
        } else {
            return fs;
        }
    }


    public static File[] orderByName(File fliePath, boolean desc) {
        File[] files = fliePath.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });

        if (desc) {
            File[] nfs = new File[files.length];
            for (int i = files.length - 1; i > -1; i--) {
                nfs[files.length - 1 - i] = files[i];
            }
            return nfs;
        } else {
            return files;
        }

    }


    public static File[] orderByLength(File fliePath, boolean desc) {
        File[] files = fliePath.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.length() - f2.length();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;
            }

            public boolean equals(Object obj) {
                return true;
            }
        });

        if (desc) {
            File[] nfs = new File[files.length];
            for (int i = files.length - 1; i > -1; i--) {
                nfs[files.length - 1 - i] = files[i];
            }
            return nfs;
        } else {
            return files;
        }
    }



    public static List<File> filter(File[] files, String filter) {
        List<File> filels = new ArrayList<>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(filter)) {
                    filels.add(files[i]);
                }
            }
        }
        return filels;
    }


    public static File[] fileNameFilter(File file, final String filterName) {
        if (!file.isDirectory()) {
            return null;
        }
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().contains(filterName)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        return files;

    }


    public static File[] getFiles(String fileDir) {
        return getFiles(new File(fileDir));
    }


    public static File[] getFiles(File fileDir) {
        if (!fileDir.isDirectory()) {
            return null;
        }
        return fileDir.listFiles();
    }

}
