package cjx.liyueyun.baselib.base.mvp.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cjx.liyueyun.baselib.base.mvp.BaseApplication;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by SongJie on 11/15 0015.
 */
public class Tool {

    /**
     * 检测网络是否连接
     */
    public static boolean isNetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断字符串是否为空或null
     * @param input
     * @return
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equals(input.toLowerCase()) || input.trim().length() == 0)
            return true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为默认值
     */
    public static boolean isDefaule(String str){
        if(str == null || str.contains("unknown") || str.contains("000000") || str.contains("123456") || str.contains("abcdef")|| str.contains("ABCDEF") || str.contains("null")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * MD5加密
     * @param s
     * @return
     */
    public static String get32MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            // 使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断某个服务是否正在运行的方法
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * 获取硬件信息
     * android.os.Build.Model 获得型号
     * android.os.Build.BRAND 获得品牌
     * android.os.Build.MANUFACTURER 硬件厂商
     * android.os.Build.PRODUCT	手机厂商
     */
    public static String getDeviceInfo(){
        String deviceStr = "";
        String board = Build.BRAND;
        if(!Tool.isEmpty(board)){
            deviceStr = board+"电视";
        }else{
            deviceStr = "我的电视";
        }
//        String model  = Build.MODEL;
//        if(!Tool.isEmpty(model)){
//            if(model.contains(deviceStr)){
//                deviceStr = model;
//            }else {
//                deviceStr = deviceStr + " " + model;
//            }
//        }
//        if(Tool.isEmpty(deviceStr)){
//            deviceStr = "TV " + getCurrentFormatDate("yyyyMMdd");
//        }
        return deviceStr;
    }

    /**
     * 返回app运行状态
     * 1:程序在前台运行
     * 2:程序在后台运行
     * 3:程序未启动
     * 注意：需要配置权限<uses-permission android:name="android.permission.GET_TASKS" />
     */
    public static int getAppSatus(Context context, String pageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(50);

        //判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals(pageName)) {
            return 1;
        } else {
            //判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(pageName)) {
                    return 2;
                }
            }
            return 3;//栈里找不到，返回3
        }
    }

    /**
     * 获取当前客户端版本号
     */
    public static int getVersionCode(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }
    /**
     * 获取当前客户端版本号
     */
    public static String getVersionName(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * 返回当前时间yyyyMMddHHmmss,pattern格式
     */
    public static String getCurrentFormatDate(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if(pattern.contains("yyyy-MM-dd\'T\'HH")){
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }

    /**
     * 返回当前时间yyyyMMddHHmmss,pattern格式
     */
    public static Date getDate(String data,String pattern) {
        Date time = null;
        SimpleDateFormat format;
        if(pattern == null){
            format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }else if(pattern.contains("yyyy-MM-dd\'T\'HH")){
            format = new SimpleDateFormat(pattern);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
        }else{
            format = new SimpleDateFormat(pattern);
        }
        //格式化时间
        try {
            time=format.parse(data);
        }catch (Exception ex){

        }
        return time;
    }

    /**
     * 得到一个时间延后或前移几天的时间,delay为前移或后延的天数
     */
    public static Date getNextDay(int delay) {
        Date d = new Date();
        long myTime = (d.getTime() / 1000) + delay * 24 * 60 * 60;
        d.setTime(myTime * 1000);
        return d;
    }

    /**
     * 得到一个时间延后的时间,delay为前移或后延的秒
     * "yyyy-MM-dd HH:mm:ss"
     * delay 单位为S
     */
    public static String getNewUpdataTime(String updatatime) {
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'");
        try {
            Date time=format.parse(updatatime);
            time.setTime(time.getTime() + 1);
            return format.format(time);
        }catch (Exception ex){
            return null;
        }
    }

    /**
     * 得到一个时间延后的时间,delay为前移或后延的秒
     * "yyyy-MM-dd HH:mm:ss"
     * delay 单位为S
     */
    public static String getDelayTime(Date date,int delay) {
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long myTime = (date.getTime() / 1000) + delay;
            date.setTime(myTime * 1000);
            return format.format(date);
        }catch (Exception ex){
            return null;
        }
    }

    /**
     * 得到一个时间延后或前移几天的时间,delay为前移或后延的天数
     */
    public static String getDay(String hour,String minute) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date d = new Date();
        long myTime = Integer.valueOf(hour) * 60 * 60 + Integer.valueOf(minute) * 60;
        d.setTime(myTime * 1000);
        return format.format(d);
    }

    /**
     * 判断屏幕是否是超高清
     */
    public static boolean isUHDScreen(){
        DisplayMetrics dm = BaseApplication.getAppContext().getResources().getDisplayMetrics();
        if(dm.widthPixels>1920 && dm.heightPixels>1080){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取屏幕相对1920的宽度
     */
    public static int getDimenWidth(Context context,int width){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels * width/1920;
    }
    /**
     * 获取屏幕相对1080的宽度高度
     */
    public static int getDimenhight(Context context,int hight){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels * hight/1080;
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断应用是否在运行
     * boolean true为在运行，false为已结束
     */
    public static boolean isRuning(Context context, Intent intent) {
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (tasks.size() > 0 && tasks.get(0).baseActivity.equals(intent.getComponent())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取文件存储路径
     */
    public static String getSavePath(String folderName) {
        String folderPath;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            folderPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/"+ folderName +"/";
        }else {
            folderPath = BaseApplication.getAppContext().getFilesDir().getAbsolutePath()+ "/"+ folderName +"/";
        }
        File file = new File(folderPath);
        if(!file.exists()) {
            file.mkdirs();
        }
        return folderPath;
    }

    /**
     * 获取进程名
     */
    public static String getProcessName(Context context){
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null && !runningApps.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        }
        return "";
    }


    /**
     * Drawable转Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     *  URL转文件md5的名称
     * 将字符串转成MD5值
     */
    public static String urlToMD5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 删除文件前先重命名,解决fat32系统删除后无法再创建的问题
     */
    public static void deleteFile(File file){
        File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
        file.renameTo(to);
        if (to.isDirectory()) {
            String[] children = to.list();
            for (int i = 0; i < children.length; i++) {
                File temp = new File(to, children[i]);
                if (temp.isDirectory()) {
                    deleteFile(temp);
                } else {
                    logUtil.d_3("deleteFile 方法 :", "删除:"+temp.getPath());
                    boolean b = temp.delete();
                    if (b == false) {
                        logUtil.d_3("deleteFile 方法 :", "删除失败");
                    }
                }
            }
            to.delete();
        }
    }

    /**
     * 根据保存时间判断是否到服务器更新数据,10分钟内不更新
     */
    public static boolean needUpdata(Long savetime){
        Long lengthTime = System.currentTimeMillis() - savetime;
        if(lengthTime > 10 * 60 * 1000){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据正则返回截取内容
     */
    public static String getStringByType(String content, String type){
        Pattern p = Pattern.compile(type);
        Matcher m = p.matcher(content);
        if(m.find()){
            return m.group();
        }
        return "";
    }


    /**
     * 获取API访问的错误信息
     */
    public static String getApiErrorMsg(String message){
        String errStr = Tool.getStringByType(message, "(?<=error\":\")([^\"]*)");
        if(Tool.isEmpty(errStr)){
            errStr = Tool.getStringByType(message, "(?<=message\":\")([^\"]*)");
        }
        if(Tool.isEmpty(errStr)){
            errStr = message;
        }
        return errStr;
    }


    /**
     * 获取当前上下文的屏幕宽度
     *
     * @param activity 上下文
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获取当前上下文的屏幕高度
     *
     * @param activity 上下文
     * @return 屏幕高度
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    /**
     *  获取URL里面的MD5
     */
    public static String getMd5InUrl(String url){
        String[] strUrlList = url.split("/");
        for(int i=0;i<strUrlList.length;i++){
            if(strUrlList[i].length() == 32 && !strUrlList[i].contains(".")){
                return strUrlList[i];
            }
        }
        return null;
    }

    /**
     * 获取视频文件截图
     */
    public  static Bitmap getVideoThumb(String path) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据文件路径获取缩略图
            retriever.setDataSource(path, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return  bitmap;
    }
}

