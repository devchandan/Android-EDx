package hk.ust.cse.comp107x.chatclientnetwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Contacts extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG="Contacts";

    Toolbar toolbar;
    String[] names;

    ListView friendView;

    MyArrayAdapter myArrayAdapter = null;

    public class FriendInfo {
        int id;
        String name;
        String statusMsg;
        String imageURL;
    }

    List<FriendInfo> friendInfoList = null;

    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mContext = this;

        // If you are using a ListView widget, then your activity should implement
        // the onItemClickListener. Then you should set the OnItemClickListener for
        // the ListView.
        friendView = (ListView) findViewById(R.id.friendListView);
        friendView.setOnItemClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_contacts); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call

        if (isOnline()) {
            // Start the AsyncTask to process the Json string in the background and then initialize the listview
            FriendsProcessor mytask = new FriendsProcessor();
            mytask.execute(Constants.JSON_URL);
        }
        else {
            // Toast displays the message on the screen for a period of time
            Toast.makeText(this, "You are Offline! Turn on your network!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }

    // This method checks to see if the device is online. Returns true if online, else false
    private boolean isOnline() {

        // Connectivity manager gives you access to the current state of the connection
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
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

    // This AsyncTask processes the Json string by reading it from a file in the assets folder and
    // then converts the string into a list of FriendInfo objects. You will also see the use of
    // a progress dialog to show that work is being processed in the background.

    private class FriendsProcessor extends AsyncTask<String, Void, Integer> {

        ProgressDialog progressDialog;
        int delay = 5000 ; // ms

        public FriendsProcessor() {
            super();
        }


        // The onPreExecute is executed on the main UI thread before background processing is
        // started. In this method, we start the progressdialog.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(mContext, "Wait!","Downloading Friends List");
        }

        // This method is executed in the background and will return a result to onPostExecute
        // method. It receives the file name as input parameter.
        @Override
        protected Integer doInBackground(String... urls) {
            InputStream inputStream = null;

            // TODO connect to server, download and process the JSON string
            HttpURLConnection urlConnection = null;
            Integer result = 0;
            try {
                /* forming th java.net.URL object */
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                 /* optional request header */
                urlConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
                urlConnection.setRequestProperty("Accept", "application/json");

                /* for Get request */
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    // Convert the read in information to a Json string
                    String response = convertInputStreamToString(inputStream);

                    // now process the string using the method that we implemented in the previous exercise
                    processFriendInfo(response);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result == 1) {
                myArrayAdapter = new MyArrayAdapter(mContext, friendInfoList);
                friendView.setAdapter((ListAdapter) myArrayAdapter);
            }

            progressDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            progressDialog.dismiss();
        }
    }

    // This method converts an input stream into a string. It reads in the input line by line
    // and then converts it into a string.
    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null){
            result += line;
        }

            /* Close Stream */
        if(null!=inputStream){
            inputStream.close();
        }
        return result;
    }

    // This class processes the Json string and converts it into a list of FriendInfo objects
    // We make use of the Gson library to do this automatically
    private void processFriendInfo(String infoString) {

        // Create a new Gson object
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        // Use the Gson library to automatically process the string and convert it into
        // the list of FriendInfo objects. The use of the library saves you the need for
        // writing your own code to process the Json string
        friendInfoList = new ArrayList<FriendInfo>();
        friendInfoList = Arrays.asList(gson.fromJson(infoString, FriendInfo[].class));
    }
}
