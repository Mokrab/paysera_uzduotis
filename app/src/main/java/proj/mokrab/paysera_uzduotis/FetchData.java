package proj.mokrab.paysera_uzduotis;

import android.os.AsyncTask;
import java.net.MalformedURLException;
import java.net.URL;


public class FetchData extends AsyncTask<Void, Void, String> {

    private String urlString;
    private String response;
    private HttpHandler connection;




    public FetchData(String u) {
        urlString = u;
        connection = new HttpHandler();
    }


    @Override
    protected String doInBackground(Void... voids) {

        try {
           URL url = new URL(urlString);
           response = connection.openHttpConnection(url);


        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String str) {
       super.onPostExecute(str);

    }


}
