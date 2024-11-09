package org.cowain.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * httpClient工具类
 */
public class HttpClientUtils {
    static final int TIMEOUT_MSEC = 5 * 1000;

    //发送get请求
    public static String doGet(String url, Map<String, String> params) {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        CloseableHttpResponse execute = null;
        try {
            //构建参数
            URIBuilder uriBuilder = new URIBuilder(url);
            if (params != null) {
                for (String key : params.keySet()) {
                    uriBuilder.setParameter(key, params.get(key));
                }
            }
            URI uri = uriBuilder.build();
            //创建请求方式
            HttpGet httpGet = new HttpGet(uri);

            //发送请求
            execute = httpClient.execute(httpGet);
            //结果获取
            int statusCode = execute.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = execute.getEntity();
                result = EntityUtils.toString(entity, "UTF-8");
            }

            //关闭资源
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                execute.close();
                httpClient.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    //发送post请求
    public static String doPost(String url, Map<String, String> params) throws IOException {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            //创建http post请求
            HttpPost httpPost = new HttpPost(url);
            //创建参数列表
            if (params != null) {
                List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
                for (String key : params.keySet()) {
                    paramsList.add(new BasicNameValuePair(key, params.get(key)));
                }
                //模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramsList);
                httpPost.setEntity(entity);
            }

            httpPost.setConfig(builderRequestConfig());
            //执行http请求
            response = httpClient.execute(httpPost);

            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return resultString;
    }

    //发送POST方式请求
    public static String doPost4Json(String url, Map<String, String> params) {
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            //post请求
            HttpPost httpPost = new HttpPost(url);
            if (params != null) {
                //构建json格式数据
                JSONObject jsonObject = new JSONObject();
                for (String key : params.keySet()) {
                    jsonObject.put(key, params.get(key));
                }
                StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
                //设置请求编码
                entity.setContentEncoding("utf-8");
                //设置数据类型
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }

            httpPost.setConfig(builderRequestConfig());

            //执行http请求
            response = httpClient.execute(httpPost);

            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return resultString;
    }

    private static RequestConfig builderRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(TIMEOUT_MSEC)
                .setConnectionRequestTimeout(TIMEOUT_MSEC)
                .setSocketTimeout(TIMEOUT_MSEC).build();
    }
}
