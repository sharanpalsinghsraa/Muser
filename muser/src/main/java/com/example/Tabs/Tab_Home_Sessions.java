package com.example.Tabs;


import java.util.ArrayList;
import java.util.HashMap;

import com.example.Adapter.Trending_Videos_Adapter;
import com.example.ProgressTask.Get_Home_Pictures_Videos_ProgressTask;
import com.example.ProgressTask.Get_Pictures_Videos_ProgressTask;
import com.example.classes.Global;
import com.ameba.muser.Drawer;
import com.ameba.muser.R;
import com.ameba.muser.R.layout;
import com.example.classes.Util_Class;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Tab_Home_Sessions extends Fragment 
{
	Context con;
	public static GridView gridView;
	PullToRefreshGridView mPullRefreshGridView;
	public static TextView error_message;
	SharedPreferences rem_pref;
	Tab_Home_Sessions con2;
	
	
	ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String, String>>();
	Trending_Videos_Adapter adapter;
	
	ImageView temp_logo;
	//String s="Session's are only available with paid <\br> subscriptions, if  you would like access to a<\br> trainer(s) session's <Html> <b>click here...</b></Html";


	View rootView;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{

		
		con = getActivity();
		con2=this;
		//list.clear();
		
		//text.setText(Html.fromHtml(s));

		if(savedInstanceState==null)
		{
			rootView = inflater.inflate(R.layout.custom_common_gridview, container, false);

			rem_pref = con.getSharedPreferences("Remember", con.MODE_WORLD_READABLE);
			mPullRefreshGridView = (PullToRefreshGridView) rootView.findViewById(R.id.gridView);
			gridView = mPullRefreshGridView.getRefreshableView();
			error_message = (TextView) rootView.findViewById(R.id.error_message);
			temp_logo = (ImageView) rootView.findViewById(R.id.temp_logo);

		}
			mPullRefreshGridView.setOnRefreshListener(new OnRefreshListener2<GridView>()
			{

				@Override
				public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView)
				{
					refresh();
				}

				@Override
				public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView)
				{
					refresh();
				}

			});
		
		return rootView;
	}

	@Override public
	void onResume()
	{
		super.onResume();

		Log.e("onResume:Tab_Home_Sessions", rem_pref.getString("current_frag", ""));
		if (rem_pref.getString("current_frag","").equals("Home"))
		{

			//list.clear();
			refresh();
		}

	}
	
	public void refresh()
	{
		
		if(list.size()==0)
		{
			show_temp_logo();
			error_message.setText(con.getResources().getString(R.string.please_wait));
			
		}
		Global.set_user_id(rem_pref.getString("user_id", ""));
		Log.e("Where", "Home Sessions");




		/*if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB )
		{
			new Get_Home_Pictures_Videos_ProgressTask(con,con2, Global.get_user_id(), "V","S").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
		else
		{
			new Get_Home_Pictures_Videos_ProgressTask(con,con2, Global.get_user_id(), "V","S").execute();
		}*/


		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB )
		{
			new Get_Pictures_Videos_ProgressTask(con, con2, Global.get_user_id(), "V", "").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
		else
		{
			//			Global.get_user_id()
			new Get_Pictures_Videos_ProgressTask(con, con2,Global.get_user_id(), "V", "").execute();
		}
	}


	
	
	public void set_data(ArrayList<HashMap<String, String>> list)
	{

		hide_temp_logo();
		//if(this.list.size() == 0)
		if(adapter==null)
		{

			this.list = list;
			adapter = new Trending_Videos_Adapter(con,con2, list);
			gridView.setAdapter(adapter);
		}
		else
		{
			this.list = list;
			adapter.add_data(this.list);
			adapter.notifyDataSetChanged();
		}

	}
	
	public void on_Failure()
	{
		
		show_temp_logo();
		error_message.setText(con.getResources().getString(R.string.no_data_found));
	}
	
	
	
	public void refresh_complete()
	{
		if(mPullRefreshGridView.isRefreshing())
			mPullRefreshGridView.onRefreshComplete();
	}
	
	private void show_temp_logo()
	{
		temp_logo.setVisibility(View.VISIBLE);
		error_message.setVisibility(View.VISIBLE);
		mPullRefreshGridView.setVisibility(View.GONE);
		
	}
	
	private void hide_temp_logo()
	{
		temp_logo.setVisibility(View.GONE);
		error_message.setVisibility(View.GONE);
		mPullRefreshGridView.setVisibility(View.VISIBLE);
	}
}