package com.rizky_02736.desemar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class List extends AppCompatActivity {
    java.util.List<DataAdapter> ListOfDataAdapter;
    RecyclerView recyclerView;
    String URL_JSON = "http://192.168.43.14/Desemar/android/service_data.php";

    String TAG_ID="id";
    String TAG_JUDUL="jobname";
    String TAG_DESKRIPSI="requirement";
    String TAG_URL="img";

    JsonArrayRequest RequestOfJSonArray;
    RequestQueue requestQueue;

    View view;
    int RecyclerViewItemPosition;

    RecyclerView.LayoutManager layoutManagerOfrecyclerview;
    RecyclerView.Adapter recyclerViewadapter;
    ArrayList<String> ImageTitleidArrayListForClick;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ImageTitleidArrayListForClick = new ArrayList<>();
        ListOfDataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclertemp);
        recyclerView.setHasFixedSize(true);
        layoutManagerOfrecyclerview = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerOfrecyclerview);

        JSON_HTTP();


        //fungsi klik
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(List.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                view = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(view != null && gestureDetector.onTouchEvent(motionEvent)){
                    RecyclerViewItemPosition=Recyclerview.getChildAdapterPosition(view);

                    Intent intent = new Intent(List.this, JobActivity.class);
                    intent.putExtra("send_id",ImageTitleidArrayListForClick.get(RecyclerViewItemPosition));
                    startActivity(intent);
                }

                return false;
            }


            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }

    public void JSON_HTTP(){
        RequestOfJSonArray = new JsonArrayRequest(URL_JSON,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ParseJSonResponse(response);
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(List.this);
        requestQueue.add(RequestOfJSonArray);}

        public void ParseJSonResponse (JSONArray array){
            for (int i = 0; i<array.length(); i++){
                DataAdapter GetDataAdapter2 = new DataAdapter();
                JSONObject json=null;
                try{
                    json = array.getJSONObject(i);
                    ImageTitleidArrayListForClick.add(json.getString(TAG_JUDUL));
                    GetDataAdapter2.setid(json.getString(TAG_ID));
                    GetDataAdapter2.setjudul(json.getString(TAG_JUDUL));
                    GetDataAdapter2.setdeskripsi(json.getString(TAG_DESKRIPSI));
                    GetDataAdapter2.seturl(json.getString(TAG_URL));
                } catch (JSONException e){
                    e.printStackTrace();
                }
                ListOfDataAdapter.add(GetDataAdapter2);
            }
            recyclerViewadapter = new RecyclerViewAdapter(ListOfDataAdapter, this);
            recyclerView.setAdapter(recyclerViewadapter);
        }

    }
