/**
 * Author: Kabir Soneja
 * Andrew ID: ksoneja
 * Last Modified: April 06, 2020
 */

package edu.cmu.java.openaqapp;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


public class OpenAirQuality extends AppCompatActivity {

    Spinner countrynames;

    //Input countries
    String load_input[] = {"India","United States","China","Columbia","Germany","Ireland","Pakistan","Spain","Taiwan","Thailand","France","Australia","Austria"};

    /*
     * On create Method to launch at the start.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final OpenAirQuality ma = this;

        LoadData();                                                             //To load data into the spinner

        ImageView iv= (ImageView)findViewById(R.id.imageView);                 //Setting the image view
        String path = getApplication().getFilesDir().getAbsolutePath();
        InputStream is = null;
        try {
            is = new FileInputStream(path + "/corona.jpg");              //Image path
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        iv.setImageResource(R.drawable.corona);

        /*
         * Listener for dropdown
         */
        countrynames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View v, int position, long id)
            {
                String option = arg0.getItemAtPosition(position).toString();                //Getting the selected option
                GetAirQuality ga = new GetAirQuality();
                ga.search(option,ma);

                Button exit = findViewById(R.id.exit_button);                               //Exit button
                exit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {                                              //Listener for exit button
                        finish();
                        System.exit(0);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /*
     * Method to set the table view and populate the data from the api.
     * This method takes the response and extracts relevant information and stores it into each of the table rows.
     */
    public void ready(String response) {

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(response.toString());                 //Parsing the response received from the web service
            JSONArray jsonArray = (JSONArray) jsonObject.get("results");

                if ((JSONObject) jsonArray.get(0) != null){
                    JSONObject j2 = (JSONObject) jsonArray.get(0);
                    TextView textView3 = findViewById(R.id.col3);
                    textView3.setText(j2.get("locations").toString());
                    TextView textView4 = findViewById(R.id.col4);
                    textView4.setText(j2.get("count").toString());
                    TextView textView20 = findViewById(R.id.textView21);
                    textView20.setText(j2.get("name").toString());
                } else {
                    TextView textView3 = findViewById(R.id.col3);
                    textView3.setText("No data");
                    TextView textView4 = findViewById(R.id.col4);
                    textView4.setText("No data");
                    TextView textView20 = findViewById(R.id.textView21);
                    textView20.setText("No data");
                }

            if ((JSONObject) jsonArray.get(1) != null) {
                JSONObject j3 = (JSONObject) jsonArray.get(1);
                TextView textView5 = findViewById(R.id.col5);
                textView5.setText(j3.get("locations").toString());
                TextView textView6 = findViewById(R.id.col6);
                textView6.setText(j3.get("count").toString());
                TextView textView21 = findViewById(R.id.textView22);
                textView21.setText(j3.get("name").toString());
            } else {
                TextView textView5 = findViewById(R.id.col5);
                textView5.setText("No data");
                TextView textView6 = findViewById(R.id.col6);
                textView6.setText("No data");
                TextView textView21 = findViewById(R.id.textView22);
                textView21.setText("No data");
            }

            if ((JSONObject) jsonArray.get(2) != null) {
                JSONObject j4 = (JSONObject) jsonArray.get(2);
                TextView textView7 = findViewById(R.id.col7);
                textView7.setText(j4.get("locations").toString());
                TextView textView8 = findViewById(R.id.col8);
                textView8.setText(j4.get("count").toString());
                TextView textView22 = findViewById(R.id.textView24);
                textView22.setText(j4.get("name").toString());
            } else {
                TextView textView7 = findViewById(R.id.col7);
                textView7.setText("No data");
                TextView textView8 = findViewById(R.id.col8);
                textView8.setText("No data");
                TextView textView22 = findViewById(R.id.textView24);
                textView22.setText("No data");
            }
            if ((JSONObject) jsonArray.get(3) != null) {
                JSONObject j5 = (JSONObject) jsonArray.get(3);
                TextView textView9 = findViewById(R.id.col9);
                textView9.setText(j5.get("locations").toString());
                TextView textView10 = findViewById(R.id.col10);
                textView10.setText(j5.get("count").toString());
                TextView textView24 = findViewById(R.id.textView25);
                textView24.setText(j5.get("name").toString());

            } else {
                TextView textView9 = findViewById(R.id.col9);
                textView9.setText("No data");
                TextView textView10 = findViewById(R.id.col10);
                textView10.setText("No data");
                TextView textView24 = findViewById(R.id.textView25);
                textView24.setText("No data");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /*
     * Method to Load data into the spinner.
     */
    public void LoadData() {
        countrynames = (Spinner) findViewById(R.id.spinner);                    //Get the spinner by id
        List<String> countries = Arrays.asList(load_input);                     //Array List for all the countries
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, countries);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrynames.setAdapter(dataAdapter);
    }

}
