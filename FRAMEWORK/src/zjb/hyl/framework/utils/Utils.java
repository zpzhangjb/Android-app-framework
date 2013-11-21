package zjb.hyl.framework.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import zjb.hyl.framework.R;
import zjb.hyl.framework.net.MStrOperate;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.telephony.SmsManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

public class Utils {

	public static boolean DEBUG = false;
	public static final String DIRECTORY;
	public static final String PICTUREDIR;
	private static Bitmap b;
	private static byte[] bt;
	public static final int MAX_TEXT_INPUT_LENGTH = 160;
	public static final DisplayImageOptions default_person_icon_options = new DisplayImageOptions.Builder()
//	.showStubImage(R.drawable.navigation_default_headpic)
//	.showImageForEmptyUri(R.drawable.navigation_default_headpic)
//	.showImageOnFail(R.drawable.navigation_default_headpic)
	.cacheInMemory()
	.cacheOnDisc()
	.displayer(new SimpleBitmapDisplayer())
	.build();

	
	public static DisplayImageOptions getOptions(int resId){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showStubImage(resId)
		.showImageForEmptyUri(resId)
		.showImageOnFail(resId)
		.cacheInMemory()
		.cacheOnDisc()
		.displayer(new SimpleBitmapDisplayer())
		.build();
		return options;
	}
	static {
		DIRECTORY = "/chanjet/";
		PICTUREDIR = DIRECTORY + ".pic/singin/";
	}

	public static String getCachePath() {
		String SD = getSdcard();
		if (SD != null) {
			return SD + PICTUREDIR;
		}
		return null;
	}

	/**
	 * 要更加准确的匹配手机号码只匹配11位数字是不够的，比如说就没有以144开始的号码段，
	 * 
	 * 　　 * 故先要整清楚现在已经开放了多少个号码段，国家号码段分配如下：
	 * 
	 * 　　 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	 * 
	 * 　　 * 联通：130、131、132、152、155、156、185、186
	 * 
	 * 　　 * 电信：133、153、180、189、（1349卫通）
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		if (MStrOperate.hasValue(mobiles)) {
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			return m.matches();
		}
		return false;
	}

	public static boolean isNumber(String text) {
		if (MStrOperate.hasValue(text)) {
			Pattern pattern = Pattern.compile("^[0-9]*$");
			Matcher matcher = pattern.matcher(text);
			return matcher.matches();
		}
		return false;
	}

	/**
	 * 发短信
	 * 
	 * @param context
	 * @param numbers
	 * @param content
	 */
	public static void sendMsg(Context context, String numbers, String content) {
		SmsManager sm = SmsManager.getDefault();
		String[] nums = numbers.split(",");
		for (String num : nums) {
			PendingIntent send = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
			sm.sendTextMessage(num, null, content, send, null);
			Log.i("TAG", "number:" + num);
		}
	}

	/**
	 * 获取Sdcard缓存目录
	 */

	public static String getFileDir() {
		if (getSdcard() != null) {
			return getSdcard() + DIRECTORY;
		}
		return null;
	}

	/**
	 * 创建进度条对话框
	 * 
	 * @param context
	 * @param resId
	 *            文本Id
	 * @param isCancel
	 *            是否能取消
	 * @return
	 */
	/*public static Dialog createProgressDialog(Context context, int resId, boolean isCancel) {
		Dialog dialog = new Dialog(context, R.style.TransparentDialog);
		dialog.setContentView(R.layout.progressing);
		TextView tvContentText = (TextView) dialog.findViewById(R.id.tv_progress_text);
		tvContentText.setText(resId);
		dialog.setCancelable(isCancel);

		Window window = dialog.getWindow();
		WindowManager.LayoutParams wl = window.getAttributes();

		wl.gravity = Gravity.CENTER;
		wl.flags = wl.flags | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
		window.setAttributes(wl);

		return dialog;
	}*/

	/**
	 * 检测网络状态
	 * 
	 * @return If the network normally returns true, otherwise false
	 */
	public static boolean isNetWorking(Context context) {
		ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		cwjManager.getActiveNetworkInfo();
		if (cwjManager.getActiveNetworkInfo() != null) {
			return cwjManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}

	/**
	 * 根据路径创建文件
	 * 
	 * @param path
	 *            filepath
	 * @return
	 */
	public static File newInstanceFile(String path) {
		return new File(path);
	}

	/**
	 * 根据路径判断文件是否存在
	 * 
	 * @param path
	 *            filepath
	 * @return If there are returns true, otherwise false
	 */
	public static boolean isExist(String path) {
		return new File(path).exists();
	}

	/**
	 * 获取Sdcard的目录
	 * 
	 * @return 如果存在返回ExternalStorageDirectory,不存在则返回null
	 */
	public static File sdcard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory();
		}
		return null;
	}

	/**
	 * 获取Sdcard的目录
	 * 
	 * @return
	 */
	public static String getSdcard() {
		if (sdcard() != null) {
			return sdcard().getAbsolutePath();
		}
		return null;
	}

	/**
	 * 获取文件的名称
	 * 
	 * @param urlpath
	 * @return
	 */
	public static String getFileName(String urlpath) {
		// return urlpath.substring(urlpath.lastIndexOf("/") + 1);
		return urlpath.replaceAll("/", "_");
	}

	/**
	 * 获取网络图片的本地保存路径
	 * 
	 * @param urlpath
	 *            网络URL
	 * @return 返回本地保存路径
	 */
	public static String getFilePath(String urlpath) {

		if (Utils.getSdcard() != null) {
			return Utils.getSdcard() + PICTUREDIR + Utils.getFileName(urlpath);
		} else {
			return null;
		}
	}

	public static boolean copy(InputStream is, OutputStream os) {
		if (is == null) {
			return false;
		}
		try {
			byte[] b = new byte[524288];
			while (true) {
				int i = is.read(b);
				if (i == -1) {
					break;
				}
				os.write(b, 0, i);
				os.flush();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 判断SDcard是否挂载
	 * 
	 * @return
	 */
	public static boolean hasSDcardMounted() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 文件或目录是否存在
	 * 
	 * @param file
	 *            文件或目录
	 * @return
	 */
	public static boolean isExisted(File file) {
		if (file != null && file.exists())
			return true;
		return false;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getMinSec(String famtt) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(famtt);
		return sdf.format(date);
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
		String str = format.format(date);
		return str;
	}
	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr2(Date date) {
		if(date == null){
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm");
		String str = format.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}


	public static boolean isEmpty(String str) {
		if ("".equals(str) || str == null) {
			return true;
		}
		return false;
	}

	/**
	 * 从服务器获取客户端安装包
	 * 
	 * @param filepath
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public static File getFileFromServer(String filepath, ProgressDialog pd) throws Exception {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			URL url = new URL(filepath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			// 获取到文件的大小
			pd.setMax(conn.getContentLength() / 1024);
			InputStream is = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory(), "开发者社区.apk");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);

			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				pd.setProgress(total / 1024);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}

	/**
	 * 从一个字符串中删除指定字符
	 * 
	 * @param source
	 * @param oldc
	 * @return
	 */
	public static String deleteAll(String source, char oldc) {
		StringBuffer sbu = new StringBuffer();
		// 字符串的长度
		int lenOfsource = source.length();
		// 指定字符在字符串中的位置
		int i;
		// 从指定位置开始寻找
		int posStart;
		// int indexOf(char ch,int intStart)
		// 如果ch在字符串中的位置大于intStart
		// 返回实际的位置
		// 否则返回0或者-1
		// 此方法原理:
		// 从字符串的第一个位置开始找起
		// 如果找到oldc,就截取从posStart开始到i结束的字符串
		// substring(int begin,int end)不包括结束时的字符
		// 这样就删除了第一次找到的指定字符
		// 由于后面的指定字符肯定在第一次找到的指定字符后面
		// 所以再对指定位置posStart在第一次找到的位置上加1
		// posStart=i+1
		for (posStart = 0; (i = source.indexOf(oldc, posStart)) >= 0; posStart = i + 1) {
			sbu.append(source.substring(posStart, i));
		}

		// 经过上一次循环删除了所有的指定字符
		// 但如果在此字符后面还有字符串
		// 则也要截取到
		// 此时posStart的值是最后一个指定字符的位置+1
		// 如果posStart小于字符串长度,则肯定还有未添加的字符串
		// 所以再加上一个判断
		if (posStart < lenOfsource) {
			sbu.append(source.substring(posStart));
		}

		return sbu.toString();

	}

	/**
	 * 从一个字符串中删除指定子字符串
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String delString(String str1, String str2) {
		int i = 0;
		int j = 0;
		StringBuffer sb = new StringBuffer(str1);
		for (i = 0; i < sb.length();) {// 这里for循环组后一个位置为空哈
			j = sb.indexOf(str2, i);
			if (j != -1)
				sb.delete(j, j + str2.length());// 当删除了一个之后就从这个字符串的下一个开始了哈，这样也就节约了查找的次数
			i = j + str2.length();
			if (j == -1) {
				i = sb.length();
			}
		}
		return sb.toString();
	}

	/**
	 * 判断某子字符串是否在某字符串中
	 * 
	 * @param str1
	 *            父字符串
	 * @param str2
	 *            子字符串
	 * @return
	 */
	public static boolean isContain(String str1, String str2) {
		return str1.startsWith(str2);
	}

	// 过滤特殊字符
	public static String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 判断如果输入的用户名的首个字符为"*"则表示进入测试环境
	 * 
	 * @param parent
	 * @param child
	 * @return
	 */
	public static boolean istest(String parent, String child) {
		String substring = parent.substring(0, 1);
		if ("*".equals(substring)) {
			return true;
		} else {
			return false;
		}
	}

	public static Bitmap loadImageFromUrl(String url) {
		URL m;
		InputStream i = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
			/*
			 * Drawable d = Drawable.createFromStream(i, "src"); BitmapDrawable
			 * bd = (BitmapDrawable)d; Bitmap b = bd.getBitmap();
			 */
			// 防止图片过大内存溢出
			Bitmap b = BitmapFactory.decodeStream(i);
			return b;
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 以最省内存的方式读取资源的图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */

	/**
	 * 压缩图片
	 * 
	 * @param fromFile
	 *            图片路径
	 * @param limit
	 *            图片限制大小
	 * @return
	 */
	public static String compressImage(String fromFile, int limit) {
		Log.d("compressImage", "fromFile " + fromFile);
		InputStream ios = null;
		if (!TextUtils.isEmpty(fromFile)) {
			String rs = fromFile;
			File file = new File(fromFile);
			if (file != null && file.isFile() && file.length() / 1024 <= 1024) {
				Log.d("compressImage", "less 1M " + file.length() / 1024);
				return rs;
			}
			try {

				ios = new FileInputStream(fromFile);
				BitmapFactory.Options bmOptions = new BitmapFactory.Options();
				bmOptions.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(ios, null, bmOptions);
				int quality = 100;
				Bitmap resizeBitmap = null;
				// int bitmapWidth = bitmap.getWidth();
				// int bitmapHeight = bitmap.getHeight();
				int bitmapWidth = bmOptions.outWidth;
				int bitmapHeight = bmOptions.outHeight;
				if (bitmapWidth > limit || bitmapHeight > limit) {
					// // 缩放图片的尺寸
					float scale = (float) limit / (bitmapWidth > bitmapHeight ? bitmapWidth : bitmapHeight);
					bmOptions.inSampleSize = computeSampleSize(bmOptions, -1, (int) (bitmapWidth * scale)
							* (int) (bitmapHeight * scale));
					bmOptions.inJustDecodeBounds = false;
					// bmOptions.inSampleSize = 2;
					bmOptions.outWidth = (int) (bitmapWidth * scale);
					bmOptions.outHeight = (int) (bitmapHeight * scale);
					resizeBitmap = BitmapFactory.decodeFile(fromFile, bmOptions);
				}
				// save file
				String destFilePath = fromFile.substring(0, fromFile.lastIndexOf(".")) + "_scale" + ".png";
				File myCaptureFile = new File(destFilePath);
				if (myCaptureFile == null)
					return rs;
				FileOutputStream out = new FileOutputStream(myCaptureFile);
				if (resizeBitmap != null) {
					resizeBitmap.compress(Bitmap.CompressFormat.PNG, quality, out);
					out.flush();
					out.close();
				}

				if (resizeBitmap != null && !resizeBitmap.isRecycled()) {
					resizeBitmap.recycle();// 释放资源，否则会内存溢出
				}
				Log.e("ImageHelper", "myCaptureFile.length() " + myCaptureFile.length());
				if (myCaptureFile.exists() && myCaptureFile.length() > 0) {
					return destFilePath;
				}
			} catch (Exception ee) {
			}
			Log.e("ImageHelper", "<<<|||><<<<|||>< finish");
			return rs;
		} else {
			Log.e("ImageHelper", "<<< null <<<< ", new Throwable());
			return null;
		}
	}

	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength),
				Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static String comeFrom(int f) {
		String form = "开发者社区桌面端";
		if (f == 0) {
			form = "网页版";
		} else if (f == 1) {
			form = "iPhone端";
		} else if (f == 2) {
			form = "Android端";
		} else if (f == 3) {
			form = "WinPhone端";
		}
		return form;
	}

	public static String cutString(String str, int a) {
		String temp = "";
		/*int k = 0;
		for (int i = 0; i < str.length(); i++) {
			byte[] b = (str.charAt(i) + "").getBytes(); // 每循环一次，将str里的值放入byte数组
			k = k + b.length;
			if (k > a) { // 如果数组长度大于6，随机跳出循环
				break;
			}
			temp = temp + str.charAt(i); // 拼接新字符串
		}
		if (k > a) {
			temp += "…";
		}*/
		if(str.length() > 4) {
			temp = str.substring(0, 3) + "...";
		}else{
			temp = str;
		}
		return temp;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static String readInStream(Context context, String txtname) {
		FileInputStream inStream = null;
		StringBuffer sbuffer = new StringBuffer();
		try {
			inStream = context.openFileInput(txtname);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
				sbuffer.append(outStream.toString());
			}
			outStream.close();
			inStream.close();
			return outStream.toString();
		} catch (IOException e) {
			Log.i("FileTest", e.getMessage());
		}
		return sbuffer.toString();
	}
	
	public static String MiliontoTime(long time) throws Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateTime = df.format(time);               
		return dateTime;

		
	}
	
	public static String getFileFromServer(String filepath) {
		try{
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				URL url = new URL(filepath);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5000);
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				return in.readLine().replace("[", "").replace("]", "");
			}
		}catch(Exception e){
			Log.d("ssss","e " + e.getLocalizedMessage());
					}
		return "";

	}
	
	/**
	 * 将时间戳转化为多长时间前
	 * @param timestamp
	 * @return
	 */
	public static String converTime(long timestamp) {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - timestamp;// 与现在时间相差秒数
		String timeStr = null;
		if(timeGap <= 0) {
			timeStr = "刚刚";
			return timeStr;
		}else{
			if (timeGap > 24 * 60 * 60) {// 1天以上
				timeStr = format.format(new Date(timestamp * 1000)); // timeGap/(24*60*60)+"天前";
			} else if (timeGap > 60 * 60) {// 1小时-24小时
				timeStr = timeGap / (60 * 60) + "小时前";
			} else if (timeGap > 60) {// 1分钟-59分钟
				timeStr = timeGap / 60 + "分钟前";
			} else {// 1秒钟-59秒钟
				timeStr = timeGap + "秒前";
			}
			return timeStr;
		}
	}

	
	/**
	 * 将Byte转化为KB，MB
	 */
	public static String fileSize(String size){
		if(TextUtils.isDigitsOnly(size)){
			DecimalFormat df = new DecimalFormat("###.##");
			float f;
			Long longSize = Long.parseLong(size);
			if(longSize < 1024*1024){
				f = (float)((float)longSize/(float)1024);
				return(df.format(new Float(f).doubleValue()) + "KB");
			}else{
				f = (float)((float)longSize/(float)(1024*1024));
				return(df.format(new Float(f).doubleValue()) + "MB");
			}
		}else{
			return size + "B";
		}
	}
	
	/**
	 * 
	 * 关键字高亮显示
	 * @param context
	 * @param spannableString
	 * @param keywords
	 */
	public static void highlightContent(Context context,SpannableString spannableString,String keywords) {
		Matcher emotions = Pattern.compile(keywords).matcher(spannableString);

		while (emotions.find()) {
			
			spannableString.setSpan(new ForegroundColorSpan(Color.rgb(255, 0, 0)), emotions.start(), emotions.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//			spannableString.setSpan(new StyleSpan(Typeface.BOLD), emotions.start(), emotions.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
	}
}
