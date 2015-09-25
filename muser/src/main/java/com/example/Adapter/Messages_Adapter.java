package com.example.Adapter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ameba.muser.Chat_sharan;
import com.example.classes.RoundedCornersGaganImg;
import com.example.classes.Util_Class;
import com.ameba.muser.Chats;
import com.ameba.muser.R;
import com.rockerhieu.emojicon.EmojiconTextView;

public class Messages_Adapter extends BaseAdapter
{
	
	Context con;
	ArrayList<HashMap<String, String>> list;

	public Messages_Adapter(Context con, ArrayList<HashMap<String, String>> list) 
	{
		this.con = con;
		this.list=list;
	}
	
	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View row, ViewGroup parent) 
	{
		LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		row = inflater.inflate(R.layout.custom_messages_list_item, parent, false);
		
		RoundedCornersGaganImg other_profile_image=(RoundedCornersGaganImg)row.findViewById(R.id.other_profile_image);
		TextView other_user_name=(TextView)row.findViewById(R.id.other_user_name);
		final EmojiconTextView message=(EmojiconTextView)row.findViewById(R.id.message);
		TextView time=(TextView)row.findViewById(R.id.time);
		
		other_profile_image.setImageUrl(con,list.get(position).get("other_profile_image"));
		
		other_user_name.setText(list.get(position).get("other_user_name"));
		try
		{
			message.setText(Util_Class.emoji_decode(list.get(position).get("last_message")));
		}
		catch(UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Util_Class util=new Util_Class();
		time.setText(util.get_time2(list.get(position).get("timestamp")));
		
		ViewTreeObserver vto = message.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener()
		{

			@Override
			public void onGlobalLayout()
			{
				ViewTreeObserver obs = message.getViewTreeObserver();
				obs.removeGlobalOnLayoutListener(this);
				if(message.getLineCount() > 2)
				{
					int lineEndIndex = message.getLayout().getLineEnd(1);
					String text = message.getText().subSequence(0, lineEndIndex -1) + "...";
					message.setText(text);
				}
			}
		});
		
		
		row.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{

				Intent i = new Intent(con, Chat_sharan.class);
				i.putExtra("img", list.get(position).get("other_profile_image"));
				i.putExtra("id", list.get(position).get("other_user_id"));
				con.startActivity(i);

			}
		});


		
		
		
		return row;
	}

}
