package org.burstingbrains.pocketmonsters.waitingroom;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class WaitingRoomListAdapter extends BaseAdapter {

	private final Context applicationContext;

	private List<WaitingRoomGameModel> games;

	public WaitingRoomListAdapter(final Context applicationContext) {
		this.applicationContext = applicationContext;

		games = new ArrayList<WaitingRoomGameModel>();
	}
	
	public void setGamesList(List<WaitingRoomGameModel> games){
		this.games = games;
	}

	@Override
	public int getCount() {
		return games.size();
	}

	@Override
	public WaitingRoomGameModel getItem(final int position) {
		return (games.get(position));
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {

		WaitingRoomGameView returnView;

		if (convertView == null) {
			returnView = new WaitingRoomGameView(applicationContext);
		} else {
			returnView = (WaitingRoomGameView) convertView;
		}

		return returnView;

	}

}
