package com.tareas.diego.tarea2v5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener{


    private ListView lv;
    TextView tvname;
    TextView tvyear;
    TextView tvtype;
    TextView tvid;
    ImageView ivposter;
    ArrayList<String> names ;
    ArrayAdapter<String> adapter;
    MovieListFragment recibir;
    private customAdapter customeAdapter;
    private ArrayList<ImageModel> imageModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.movieLV);


        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                        Object o = lv.getAdapter().getItem(position);
                        String pen = o.toString();

                        tvname = findViewById(R.id.tvname);
                        tvyear = findViewById(R.id.tvyear);
                        tvtype = findViewById(R.id.tvtype);
                        tvid   = findViewById(R.id.tvid);
                        ivposter = findViewById(R.id.ivposter);

                        String[] parts = pen.split(";");

                        tvname.setText(parts[0]);
                        tvyear.setText(parts[1]);
                        tvtype.setText(parts[2]);
                        Picasso.get().load(parts[3].toString()).into(ivposter);
                        tvid.setText(parts[4]);

                        //Toast.makeText(getApplicationContext(), "You have chosen the pen: " + " " + parts[0], Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    @Override
    public void onFragmentInteraction(String texto) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment2);
        recibir = (MovieListFragment) fragment;
        recibir.mostrar(texto);
        //searchMovie(texto);
        populateList(texto);

    }

    private void populateList(String palabra){
            ArrayList<ImageModel> list = new ArrayList<>();


            String PLACES_URL = "http://www.omdbapi.com/?s="+palabra+"&apikey=82a5f152";
            String LOG_TAG = "VolleyPlacesRemoteDS";


            // Instantiate the RequestQueue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //Prepare the Request
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET, //GET or POST
                    PLACES_URL, //URL
                    null, //Parameters
                    new Response.Listener<JSONObject>() { //Listener OK

                        @Override
                        public void onResponse(JSONObject responsePlaces) {
                            ArrayList<ImageModel> list2 = new ArrayList<>();
                            try {

                                JSONArray resultados =  responsePlaces.getJSONArray("Search");

                                for (int i=0; i< resultados.length(); i++){

                                    ImageModel imageModel = new ImageModel();
                                    JSONObject xd  =  resultados.getJSONObject(i);

                                    String name =   xd.getString("Title");
                                    String poster =   xd.getString("Poster");
                                    String year =   xd.getString("Year");
                                    String type =   xd.getString("Type");
                                    String imdbID =   xd.getString("imdbID");

                                    imageModel.setName(name);
                                    imageModel.setImage_drawable(poster);
                                    imageModel.setYear(year);
                                    imageModel.setType(type);
                                    imageModel.setId(imdbID);
                                    list2.add(imageModel);

                                    customeAdapter = new customAdapter(getApplicationContext(),list2);
                                    lv.setAdapter(customeAdapter);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() { //Listener ERROR

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            //Send the request to the requestQueue
            requestQueue.add(request);

    }

}
