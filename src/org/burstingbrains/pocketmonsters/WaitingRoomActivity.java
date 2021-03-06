package org.burstingbrains.pocketmonsters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.burstingbrains.pocketmonsters.constants.GameConstants;
import org.burstingbrains.pocketmonsters.singleton.PoolManagerSingleton;
import org.burstingbrains.pocketmonsters.singleton.SimpleDBSingleton;
import org.burstingbrains.pocketmonsters.waitingroom.WaitingRoomGameModel;
import org.burstingbrains.pocketmonsters.waitingroom.WaitingRoomGameView;
import org.burstingbrains.pocketmonsters.waitingroom.WaitingRoomListAdapter;
import org.burstingbrains.sharedlibs.handler.BBSHandler;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

@TargetApi(11)
public class WaitingRoomActivity extends Activity implements GameConstants{

	private static final String DUMMY_STRING = "DummyString";

	private static SimpleDBSingleton database = SimpleDBSingleton.getSingleton();
	private static final PoolManagerSingleton poolManager = PoolManagerSingleton.getSingleton();
	
	private WaitingRoomHandler waitingRoomHandler;
	
	private List<WaitingRoomGameModel> gameModels;
	
	private ListView waitingRoomGamesListView;
	private WaitingRoomListAdapter waitingRoomListAdapter;
	
	private String gameName = DUMMY_STRING;
	private String p1Ready = DUMMY_STRING;
	private String p2Ready = DUMMY_STRING;

	private WaitingRoomGameView selectedGameView;
	private Button selectedSitButton;
	
	private Handler androidHandler;
	private TimerTask mainLoopTimerTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting_room);

		waitingRoomHandler = new WaitingRoomHandler();
		
		gameModels = new ArrayList<WaitingRoomGameModel>();
		
		//database.createItem(SimpleDBSingleton.WAITING_ROOM_DOMAIN, "3");
		//database.updateAttribute(SimpleDBSingleton.WAITING_ROOM_DOMAIN, "1", "Name", "Value2");
		//database.getAttributeValue(SimpleDBSingleton.WAITING_ROOM_DOMAIN, "2", "Name");
		
		waitingRoomGamesListView = (ListView) this.findViewById(R.id.waiting_room_listView);
		
		waitingRoomListAdapter = new WaitingRoomListAdapter(this.getApplicationContext(), waitingRoomHandler);
		waitingRoomListAdapter.setGamesList(gameModels);
		waitingRoomListAdapter.notifyDataSetChanged();
		waitingRoomGamesListView.setAdapter(waitingRoomListAdapter);
		
		Button refreshButton = (Button) this.findViewById(R.id.refresh_button);
		refreshButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new GetAndShowDataFromServerTask().execute();
			}
		});
		
		Button createButton = (Button) this.findViewById(R.id.create_button);
		createButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new CreateGameTask().execute();
			}
		});
		
		Button quickplayButton = (Button) this.findViewById(R.id.quickplay_button);
		quickplayButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				long delayInMilli = 80;
				androidHandler.postDelayed(launchMultiplayerActivityRunnable, delayInMilli);
			}
		});
		
		Timer timer = new Timer();
		androidHandler = new Handler();
		mainLoopTimerTask = new CheckIfGameIsReadyToStartTask(); 
		int loopTimeInMilli = 1000;
		timer.schedule(mainLoopTimerTask, 0, loopTimeInMilli);
		
	}


	public void updateWaitingRoomListView() {
		waitingRoomListAdapter.setGamesList(gameModels);
		waitingRoomListAdapter.notifyDataSetChanged();

	}

	

	//====================================
	// Async Tasks
	//====================================
	class GetAndShowDataFromServerTask extends AsyncTask<Void, Void, Void>{
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(WaitingRoomActivity.this, "Hello", "Loading. Please wait...", true);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			
			List<String> itemNames = database.getItemNamesForDomain(SimpleDBSingleton.WAITING_ROOM_DOMAIN);
			
			// Create the necessary amount of new GameModels and add it to the List
			int numNewItems = itemNames.size() - gameModels.size();
			for(int i=0; i<numNewItems; ++i ){
				gameModels.add(poolManager.gameModelPool.newObject());
			}
			
			// Remove the necessary amount of new GameModels from the List
			int numRemoveItems = gameModels.size() - itemNames.size();
			for(int i=0; i<numRemoveItems; ++i ){
				WaitingRoomGameModel removedGameModel = gameModels.remove(gameModels.size() - 1);
				poolManager.gameModelPool.free(removedGameModel);
			}

			int i = 0;
			for(String gameId : itemNames){
				HashMap<String, String> attributes = database.getAttributesForItem(SimpleDBSingleton.WAITING_ROOM_DOMAIN, gameId);
				gameModels.get(i).gameId = gameId;
				gameModels.get(i).p1Ready = attributes.get("P1Ready");
				gameModels.get(i).p2Ready = attributes.get("P2Ready");
				++i;
			}
	
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			updateWaitingRoomListView();
			dialog.dismiss();
		}
	}
	
	class CreateGameTask extends AsyncTask<Void, Void, Void>{
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(WaitingRoomActivity.this, "Hello", "Loading. Please wait...", true);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			HashMap<String,String> attributes = new HashMap<String, String>();
			attributes.put("P1Ready", "false");
			attributes.put("P2Ready", "false");
			
			if(!gameModels.isEmpty()){
				int newGameId = Integer.parseInt(gameModels.get(gameModels.size()-1).gameId) + 1;
				database.updateAttributes(SimpleDBSingleton.WAITING_ROOM_DOMAIN, String.valueOf(newGameId), attributes);
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			dialog.dismiss();
			new GetAndShowDataFromServerTask().execute();
		}
	}
	
	class SitTask extends AsyncTask<String, Void, Void>{
		
		@Override
		protected Void doInBackground(String... params) {
			
			database.updateAttribute(SimpleDBSingleton.WAITING_ROOM_DOMAIN, params[0], params[1], "true");
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			selectedSitButton.setText("Unsit");
		}
	}
	
	class UnsitTask extends AsyncTask<String, Void, Void>{
		
		@Override
		protected Void doInBackground(String... params) {
			
			database.updateAttribute(SimpleDBSingleton.WAITING_ROOM_DOMAIN, params[0], params[1], "false");
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			selectedSitButton.setText("Sit");
			selectedSitButton = null;
		}
	}
	
	private class CheckIfGameIsReadyToStartTask extends TimerTask
	{
	    public void run()
	    {
	    	if(selectedGameView != null && selectedGameView.isGameModelReady()){
	    		long delayInMilli = 80;
	    		androidHandler.postDelayed(cancelTimerTaskAndStartGameRunnable, delayInMilli);
	    	}

	    }
	}


	//====================================
	// Runnables
	//====================================
	public Runnable cancelTimerTaskAndStartGameRunnable = new Runnable(){
		public void run(){
			mainLoopTimerTask.cancel();
			Intent myIntent = new Intent(WaitingRoomActivity.this, MultiplayerActivity.class);
			startActivity(myIntent);
		}
	};
	
	private Runnable launchMultiplayerActivityRunnable = new Runnable(){
		public void run(){
			Intent myIntent = new Intent(WaitingRoomActivity.this, MultiplayerActivity.class);
			startActivity(myIntent);
		}
	};
	
	//====================================
	// Waiting Room Handler
	//====================================
	public class WaitingRoomHandler{
		
		public boolean isPlayerSatDown(){
			return selectedSitButton != null;
		}
		
		public void activatePlayerSitDown(WaitingRoomGameView newGameView, Button newSitButton, String gameId, String playerReady){
			selectedGameView = newGameView;
			selectedSitButton = newSitButton;
			new SitTask().execute(gameId, playerReady);
		}
		
		public void activatePlayerStandUp(String gameId, String playerReady){
			if(selectedSitButton != null){
				new UnsitTask().execute(gameId, playerReady);
			}
		}

		public Button getActiveButton() {
			return selectedSitButton;
		}
		
	}
}
