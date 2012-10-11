package org.burstingbrains.pocketmon.singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.burstingbrains.pocketmonsters.PropertyLoader;

import android.os.AsyncTask;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

public class SimpleDBSingleton {
	private static final SimpleDBSingleton simpleDBSingleton = new SimpleDBSingleton();
	
	public static final String WAITING_ROOM_DOMAIN= "WaitingRoom";
	public static final String ACTIVE_GAME_DOMAIN= "ActiveGame";
	
	private static final String DUMMY_STRING = "DummyString";
	
	private AmazonSimpleDBClient sdbClient;
	
	List<ReplaceableAttribute> reusablePutAttribute; // This list holds one element. This is so that it plays nice with Amazon's APIs
	List<Attribute> reusableGetAttributes;
	
	String reusableAttributeValue;
	
	private SimpleDBSingleton(){
        AWSCredentials credentials = new BasicAWSCredentials(PropertyLoader.getInstance().getAccessKey(), PropertyLoader.getInstance().getSecretKey()  );
        sdbClient = new AmazonSimpleDBClient(credentials);
        

        reusablePutAttribute = new ArrayList<ReplaceableAttribute>(1);
        reusablePutAttribute.add(new ReplaceableAttribute( "SimpleDBSingleton", "constructor", Boolean.TRUE ));
        
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
	
	public void createItem( String domainName, String itemName ) {		
		new PutAttributesRequestTask().execute(domainName, itemName, "Name", "Value");
	}
	
	public void updateAttribute(String domainName, String itemName, String attributeName, String attributeValue){
		new PutAttributesRequestTask().execute(domainName, itemName, attributeName, attributeValue);		
	}

	public void getAttributeValue(String domainName, String itemName, String attributeName){
		new GetAttributesRequestTask().execute(domainName, itemName, attributeName );
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
