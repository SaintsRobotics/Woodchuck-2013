/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grueziforgelauncher;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author liam_000
 */
public class MinecraftLogin {
    public static String getSessionID(String u, String p){
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("https://login.minecraft.net/?user=".concat(u).concat("&password=").concat(p).concat("&version=9999999"));
            System.out.println(httpget.getURI());
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String s=httpclient.execute(httpget, responseHandler);
            System.out.println(s);
            httpclient.getConnectionManager().shutdown();
            return s;
            
        } catch (IOException ex) {
            Logger.getLogger(MinecraftLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
