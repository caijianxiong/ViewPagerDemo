package cjx.liyueyun.baselib.base.mvp.utils;

import android.util.Log;

import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SongJie on 11/15 0015.
 */
public class logUtil {
    private static Object mLockFile = new Object();
    private static String folderPath ;
    private static String logFileName;
    private static boolean isWeiteFile = false;
    /**
     * 等级越高打印越多
     * 1级打印提示信息，正式版本
     * 2级打印错误信息，测试版本
     * 3级打印调试信息，程序员调试版本
     */
    private static int debugLevel = 3;

    public static void initLog() {
        String today = Tool.getCurrentFormatDate("yyyyMMdd");
        DateFormat df=new SimpleDateFormat("yyyyMMdd");
        logFileName = today+"logFile.txt";

//        folderPath = Tool.getSavePath(MyConstant.folderNameLog);
        File[] allFiles = new File(folderPath).listFiles();
        if(allFiles != null) {
            for (int i = 0; i < allFiles.length; i++) {
                File file = allFiles[i];
                if (file.isFile()) {
                    try {
                        String fileDate = file.getName().substring(0, 8);
                        Date toDay = df.parse(today);
                        Date fileDay = df.parse(fileDate);
                        if ((toDay.getTime()-fileDay.getTime())/(1000*60*60*24) < 4) {
                            continue;
                        }
                    } catch (Exception e) {
                    }
                    Tool.deleteFile(file);
                }
            }
        }
    }

    public static void setBugLevel(int level){
        debugLevel = level;
        if (level>1){
            isWeiteFile=true;
        }
    }
    public static void d_1(String TAG,String msg){
        String str = "❶cjx❶❶❶❶❶"+msg;
        if(debugLevel>0){
            Log.i(TAG,str);
        }
        writeTxtFile(str, logFileName);
    }

    public static void d_2(String TAG,String msg){
        String str = "❷cjx❷❷❷❷❷"+msg;
        if(debugLevel>1){
            Log.i(TAG,str);
        }
        writeTxtFile(str, logFileName);
    }

    public static void d_3(String TAG,String msg){
        String str = "❸cjx❸❸❸❸❸"+msg;
        if(debugLevel>2){
            Log.i(TAG,str);
        }
        writeTxtFile(str, logFileName);
    }

    public static void d_e(String TAG,Throwable ex){
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String str_error = "@cjx@@@@@@@@"+"name = "+ex.getClass().getName()+"\n message = "+writer.toString();
        Log.e(TAG,str_error);
        writeTxtFile(str_error,logFileName);
    }

    public static void writeTxtFile(String content, String filename) {
        if(isWeiteFile) {
            synchronized (mLockFile) {
                String strContent = Tool.getCurrentFormatDate("yyyy-MM-dd  HH:mm:ss ") + "--" + content + "\n\n";
                try {
                    if (Tool.isEmpty(filename)) {
                        return;
                    }
                    File file = new File(folderPath + filename);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }

                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    RandomAccessFile raf = new RandomAccessFile(file, "rw");
                    raf.seek(file.length());
                    raf.write(strContent.getBytes());
                    raf.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
