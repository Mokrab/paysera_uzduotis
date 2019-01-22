package proj.mokrab.paysera_uzduotis;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();
    private String response = "";

    public HttpHandler() {

    }

    public String openHttpConnection(URL url) {

        try {

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                response = response + line;
                Log.d("Buffered", response);
            }


        } catch (MalformedURLException e) {
            Log.d(TAG, "Malformed Exception" + e.getMessage());
        } catch (ProtocolException e) {
            Log.d(TAG, "Protocol Exception" + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "IO Exception" + e.getMessage());
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        }
        return response;

    }

}
