import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by jago2 on 12.08.2018.
 */
public class HttpClientExample {

    public static void main(String[] args) {
        HttpClientExample example = new HttpClientExample();
        try {
            example.defaultClient("https://en.wikipedia.org/wiki/Horse");
            example.defaultClient("https://en.wikipedia.org/wiki/Hores");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void defaultClient(final String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(url);

            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                    System.out.println("--------------------------------------------------");
                    System.out.println("URL: " + url);
                    System.out.println("returned code: " + httpResponse.getStatusLine().getStatusCode() + " " + httpResponse.getStatusLine().getReasonPhrase());
                    System.out.println("--------------------------------------------------");
                    System.out.println("Headers: ");
                    for (Header header : httpResponse.getAllHeaders()) {
                        System.out.println(header);
                    }
                    HttpEntity entity = httpResponse.getEntity();
                    if (entity != null) {
                        return EntityUtils.toString(entity);
                    }
                    throw new ClientProtocolException("Unexpected response status: " + httpResponse.getStatusLine().getStatusCode());
                }
            };

            String responseBody = httpclient.execute(request, responseHandler);
            System.out.println("--------------------------------------------------");
            System.out.println("body length: "+responseBody.length());

        } finally {
            httpclient.close();
        }


    }
}
