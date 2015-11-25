package io.cloudboost;

import io.cloudboost.beans.CBResponse;
import io.cloudboost.util.CBParser;

import java.util.ArrayList;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author cloudboost
 * 
 */
public class CloudQuery {

	private String tableName;
	private JSONObject query;
	private JSONObject select;
	ArrayList<String> includeList;
	ArrayList<String> include;
	private JSONObject sort;
	private int skip;
	private int limit;
	private ArrayList<String> $include;
	private ArrayList<String> $includeList;

	/**
	 * 
	 * Constructor
	 * 
	 * @param tableName
	 */
	public CloudQuery(String tableName) {

		this.tableName = tableName;
		query = new JSONObject();
		select = new JSONObject();
		include = new ArrayList<String>();
		includeList = new ArrayList<String>();
		$include = new ArrayList<String>();
		$includeList = new ArrayList<String>();
		try {
			this.query.put("$include", $include);
			this.query.put("$includeList", $includeList);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sort = new JSONObject();

		this.skip = 0;
		this.limit = 10;
	}

	public JSONObject getQuery() {
		return query;
	}

	public void setQuery(JSONObject query) {
		this.query = query;
	}

	/**
	 * 
	 * CloudQuery Or
	 * 
	 * 
	 * @param object1
	 * @param object2
	 * @throws CloudException
	 */
	public static CloudQuery or(CloudQuery object1, CloudQuery object2)
			throws CloudException {
		String tableName1 = object1.tableName;
		String tableName2 = object2.tableName;

		if (tableName1.toLowerCase().equals(tableName2.toLowerCase()) == false) {
			throw new CloudException("Table names are not same");
		}

		JSONArray array = new JSONArray();
		array.put(object1.query);
		array.put(object2.query);

		CloudQuery object = new CloudQuery(tableName1);
		try {
			object.query.put("$or", array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;
	}

	/**
	 * 
	 * CloudQuery EqualTo
	 * 
	 * 
	 * @param columnName
	 * @param obj
	 * @return
	 */
	public CloudQuery equalTo(String columnName, Object obj) {
		if (columnName.equals("id")) {
			columnName = "_id";
		}
		if (obj != null) {
			try {
				if (obj instanceof CloudObject || obj instanceof CloudRole) {
					columnName = columnName + "._id";
					obj = ((CloudObject) obj).document.getString("_id");
				}

				this.query.put(columnName, obj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.doesNotExists(columnName);
		}
		return this;
	}

	/**
	 * 
	 * CloudQuery Not Equal To
	 * 
	 * @param columnName
	 * @param obj
	 * @return
	 */

	public CloudQuery notEqualTo(String columnName, Object obj) {
		if (columnName.equals("id")) {
			columnName = "_id";
		}
		if (obj != null) {
			try {
				if (obj instanceof CloudObject) {
					columnName = columnName + "._id";
					obj = ((CloudObject) obj).document.getString("_id");
				}

				JSONObject $ne = new JSONObject("{ $ne: " + obj + " }");

				this.query.put(columnName, $ne);
			} catch (JSONException e) {
				JSONObject $ne;
				try {
					$ne = new JSONObject("{ $ne: " + "" + " }");
					this.query.put(columnName, $ne);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		} else {
			this.exists(columnName);
		}
		return this;
	}

	/**
	 * 
	 * CloudQuery Exists
	 * 
	 * @param columnName
	 * @return
	 */
	public CloudQuery exists(String columnName) {
		if (columnName == "id" || columnName == "expires")
			columnName = "_" + columnName;

		try {
			this.query.put(columnName, JSONObject.NULL);

			JSONObject exists = new JSONObject();
			exists.put("$exists", true);
			this.query.put(columnName, exists);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 
	 * CloudQuery Exists
	 * 
	 * @param columnName
	 * @return
	 */
	public CloudQuery doesNotExists(String columnName) {

		if (columnName == "id" || columnName == "expires")
			columnName = "_" + columnName;

		try {
			this.query.put(columnName, JSONObject.NULL);

			JSONObject exists = new JSONObject();
			exists.put("$exists", false);
			this.query.put(columnName, exists);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 
	 * CloudQuery Include List
	 * 
	 * @param columnName
	 * @return
	 */
	public CloudQuery includeList(String columnName) {
		if (columnName == "id" || columnName == "expires")
			columnName = "_" + columnName;

		this.includeList.add(columnName);
		JSONObject $includeList = new JSONObject();
		try {
			$includeList.put("$includeList", this.includeList);

			this.query.put("$includeList", this.includeList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 
	 * CloudQuery Include
	 * 
	 * @param columnName
	 * @return
	 */
	public CloudQuery include(String columnName) {
		if (columnName == "id" || columnName == "expires")
			columnName = "_" + columnName;

		this.include.add(columnName);
		JSONObject $include = new JSONObject();
		try {
			$include.put("$include", this.include);

			this.query.put("$include", this.include);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 
	 * CloudQuery All
	 * 
	 * @param columnName
	 * @return
	 */
	public CloudQuery all(String columnName) {
		if (columnName == "id")
			columnName = "_" + columnName;

		try {
			this.query.put("$all", columnName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	/**
	 * 
	 * CloudQuery Any
	 * 
	 * @param columnName
	 * @return
	 */
	public CloudQuery any(String columnName) {
		if (columnName == "id")
			columnName = "_" + columnName;

		try {
			this.query.put("$any", columnName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	/**
	 * 
	 * CloudQuery First
	 * 
	 * @param columnName
	 * @return
	 */
	public CloudQuery first(String columnName) {
		if (columnName == "id")
			columnName = "_" + columnName;

		try {
			this.query.put("$first", columnName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	};

	/**
	 * 
	 * CloudQuery greaterThan
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 */
	public CloudQuery greaterThan(String columnName, Object data) {

		if (columnName == "id")
			columnName = "_" + columnName;
		try {
			if (this.query.isNull(columnName)) {
				this.query.put(columnName, JSONObject.NULL);
			}
			JSONObject $gt = new JSONObject();
			$gt.put("$gt", data);

			this.query.put(columnName, $gt);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	/**
	 * 
	 * CloudQuery Greater Than Equal To
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 */
	public CloudQuery greaterThanEqualTo(String columnName, Object data) {

		if (columnName == "id")
			columnName = "_" + columnName;
		try {
			if (this.query.isNull(columnName)) {
				this.query.put(columnName, JSONObject.NULL);
			}
			JSONObject $gte = new JSONObject();
			$gte.put("$gte", data);

			this.query.put(columnName, $gte);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	/**
	 * 
	 * CloudQuery Less Than
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 */
	public CloudQuery lessThan(String columnName, Object data) {

		if (columnName == "id")
			columnName = "_" + columnName;
		try {
			if (this.query.isNull(columnName)) {
				this.query.put(columnName, JSONObject.NULL);
			}
			JSONObject $lt = new JSONObject();
			$lt.put("$lt", data);

			this.query.put(columnName, $lt);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	/**
	 * 
	 * CloudQuery Less Than Equal to
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 */

	public CloudQuery lessThanEqualTo(String columnName, Object data) {

		if (columnName == "id")
			columnName = "_" + columnName;
		try {
			if (this.query.isNull(columnName)) {
				this.query.put(columnName, JSONObject.NULL);
			}
			JSONObject $lte = new JSONObject();
			$lte.put("$lte", data);

			this.query.put(columnName, $lte);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	/**
	 * 
	 * CloudQuery OrderByAsc
	 * 
	 * @param columnName
	 * @return
	 */
	public CloudQuery orderByAsc(String columnName) {

		if (columnName == "id")
			columnName = "_" + columnName;

		try {
			this.sort.put(columnName, 1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	/**
	 * 
	 * CloudQuery OrderByDesc
	 * 
	 * @param columnName
	 * @return
	 */
	public CloudQuery orderByDesc(String columnName) {

		if (columnName == "id")
			columnName = "_" + columnName;

		try {
			this.sort.put(columnName, -1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	/**
	 * 
	 * CloudQuery Limit
	 * 
	 * @param data
	 * @return
	 */
	public CloudQuery setLimit(int data) {

		this.limit = data;
		return this;
	}

	/**
	 * 
	 * CloudQuery Skip
	 * 
	 * @param data
	 * @return
	 */
	public CloudQuery setSkip(int data) {

		this.skip = data;
		return this;
	}

	/**
	 * 
	 * CloudQuery Select Column
	 * 
	 * @param columnNames
	 * @return
	 */
	public CloudQuery selectColumn(String[] columnNames) {
		try {
			if (this.select.length() == 0) {
				this.select.put("_id", 1);
				this.select.put("createdAt", 1);
				this.select.put("updatedAt", 1);
				this.select.put("ACL", 1);
				this.select.put("_type", 1);
				this.select.put("_tableName", 1);
			}

			for (int i = 0; i < columnNames.length; i++) {

				this.select.put(columnNames[i], 1);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 
	 * CloudQuery Do Not Select Column
	 * 
	 * @param columnNames
	 * @return
	 */
	public CloudQuery doNotSelectColumn(String[] columnNames) {

		for (int i = 0; i < columnNames.length; i++) {
			try {
				this.select.put(columnNames[i], 0);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return this;
	}

	/**
	 * 
	 * CloudQuery ContainedIn
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 * @throws CloudException
	 */
	@SuppressWarnings("unchecked")
	public CloudQuery containedIn(String columnName, Object[] data)
			throws CloudException {

		if (columnName.equals("id") || columnName.equals("expires"))
			columnName = "_" + columnName;

		JSONObject column = new JSONObject();
		try {
			column = this.query.getJSONObject(columnName);
		} catch (JSONException e) {
			try {
				this.query.put(columnName, JSONObject.NULL);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		ArrayList<String> $in;
		ArrayList<String> $nin;

		if (data instanceof CloudObject[] || data instanceof Integer[]
				|| data instanceof String[] || data instanceof Double[]) {

			CloudObject[] object = new CloudObject[data.length];
//			columnName = columnName + "._id";

			try {
				this.query.put("$include", $include);
				this.query.put("$includeList", $includeList);
				if (data instanceof CloudObject[]) {
					Object[] dataz=new Object[data.length];
					for (int i = 0; i < data.length; i++) {
						object[i] = (CloudObject) data[i];
						if (object[i].getId() == null) {
							throw new CloudException(
									"CloudObject passed should be saved and should have an id before being passed to containedIn");
						}
						dataz[i] = object[i].getId();
					}
					data=dataz;
					if (this.query.get(columnName) == null) {
						this.query.put(columnName, (Object) null);
					}
					if(!column.has("$in"))
						column.put("$in", new ArrayList<String>());
						if(column.get("$in") == null){
							$in = new ArrayList<String>();
							column.put("$in", $in);
						}

					if(!column.has("$nin"))
						column.put("$nin", new ArrayList<String>());
					if (column.get("$nin") == null) {
						$nin = new ArrayList<String>();
						column.put("$nin", $nin);
					}

					$in = (ArrayList<String>) column.get("$in");
					$nin = (ArrayList<String>) column.get("$nin");

					for (int i = 0; i < data.length; i++) {
						if (!$in.contains(data[i].toString())) {
							$in.add(data[i].toString());
							column.put("$in", $in);
							this.query.put(columnName, column);
						}

						if ($nin.contains(data[i].toString())) {
							$nin.remove(data[i].toString());
							column.put("$nin", $nin);
							this.query.put(columnName, column);
						}

					}
				} else {

					this.query.put(columnName, JSONObject.NULL);

					$in = new ArrayList<String>();
					column.put("$in", $in);
					$nin = new ArrayList<String>();
					column.put("$nin", $nin);

					for (int i = 0; i < data.length; i++) {

						$in.add(data[i].toString());
						if ($nin.contains(data[i].toString())) {
							$nin.remove(data[i].toString());
						}
					}
					column.put("$in", $in);
					column.put("$nin", $nin);

					this.query.put(columnName, column);

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new CloudException(
					"Pass only Integer, Double, String or CloudObject as an argument");
		}

		return this;
	}

	/**
	 * 
	 * CloudQuery Not Contained In
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 * @throws CloudException
	 */
	@SuppressWarnings("unchecked")
	public CloudQuery notContainedIn(String columnName, Object[] data)
			throws CloudException {

		if (columnName.equals("id") || columnName.equals("expires"))
			columnName = "_" + columnName;

		JSONObject column = new JSONObject();
		try {
			this.query.put("$include", $include);
			this.query.put("$includeList", $includeList);
			if (this.query.has(columnName)) {
				column = this.query.getJSONObject(columnName);
			}

			ArrayList<String> $in = new ArrayList<String>();
			ArrayList<String> $nin = new ArrayList<String>();

			if (data instanceof CloudObject[] || data instanceof Integer[]
					|| data instanceof String[] || data instanceof Double[]) {

				CloudObject[] object = new CloudObject[data.length];

				if (data instanceof CloudObject[]) {
					columnName = columnName + "._id";
					for (int i = 0; i < data.length; i++) {
						object[i] = (CloudObject) data[i];
						if (object[i].getId() == null) {
							throw new CloudException(
									"CloudObject passed should be saved and should have an id before being passed to containedIn");
						}
						data[i] = object[i].getId();
					}

					if (this.query.isNull(columnName)) {
						this.query.put(columnName, JSONObject.NULL);
					}

					if (column.get("$in") == null) {
						$in = new ArrayList<String>();
						// column.put("$in", $in);
					}

					if (column.get("$nin") == null) {
						$nin = new ArrayList<String>();
						column.put("$nin", $nin);
					}
					// JSONArray in = column.getJSONArray("$in");
					JSONArray nin = column.getJSONArray("$nin");
					// for (int i = 0; i < in.length(); i++) {
					// $in.add(in.get(i).toString());
					// }
					for (int i = 0; i < nin.length(); i++) {
						$nin.add(nin.get(i).toString());
					}
					$in = (ArrayList<String>) column.get("$in");
					$nin = (ArrayList<String>) column.get("$nin");

					for (int i = 0; i < data.length; i++) {
						if (!$nin.contains(data[i].toString())) {
							$nin.add(data[i].toString());
							column.put("$nin", $nin);
							this.query.put(columnName, column);
						}

						if ($in.contains(data[i].toString())) {
							$in.remove(data[i].toString());
							// column.put("$in", $in);
							column.remove("$in");
							this.query.put(columnName, column);
						}

					}
				} else {

					if (this.query.isNull(columnName)) {
						this.query.put(columnName, (Object) null);
					}

					if (column.isNull("$in")) {
						$in = new ArrayList<String>();
						column.put("$in", $in);
					}

					if (column.isNull("$nin")) {
						$nin = new ArrayList<String>();
						column.put("$nin", $nin);
					}

					for (int i = 0; i < data.length; i++) {
						$nin.add(data[i].toString());
						if ($in.contains(data[i].toString())) {
							$in.remove(data[i].toString());
						}
					}
					column.put("$in", $in);
					column.remove("$in");
					column.put("$nin", $nin);
					this.query.put(columnName, column);
				}

			} else {
				throw new CloudException(
						"Pass only Integer, Double, String or CloudObject as an argument");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 
	 * CloudQuery Contains All
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 * @throws CloudException
	 */
	@SuppressWarnings("unchecked")
	public CloudQuery containsAll(String columnName, Object[] data)
			throws CloudException {

		if (columnName.equals("id") || columnName.equals("expires"))
			columnName = "_" + columnName;
		try {
			this.query.put("$include", $include);
			this.query.put("$includeList", $includeList);
			if (data instanceof CloudObject[] || data instanceof Integer[]
					|| data instanceof String[] || data instanceof Double[]) {

				ArrayList<Object> $all;
				CloudObject[] object = new CloudObject[data.length];
				JSONObject column = new JSONObject();
				if (this.query.has(columnName)) {
					column = this.query.getJSONObject(columnName);
				}
//				columnName = columnName + "._id";

				if (data instanceof CloudObject[]) {

					for (int i = 0; i < data.length; i++) {
						object[i] = (CloudObject) data[i];
						if (object[i].getId() == null) {
							throw new CloudException(
									"CloudObject passed should be saved and should have an id before being passed to containedIn");
						}
						data[i] = object[i].getId();
					}

					if (this.query.has(columnName)) {
						this.query.put(columnName, JSONObject.NULL);
					}

					if (column.has("$all")) {
						$all = new ArrayList<Object>();
					}

					$all = (ArrayList<Object>) column.get("$all");

					for (int i = 0; i < data.length; i++) {
						if (!$all.contains(data[i])) {
							$all.add(data[i]);
						}
					}

					column.put("$all", $all);
					this.query.put(columnName, column);

				} else {

					if (this.query.has(columnName)) {
						this.query.put(columnName, JSONObject.NULL);
					}

					$all = new ArrayList<Object>();
					if (column.has("$all"))
						$all = PrivateMethod._toObjectArray(column
								.getJSONArray("$all"));

					for (int i = 0; i < data.length; i++) {
						$all.add(data[i]);
					}

					column.put("$all", $all);
					this.query.put(columnName, column);
				}

			} else {
				throw new CloudException(
						"Pass only Integer, Double, String or CloudObject as an argument");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 
	 * CloudQuery Starts With
	 * 
	 * @param columnName
	 * @param value
	 * @return
	 */
	public CloudQuery startsWith(String columnName, Object value) {

		if (columnName.equals("id") || columnName.equals("expires"))
			columnName = "_" + columnName;

		String regex = "^" + value.toString();
		try {
			this.query.put(columnName, JSONObject.NULL);
			
			JSONObject args = new JSONObject();
			args.put("$regex", regex);
			args.put("$options", "im");
			this.query.put(columnName, args);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 
	 * CloudQuery Near
	 * 
	 * @param columnName
	 * @param geoPoint
	 * @param maxDistance
	 * @param minDistance
	 * @return
	 */
	public CloudQuery near(String columnName, CloudGeoPoint geoPoint,
			Double maxDistance, Double minDistance) {

		try {
			this.query.put(columnName, JSONObject.NULL);

			String $near = "{ '$geometry': {coordinates:"
					+ geoPoint.document.get("coordinates")
					+ " , type:'Point' }, '$maxDistance': " + maxDistance
					+ ", '$minDistance': " + minDistance + "}";
			this.query.put(columnName, $near);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 
	 * CloudQuery Geo With In
	 * 
	 * 
	 * @param columnName
	 * @param geoPoint
	 * @param radius
	 * @return
	 */
	//
	public CloudQuery geoWithin(String columnName, CloudGeoPoint geoPoint,
			Double radius) {

		try {
			this.query.put(columnName, JSONObject.NULL);

			String $geoWithin = "{ '$centerSphere':["
					+ geoPoint.document.get("coordinates") + ", " + radius
					/ 3963.2 + "] }";
			this.query.put(columnName, $geoWithin);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 
	 * CloudQuery Geo With In
	 * 
	 * @param columnName
	 * @param geoPoint
	 * @return
	 */
	public CloudQuery geoWithin(String columnName, CloudGeoPoint[] geoPoint) {
		JSONArray coordinates = new JSONArray();
		try {

			for (int i = 0; i < geoPoint.length; i++) {
				if (geoPoint[i].document.get("coordinates") != null) {
					JSONArray point = new JSONArray(geoPoint[i].document.get(
							"coordinates").toString());
					coordinates.put(point.get(0));
					coordinates.put(point.get(1));
				}
			}
			this.query.put(columnName, JSONObject.NULL);
			String $geoWithin = "{ '$geometry':{ 'type': 'Polygon', 'coordinates': "
					+ coordinates.toString() + "} }";
			this.query.put(columnName, $geoWithin);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	public void count(CloudIntegerCallback callbackObject)
			throws CloudException {
		if (CloudApp.getAppId() == null) {
			throw new CloudException("App Id is null");
		}

		JSONObject params = new JSONObject();
		try {
			params.put("query", this.query);

			params.put("limit", this.limit);
			params.put("skip", this.skip);
			params.put("key", CloudApp.getAppKey());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// client = new AsyncHttpClient();
		String url = CloudApp.getApiUrl() + "/data/" + CloudApp.getAppId()
				+ "/" + this.tableName + "/count";
		Future<?> f = null;
		// client.preparePost(url).addHeader("Content-type",
		// "application/json").setBody(params.toString()).execute();
		// try {
		// if(f.get().getHeader("sessionId") != null){
		// PrivateMethod._setSessionId(f.get().getHeader("sessionId"));
		// }else{
		// PrivateMethod._deleteSessionId();
		// }
		// if(f.get().getStatusCode() == 200){
		// callbackObject.done(Integer.parseInt(f.get().getResponseBody()),
		// null);
		// }else{
		// CloudException e = new CloudException(f.get().getResponseBody());
		// callbackObject.done((Integer)null, e);
		// }
		// } catch (InterruptedException | ExecutionException | IOException e) {
		// CloudException e1 = new CloudException(e.toString());
		// callbackObject.done((Integer)null, e1);
		// e.printStackTrace();
		// }
	}

	public void distinct(String[] keys, CloudObjectArrayCallback callbackObject)
			throws CloudException {
		if (CloudApp.getAppId() == null) {
			throw new CloudException("App Id is null");
		}

		JSONObject params = new JSONObject();
		try {
			params.put("query", this.query);

			params.put("onKey", keys);
			params.put("select", this.select);
			params.put("limit", this.limit);
			params.put("skip", this.skip);
			params.put("key", CloudApp.getAppKey());

		String url = CloudApp.getApiUrl() + "/data/" + CloudApp.getAppId()
				+ "/" + this.tableName + "/distinct";

		CBResponse response=CBParser.callJson(url, "POST", params);
		 if(response.getStatusCode() == 200){
		 JSONArray body = new JSONArray(response.getResponseBody());
		 CloudObject[] object = new CloudObject[body.length()];
		
		 for(int i=0; i<object.length; i++){
		 object[i] = new
		 CloudObject(body.getJSONObject(i).get("_tableName").toString());
		 object[i].document = body.getJSONObject(i);
		 }
		 callbackObject.done(object, null);
		 }else{
		 CloudException e = new CloudException(response.getResponseBody());
		 callbackObject.done((CloudObject[])null, e);
		 }
		 } catch (JSONException e) {
		 CloudException e1 = new CloudException(e.toString());
		 callbackObject.done((CloudObject[])null, e1);
		 e.printStackTrace();
		 }
	}
//
//	 protected String getASCIIContentFromEntity(HttpEntity entity)
//	 throws IllegalStateException, IOException {
//	 InputStream in = entity.getContent();
//	 StringBuffer out = new StringBuffer();
//	 int n = 1;
//	 while (n > 0) {
//	 byte[] b = new byte[4096];
//	 n = in.read(b);
//	 if (n > 0)
//	 out.append(new String(b, 0, n));
//	 }
//	 return out.toString();
//	 }

	public void find(CloudObjectArrayCallback callbackObject)
			throws CloudException {
		if (CloudApp.getAppId() == null) {
			throw new CloudException("App Id is null");
		}

		JSONObject params = new JSONObject();
		try {
			params.put("query", this.query);

			params.put("select", this.select);
			params.put("limit", this.limit);
			params.put("skip", this.skip);
			params.put("sort", this.sort);
			params.put("key", CloudApp.getAppKey());
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
		String url = CloudApp.getApiUrl() + "/data/" + CloudApp.getAppId()
				+ "/" + this.tableName + "/find";
		CBResponse response = CBParser.callJson(url, "POST", params);
		try {

			if (response.getStatusCode() == 200) {
				JSONArray body = new JSONArray(response.getResponseBody());
				CloudObject[] object = new CloudObject[body.length()];

				for (int i = 0; i < object.length; i++) {
					object[i] = new CloudObject(body.getJSONObject(i)
							.getString("_tableName"));
					object[i].document = body.getJSONObject(i);
				}

				callbackObject.done(object, null);
			} else {
				CloudException e = new CloudException(
						response.getResponseBody());
				callbackObject.done((CloudObject[]) null, e);
			}
		} catch (JSONException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((CloudObject[]) null, e1);
			e.printStackTrace();
		}
	}

	public void findById(Object id, CloudObjectCallback callbackObject)
			throws CloudException {
		if (CloudApp.getAppId() == null) {
			throw new CloudException("App Id is null");
		}

		 this.equalTo("id", id);
		this.sort = new JSONObject();
		JSONObject params = new JSONObject();

		try {
			params.put("query", this.query);

			params.put("select", this.select);
			params.put("limit", 1);
			params.put("skip", 0);
			params.put("sort", this.sort);
			params.put("key", CloudApp.getAppKey());
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
		String url = CloudApp.getApiUrl() + "/data/" + CloudApp.getAppId()
				+ "/" + this.tableName + "/find";
		CBResponse response = CBParser.callJson(url, "POST", params);
		try {

			if (response.getStatusCode() == 200) {
				JSONArray body = new JSONArray(response.getResponseBody());
				CloudObject[] object = new CloudObject[body.length()];

				for (int i = 0; i < 1; i++) {
					object[i] = new CloudObject(body.getJSONObject(i)
							.get("_tableName").toString());
					object[i].document = body.getJSONObject(i);
				}
				callbackObject.done(object[0], null);
			} else {
				CloudException e = new CloudException(
						response.getResponseBody());
				callbackObject.done((CloudObject) null, e);
			}
		} catch (JSONException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((CloudObject) null, e1);
			e.printStackTrace();
		}
	}

	public void findOne(CloudObjectCallback callbackObject)
			throws CloudException {
		if (CloudApp.getAppId() == null) {
			throw new CloudException("App Id is null");
		}

		JSONObject params = new JSONObject();
		try {
			params.put("query", this.query);

			params.put("select", this.select);
			params.put("skip", this.skip);
			params.put("sort", this.sort);
			params.put("key", CloudApp.getAppKey());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String url = CloudApp.getApiUrl() + "/data/" + CloudApp.getAppId()
				+ "/" + this.tableName + "/findOne";

		CBResponse response = CBParser.callJson(url, "POST", params);

		try {

			if (response.getStatusCode() == 200) {
				JSONObject body = new JSONObject(response.getResponseBody());
				CloudObject object = new CloudObject(
						body.getString("_tableName"));
				object.document = body;

				callbackObject.done(object, null);
			} else {
				CloudException e = new CloudException(
						response.getResponseBody());
				callbackObject.done((CloudObject) null, e);
			}
		} catch (JSONException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((CloudObject) null, e1);
			e.printStackTrace();
		}
	}
}