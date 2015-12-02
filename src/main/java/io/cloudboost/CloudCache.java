package io.cloudboost;

import io.cloudboost.beans.CBResponse;
import io.cloudboost.util.CBParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CloudCache {
	JSONObject document;
	int size;
	String cacheName;
	JSONArray items;
	String _tableName;

	public CloudCache(String cacheName) throws CloudException {
		if (cacheName == null || "null".equals(cacheName)
				|| "".equals(cacheName))

			throw new CloudException("Invalid cache name");

		this.document = new JSONObject();
		this.cacheName = cacheName;
		this._tableName = "cache";
		this.size = 0;
		this.items = new JSONArray();
		try {
			document.put("_tableName", this._tableName);
			document.put("size", "");
			document.put("name", this.cacheName);
			document.put("items", this.items);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void invalidateAttributes() {
		try {
			document.put("_tableName", this._tableName);
			document.put("size", "");
			document.put("name", this.cacheName);
			document.put("items", this.items);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void addItem(String key, Object value, ObjectCallback call)
			throws CloudException {
		if (CloudApp.getAppId() == null) {
			try {
				throw new CloudException("App Id is null");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		if (key == null || "".equals(key)) {
			throw new CloudException("Cache key is null");
		}
		if (value == null || "".equals(value))
			throw new CloudException("Cache value is null");

		JSONObject param = new JSONObject();
		try {
			param.put("key", CloudApp.getAppKey());
			param.put("item", value);
			String url = CloudApp.getApiUrl() + "/cache/" + CloudApp.getAppId()
					+ "/" + this.document.getString("name") + "/" + key;
			CBResponse response = CBParser.callJson(url, "PUT", param);
			if (response.getStatusCode() == 200) {

				call.done(response.getResponseBody(), null);
			} else
				call.done(null, new CloudException(response.getStatusMessage()));
		} catch (JSONException e) {
			call.done(null, new CloudException(e.getMessage()));
		}

	}

	public void deleteItem(String key, ObjectCallback call)
			throws CloudException {
		if (CloudApp.getAppId() == null) {
			try {
				throw new CloudException("App Id is null");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		JSONObject param = new JSONObject();
		try {
			param.put("key", CloudApp.getAppKey());
			String url = CloudApp.getApiUrl() + "/cache/" + CloudApp.getAppId()
					+ "/" + this.document.getString("name") + "/item/" + key;
			CBResponse response = CBParser.callJson(url, "DELETE", param);
			if (response.getStatusCode() == 200) {
				call.done(response.getResponseBody(), null);
			} else
				call.done(null, new CloudException(response.getStatusMessage()));
		} catch (JSONException e) {
			call.done(null, new CloudException(e.getMessage()));
		}

	}

	public void getCache(String key, ObjectCallback call) throws CloudException {
		if (CloudApp.getAppId() == null) {
			try {
				throw new CloudException("App Id is null");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		JSONObject param = new JSONObject();
		try {
			param.put("key", CloudApp.getAppKey());
			String url = CloudApp.getApiUrl() + "/cache/" + CloudApp.getAppId()
					+ "/" + this.document.getString("name") + "/" + key
					+ "item";
			CBResponse response = CBParser.callJson(url, "POST", param);
			if (response.getStatusCode() == 200) {
				call.done(response.getResponseBody(), null);
			} else
				call.done(null, new CloudException(response.getStatusMessage()));
		} catch (JSONException e) {
			call.done(null, new CloudException(e.getMessage()));
		}

	}

	public void create(ObjectCallback call) throws CloudException {
		if (CloudApp.getAppId() == null) {
			try {
				throw new CloudException("App Id is null");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		JSONObject param = new JSONObject();
		try {
			param.put("key", CloudApp.getAppKey());
			String url = CloudApp.getApiUrl() + "/cache/" + CloudApp.getAppId()
					+ "/" + this.document.getString("name") + "/create";
			CBResponse response = CBParser.callJson(url, "POST", param);
			if (response.getStatusCode() == 200) {
				call.done(response.getResponseBody(), null);
			} else
				call.done(null, new CloudException(response.getStatusMessage()));
		} catch (JSONException e) {
			call.done(null, new CloudException(e.getMessage()));
		}

	}

	public void getItemsCount(ObjectCallback call) throws CloudException {
		if (CloudApp.getAppId() == null) {
			try {
				throw new CloudException("App Id is null");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		JSONObject param = new JSONObject();
		try {
			param.put("key", CloudApp.getAppKey());
			String url = CloudApp.getApiUrl() + "/cache/" + CloudApp.getAppId()
					+ "/" + this.document.getString("name") + "/items/count";
			CBResponse response = CBParser.callJson(url, "POST", param);
			if (response.getStatusCode() == 200) {
				call.done(response.getResponseBody(), null);
			} else
				call.done(null, new CloudException(response.getStatusMessage()));
		} catch (JSONException e) {
			call.done(null, new CloudException(e.getMessage()));
		}

	}
	public void getAll_0(ObjectCallback call) throws CloudException {
		if (CloudApp.getAppId() == null) {
			try {
				throw new CloudException("App Id is null");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		JSONObject param = new JSONObject();
		try {
			param.put("key", CloudApp.getAppKey());
			String url = CloudApp.getApiUrl() + "/cache/" + CloudApp.getAppId();
			CBResponse response = CBParser.callJson(url, "POST", param);
			if (response.getStatusCode() == 200) {
				call.done(response.getResponseBody(), null);
			} else
				call.done(null, new CloudException(response.getStatusMessage()));
		} catch (JSONException e) {
			call.done(null, new CloudException(e.getMessage()));
		}

	}
	public void getAll(ObjectCallback call) throws CloudException {
		if (CloudApp.getAppId() == null) {
			try {
				throw new CloudException("App Id is null");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		JSONObject param = new JSONObject();
		try {
			param.put("key", CloudApp.getAppKey());
			String url = CloudApp.getApiUrl() + "/cache/" + CloudApp.getAppId()
					+ "/" + this.document.getString("name") + "/items";
			CBResponse response = CBParser.callJson(url, "POST", param);
			if (response.getStatusCode() == 200) {
				call.done(response.getResponseBody(), null);
			} else
				call.done(null, new CloudException(response.getStatusMessage()));
		} catch (JSONException e) {
			call.done(null, new CloudException(e.getMessage()));
		}

	}
	public static void getAllCaches(ObjectCallback call) throws CloudException {
		if (CloudApp.getAppId() == null) {
			try {
				throw new CloudException("App Id is null");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		JSONObject param = new JSONObject();
		try {
			param.put("key", CloudApp.getAppKey());
			String url = CloudApp.getApiUrl() + "/cache/" + CloudApp.getAppId();
			CBResponse response = CBParser.callJson(url, "POST", param);
			if (response.getStatusCode() == 200) {
				call.done(response.getResponseBody(), null);
			} else
				call.done(null, new CloudException(response.getStatusMessage()));
		} catch (JSONException e) {
			call.done(null, new CloudException(e.getMessage()));
		}

	}
	public void clear(ObjectCallback call) throws CloudException {
		if (CloudApp.getAppId() == null) {
			try {
				throw new CloudException("App Id is null");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		JSONObject param = new JSONObject();
		try {
			param.put("key", CloudApp.getAppKey());
			String url = CloudApp.getApiUrl() + "/cache/" + CloudApp.getAppId()
					+ "/" + this.document.getString("name") + "/clear";
			CBResponse response = CBParser.callJson(url, "DELETE", param);
			if (response.getStatusCode() == 200) {
				call.done(response.getResponseBody(), null);
			} else
				call.done(null, new CloudException(response.getStatusMessage()));
		} catch (JSONException e) {
			call.done(null, new CloudException(e.getMessage()));
		}
	}

	public void delete(ObjectCallback call) throws CloudException {
		if (CloudApp.getAppId() == null) {
			try {
				throw new CloudException("App Id is null");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		JSONObject param = new JSONObject();
		try {
			param.put("key", CloudApp.getAppKey());
			String url = CloudApp.getApiUrl() + "/cache/" + CloudApp.getAppId()
					+ "/" + this.document.getString("name");
			CBResponse response = CBParser.callJson(url, "DELETE", param);
			if (response.getStatusCode() == 200) {
				call.done(response.getResponseBody(), null);
			} else
				call.done(null, new CloudException(response.getStatusMessage()));
		} catch (JSONException e) {
			call.done(null, new CloudException(e.getMessage()));
		}

	}
	public static void deleteAll(ObjectCallback call) throws CloudException {
		if (CloudApp.getAppId() == null) {
			try {
				throw new CloudException("App Id is null");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		JSONObject param = new JSONObject();
		try {
			param.put("key", CloudApp.getAppKey());
			String url = CloudApp.getApiUrl() + "/cache/" + CloudApp.getAppId();
			CBResponse response = CBParser.callJson(url, "DELETE", param);
			if (response.getStatusCode() == 200) {
				call.done(response.getResponseBody(), null);
			} else
				call.done(null, new CloudException(response.getStatusMessage()));
		} catch (JSONException e) {
			call.done(null, new CloudException(e.getMessage()));
		}

	}
	public void get(String key,ObjectCallback call) throws CloudException {
		if (CloudApp.getAppId() == null) {
			try {
				throw new CloudException("App Id is null");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		JSONObject param = new JSONObject();
		try {
			param.put("key", CloudApp.getAppKey());
			String url = CloudApp.getApiUrl() + "/cache/" + CloudApp.getAppId()
					+ "/" + this.document.getString("name")+"/"+key+"/item";
			CBResponse response = CBParser.callJson(url, "POST", param);
			if (response.getStatusCode() == 200) {
				call.done(response.getResponseBody(), null);
			} else
				call.done(null, new CloudException(response.getStatusMessage()));
		} catch (JSONException e) {
			call.done(null, new CloudException(e.getMessage()));
		}

	}
	public void getInfo(ObjectCallback call) throws CloudException {
		if (CloudApp.getAppId() == null) {
			try {
				throw new CloudException("App Id is null");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		JSONObject param = new JSONObject();
		try {
			param.put("key", CloudApp.getAppKey());
			String url = CloudApp.getApiUrl() + "/cache/" + CloudApp.getAppId()
					+ "/" + this.document.getString("name");
			CBResponse response = CBParser.callJson(url, "POST", param);
			if (response.getStatusCode() == 200) {
				call.done(response.getResponseBody(), null);
			} else
				call.done(null, new CloudException(response.getStatusMessage()));
		} catch (JSONException e) {
			call.done(null, new CloudException(e.getMessage()));
		}

	}
}
