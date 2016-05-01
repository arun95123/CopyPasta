package in.copypasta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arunkumar on 4/30/2016.
 */
public class Login extends AppCompatActivity {

    String uname = "";
    String pword = "";
    String logurl = "http://54.200.231.130:3001/login";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button login = (Button) findViewById(R.id.login);
        final EditText username = (EditText) findViewById(R.id.Uname);
        final EditText password = (EditText) findViewById(R.id.Pword);

        TextView signup=(TextView)findViewById(R.id.register);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Login.this,Register.class);
                startActivity(i);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname = username.getText().toString();
                pword = password.getText().toString();

                new callogin().execute();
            }
        });


    }

    protected void onPause() {
        super.onPause();
        finish();
    }


    private class callogin extends AsyncTask<String, String, Boolean> {
        private ProgressDialog nDialog;
        EditText temp;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* nDialog = new ProgressDialog(getActivity());
            nDialog.setTitle("Fetching data from server");
            nDialog.setMessage("Please Wait..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();*/
            //  Toast.makeText(getActivity().getApplicationContext(),"fetching results",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(String... args) {


            JSONObject jsonobject;
            final JSONParser jParser2 = new JSONParser();
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();


            params2.add(new BasicNameValuePair("username", uname));
            params2.add(new BasicNameValuePair("password", pword));

            jsonobject = jParser2.makeHttpRequest(logurl, "POST", params2);

            try {
                if (jsonobject != null) {

                    String result = jsonobject.getString("success");
                    //String message=jsonobject.getString("message");

                    if (result.equals("true")) {


                        MainActivity.token = jsonobject.getString("token");
                        MainActivity.mail = jsonobject.getString("email");

                        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putString("token", MainActivity.token);
                        editor.putString("mail", MainActivity.mail);

                        editor.putString("isloggedin", "true");
                        editor.commit();

                        return true;


                    }


                } else {
                    // Toast.makeText(getActivity(), "No response from server", Toast.LENGTH_LONG).show();
                    return false;
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return false;

        }




        @Override
        protected void onPostExecute(Boolean th) {
            // nDialog.dismiss();
            if (!th) {

                Toast.makeText(Login.this, "No response from server", Toast.LENGTH_LONG).show();

            } else {
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);

            }


        }

    }

}
