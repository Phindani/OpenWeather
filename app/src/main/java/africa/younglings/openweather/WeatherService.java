package africa.younglings.openweather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WeatherService extends AsyncTask <Void,Void, Void> {
    String jsonString = "";
    String key = "dc2d8a0b75afc896aaa1aa6e9dce7952";
    String city;
    String iconId = "";
    URL iconUrl;
    Bitmap bitmap;
    String description = "";
    String humidity = "";
    String temperature = "";
    String city_country = "";
    String date_description = "";
    String pressure = "";
    String pressure_info = "";
    String wind_info = "";
    String clouds_info;
    String humidity_info;
    String coords = "";
    String coords_info = "";
    String cloudiness = "";
    String sunrise = "";
    String sunrise_info = "";
    String sunset_info = "";
    String sunset = "";
    String cityName = "";
    ImageView imageView;
    Date currentDate = new Date(System.currentTimeMillis());
    Time currentTime = new Time(System.currentTimeMillis());




    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        city = MainActivity.et_city_name.getText().toString().toLowerCase();
        if (city.contains(" ")) city.replace(' ','+');
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=cape+town&units=metric&appid=" + key);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                jsonString = jsonString + line;
            }

            //Using JSONArray to access each element of weather array, to get icon and description
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray weather = jsonObject.getJSONArray("weather");
            for (int i = 0; i < weather.length(); i++) {
                JSONObject weatherJSONObject = weather.getJSONObject(i);

                //assign icon code with data from weather array element 'icon'
                iconId = weatherJSONObject.getString("icon");

                //assign description with data from weather array element 'description'
                description = weatherJSONObject.getString("description");
            }

            //declare icon url and assign it the url to point to the icon for specific weather for a city, and using icon code I fot from JSON data
            iconUrl = new URL("http://openweathermap.org/img/w/" + iconId + ".png");
            InputStream in = iconUrl.openStream();
            bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(in), 128, 128, false);

            //declare a variable and assign name with data from json object 'name"
            String cityName = jsonObject.getString("name");

            //set country code info and build a string, to display city name together with country code
            JSONObject sys = jsonObject.getJSONObject("sys");
            city_country = "Weather in " + cityName + ", " + sys.getString("country") + "\n";

            //build a string consisting of weather description, current date and current time
            date_description = description + "\n\n" + "get at " + currentDate + "  " + currentTime;

            //set wind info and build a string for display in TextView
            JSONObject wind = jsonObject.getJSONObject("wind");
            String wind_details = wind.getString("speed") + " m/s" + "\n\t\t\t\t\t\t\t\t\t" +
                    "\t\t\t\t\t\t\t\t\t\t" + wind.getString("deg") + " degrees";
            wind_info = "Wind" + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + wind_details;

            //set clouds info and build a string for display in TextView
            JSONObject clouds = jsonObject.getJSONObject("clouds");
            cloudiness = clouds.getString("all");
            clouds_info = "Cloudiness" + "\t\t\t\t\t\t\t\t" + cloudiness + " %";

            //set temperature info and build a string for display in TextView
            JSONObject main = jsonObject.getJSONObject("main");
            temperature = main.getString("temp") + " \u2103";

            //set pressure info and build a string for display in TextView
            pressure = main.getString("pressure");
            pressure_info = "Pressure" + "\t\t\t\t\t\t\t\t\t\t" + pressure + " hpa";

            //set humidity info and build a string for display in TextView
            humidity = main.getString("humidity");
            humidity_info = "Humidity" + "\t\t\t\t\t\t\t\t\t\t\t" + humidity + " %";

            //convert sunrise
            Date dt_sunrise = new Date(sys.getLong("sunrise") * 1000);
            SimpleDateFormat sfd_sunrise = new SimpleDateFormat("HH:mm");
            sunrise = sfd_sunrise.format(dt_sunrise).toString();
            sunrise_info = "Sunrise" + "\t\t\t\t\t\t\t\t\t\t\t\t\t" + sunrise;

            //convert sunset
            Date dt_sunset = new Date(sys.getLong("sunset") * 1000);
            SimpleDateFormat sfd_sunset = new SimpleDateFormat("HH:mm");
            sunset = sfd_sunset.format(dt_sunset).toString();
            sunset_info = "Sunset" + "\t\t\t\t\t\t\t\t\t\t\t\t\t" + sunset;

            //set coordinates info and build a string for display in TextView
            JSONObject coord = jsonObject.getJSONObject("coord");
            coords = "[" + coord.getString("lat") + "," + coord.getString("lon") + "]";
            coords_info = "Geo coords" + "\t\t\t\t\t\t\t\t" + coords;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        WeatherDetailsActivity.tv_cityName.setText(this.city_country);
        WeatherDetailsActivity.tv_temperature.setText(this.temperature);
        WeatherDetailsActivity.tv_date_time.setText(this.date_description);
        WeatherDetailsActivity.tv_wind.setText(this.wind_info);
        WeatherDetailsActivity.tv_cloudiness.setText(this.clouds_info);
        WeatherDetailsActivity.tv_pressure.setText(this.pressure_info);
        WeatherDetailsActivity.tv_humidity.setText(this.humidity_info);
        WeatherDetailsActivity.tv_sunrise.setText(this.sunrise_info);
        WeatherDetailsActivity.tv_sunset.setText(this.sunset_info);
        WeatherDetailsActivity.tv_geo_coords.setText(this.coords_info);
        WeatherDetailsActivity.imageView.setImageBitmap(bitmap);


    }
}
