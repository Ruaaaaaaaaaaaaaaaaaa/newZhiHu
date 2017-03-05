package com.wmj.newzhihu.utils;

import android.content.Context;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WmjUtils {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	* 将px值转换为sp值，保证文字大小不变
	* 
	* @param pxValue
	* @param fontScale
	*            （DisplayMetrics类中属性scaledDensity）
	* @return
	*/
	public static int px2sp(Context context, float pxValue) {
	final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
	return (int) (pxValue / fontScale + 0.5f);
	}


	/**
	* 将sp值转换为px值，保证文字大小不变
	* 
	* @param spValue
	* @param fontScale
	*            （DisplayMetrics类中属性scaledDensity）
	* @return
	*/
	public static int sp2px(Context context, float spValue) {
	final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
	return (int) (spValue * fontScale + 0.5f);
	}
	
	/**
	 * Toast工具类
	 */
	
	private static Toast toast;

	public static void showToast(Context context, String content) {
		if (toast == null) {
			toast = Toast.makeText(context,content,Toast.LENGTH_SHORT);
	    } else {
	    	toast.setText(content);
	    }
	    toast.show();
	}
	
	/**
	 * 毫秒 时间转化为 标准格式时间
	 */
	public static String toDate(long time){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(time);
		return formatter.format(date);
	}
	
	public static Long toLong(String time) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return 	sdf.parse(time).getTime();
	}
}
