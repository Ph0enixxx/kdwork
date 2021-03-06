package com.kingdee.drc.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	public static String postData(String methodName,
			Map<String, String> reparams) {
		String strResult = null;
		HttpPost post = new HttpPost(Constant.URL);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Set<String> keys = reparams.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			params.add(new BasicNameValuePair(key, reparams.get(key)));
		}
		params.add(new BasicNameValuePair("token", Constant.TOKEN));
		params.add(new BasicNameValuePair("methodName", methodName));
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode("3");
		try {
			HttpEntity httpentity = new UrlEncodedFormEntity(params, "UTF-8");
			post.setEntity(httpentity);
			HttpClient client = new DefaultHttpClient();
			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(res.getEntity(), "UTF-8");
			} else {
				jsonUtil.setMsg(res.getStatusLine().toString());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			jsonUtil.setMsg(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			jsonUtil.setMsg(e.getMessage());
		}
		return strResult == null ? jsonUtil.toString() : strResult;
	}
}
