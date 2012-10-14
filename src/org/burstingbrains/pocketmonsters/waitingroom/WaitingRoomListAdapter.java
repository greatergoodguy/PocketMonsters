package org.burstingbrains.pocketmonsters.waitingroom;

import java.util.ArrayList;
import java.util.List;

import org.burstingbrains.pocketmonsters.WaitingRoomActivity.WaitingRoomHandler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class WaitingRoomListAdapter extends BaseAdapter {

	private final Context applicationContext;
	private final WaitingRoomHandler handler;

	private List<WaitingRoomGameModel> gameModels;

	public WaitingRoomListAdapter(final Context applicationContext, WaitingRoomHandler handler) {
		this.applicationContext = applicationContext;
		this.handler = handler;

		gameModels = new ArrayList<WaitingRoomGameModel>();
	}
	
	public void setGamesList(List<WaitingRoomGameModel> gameModels){
		this.gameModels = gameModels;
	}

	@Override
	public int getCount() {
		return gameModels.size();
	}

	@Override
	public WaitingRoomGameModel getItem(final int position) {
		return (gameModels.get(position));
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {

		WaitingRoomGameView returnView;

		if (convertView == null) {
			returnView = new WaitingRoomGameView(applicationContext, handler);
		} else {
			returnView = (WaitingRoomGameView) convertView;
		}

		returnView.updateView(gameModels.get(position));
		
		return returnView;
	}

}
