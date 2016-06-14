package ggn.com.busroutekml;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ggn.com.busroutekml.UtillsG.MakerG;
import ggn.com.busroutekml.UtillsG.ShowBusRoute;
import ggn.com.busroutekml.UtillsG.UtillJ;
import ggn.com.busroutekml.routeHelper.Route;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;


    MakerG marker1, marker2;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    private void setMarker(GoogleMap googleMap, LatLng latLng)
    {

        if (marker1 == null)
        {
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Your start location")
                    .snippet("This is your start location"));


            marker1 = new MakerG(latLng, marker.getId(), marker);
            marker.showInfoWindow();
        }
        else
        {
            if (marker2 != null)
            {
                marker2.getMarker().remove();
            }

            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Your end location")
                    .snippet("This is your end location"));

            marker.showInfoWindow();

            marker2 = new MakerG(latLng, marker.getId(), marker);


            Route route=new Route();
            route.drawRoute(googleMap,MapsActivity.this,marker1.getMarkerPosition(),marker2.getMarkerPosition(),Route.LANGUAGE_ENGLISH);

//            UtillJ.showToast("Tap to change your end location", MapsActivity.this, true);
        }
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
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        new ShowBusRoute(MapsActivity.this, mMap).execute();


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng latLng)
            {
                setMarker(mMap, latLng);
            }
        });

    }


}
