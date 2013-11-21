package zjb.hyl.framework.widget;

import zjb.hyl.framework.R;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class BaseLayout extends RelativeLayout {

	private View titlebar;

	public ImageView leftButton;
	public TextView rightButton;
	public TextView tvInfo;
	public TextView tv_des;

	public View rlTitle;

	public BaseLayout(Context context, int contentViewId) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService("layout_inflater");
		titlebar = inflater.inflate(R.layout.titlebar, null);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, 57/*getResources()
				.getDimensionPixelOffset(R.dimen.titlebar_height)*/);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		addView(titlebar, params); // 添加头部布局

		params = new RelativeLayout.LayoutParams(-1, -1);
		View contentView = inflater.inflate(contentViewId, null);
		params.addRule(RelativeLayout.BELOW, R.id.rlTitle);
		addView(contentView, params); // 添加内容布局

		leftButton = ((ImageView) findViewById(R.id.tvLeft));
		rightButton = ((TextView) findViewById(R.id.tvRight));
		tvInfo = ((TextView) findViewById(R.id.tvInfo));
		tv_des = ((TextView) findViewById(R.id.tv_des));
		rlTitle = findViewById(R.id.rlTitle);
		titlebar.setVisibility(8);
	}

	public void setButtonTypeAndInfo(int flag, String leftbar, String title, String rightbar, boolean isClickTitle) {
		titlebar.setVisibility(0);
		Resources resources = getResources();
		if (!TextUtils.isEmpty(title)) {
			setTitle(title);
		}
		if (!TextUtils.isEmpty(leftbar)) {
//			leftButton.setText(leftbar);
//			leftButton.setBackgroundResource(R.drawable.back_btn);
		}
		if (!TextUtils.isEmpty(rightbar)) {

				rightButton.setText(rightbar);
		}
	}

	/**
	 * 设置左边的Button
	 */
	public void setLeftButton(boolean needText, String text, int backgroundId) {
		if (titlebar.getVisibility() != View.VISIBLE) {
			titlebar.setVisibility(View.VISIBLE);
		}
		if (needText && !TextUtils.isEmpty(text)) {
//			leftButton.setText(text);
		}
//		leftButton.setBackgroundResource(backgroundId);
		leftButton.setImageResource(backgroundId);
	}

	/**
	 * 设置右边的button
	 */
	public void setRightButton(boolean needText, String text, int backgroundId) {
		if (titlebar.getVisibility() != View.VISIBLE) {
			titlebar.setVisibility(View.VISIBLE);
		}
		if (needText && !TextUtils.isEmpty(text)) {
			rightButton.setText(text);
		}
		rightButton.setBackgroundResource(backgroundId);
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		if (tvInfo != null) {
			tvInfo.setText(title, TextView.BufferType.SPANNABLE);
		}

	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */
	public void setTitle(String title, float textSize, boolean pic, int picResourse) {
		if (tvInfo != null) {
			tvInfo.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置标题背景
	 * 
	 * @param titleBarBackgroundId
	 *            背景图片Id
	 */
	public void setTitleBarBackground(int titleBarBackgroundId) {
		if (titlebar != null)
			titlebar.setBackgroundResource(titleBarBackgroundId);
	}

	public void setTitleBelowInfo(String content) {
		// TODO Auto-generated method stub
		if(tv_des!=null) {
			tv_des.setVisibility(View.VISIBLE);
			tv_des.setText(content);
		}
	}
	public void setTitleBelowInfo() {
		// TODO Auto-generated method stub
		if(tv_des!=null) {
			tv_des.setVisibility(View.GONE);
		}
	}

	public ImageView getLeftButton() {
		// TODO Auto-generated method stub
		return leftButton;
	}
	public TextView getRightButton() {
		// TODO Auto-generated method stub
		return rightButton;
	}
	public View getTitlebar() {
		// TODO Auto-generated method stub
		return titlebar;
	}
	public TextView getTitle() {
		// TODO Auto-generated method stub
		return tvInfo;
	}
	public TextView getTitleBelow() {
		// TODO Auto-generated method stub
		return tv_des;
	}
}
