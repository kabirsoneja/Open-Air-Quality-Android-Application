/**
 * Author: Kabir Soneja
 * Andrew ID: ksoneja
 * Last Modified: April 05, 2020
 */

package edu.cmu.java.openaqapp;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * This class provides capabilities to search the api for the country information given a search term.  The method "search" is the entry to the class.
 * Network operations cannot be done from the UI thread, therefore this class makes use of an AsyncTask inner class that will do the network
 * operations in a separate worker thread.
 */
public class GetAirQuality {

    OpenAirQuality aq = null;
    public static String output;
    public void search(String searchTerm, OpenAirQuality aq) {
        this.aq = aq;
        new AsyncFlickrSearch().execute(searchTerm);
    }

    private class AsyncFlickrSearch extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return getResult(urls[0]);
        }

        protected void onPostExecute(String input) {
           aq.ready(output);
        }

        public String getResult(String name) {

            // Make an HTTP GET passing the name on the URL line

            String response = "";
            HttpURLConnection conn;
            int status = 0;

            try {

                // pass the name on the URL line
               // URL url = new URL("http://192.168.0.13:8080/getCountry/"+name);
                URL url = new URL("https://nameless-brook-59843.herokuapp.com/getCountry/"+name);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                // tell the server what format we want back
                conn.setRequestProperty("Accept", "text/plain");

                // wait for response
                status = conn.getResponseCode();

                // If things went poorly, don't try to read any response, just return.
                if (status != 200) {
                    // not using msg
                    String msg = conn.getResponseMessage();
                    //return conn.getResponseCode();
                }
                String output = "";
                // things went well so let's read the response
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                while ((output = br.readLine()) != null) {
                    response += output;

                }
                conn.disconnect();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }   catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(response.toString());
            output = response;
            return response;
        }

    }
}