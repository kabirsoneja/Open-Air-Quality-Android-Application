/**
 * Author: Kabir Soneja
 * Andrew ID: ksoneja
 * Last Modified: April 04, 2020
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AirQualityWebService extends HttpServlet {
    private static HttpsURLConnection connection;

    // GET returns a value given a key
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Map to store key value pairs
        //Key: Country name
        //Value: Two letter country code
        Map<String, String> map = new HashMap<>();
        map.put("india","IN");
        map.put("united states","US");
        map.put("china","CN");
        map.put("columbia","CO");
        map.put("germany","DE");
        map.put("ireland","IE");
        map.put("pakistan","PK");
        map.put("spain","ES");
        map.put("sweden","SE");
        map.put("united arab emirates","AE");
        map.put("taiwan","TW");
        map.put("thailand","TH");
        map.put("france","FR");
        map.put("Chile","CL");
        map.put("australia","AU");
        map.put("Austria","AT");

        //Response buffer to hold the response
        StringBuffer responseBuffer = new StringBuffer();
        BufferedReader reader;
        String line;

        String result = "";

        // The name is on the path /name so skip over the '/'
        String cname = (request.getPathInfo()).substring(1);
        if (cname.isEmpty()) {
            response.setStatus(200);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request,response);
            return ;
        }
        else {
            System.out.println("Console: Country - "+cname);
            String input = map.get(cname.toLowerCase());

            //OpenAQ API url.
            try {
                URL url = new URL("https://api.openaq.org/v1/cities?limit=10&country="+input);
                connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/json");

                //Connection set up
                connection.setRequestMethod("GET");
                connection.connect();

                int status = connection.getResponseCode();

                if (status > 299) {
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    while ((line = reader.readLine()) != null) {
                        responseBuffer.append(line);
                    }
                }
                else {
                    reader = new BufferedReader((new InputStreamReader(connection.getInputStream())));
                    while ((line = reader.readLine()) != null) {
                        responseBuffer.append(line);
                    }
                    reader.close();
                }

                //Parsing the response and retrieving the required parameters
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(responseBuffer.toString());
                JSONArray jsonArray = (JSONArray) jsonObject.get("results");
                JSONObject j1 = new JSONObject();
                j1.put("results",jsonArray);
                PrintWriter out = response.getWriter();
                out.println(j1.toJSONString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
        }

    }
}
