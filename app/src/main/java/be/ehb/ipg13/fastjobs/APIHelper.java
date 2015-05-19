package be.ehb.ipg13.fastjobs;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Nadir on 19/04/2015.
 */
public class APIHelper extends AsyncTask<String, Void, String> {
    public HttpResponse uitkomst;
    public String url, xml;
    public int startIndex, aantal;

    BasicHttpResponse httpResponse = null;
    DefaultHttpClient httpclient = new DefaultHttpClient();
    HttpPost httppost;

    public APIHelper(String xml, int startIndex, int aantal) {
        this.xml = xml;
        this.startIndex = startIndex;
        this.aantal = aantal;
    }

    @Override
    protected String doInBackground(String... params) {
        url = "http://vdabservices-cbt.vdab.be/vindeenjob/1.0.0/" + startIndex + "/" + aantal;

        httppost = new HttpPost(url);

        httppost.setHeader("Accept", "application/xml");
        httppost.setHeader("Content-Type", "application/xml");
        httppost.setHeader("Authorization", "Bearer HQfqJjjwfJVL6zCvWwsfmj3bePAa");

        StringEntity se = null;
        try {
            se = new StringEntity(xml, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        se.setContentType("text/xml");
        httppost.setEntity(se);
        boolean gelukt = true;
        int teller = 0;
        while (gelukt && teller < 10) {
            teller++;
            try {
                httpResponse = (BasicHttpResponse) httpclient.execute(httppost);
                uitkomst = httpResponse;
                gelukt = false;
            } catch (Exception e) {
                System.out.println("HTTP RESPONSE FOUT 1: " + httpResponse);
                System.out.println("MES: " + e.getMessage());
            }
        }
        try {
            if (httpResponse != null) {
                HttpEntity entity = httpResponse.getEntity();
                String data = EntityUtils.toString(entity);
                if (data != null) {
                    return data;
                }else{
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("HTTP RESPONSE FOUT 2: " + httpResponse);
        }
        return null;
    }
}
