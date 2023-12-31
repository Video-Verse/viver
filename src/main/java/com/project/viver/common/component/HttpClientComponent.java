package com.project.viver.common.component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HttpClientComponent {

	public static final Logger log = LoggerFactory.getLogger(HttpClientComponent.class);

	private RequestConfig requestConfig = RequestConfig.custom()
										.setConnectionRequestTimeout(20 * 1000)
										.setSocketTimeout(20 * 1000)
										.setConnectTimeout(20 * 1000)
										.build();

	/**
	 * HttpClient Get
	 *
	 * @param url
	 * @param header
	 * @param body
	 * @return
	 */
	public Map<String, Object> get(String domain, String url, Map<String, Object> header, Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> map = null;

			List<NameValuePair> paramList = convertParam(params);

		try {
			CredentialsProvider provider = new BasicCredentialsProvider();
//			provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pwd));

			HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setDefaultCredentialsProvider(provider).build();
			URI uri;
			uri = new URIBuilder(domain + url).addParameters(paramList).build();
			HttpGet httpGet = new HttpGet(uri);

			for (String key : header.keySet()) {
				httpGet.setHeader(key, header.get(key).toString());
			}

			log.debug(httpGet.toString());

			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity responseEntity = response.getEntity();
			 if (responseEntity != null) {
		            String contentType = responseEntity.getContentType().getValue();
		            String responseString = EntityUtils.toString(responseEntity, "UTF-8");

		            if (contentType.contains("application/xml")) {
		    	        JSONObject jsonObject = XML.toJSONObject(responseString);
		    	        String jsonString = jsonObject.toString();
		    	        map = objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
		            } else if (contentType.contains("application/json")) {
		                map = objectMapper.readValue(responseString, new TypeReference<Map<String, Object>>() {});
		            }

		            EntityUtils.consume(responseEntity); // Release resources
		        }
			
		} catch (ParseException e) {
			log.debug("ParseException Occured");
		} catch (URISyntaxException e) {
			log.debug("URISyntaxException Occured");
		} catch (IOException e) {
			log.debug("IOException Occured");
		}
		return map;
	}

	/**
	 * HttpClient Post(parameters)
	 *
	 * @param url
	 * @param contentType
	 * @param data
	 * @return
	 */
	public Map<String, Object> post(String domain, String url, Map<String, Object> header, Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			CredentialsProvider provider = new BasicCredentialsProvider();
//			provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pwd));

			HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setDefaultCredentialsProvider(provider).build();
			HttpPost httpPost = new HttpPost(domain + url);

			for (String key : header.keySet()) {
				httpPost.setHeader(key, header.get(key).toString());
			}

			List<NameValuePair> paramList = convertParam(params);

			httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));

			HttpResponse response;
			response = httpClient.execute(httpPost);
			result.put("code", response.getStatusLine().getStatusCode());
			result.put("xml", EntityUtils.toString(response.getEntity(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.debug("UnsupportedEncodingException Occured");
		} catch (ClientProtocolException e) {
			log.debug("ClientProtocolException Occured");
		} catch (IOException e) {
			log.debug("IOException Occured");
		}catch (ParseException e) {
			log.debug("ParseException Occured");
		}

		return result;
	}

	/**
	 * HttpClient put(parameters)
	 *
	 * @param url
	 * @param contentType
	 * @param data
	 * @return
	 */
	public Map<String, Object> put(String domain, String url, Map<String, Object> header, Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			CredentialsProvider provider = new BasicCredentialsProvider();
//			provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pwd));

			HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setDefaultCredentialsProvider(provider).build();
			HttpPut httpPut = new HttpPut(domain + url);

			for (String key : header.keySet()) {
				httpPut.setHeader(key, header.get(key).toString());
			}

			List<NameValuePair> paramList = convertParam(params);
			httpPut.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));

			HttpResponse response = httpClient.execute(httpPut);
			result.put("code", response.getStatusLine().getStatusCode());
			result.put("xml", EntityUtils.toString(response.getEntity(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.debug("UnsupportedEncodingException Occured");
		} catch (ClientProtocolException e) {
			log.debug("ClientProtocolException Occured");
		} catch (IOException e) {
			log.debug("IOException Occured");
		} catch (ParseException e) {
			log.debug("ParseException Occured");
		}
		return result;
	}

	/**
	 * HttpClient Delete
	 *
	 * @param url
	 * @param header
	 * @param body
	 * @return
	 */
	public Map<String, Object> delete(String domain, String url, Map<String, Object> header, Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			List<NameValuePair> paramList = convertParam(params);

			CredentialsProvider provider = new BasicCredentialsProvider();
//			provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pwd));

			HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setDefaultCredentialsProvider(provider).build();
			URI uri = new URIBuilder(domain + url).addParameters(paramList).build();
			HttpDelete httpDelete = new HttpDelete(uri);

			for (String key : header.keySet()) {
				httpDelete.setHeader(key, header.get(key).toString());
			}

			HttpResponse response = httpClient.execute(httpDelete);
			result.put("code", response.getStatusLine().getStatusCode());
			result.put("xml", EntityUtils.toString(response.getEntity(), "UTF-8"));
		}  catch (URISyntaxException e) {
			log.debug("URISyntaxException Occured");
		} catch (ClientProtocolException e) {
			log.debug("ClientProtocolException Occured");
		} catch (IOException e) {
			log.debug("IOException Occured");
		} catch (ParseException e) {
			log.debug("ParseException Occured");
		}
		return result;
	}

	/**
	 * map to NameValuePairList
	 *
	 * @param param
	 * @return
	 */
	public List<NameValuePair> convertParam(Map<String, Object> param) {
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		for (String key : param.keySet()) {
			data.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
		}
		return data;
	}
}
