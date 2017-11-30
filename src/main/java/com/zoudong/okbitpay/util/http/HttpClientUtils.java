package com.zoudong.okbitpay.util.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;

public class HttpClientUtils {
  private final static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

  private final static String UTF8 = "UTF-8";

  public static CloseableHttpClient httpclient = null;

  static {
    // 初始化线程池
    RequestConfig params = RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(1000).setSocketTimeout(4000)
        .setExpectContinueEnabled(true).build();

    PoolingHttpClientConnectionManager pccm = new PoolingHttpClientConnectionManager();
    pccm.setMaxTotal(300); // 连接池最大并发连接数
    pccm.setDefaultMaxPerRoute(50); // 单路由最大并发数

    HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
      public boolean retryRequest(IOException exception , int executionCount , HttpContext context) {
        // 重试1次,从1开始
        if (executionCount > 1) {
          return false;
        }
        if (exception instanceof NoHttpResponseException) {
          LOGGER.info(
              "[NoHttpResponseException has retry request:" + context.toString() + "][executionCount:" + executionCount + "]");
          return true;
        }
        else if (exception instanceof SocketException) {
          LOGGER.info("[SocketException has retry request:" + context.toString() + "][executionCount:" + executionCount + "]");
          return true;
        }
        return false;
      }
    };
    httpclient = HttpClients.custom().setConnectionManager(pccm).setDefaultRequestConfig(params).setRetryHandler(retryHandler)
        .build();
  }

  public static JSONObject urlPost(String url , Map<String, Object> parameters , Integer connectionRequestTimeout ,
      Integer connectTimeout , Integer socketTimeout) {

    Long startTs = System.currentTimeMillis();
    List<NameValuePair> nvps = new ArrayList<NameValuePair>();

    if (parameters != null && !parameters.isEmpty()) {
      for (Entry<String, Object> entry : parameters.entrySet()) {
        nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
      }
    }

    try {
      LOGGER.info("post-req:url:{},param:{}", url, JSON.toJSONString(parameters));
      HttpPost post = new HttpPost(url);

      if (connectionRequestTimeout != null && connectTimeout != null && socketTimeout != null) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
            .setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout).build();
        post.setConfig(requestConfig);
      }
      post.setEntity(new UrlEncodedFormEntity(nvps, UTF8));

      JSONObject result = invoke(post);
      Long endTs = System.currentTimeMillis();
      Long currentMethodCallTime = endTs - startTs;
      if (currentMethodCallTime > 5000) {
        LOGGER.warn("url:{},call time {} ms", url, currentMethodCallTime);
        LOGGER.info("所有存活线程="+Thread.getAllStackTraces().size());
      }
      LOGGER.info("post-rps:{}", result);
      return result;
    }
    catch (UnsupportedEncodingException e) {
      LOGGER.error("[HttpClientUtils][post][Unsupported Encoding Exception]", e);
    }
    return null;
  }




  public static JSONObject jsonPost(String url, JSONObject jsonParam,Integer connectionRequestTimeout ,
                                    Integer connectTimeout , Integer socketTimeout) {
    Long startTs = System.currentTimeMillis();
    HttpPost httpPost = new HttpPost(url);
    // 设置请求和传输超时时间
    if (connectionRequestTimeout != null && connectTimeout != null && socketTimeout != null) {
      RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
              .setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout).build();
      httpPost.setConfig(requestConfig);
    }
    LOGGER.info("post-req:url:{},param:{}", url, jsonParam);
    if (null != jsonParam) {
      // 解决中文乱码问题
      StringEntity entity = new StringEntity(jsonParam.toString(),
              "utf-8");
      entity.setContentEncoding("UTF-8");
      entity.setContentType("application/json");
      httpPost.setEntity(entity);
    }
    JSONObject result = invoke(httpPost);
    Long endTs = System.currentTimeMillis();
    Long currentMethodCallTime = endTs - startTs;
    if (currentMethodCallTime > 5000) {
      LOGGER.warn("url:{},call time {} ms", url, currentMethodCallTime);
      LOGGER.info("所有存活线程=" + Thread.getAllStackTraces().size());
    }
    LOGGER.info("post-rps:{}", result);
    return result;
  }




  private static JSONObject invoke(HttpUriRequest request) {
    CloseableHttpResponse response = null;
    JSONObject jsonResult = null;
    try {
      response = httpclient.execute(request);
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        String str = "";
        HttpEntity entity = response.getEntity();
        str=EntityUtils.toString(entity, UTF8);
        //把json字符串转换成json对象
        jsonResult = JSONObject.parseObject(str);
        return jsonResult;
      }
    }
    catch (IOException e) {
      LOGGER.error(
          "[HttpClientUtils][invoke][method:" + request.getMethod() + " URI:" + request.getURI() + "] is request exception", e);
    }
    finally {
      if (response != null) {
        try {
          response.close();
        }
        catch (IOException e) {
          LOGGER.error(
              "[HttpClientUtils][invoke][method:" + request.getMethod() + " URI:" + request.getURI() + "] is closed exception",
              e);
        }
      }
    }
    return null;
  }
}