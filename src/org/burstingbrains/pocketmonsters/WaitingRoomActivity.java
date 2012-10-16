package org.burstingbrains.pocketmonsters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.burstingbrains.pocketmon.constants.GameConstants;
import org.burstingbrains.pocketmon.singleton.PoolManagerSingleton;
import org.burstingbrains.pocketmon.singleton.SimpleDBSingleton;
import org.burstingbrains.pocketmonsters.util.Pool;
import org.burstingbrains.pocketmonsters.waitingroom.WaitingRoomGameModel;
import org.burstingbrains.pocketmonsters.waitingroom.WaitingRoomListAdapter;
import org.burstingbrains.sharedlibs.handler.BBSHandler;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

@TargetApi(11)
public class WaitingRoomActivity extends Activity implements GameConstants{

	private static final String DUMMY_STRING = "DummyString";

	private static SimpleDBSingleton database = SimpleDBSingleton.getSingleton();
	private static final PoolManagerSingleton poolManager = PoolManagerSingleton.getSingleton();
	
	private WaitingRoomHandler handler;
	
	private List<WaitingRoomGameModel> gameModels;
	
	private ListView waitingRoomGamesListView;
	private WaitingRoomListAdapter waitingRoomListAdapter;
	
	private String gameName = DUMMY_STRING;
	private String p1Ready = DUMMY_STRING;
	private String p2Ready = DUMMY_STRING;

	private Button activeSitButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting_room);

		handler = new WaitingRoomHandler();
		
		gameModels = new ArrayList<WaitingRoomGameModel>();
		
		//database.createItem(SimpleDBSingleton.WAITING_ROOM_DOMAIN, "3");
		//database.updateAttribute(SimpleDBSingleton.WAITING_ROOM_DOMAIN, "1", "Name", "Value2");
		//database.getAttributeValue(SimpleDBSingleton.WAITING_ROOM_DOMAIN, "2", "Name");
		
		waitingRoomGamesListView = (ListView) this.findViewById(R.id.waiting_room_listView);
		
		waitingRoomListAdapter = new WaitingRoomListAdapter(this.getApplicationContext(), handler);
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
	}


	public void updateWaitingRoomListView() {
		waitingRoomListAdapter.setGamesList(gameModels);
		waitingRoomListAdapter.notifyDataSetChanged();

	}

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
			
			int newGameId = Integer.parseInt(gameModels.get(gameModels.size()-1).gameId) + 1;
			database.updateAttributes(SimpleDBSingleton.WAITING_ROOM_DOMAIN, String.valueOf(newGameId), attributes);
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			dialog.dismiss();
			new GetAndShowDataFromServerTask().execute();
		}
	}
	
	public class WaitingRoomHandler extends BBSHandler{
		
		public boolean isPlayerSatDown(){
			return activeSitButton != null;
		}
		
		public void activatePlayerSitDown(Button newSitButton, String gameId, String playerReady){
			activeSitButton = newSitButton;
			new SitTask().execute(gameId, playerReady);
		}
		
		public void activatePlayerStandUp(String gameId, String playerReady){
			if(activeSitButton != null){
				new UnSitTask().execute(gameId, playerReady);
			}
		}

		public Button getActiveButton() {
			return activeSitButton;
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
			activeSitButton.setText("Unsit");
		}
	}
	
	class UnSitTask extends AsyncTask<String, Void, Void>{
		
		@Override
		protected Void doInBackground(String... params) {
			
			database.updateAttribute(SimpleDBSingleton.WAITING_ROOM_DOMAIN, params[0], params[1], "false");
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			activeSitButton.setText("Sit");
			activeSitButton = null;
		}
	}
}
