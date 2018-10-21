package cc.coopersoft.framework.tools;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by cooper on 9/25/16.
 */
public class HttpJsonDataGet {

    public static class ApiServerException extends Exception {

        private int httpStatus;

        public ApiServerException(int httpStatus) {
            super("code:" + httpStatus);
            this.httpStatus = httpStatus;
        }


        public ApiServerException(Throwable cause) {
            super(cause);
            httpStatus = -1;
        }

        public int getHttpStatus() {
            return httpStatus;
        }
    }

    private static class FileUploadResult{
        public int size;

        public String name;

        public String error;

        public String fid;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }
    }

    private static Logger log = Logger.getLogger(HttpJsonDataGet.class.getName());

    private interface JsonParser{

        <T> T readValue(InputStream src) throws IOException;
    }

    private static ObjectMapper getMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return mapper;
    }

    public static class JsonClassParser<T> implements JsonParser{

        private Class<T> valueType;

        public JsonClassParser(Class<T> valueType) {
            this.valueType = valueType;
        }

        public T readValue(InputStream src) throws IOException {
            return getMapper().readValue(src, valueType);
        }
    }


    public static class JsonTypeParser implements JsonParser{

        private JavaType valueType;

        public JsonTypeParser(JavaType valueType) {
            this.valueType = valueType;
        }

        public <T> T readValue(InputStream src) throws IOException {
            return getMapper().readValue(src, valueType);
        }
    }


    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static String putFile(String address, byte[] file, String userId, String rnd, String digest) throws ApiServerException {
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpEntity requestEntity = MultipartEntityBuilder.create().addBinaryBody("file",file, ContentType.APPLICATION_OCTET_STREAM,"file").build();
        Map<String,String> urlParams = new HashMap<String, String>(3);
        urlParams.put("uid",userId);
        urlParams.put("rnd",rnd);
        urlParams.put("digest",digest);

        HttpPost post = new HttpPost(paramsToUrl(address, urlParams));

        post.setHeader("Access-Control-Allow-Origin","*");
        post.setHeader("Access-Control-Allow-Credentials","true");
        post.setHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS");
        post.setHeader("Access-Control-Allow-Headers","DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type");
        post.setEntity(requestEntity);
        try {
            HttpResponse httpResponse = closeableHttpClient.execute(post);

            int responseCode = httpResponse.getStatusLine().getStatusCode();
            if (responseCode == HttpStatus.SC_MOVED_PERMANENTLY
                    || responseCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                return putFile(httpResponse.getLastHeader("Location").getValue(),file,userId,rnd,digest);
            } else {
                if (HttpStatus.SC_OK == responseCode) {
                    ObjectMapper mapper = new ObjectMapper();
                    FileUploadResult result = mapper.readValue(httpResponse.getEntity().getContent(),FileUploadResult.class);
                    //String result = EntityUtils.toString(httpResponse.getEntity());
                    EntityUtils.consume(httpResponse.getEntity());
                    return result.getFid();
                } else {
                    throw new ApiServerException(responseCode);
                }
            }

        } catch (IOException e) {
            throw new ApiServerException(e);
        }


    }

    public static <T> T httpPostJsonData(String address, Map<String,String> urlParams, Map<String,String> postParams , JsonParser jsonParser) throws ApiServerException {
        return httpPostJsonData(paramsToUrl(address, urlParams),postParams,jsonParser);
    }

    public static <T> T httpPostJsonData(String address, Map<String,String> postParams , JsonParser jsonParser) throws ApiServerException {

        List<NameValuePair> values = new ArrayList<NameValuePair>();
        for(Map.Entry<String,String> entry: postParams.entrySet()){
            values.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try {
            Logger.getLogger(HttpJsonDataGet.class.getName()).config("post to address:" + address);
            HttpPost httpPost = new HttpPost(address);
            try {
                httpPost.setHeader("Accept-Charset", "UTF-8");
                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

                httpPost.setEntity(new UrlEncodedFormEntity(values, "UTF-8"));

                HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
                CloseableHttpClient closeableHttpClient = httpClientBuilder.build();


                try {
                    HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
                    int responseCode = httpResponse.getStatusLine().getStatusCode();
                    Logger.getLogger(HttpJsonDataGet.class.getName()).config("post responseCode:" + responseCode);
                    if (responseCode == HttpStatus.SC_MOVED_PERMANENTLY
                            || responseCode == HttpStatus.SC_MOVED_TEMPORARILY) {

                        return httpPostJsonData(httpResponse.getLastHeader("Location").getValue(), postParams, jsonParser);
                    } else {
                        if (HttpStatus.SC_OK == responseCode) {
                            T result = jsonParser.readValue(httpResponse.getEntity().getContent());
                            EntityUtils.consume(httpResponse.getEntity());
                            return result;
                        } else {
                            throw new ApiServerException(responseCode);
                        }
                    }
                } finally {
                    closeableHttpClient.close();
                }
            } finally {
                httpPost.abort();
            }
        }catch (UnsupportedEncodingException e) {
            throw new ApiServerException(e);
        }catch (IOException e) {
            throw new ApiServerException(e);
        }
    }

    private static String paramsToUrl(String address, Map<String,String> params){
        String paramStr = "";

        if (params != null ) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if ("".equals(paramStr)) {
                    paramStr = "?";
                } else {
                    paramStr += "&";
                }
                paramStr += entry.getKey() + "=" + entry.getValue();
            }
        }

        return address + paramStr;
    }


    public static <T> T httpGetJsonData(String address, Map<String,String> params, JsonParser jsonParser) throws ApiServerException {

        try {
            HttpGet httpGet = new HttpGet(paramsToUrl(address,params));
            try {

                httpGet.setHeader("Accept-Charset", "UTF-8");
                httpGet.setHeader("Content-Type", "application/json; charset=utf-8");

                HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

                CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
                try {
                    HttpResponse httpResponse = closeableHttpClient.execute(httpGet);

                    int responseCode = httpResponse.getStatusLine().getStatusCode();
                    if (responseCode == HttpStatus.SC_MOVED_PERMANENTLY
                            || responseCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                        return httpGetJsonData(httpResponse.getLastHeader("Location").getValue(),params,jsonParser);
                    } else {
                        if (HttpStatus.SC_OK == responseCode) {
                            T result = jsonParser.readValue(httpResponse.getEntity().getContent());
                            EntityUtils.consume(httpResponse.getEntity());
                            return result;
                        } else if (HttpStatus.SC_NOT_FOUND == responseCode){
                            return null;
                        } else {
                            throw new ApiServerException(responseCode);
                        }
                    }
                }finally {
                    closeableHttpClient.close();
                }
            }finally {
                httpGet.abort();
            }


        } catch (MalformedURLException e) {
            log.log(Level.WARNING,e.getMessage(),e);
            throw new ApiServerException(e);
        } catch (UnsupportedEncodingException e) {
            log.log(Level.WARNING,e.getMessage(),e);
            throw new ApiServerException(e);
        } catch (JsonParseException e) {
            log.log(Level.WARNING,e.getMessage(),e);
            throw new ApiServerException(e);
        } catch (JsonMappingException e) {
            log.log(Level.WARNING,e.getMessage(),e);
            throw new ApiServerException(e);
        } catch (ClientProtocolException e) {
            log.log(Level.WARNING,e.getMessage(),e);
            throw new ApiServerException(e);
        } catch (IOException e) {
            log.log(Level.WARNING,e.getMessage(),e);
            throw new ApiServerException(e);
        }
    }

    public static <T> T getData(String address, Map<String,String> params, JavaType valueType) throws ApiServerException {
        return httpGetJsonData(address,params, new JsonTypeParser(valueType));
    }

    public static <T> T getData(String address, Map<String,String> params, Class<T> valueType) throws ApiServerException {
        return httpGetJsonData(address,params, new JsonClassParser<T>(valueType));
    }

    public static <T> T postData(String address, Map<String,String> postParams, Class<T> valueType) throws ApiServerException {
        return httpPostJsonData(address,postParams, new JsonClassParser(valueType));
    }

    public static <T> T postData(String address, Map<String,String> urlParams , Map<String,String> postParams, Class<T> valueType) throws ApiServerException {
        return httpPostJsonData(address,urlParams,postParams, new JsonClassParser(valueType));
    }

    public static <T> T postData(String address, Map<String,String> postParams, JavaType valueType) throws ApiServerException {
        return httpPostJsonData(address,postParams, new JsonTypeParser(valueType));
    }

    public static <T> T postData(String address, Map<String,String> urlParams, Map<String,String> postParams, JavaType valueType) throws ApiServerException {
        return httpPostJsonData(address,urlParams,postParams, new JsonTypeParser(valueType));
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

}
