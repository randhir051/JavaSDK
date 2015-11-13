package io.cloudboost;

import io.cloudboost.beans.CBResponse;
import io.cloudboost.util.CBParser;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CloudQueue {
	public ACL acl;
	protected JSONObject document;
	private CloudQueue thisObj;
	protected ArrayList<String> _modifiedColumns;
	protected ArrayList<String> subscribers;
	protected String queueType = "pull";
	protected long timeout = 1800;
	protected long delay = 1800;
	protected ArrayList<QueueMessage> messages;
	public static final String QUEUE_TYPE="queueType";

	public CloudQueue(String queueName, String queueType) {
		this.acl = new ACL();
		this.messages = new ArrayList<>();
		this.subscribers = new ArrayList<>();
		// adding properties of this object is document HashMap, which can
		// letter pass to serialization
		document = new JSONObject();
		try {
			document.put("subscribers", this.subscribers);
			document.put("retry", JSONObject.NULL);
			document.put("name", queueName==null?"null":queueName);
			document.put("_type", "queue");
			document.put("ACL", acl.getACL());
			document.put("expires", JSONObject.NULL);
			document.put("queueType", queueType == null ? this.queueType
					: queueType);
			document.put("messages", this.messages);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.thisObj=this;
	}
	public Object getAttribute(String key){
		try {
			return document.get(key);
		} catch (JSONException e) {
			return null;
		}
	}
	public void setAttribute(String propertyName,Object value){
		try {
			document.put(propertyName, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean validate() {
		String name;
		try {
			name = document.getString("name");

			if (name != null || !"".equals(name))
			return true;
			else return false;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void peek(int count, CloudQueueMessageCallback callback) {
		JSONObject data = new JSONObject();
		String url;
		try {
			data.put("count", count);
			data.put("key", CloudApp.getAppKey());
			url = CloudApp.getApiUrl() + "/queue/" + CloudApp.getAppId() + '/'
					+ document.get("name") + "/peek";
			if (validate()) {
				CBResponse response = CBParser.callJson(url, "POST", data);
				if (response.getStatusCode() == 200) {

					JSONArray body = null;
					try {
						body = new JSONArray(response.getResponseBody());
					} catch (JSONException e) {
						body = new JSONArray();
						body.put(new JSONObject(response.getResponseBody()));
					}

					QueueMessage[] msgArr = new QueueMessage[body.length()];

					for (int i = 0; i < body.length(); i++) {
						JSONObject obj = body.getJSONObject(i);
						QueueMessage msg = new QueueMessage();
						msg.setDocument(obj);
						;
						msgArr[i] = msg;
					}
					callback.done(msgArr, null);
				} else {
					callback.done(null,
							new CloudException(response.getStatusMessage()));
				}

			} else {
				callback.done(null, new CloudException(
						"Object Validation Failure"));

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deleteMessage(String id, CloudQueueMessageCallback callback) {
		JSONObject data = new JSONObject();
		String url;
		try {
			data.put("key", CloudApp.getAppKey());
			url = CloudApp.getApiUrl() + "/queue/" + CloudApp.getAppId() + '/'
					+ document.get("name") + "/message/" + id;
			if (validate()) {
				CBResponse response = CBParser.callJson(url, "DELETE", data);
				if (response.getStatusCode() == 200) {

					JSONArray body = null;
					try {
						body = new JSONArray(response.getResponseBody());
					} catch (JSONException e) {
						body = new JSONArray();
						body.put(new JSONObject(response.getResponseBody()));
					}

					QueueMessage[] msgArr = new QueueMessage[body.length()];

					for (int i = 0; i < body.length(); i++) {
						JSONObject obj = body.getJSONObject(i);
						QueueMessage msg = new QueueMessage();
						msg.setDocument(obj);
						;
						msgArr[i] = msg;
					}
					callback.done(msgArr, null);
				} else {
					callback.done(null,
							new CloudException(response.getStatusMessage()));
				}

			} else {
				callback.done(null, new CloudException(
						"Object Validation Failure"));

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deleteMessage(QueueMessage message,
			CloudQueueMessageCallback callback) {
		JSONObject data = new JSONObject();
		String url;
		try {
			data.put("key", CloudApp.getAppKey());
			url = CloudApp.getApiUrl() + "/queue/" + CloudApp.getAppId() + '/'
					+ document.get("name") + "/message/" + message.getId();
			if (validate()) {
				CBResponse response = CBParser.callJson(url, "DELETE", data);
				if (response.getStatusCode() == 200) {

					JSONArray body = null;
					try {
						body = new JSONArray(response.getResponseBody());
					} catch (JSONException e) {
						body = new JSONArray();
						body.put(new JSONObject(response.getResponseBody()));
					}

					QueueMessage[] msgArr = new QueueMessage[body.length()];

					for (int i = 0; i < body.length(); i++) {
						JSONObject obj = body.getJSONObject(i);
						QueueMessage msg = new QueueMessage();
						msg.setDocument(obj);
						;
						msgArr[i] = msg;
					}
					callback.done(msgArr, null);
				} else {
					callback.done(null,
							new CloudException(response.getStatusMessage()));
				}

			} else {
				callback.done(null, new CloudException(
						"Object Validation Failure"));

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getMessageById(String id, CloudQueueMessageCallback callback) {
		JSONObject data = new JSONObject();
		String url;
		try {
			data.put("key", CloudApp.getAppKey());
			url = CloudApp.getApiUrl() + "/queue/" + CloudApp.getAppId() + '/'
					+ document.get("name") + "/message/" + id;
			if (validate()) {
				CBResponse response = CBParser.callJson(url, "POST", data);
				if (response.getStatusCode() == 200) {

					JSONArray body = null;
					try {
						body = new JSONArray(response.getResponseBody());
					} catch (JSONException e) {
						body = new JSONArray();
						body.put(new JSONObject(response.getResponseBody()));
					}

					QueueMessage[] msgArr = new QueueMessage[body.length()];

					for (int i = 0; i < body.length(); i++) {
						JSONObject obj = body.getJSONObject(i);
						QueueMessage msg = new QueueMessage();
						msg.setDocument(obj);
						;
						msgArr[i] = msg;
					}
					callback.done(msgArr, null);
				} else {
					callback.done(null,
							new CloudException(response.getStatusMessage()));
				}

			} else {
				callback.done(null, new CloudException(
						"Object Validation Failure"));

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deleteSubscriber(Object subscriber, CloudQueueCallback callback) {
		subscribers.clear();
		try {
			if (subscriber instanceof String[]) {
				String[] mss = (String[]) subscriber;
				for (String str : mss)
					subscribers.add(str);

			} else if (subscriber instanceof String) {
				String mss = (String) subscriber;
				subscribers.add(mss);
			} else if (subscriber == null) {
				throw new CloudException("Cannot push null into queue");

			}
			String url = null;
			JSONObject data = new JSONObject();
			document.put("subscribers", subscribers);
			data.put("document", document);

			data.put("key", CloudApp.getAppKey());
			url = CloudApp.getApiUrl() + "/queue/" + CloudApp.getAppId() + '/'
					+ document.get("name") + "/subscriber";
			this.thisObj = this;
			if (validate()) {
				CBResponse response = CBParser.callJson(url, "DELETE", data);
				if (response.getStatusCode() == 200) {

					JSONObject body = new JSONObject(response.getResponseBody());
					this.document = body;
					callback.done(this.thisObj, null);
				} else {
					callback.done(null,
							new CloudException(response.getStatusMessage()));
				}

			} else {
				callback.done(null, new CloudException(
						"Object Validation Failure"));

			}

		} catch (JSONException e) {
			callback.done(null, new CloudException(e.getMessage()));
		} catch (CloudException e) {
			callback.done(null, e);
		}
	}

	public void addSubscriber(Object subscriber, CloudQueueCallback callback) {
		subscribers.clear();
		try {
			if (subscriber instanceof String[]) {
				String[] mss = (String[]) subscriber;
				for (String str : mss) {
					subscribers.add(str);
				}

			} else if (subscriber instanceof String) {
				String mss = (String) subscriber;
				subscribers.add(mss);
			} else if (subscriber == null) {
				throw new CloudException("invalid url");

			}
			String url = null;
			JSONObject data = new JSONObject();
			document.put("subscribers", subscribers);
			data.put("document", document);

			data.put("key", CloudApp.getAppKey());
			url = CloudApp.getApiUrl() + "/queue/" + CloudApp.getAppId() + '/'
					+ document.get("name") + "/subscriber";
			this.thisObj = this;
			if (validate()) {
				CBResponse response = CBParser.callJson(url, "POST", data);
				if (response.getStatusCode() == 200) {

					JSONObject body = new JSONObject(response.getResponseBody());
					merge(body);
					callback.done(this.thisObj, null);
				} else {
					callback.done(null,
							new CloudException(response.getStatusMessage()));
				}

			} else {
				callback.done(null, new CloudException(
						"Object Validation Failure"));

			}

		} catch (JSONException e) {
			callback.done(null, new CloudException(e.getMessage()));
		} catch (CloudException e) {
			callback.done(null, e);
		}
	}

	public void removeSubscriber(Object subscriber, CloudQueueCallback callback) {
		subscribers.clear();
		try {
			if (subscriber instanceof String[]) {
				String[] mss = (String[]) subscriber;
				for (String str : mss) {
					subscribers.add(str);
				}

			} else if (subscriber instanceof String) {
				String mss = (String) subscriber;
				subscribers.add(mss);
			} else if (subscriber == null) {
				throw new CloudException("invalid url");

			}
			String url = null;
			JSONObject data = new JSONObject();
			document.put("subscribers", subscribers);
			data.put("document", document);

			data.put("key", CloudApp.getAppKey());
			url = CloudApp.getApiUrl() + "/queue/" + CloudApp.getAppId() + '/'
					+ document.get("name") + "/subscriber";
			this.thisObj = this;
			if (validate()) {
				CBResponse response = CBParser.callJson(url, "DELETE", data);
				if (response.getStatusCode() == 200) {

					JSONObject body = new JSONObject(response.getResponseBody());
					this.document = body;
					callback.done(this.thisObj, null);
				} else {
					callback.done(null,
							new CloudException(response.getStatusMessage()));
				}

			} else {
				callback.done(null, new CloudException(
						"Object Validation Failure"));

			}

		} catch (JSONException e) {
			callback.done(null, new CloudException(e.getMessage()));
		} catch (CloudException e) {
			callback.done(null, e);
		}
	}

	public void push(Object message, CloudQueueMessageCallback callback) {
		messages.clear();
		try {
			if (message instanceof QueueMessage[]) {
				QueueMessage[] mss = (QueueMessage[]) message;
				for (QueueMessage ms : mss)
					messages.add(ms);
			} else if (message instanceof String[]) {
				String[] mss = (String[]) message;
				for (String str : mss) {
					QueueMessage msg = new QueueMessage();
					msg.push(str);
					messages.add(msg);
				}
			} else if (message instanceof String) {
				String mss = (String) message;
				QueueMessage msg = new QueueMessage();
				msg.push(mss);
				messages.add(msg);
			} else if (message == null) {
				throw new CloudException("Cannot push null into queue");

			}
			JSONArray arr = new JSONArray();
			String url = null;
			JSONObject data = new JSONObject();
			for (QueueMessage ms : messages)
				arr.put(ms.getDocument());
			document.put("messages", arr);
			data.put("document", document);

			data.put("key", CloudApp.getAppKey());
			url = CloudApp.getApiUrl() + "/queue/" + CloudApp.getAppId() + '/'
					+ document.get("name") + "/message";
			this.thisObj = this;
			if (validate()) {
				CBResponse response = CBParser.callJson(url, "PUT", data);
				if (response.getStatusCode() == 200) {

					JSONArray body = null;
					try {
						body = new JSONArray(response.getResponseBody());
					} catch (JSONException e) {
						body = new JSONArray();
						body.put(new JSONObject(response.getResponseBody()));
					}

					QueueMessage[] msgArr = new QueueMessage[body.length()];

					for (int i = 0; i < body.length(); i++) {
						JSONObject obj = body.getJSONObject(i);
						QueueMessage msg = new QueueMessage();
						msg.setDocument(obj);
						;
						msgArr[i] = msg;
					}
					callback.done(msgArr, null);
				} else {
					callback.done(null,
							new CloudException(response.getStatusMessage()));
				}

			} else {
				callback.done(null, new CloudException(
						"Object Validation Failure"));

			}

		} catch (JSONException e) {
			callback.done(null, new CloudException(e.getMessage()));
		} catch (CloudException e) {
			callback.done(null, e);
		}
	}

	public void deleteQueue(CloudQueueCallback callback) {
		try {
			String url = null;
			JSONObject data = new JSONObject();
			data.put("document", this.document);
			data.put("key", CloudApp.getAppKey());
			url = CloudApp.getApiUrl() + "/queue/" + CloudApp.getAppId() + '/'
					+ document.get("name");
			this.thisObj = this;
			if (validate()) {
				CBResponse response = CBParser.callJson(url, "DELETE", data);
				if (response.getStatusCode() == 200) {

					JSONObject body = new JSONObject(response.getResponseBody());
					System.out.println("thisObj: "+this.thisObj);
					this.thisObj.document = body;
					callback.done(this.thisObj, null);
				} else {
					callback.done(null,
							new CloudException(response.getStatusMessage()));
				}

			} else {
				callback.done(null, new CloudException(
						"Object Validation Failure"));

			}

		} catch (JSONException e) {
			callback.done(null, new CloudException(e.getMessage()));
		}
	}

	public void pull(int count, CloudQueueMessageCallback callback) {
		try {
			String url = null;
			JSONObject data = new JSONObject();
			data.put("count", count);
			data.put("key", CloudApp.getAppKey());
			url = CloudApp.getApiUrl() + "/queue/" + CloudApp.getAppId() + '/'
					+ document.get("name") + "/pull";
			this.thisObj = this;
			if (validate()) {
				CBResponse response = CBParser.callJson(url, "POST", data);
				if (response.getStatusCode() == 200) {

					JSONArray body = null;
					try {
						body = new JSONArray(response.getResponseBody());
					} catch (JSONException e) {
						body = new JSONArray();
						body.put(new JSONObject(response.getResponseBody()));
					}

					QueueMessage[] msgArr = new QueueMessage[body.length()];

					for (int i = 0; i < body.length(); i++) {
						JSONObject obj = body.getJSONObject(i);
						QueueMessage msg = new QueueMessage();
						msg.setDocument(obj);
						;
						msgArr[i] = msg;
					}
					callback.done(msgArr, null);
				} else {
					callback.done(null,
							new CloudException(response.getStatusMessage()));
				}

			} else {
				callback.done(null, new CloudException(
						"Object Validation Failure"));

			}

		} catch (JSONException e) {
			callback.done(null, new CloudException(e.getMessage()));
		}
	}
	public void clear(CloudQueueCallback callback) {
		JSONObject data = new JSONObject();
		String url;
		try {
			data.put("key", CloudApp.getAppKey());
			data.put("document", this.document);
			url = CloudApp.getApiUrl() + "/queue/" + CloudApp.getAppId() + '/'
					+ document.get("name") + "/clear";
			if (validate()) {
				CBResponse response = CBParser.callJson(url, "DELETE", data);
				if (response.getStatusCode() == 200) {

					JSONObject body = new JSONObject(response.getResponseBody());
						this.thisObj.document=body;
					
					callback.done(this.thisObj, null);
				} else {
					callback.done(null,new CloudException(response.getStatusMessage()));
				}

			} else {
				callback.done(null, new CloudException(
						"Object Validation Failure"));

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void get(String queueName,CloudQueueCallback callback){
		CloudQueue queue=new CloudQueue(queueName, null);
		queue.get(callback);
	}
	public void deleteQueue(String queueName,CloudQueueCallback callback){
		CloudQueue queue=new CloudQueue(queueName, null);
		queue.deleteQueue(callback);
	}
	public void merge(JSONObject obj){
		Iterator<String> iter=obj.keys();
		while(iter.hasNext()){
			String key=iter.next();
			try {
				document.put(key, obj.get(key));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void update(CloudQueueCallback callback) {
		validate();
		JSONObject data = new JSONObject();
		String url;
		try {
			data.put("key", CloudApp.getAppKey());
			data.put("document", this.document);
			url = CloudApp.getApiUrl() + "/queue/" + CloudApp.getAppId() + '/'
					+ document.get("name");
			if (validate()) {
				CBResponse response = CBParser.callJson(url, "PUT", data);
				if (response.getStatusCode() == 200) {

					JSONObject body = new JSONObject(response.getResponseBody());
					System.out.println("this obj: "+this.thisObj);
					merge(body);
//					this.thisObj.document=body;
					
					callback.done(this.thisObj, null);
				} else {
					callback.done(null,
							new CloudException(response.getStatusMessage()));
				}

			} else {
				callback.done(null, new CloudException(
						"Object Validation Failure"));

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void get(CloudQueueCallback callback) {
		validate();
		JSONObject data = new JSONObject();
		String url;
		try {
			data.put("key", CloudApp.getAppKey());
			url = CloudApp.getApiUrl() + "/queue/" + CloudApp.getAppId() + '/'
					+ document.get("name") + "/";
			if (validate()) {
				CBResponse response = CBParser.callJson(url, "POST", data);
				if (response.getStatusCode() == 200) {

					JSONObject body = new JSONObject(response.getResponseBody());
					System.out.println("this obj: "+this.thisObj);
					this.thisObj.document=body;
					
					callback.done(this.thisObj, null);
				} else {
					callback.done(null,
							new CloudException(response.getStatusMessage()));
				}

			} else {
				callback.done(null, new CloudException(
						"Object Validation Failure"));

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void getAll(CloudQueueArrayCallback callback) {
		validate();
		JSONObject data = new JSONObject();
		String url;
		try {
			data.put("key", CloudApp.getAppKey());
			url = CloudApp.getApiUrl() + "/queue/" + CloudApp.getAppId() + '/';
			if (validate()) {
				CBResponse response = CBParser.callJson(url, "POST", data);
				if (response.getStatusCode() == 200) {
					JSONArray arr=new JSONArray(response.getResponseBody());
					CloudQueue[] queues=new CloudQueue[arr.length()];
					for(int i=0;i<arr.length();i++){
						JSONObject obj=arr.getJSONObject(i);
						CloudQueue queue=new CloudQueue(null, null);
						queue.document=obj;
						queue.thisObj=queue;
						queues[i]=queue;
					}
					
					callback.done(queues, null);
				} else {
					callback.done(null,
							new CloudException(response.getStatusMessage()));
				}

			} else {
				callback.done(null, new CloudException(
						"Object Validation Failure"));

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
