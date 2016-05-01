package in.copypasta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static String token="";
    static String mail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        String url  = "";

        Intent intent = getIntent();
        url  = intent.getDataString();

        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String islogged=pref.getString("isloggedin",null);
        if(islogged==null){


            Intent i=new Intent(MainActivity.this,Login.class);
            startActivity(i);

        }

        else if(islogged.equals("false")){


            Intent i=new Intent(MainActivity.this,Login.class);
            startActivity(i);

        }

        else{




        final JSONParser jParser = new JSONParser();
        List<NameValuePair> params3 = new ArrayList<NameValuePair>();
        params3.add(new BasicNameValuePair("token",MainActivity.token));
        params3.add(new BasicNameValuePair("url",url));
        String link = "http://54.200.231.130:3001/check";


        if(url != null){


            JSONObject jsonobject = jParser.makeHttpRequest(link, "POST", params3);

            try {
                String result1 = jsonobject.getString("success");
                if (result1.equals("true")) {
                    Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
