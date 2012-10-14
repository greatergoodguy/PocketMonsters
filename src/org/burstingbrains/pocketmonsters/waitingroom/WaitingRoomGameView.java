package org.burstingbrains.pocketmonsters.waitingroom;

import org.burstingbrains.pocketmonsters.R;
import org.burstingbrains.pocketmonsters.WaitingRoomActivity.WaitingRoomHandler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WaitingRoomGameView extends LinearLayout{

	private WaitingRoomHandler handler;
	
	private ViewGroup waitingRoomGame;
	
	private TextView gameNameTextView;
	private Button p1SitButton;
	private Button p2SitButton;
	private TextView p1ReadyTextView;
	private TextView p2ReadyTextView;
	
	public WaitingRoomGameView(Context context, final WaitingRoomHandler handler) {
		super(context);

		this.handler = handler;
		
		waitingRoomGame = (ViewGroup) View.inflate(context, R.layout.waiting_room_game_viewgroup, this);
		
		gameNameTextView = (TextView) waitingRoomGame.findViewById(R.id.game_name_textView);
		
		p1ReadyTextView = (TextView) waitingRoomGame.findViewById(R.id.p1_ready_textView);
		p1SitButton = (Button) waitingRoomGame.findViewById(R.id.p1_sit_button);
		p1SitButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!handler.isPlayerSatDown() || handler.getActiveButton() == p1SitButton){
					toggleSitButton();
				}
			}

			private void toggleSitButton() {
				if(p1SitButton.getText().toString().equals("Sit")){
					handler.setActiveSitButton(p1SitButton);
					p1SitButton.setText("Unsit");
				}
				else if(p1SitButton.getText().toString().equals("Unsit")){
					handler.deactiveSitButton();
					p1SitButton.setText("Sit");
				}
			}
		});
		
		p2ReadyTextView = (TextView) waitingRoomGame.findViewById(R.id.p2_ready_textView);
		p2SitButton = (Button) waitingRoomGame.findViewById(R.id.p2_sit_button);
		p2SitButton.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				if(!handler.isPlayerSatDown() || handler.getActiveButton() == p2SitButton){
					toggleSitButton();
				}
			}

			private void toggleSitButton() {
				if(p2SitButton.getText().toString().equals("Sit")){
					handler.setActiveSitButton(p2SitButton);
					p2SitButton.setText("Unsit");
				}
				else if(p2SitButton.getText().toString().equals("Unsit")){
					handler.deactiveSitButton();
					p2SitButton.setText("Sit");
				}
			}
		});
		
	}

	public void updateView(WaitingRoomGameModel waitingRoomGameModel) {
		gameNameTextView.setText("Game " + waitingRoomGameModel.gameId);
		
		if(waitingRoomGameModel.p1Ready.equals("true")){
			p1ReadyTextView.setVisibility(View.VISIBLE);
			p1SitButton.setVisibility(View.INVISIBLE);
		}
		else if(waitingRoomGameModel.p1Ready.equals("false")){
			p1ReadyTextView.setVisibility(View.INVISIBLE);
			p1SitButton.setVisibility(View.VISIBLE);
		}
		
		if(waitingRoomGameModel.p2Ready.equals("true")){
			p2ReadyTextView.setVisibility(View.VISIBLE);
			p2SitButton.setVisibility(View.INVISIBLE);
		}
		else if(waitingRoomGameModel.p2Ready.equals("false")){
			p2ReadyTextView.setVisibility(View.INVISIBLE);
			p2SitButton.setVisibility(View.VISIBLE);
		}
	}

}
