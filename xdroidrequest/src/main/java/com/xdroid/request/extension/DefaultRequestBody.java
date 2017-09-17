package com.xdroid.request.extension;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.xdroid.request.extension.config.RequestMethod;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;



/**
 * The default request body, provide 8 types of request, you can use this to request data request body. Also can use other ways of request.
 * @author Robin
 * @since 2015-05-13 18:12:51
 */ 
public class DefaultRequestBody {

    private static  final int PROGRESS=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PROGRESS:
                    Map<String ,Integer> progressMap= (Map<String, Integer>) msg.obj;
                    int max=progressMap.get("max");
                    int current=progressMap.get("current");
                    if (onProgressChangeListener!=null){
                        onProgressChangeListener.onProgressChange(max,current);
                    }
                    break;
            }
        }
    };

	
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    
    private static final String DEFAULT_CONTENT_TYPE= "application/x-www-form-urlencoded; charset="+"UTF-8";
	
    private final SSLSocketFactory mSslSocketFactory;
    
    private int method;
    
    private OnOriginalRequestListener onOriginalRequestListener;

    private OnProgressChangeListener onProgressChangeListener;

     /*==============================================================
      *  About POST GET HEADER FILE
      * ==============================================================
      */
    
    private Map<String, String> params=new HashMap<String, String>();

    private Map<String, String> headers=new HashMap<String, String>();

    private Map<String,String> mQueryParams=new HashMap<>();

    private Map<String, File> mFileParams=new HashMap<>();

     /*==============================================================
      *  About Query Params
      * ==============================================================
      */
    private static final String CHAR_QM = "?";
    private static final String CHAR_AND = "&";
    private static final String CHAR_EQ = "=";
    private static final String CHAR_SET = "UTF-8";

    /*==============================================================
      *  About File Upload
      * ==============================================================
      */
    private static final String LINE_FEED = "\r\n";
    private static final String CHARSET_DEFAULT = "UTF-8";
    private String mBoundary;
    private String mCharset = CHARSET_DEFAULT;

    private OutputStream mOutputStream;
    private PrintWriter mWriter;

    /*==============================================================
     *  Constructor
     * ==============================================================
     */
	public DefaultRequestBody(){
		this(null);
	}

	public DefaultRequestBody(SSLSocketFactory sslSocketFactory) {
    	this.mSslSocketFactory=sslSocketFactory;
	}

    private int getMethod() {
        return method;
    }

    /**
     * Opens an HttpURLConnection with parameters.
     * @param url
     * @return an open connection
     * @throws java.io.IOException
     */
    private HttpURLConnection openConnection(URL url, XDroidRequest<?> request) throws IOException {
        HttpURLConnection connection = createConnection(url);

        int timeoutMs = (int) request.getCacheConfig().getTimeController().getTimeout();
        connection.setConnectTimeout(timeoutMs);
        connection.setReadTimeout(timeoutMs);
        connection.setUseCaches(false);
        connection.setDoInput(true);

        // use caller-provided custom SslSocketFactory, if any, for HTTPS
        if ("https".equals(url.getProtocol()) && mSslSocketFactory != null) {
            ((HttpsURLConnection)connection).setSSLSocketFactory(mSslSocketFactory);
        }

        return connection;
    }
    
    /**
     * Create an HttpURLConnection for the specified 
     */
    protected HttpURLConnection createConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }
    
    public HttpURLConnection performRequest(XDroidRequest<?> request) throws IOException{
    	return performRequest(request, new HashMap<String,String>(), null);
    }
    
    public HttpURLConnection performRequest(XDroidRequest<?> request, Map<String, String> additionalHeaders,OnOriginalRequestListener onOriginalRequestListener)
            throws IOException {
    	if (onOriginalRequestListener!=null) {
    		this.onOriginalRequestListener=onOriginalRequestListener;
		}
    	
        String url = request.getUrl();
        HashMap<String, String> map = new HashMap<String, String>();
        map.putAll(getHeaders());
        map.putAll(additionalHeaders);
        buildQueryString(getQueryParams(),url);
        URL parsedUrl = new URL(url);
        HttpURLConnection connection = openConnection(parsedUrl, request);
        for (String headerName : map.keySet()) {
            connection.addRequestProperty(headerName, map.get(headerName));
        }
        setConnectionParametersForRequest(connection, request);
        // Initialize HttpResponse with data from the HttpURLConnection.
        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        int responseCode = connection.getResponseCode();
        if (responseCode == -1) {
            // -1 is returned by getResponseCode() if the response code could not be retrieved.
            // Signal to the caller that something was wrong with the connection.
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        StatusLine responseStatus = new BasicStatusLine(protocolVersion,
                connection.getResponseCode(), connection.getResponseMessage());
        BasicHttpResponse response = new BasicHttpResponse(responseStatus);
        response.setEntity(entityFromConnection(connection));
        for (Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            if (header.getKey() != null) {
                Header h = new BasicHeader(header.getKey(), header.getValue().get(0));
                response.addHeader(h);
            }
        }
        
        StatusLine statusLine = response.getStatusLine();
	     @SuppressWarnings("unused")
		int statusCode = statusLine.getStatusCode();
	     Map<String, String> resultHeaders = new HashMap<String, String>();
	     for (int i = 0; i < response.getAllHeaders().length; i++) {
	    	 resultHeaders.put(response.getAllHeaders()[i].getName(), response.getAllHeaders()[i].getValue());
	     }
	     
	    /* if (statusCode == HttpStatus.SC_NOT_MODIFIED) {
	    	 onTransmitInfomation(resultHeaders.get("Set-Cookie"));
	     }*/
		
	    StringBuilder sb=new StringBuilder();
		try {
			if (response.getEntity() != null) {
				InputStream ips=response.getEntity().getContent();
				 BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
				 
			        char[] buffer = new char[1024];
			        int bufferLength;
			        while ((bufferLength = reader.read(buffer, 0, buffer.length)) > 0) {
			            sb.append(buffer, 0, bufferLength);
			        }
			        reader.close();
			        ips.close();
			        connection.disconnect();
			        
			    }
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        
        if (this.onOriginalRequestListener!=null) {
			this.onOriginalRequestListener.onOriginalRequestFinish(sb.toString(),resultHeaders);
		}
        
        return connection;
    }
    

    /**
     * Initializes an HttpEntity from the given HttpURLConnection.
     * @param connection
     * @return an HttpEntity populated with data from <code>connection</code>.
     */
    private static HttpEntity entityFromConnection(HttpURLConnection connection) {
        BasicHttpEntity entity = new BasicHttpEntity();
        InputStream inputStream;
        try {
            inputStream = connection.getInputStream();
        } catch (IOException ioe) {
            inputStream = connection.getErrorStream();
        }
        entity.setContent(inputStream);
        entity.setContentLength(connection.getContentLength());
        entity.setContentEncoding(connection.getContentEncoding());
        entity.setContentType(connection.getContentType());
        return entity;
    }
    
    /* package */  void setConnectionParametersForRequest(HttpURLConnection connection,
            XDroidRequest<?> request) throws IOException {
        switch (getMethod()) {
            case RequestMethod.DEPRECATED_GET_OR_POST:
                // This is the deprecated way that needs to be handled for backwards compatibility.
                // If the request's post body is null, then the assumption is that the request is
                // GET.  Otherwise, it is assumed that the request is a POST.
                byte[] postBody = getBody();
                if (postBody != null) {
                    // Prepare output. There is no need to set Content-Length explicitly,
                    // since this is handled by HttpURLConnection using the size of the prepared
                    // output stream.
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.addRequestProperty(HEADER_CONTENT_TYPE,
                            DEFAULT_CONTENT_TYPE);
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.write(postBody);
                    out.close();
                }
                break;
            case RequestMethod.GET:
                // Not necessary to set the request method because connection defaults to GET but
                // being explicit here.
                connection.setRequestMethod("GET");
                break;
            case RequestMethod.DELETE:
                connection.setRequestMethod("DELETE");
                break;
            case RequestMethod.POST:
                connection.setRequestMethod("POST");
                if (getFileParams().size()<=0){
                    addBodyIfExists(connection, request);
                    break;
                }

                addFileBodyAndField(connection);

                break;
            case RequestMethod.PUT:
                connection.setRequestMethod("PUT");
                addBodyIfExists(connection, request);
                break;
            case RequestMethod.HEAD:
                connection.setRequestMethod("HEAD");
                break;
            case RequestMethod.OPTIONS:
                connection.setRequestMethod("OPTIONS");
                break;
            case RequestMethod.TRACE:
                connection.setRequestMethod("TRACE");
                break;
            case RequestMethod.PATCH:
                connection.setRequestMethod("PATCH");
                addBodyIfExists(connection, request);
                break;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
    }
    
    private  void addBodyIfExists(HttpURLConnection connection, XDroidRequest<?> request)
            throws IOException {
        byte[] body = getBody();
        if (body != null) {
            connection.setDoOutput(true);
            connection.addRequestProperty(HEADER_CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(body);
            out.close();
        }
    }


    /**
     * Add a file with the post field
     * @param connection
     * @throws IOException
     */
    private void addFileBodyAndField(HttpURLConnection connection) throws IOException{
        connection.setDoOutput(true);
        connection.setDoInput(true);

        mBoundary = "===" + System.currentTimeMillis() + "===";
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + mBoundary);
        mOutputStream = connection.getOutputStream();
        mWriter = new PrintWriter(new OutputStreamWriter(mOutputStream), true);

        // send data to Server
        if (getParams() != null && getParams().size() != 0) {

            Iterator<Entry<String, String>> iterator = getParams().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> item = iterator.next();
                Object value = item.getValue();
                if (value == null) {
                    value = "";
                }
                addFormField(item.getKey(), value.toString());
            }
        }

        // send file to server
        Iterator<Map.Entry<String, File>> iterator = getFileParams().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, File> item = iterator.next();
            File uploadFile = item.getValue();
            addFilePart(item.getKey(), uploadFile, uploadFile.getName());
        }


    }

    /**
     * Adds a form field to the request
     *
     * @param name  field name
     * @param value field value
     */
    private void addFormField(String name, String value) {
        mWriter.append("--" + mBoundary).append(LINE_FEED);
        mWriter.append("Content-Disposition: form-data; name=\"" + name + "\"")
                .append(LINE_FEED);
        mWriter.append("Content-Type: text/plain; charset=" + mCharset).append(
                LINE_FEED);
        mWriter.append(LINE_FEED);
        mWriter.append(value).append(LINE_FEED);
        mWriter.flush();
    }

    /**
     * Adds a upload file section to the request
     *
     * @param fieldName  name attribute in <input type="file" name="..." />
     * @param uploadFile a File to be uploaded
     * @param fileName   the filename field
     * @throws IOException
     */
    private void addFilePart(String fieldName, File uploadFile, String fileName) throws IOException {
        if (TextUtils.isEmpty(fileName)) {
            fileName = uploadFile.getName();
        }
        mWriter.append("--" + mBoundary).append(LINE_FEED);
        mWriter.append(
                "Content-Disposition: form-data; name=\"" + fieldName
                        + "\"; filename=\"" + fileName + "\"")
                .append(LINE_FEED);
        mWriter.append(
                "Content-Type: " + URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        mWriter.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        mWriter.append(LINE_FEED);
        mWriter.flush();

        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        // calculate the progress
        long total = inputStream.available();
        int max = Integer.parseInt(total + "");
        int current=0;


        while ((bytesRead = inputStream.read(buffer)) != -1) {
            mOutputStream.write(buffer, 0, bytesRead);

            // Send the current progress to the main thread
            current+=bytesRead;

            Message msg=handler.obtainMessage();
            msg.what=PROGRESS;
            Map<String,Integer> progressMap=new HashMap<>();
            progressMap.put("max", max);
            progressMap.put("current",current);
            msg.obj=progressMap;
            handler.sendMessage(msg);

            /*if (onProgressChangeListener!=null){
                onProgressChangeListener.onProgressChange(max,current);
            }*/
        }
        mOutputStream.flush();
        inputStream.close();

        mWriter.append(LINE_FEED).flush();
        mWriter.append("--" + mBoundary + "--").append(LINE_FEED);
        mWriter.close();
    }

    private byte[] getBody() {
        Map<String, String> params = getParams();
        if (params != null && params.size() > 0) {
            return encodeParameters(params, "UTF-8");
        }
        return null;
    }
    
    /**
     * Converts <code>params</code> into an application/x-www-form-urlencoded encoded string.
     */
    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    /** Combined with the url of the parameter, forming a get request url */
    public static String buildQueryString(Map<String, ?> queryParams, String url) {

        if (queryParams == null || queryParams.size() == 0) {
            return url;
        }

        StringBuilder sb = new StringBuilder();
        boolean append = false;
        if (url != null) {
            sb.append(url);
            if (url.contains(CHAR_QM)) {
                append = true;
            } else {
                sb.append(CHAR_QM);
            }
        }
        Iterator<? extends Entry<String, ?>> it = queryParams.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ?> item = it.next();
            if (append) {
                sb.append(CHAR_AND);
            } else {
                append = true;
            }

            try {
                if (TextUtils.isEmpty(item.getKey())) {
                    continue;
                }
                sb.append(URLEncoder.encode(item.getKey(), CHAR_SET));
                sb.append(CHAR_EQ);
                if (item.getValue() != null) {
                    sb.append(URLEncoder.encode(item.getValue().toString(), CHAR_SET));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /*==============================================================================================
     * Some public function calls for the outside
     *==============================================================================================
     */

    public void setMethod(int method) {
        this.method = method;
    }
    
    public Map<String, String> getParams()  {
        return params;
    }
    
    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getQueryParams() {
        return mQueryParams;
    }

    public Map<String, File> getFileParams() {
        return mFileParams;
    }

    public OnOriginalRequestListener getOnOriginalRequestListener() {
		return onOriginalRequestListener;
	}

	public void setOnOriginalRequestListener(
			OnOriginalRequestListener onOriginalRequestListener) {
		this.onOriginalRequestListener = onOriginalRequestListener;
	}

    public OnProgressChangeListener getOnProgressChangeListener() {
        return onProgressChangeListener;
    }

    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        this.onProgressChangeListener = onProgressChangeListener;
    }

    public interface OnOriginalRequestListener{
    	public void onOriginalRequestFinish(String response, Map<String, String> resultHeaders);
    }

    /** A callback for  listen into  file upload progress */
    public interface OnProgressChangeListener{
        void onProgressChange(int max,int current);
    }
	
}
