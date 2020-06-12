package com.example.pemetaanwisatadijembrana;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] nama,telpun,gambar,alamat,id;
    int JumData;
    private Double[] latitude,longtitude;
    Boolean Marker0[];
    LatLng latLng[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getlokasi(){
        String url="https://wisatadijembrana.000webhostapp.com/wisatadijembrana.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url, null, new Response.Listener<JSONArray>(){
            private Boolean[] MarkerD;

            @Override
            public void onResponse(JSONArray response) {
                final int jumData = response.length();
                Log.d("DEBUG", "PARSE JSON");
                Object[] panjang = new  Boolean[jumData];
                MarkerD = new Boolean[jumData];
                nama = new String[jumData];
                telpun = new String[jumData];
                gambar = new String[jumData];
                alamat = new String[jumData];
                id = new String[jumData];
                longtitude = new Double[jumData];
                longtitude = new Double[jumData];

                for (int i=0 ;i<jumData; i++){
                    try {
                        JSONObject data = response.getJSONObject(i);
                        id[i] = data.getString("id");
                        panjang[i] = new LatLng(data.getDouble("latitude"),data.getDouble("longtitude"));
                        nama[i] = data.getString("nama");
                        telpun[i] = data.getString("telpun");
                        gambar[i] = data.getString("gambar");
                        alamat[i] = data.getString("alamat");
                        id[i] = data.getString("id");
                        latitude[i] = data.getDouble("latitude");
                        longtitude[i] = data.getDouble("longtitude");
                        MarkerD[i] = false;
                        mMap.addMarker(new MarkerOptions()
                        .position((LatLng) panjang[i])
                                .snippet(telpun[i])
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.wisata))
                                .title(nama[i]));
                    }catch (JSONException je){

                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((LatLng) panjang[i], 15.5f));
                }
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        return false;
                    for (int i=0; i<jumData;i++){
                       if (marker.getTitle().equals(nama[i])){
                           if (MarkerD[i]){
                               Detailactivity.id = id[i];
                               Detailactivity.nama = nama[i];
                               Detailactivity.alamat = alamat[i];
                               Detailactivity.telepun = telpun[i];
                               Detailactivity.alamat = alamat[i];
                               Detailactivity.gambar = gambar[i];
                               Detailactivity.latitude = latitude[i];
                               Detailactivity.longtitude = longtitude[i];
                               Intent pindahdetail = new Intent(MapsActivity.this, Detailactivity.class);
                               startActivity(pindahdetail);
                               MarkerD[i] = false;
                           }else{
                               Marker0[i]=true;
                               Toast pesan= Toast.makeText(MapsActivity.this, "clik sekali lagi untuk melihat detail");
                               TextView tv= pesan.getView().findViewById(android.R.id.message);
                               if(tv != null){
                                    tv.setGravity(Gravity.CENTER);
                                    pesan.show();
                               }
                           }else{
                               MarkerD[i]=false;
                           }
                       }
                    }
                    return false;
                };
            });
        }; new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle("Eror");
                builder.setMessage("Connection Filed");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("reload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getlokasi();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        Object jsonArrayRequest;
        Volley.newRequestQueue( this).add(request);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getlokasi();
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }
}
