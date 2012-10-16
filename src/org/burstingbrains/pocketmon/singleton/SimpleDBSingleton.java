package org.burstingbrains.pocketmon.singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.burstingbrains.pocketmonsters.PropertyLoader;
import org.burstingbrains.pocketmonsters.util.Pool;
import org.burstingbrains.pocketmonsters.util.Pool.PoolObjectFactory;
import org.burstingbrains.pocketmonsters.waitingroom.WaitingRoomGameModel;

import android.os.AsyncTask;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;

public class SimpleDBSingleton {
	private static final SimpleDBSingleton simpleDBSingleton = new SimpleDBSingleton();
	
	public static final String WAITING_ROOM_DOMAIN= "WaitingRoom";
	public static final String ACTIVE_GAME_DOMAIN= "ActiveGame";
	
	private static final String DUMMY_STRING = "DummyString";
	
	private static final PoolManagerSingleton poolManager = PoolManagerSingleton.getSingleton();
	
	private AmazonSimpleDBClient sdbClient;
	
	List<ReplaceableAttribute> reusablePutAttribute; // This list holds one element. This is so that it plays nice with Amazon's APIs
	List<ReplaceableAttribute> reusablePutAttributes;
	
	List<Attribute> reusableGetAttributes;
	
	String reusableAttributeValue;
	
	private SimpleDBSingleton(){
        AWSCredentials credentials = new BasicAWSCredentials(PropertyLoader.getInstance().getAccessKey(), PropertyLoader.getInstance().getSecretKey()  );
        sdbClient = new AmazonSimpleDBClient(credentials);
        

        reusablePutAttribute = new ArrayList<ReplaceableAttribute>(1);
        reusablePutAttribute.add(new ReplaceableAttribute( "SimpleDBSingleton", "constructor", Boolean.TRUE ));
        
        reusablePutAttributes = new ArrayList<ReplaceableAttribute>();
        
        reusableAttributeValue = DUMMY_STRING;
	}
	
	public static SimpleDBSingleton getSingleton(){
		return simpleDBSingleton;
	}
	

	// =============================
	// Get
	// =============================
	
	public String getSavedAttributeValue(){
		return reusableAttributeValue;
	}

	// =============================
	// DB Requests
	// =============================
	
	public void asyncCreateItem( String domainName, String itemName ) {		
		new PutAttributesRequestTask().execute(domainName, itemName, "Name", "Value");
	}
	
	public void asyncUpdateAttribute(String domainName, String itemName, String attributeName, String attributeValue){
		new PutAttributesRequestTask().execute(domainName, itemName, attributeName, attributeValue);		
	}

	public void asyncGetAttributeValue(String domainName, String itemName, String attributeName){
		new GetAttributesRequestTask().execute(domainName, itemName, attributeName );
	}
	
	public void createItem( String domainName, String itemName ) {		
		new PutAttributesRequestTask().execute(domainName, itemName, "Name", "Value");
	}
	
	public List<String> getItemNamesForDomain( String domainName ) {
		SelectRequest selectRequest = new SelectRequest( "select itemName() from `" + domainName + "`" ).withConsistentRead( true );
		List<Item> items = sdbClient.select( selectRequest ).getItems();	
		
		List<String> itemNames = new ArrayList<String>();
		for ( int i = 0; i < items.size(); i++ ) {
			itemNames.add( ((Item)items.get( i )).getName() );
		}
		
		return itemNames;
	}	

	public HashMap<String,String> getAttributesForItem( String domainName, String itemName ) {
		GetAttributesRequest getRequest = new GetAttributesRequest( domainName, itemName ).withConsistentRead( true );
		GetAttributesResult getResult = sdbClient.getAttributes( getRequest );	
		
		HashMap<String,String> attributes = new HashMap<String,String>(30);
		for ( Object attribute : getResult.getAttributes() ) {
			String name = ((Attribute)attribute).getName();
			String value = ((Attribute)attribute).getValue();
			
			attributes.put(  name, value );
		}

		return attributes;
	}
	
	public void updateAttribute(String domainName, String itemName, String attributeName, String attributeValue){
		ReplaceableAttribute attribute = reusablePutAttribute.get(0);

		attribute.setName(attributeName);
		attribute.setValue(attributeValue);

		PutAttributesRequest putRequest = new PutAttributesRequest(domainName, itemName, reusablePutAttribute);	
		sdbClient.putAttributes( putRequest );
	}
	
	public void updateAttributes(String domainName, String itemName, HashMap<String,String> attributes){
		// Create the necessary amount of replaceable attributes
		int numNewItems = attributes.size() - reusablePutAttributes.size();
		for(int i=0; i<numNewItems; ++i ){
			reusablePutAttributes.add(poolManager.replacableAttributePool.newObject());
		}
		
		// Remove the necessary amount of replaceable attributes
		int numRemoveItems = reusablePutAttributes.size() - attributes.size();
		for(int i=0; i<numRemoveItems; ++i ){
			ReplaceableAttribute removeAttribute = reusablePutAttributes.remove(reusablePutAttributes.size() - 1);
			poolManager.replacableAttributePool.free(removeAttribute);
		}
		
		int i = 0;
		for ( String attributeName : attributes.keySet() ) {
			reusablePutAttributes.add( 
					new ReplaceableAttribute().withName( attributeName ).withValue( attributes.get( attributeName ) ).withReplace( true ) );
			i++;
		}

		PutAttributesRequest putRequest = new PutAttributesRequest(domainName, itemName, reusablePutAttributes);	
		sdbClient.putAttributes( putRequest );
	}
	
	// =============================
	// Async Tasks
	// =============================
	
	class PutAttributesRequestTask extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... parameters) {

			ReplaceableAttribute attribute = reusablePutAttribute.get(0);

			attribute.setName(parameters[2]);
			attribute.setValue(parameters[3]);

			PutAttributesRequest putRequest = new PutAttributesRequest(parameters[0], parameters[1], reusablePutAttribute);	
			sdbClient.putAttributes( putRequest );

			return null;
		}
	}

	class GetAttributesRequestTask extends AsyncTask<String, Void, Void> {
	    @Override
	    protected Void doInBackground(String... parameters) {

			GetAttributesRequest getRequest = new GetAttributesRequest(parameters[0], parameters[1]).withAttributeNames(parameters[2]);
			GetAttributesResult getResult = sdbClient.getAttributes( getRequest );	
			
	    	reusableGetAttributes = getResult.getAttributes();
	    	
	    	reusableAttributeValue = reusableGetAttributes.get(0).getValue();
	    	
	    	return null;
	    }
	 }
}
