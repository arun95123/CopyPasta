package in.copypasta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arunkumar on 4/30/2016.
 */
public class Register extends AppCompatActivity {

    String regurl="http://54.200.231.130:3001/signup";
    String uname="";
    String pword="";
    String mail="";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText username = (EditText) findViewById(R.id.Runame);
        final EditText password = (EditText) findViewById(R.id.Rpword);
        final EditText email = (EditText) findViewById(R.id.Remail);

        Button signup=(Button)findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname=username.getText().toString();
                pword=password.getText().toString();
                mail=email.getText().toString();
                new register().execute();
            }
        });


    }


    protected void onPause() {
        super.onPause();
        finish();
    }

    private class register extends AsyncTask<String, String, Boolean> {
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


            params2.add(new BasicNameValuePair("username",uname ));
            params2.add(new BasicNameValuePair("password", pword));
            params2.add(new BasicNameValuePair("email", mail));

            jsonobject = jParser2.makeHttpRequest(regurl, "POST", params2);

            try {
                if (jsonobject != null) {

                    String result = jsonobject.getString("success");
                    //String message=jsonobject.getString("message");

                    if (result.equals("true")) {




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

                Toast.makeText(Register.this, "No response from server", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(Register.this, "Register successfull", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);

            }


        }

    }

}
