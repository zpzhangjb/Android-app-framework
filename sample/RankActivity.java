package com.chanjet.ma.zhy.chanjeter.appstore;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.chanjet.ma.zhy.chanjeter.BaseActivity;
import com.chanjet.ma.zhy.chanjeter.R;
import com.chanjet.ma.zhy.chanjeter.models.AppDto;
import com.chanjet.ma.zhy.chanjeter.net.MArrayList;
import com.chanjet.ma.zhy.chanjeter.net.Response;
import com.chanjet.ma.zhy.chanjeter.service.Task;
import com.chanjet.ma.zhy.chanjeter.widget.CustomListView;
import com.chanjet.ma.zhy.chanjeter.widget.TitleBar;


/**
 * 
 * @Description: 排行
 * @author wuxua@chanjet.com
 * @date 2012-11-26 下午4:38:08
 * @version V1.0
 */
public class RankActivity extends BaseActivity implements 
		AdapterView.OnItemClickListener,	
		CustomListView.OnRefreshListener{

	private ArrayList<AppMsg> hotRecommendList;
	private RankAdapter mAdapter;
	private CustomListView mCustomListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.rank_activity);
//		setTitleBar(null, getString(R.string.main_tab_questansw), null,getResources().getStringArray(R.array.questansw_title));
		init();
		connect();
		
	}

	public void init() {
		mCustomListView = (CustomListView) findViewById(R.id.content_listview);
		hotRecommendList = new ArrayList<AppMsg>();
		mAdapter = new RankAdapter(this, hotRecommendList);
		mCustomListView.setAdapter(mAdapter);
		mCustomListView.setonRefreshListener(this);
		mCustomListView.setOnItemClickListener(this);
	}


	private void getActivity(@SuppressWarnings("rawtypes") Class clazz, Bundle b) {

		if (b != null) {

			startActivity(new Intent(this, clazz).putExtras(b));
		} else {
			startActivity(new Intent(this, clazz));
		}

	}

	public void connect() {
		
//		Map<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("url", NetWorkConsts.HOTRECOMMEND_URL);
//		invokeInf(paramMap);
		put(Task.FLAG_HOTRECOMMEND, new MArrayList());
	}

//	@Override
//	public void fillListView() {
//		if (!getMix()) {
//			mAdapter = new RankAdapter(this, hotRecommendList);
//			listView.setAdapter(mAdapter);
//		}
//
//		onLoad();
//	}



	@Override
	public void refresh(Response<?> response) {
		if (response.status == Response.RESULT_OK) {
			AppDto dto = (AppDto) response.result;
			if(dto.Return != null){
				List<AppMsg> data = dto.Return.hotRecommendList;
				if (data != null && data.size() > 0) {
					mAdapter.update(data);
				}
			}
			mCustomListView.onRefreshComplete();
		} else {
//			showToast(R.string.load_error);
		}
	}

	@Override
	protected void handleTitleBarEvent(TitleBar bar) {
		
	}

	@Override
	public void onRefresh(CustomListView customListView) {
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		AppMsg app = (AppMsg) parent.getAdapter().getItem(position);

		Bundle b = new Bundle();
		b.putSerializable("object", app);

		getActivity(AppDetailActivity.class, b);
	}

}
