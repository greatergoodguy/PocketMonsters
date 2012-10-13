package org.burstingbrains.pocketmonsters.waitingroom;

import org.burstingbrains.pocketmonsters.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WaitingRoomGameView extends LinearLayout{

	private ViewGroup waitingRoomGame;
	private TextView gameNameTextView;
	
	public WaitingRoomGameView(Context context) {
		super(context);


		waitingRoomGame = (ViewGroup) View.inflate(context, R.layout.waiting_room_game_viewgroup, this);
		gameNameTextView = (TextView) waitingRoomGame.findViewById(R.id.game_name_textView); 
		
	}
	
	public void setGameName(CharSequence gameName){
		gameNameTextView.setText(gameName);
	}

}
