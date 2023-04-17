package com.example.defibrillatortracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.defibrillatortracker.databinding.ActivityMapsBinding;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    List<Location> savedLocations;
    List<LatLng> limerick;
    private GoogleMap mMap;
private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMapsBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        MyApplication myApplication = (MyApplication) getApplicationContext();
        savedLocations = myApplication.getMyLocations();

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

        LatLng lim = new LatLng(52.6638, -8.6267);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lim));


        // Adding Defib Locations Around Limerick
        LatLng lim1 = new LatLng(52.66803, -8.529896);
        mMap.addMarker(new MarkerOptions().position(lim1).title("V94 YD8P"));
        LatLng lim2 = new LatLng(52.6719395, -8.5548398);
        mMap.addMarker(new MarkerOptions().position(lim2).title("V94 TRW8"));
        LatLng lim3 = new LatLng(52.666557, -8.553263);
        mMap.addMarker(new MarkerOptions().position(lim3).title("V94 YD8P"));
        LatLng lim4 = new LatLng(52.655464,  -8.553052);
        mMap.addMarker(new MarkerOptions().position(lim4).title("V94 A6F4"));
        LatLng lim5 = new LatLng(52.673176, -8.569802);
        mMap.addMarker(new MarkerOptions().position(lim5).title("V94 NX93"));
        LatLng lim6 = new LatLng(52.647333, -8.583376);
        mMap.addMarker(new MarkerOptions().position(lim6).title("V94 FK61"));
        LatLng lim7 = new LatLng(52.662984,  -8.596609);
        mMap.addMarker(new MarkerOptions().position(lim7).title("V94 XTY5"));
        LatLng lim8 = new LatLng(52.680933, -8.610998);
        mMap.addMarker(new MarkerOptions().position(lim8).title("V94 HXW5"));
        LatLng lim9 = new LatLng(52.670038, -8.629037);
        mMap.addMarker(new MarkerOptions().position(lim9).title("V94 EH90"));
        LatLng lim10 = new LatLng(52.662506, -8.625847);
        mMap.addMarker(new MarkerOptions().position(lim10).title("V94 K377"));
        LatLng lim11= new LatLng(52.662764,  -8.624603);
        mMap.addMarker(new MarkerOptions().position(lim11).title("V94 DW21"));
        LatLng lim12= new LatLng(52.668468, -8.525015);
        mMap.addMarker(new MarkerOptions().position(lim12).title("V94 A431"));
        LatLng lim13= new LatLng(52.66151, -8.62889);
        mMap.addMarker(new MarkerOptions().position(lim13).title("V94 951K"));
        LatLng lim14= new LatLng(52.657596,  -8.632492);
        mMap.addMarker(new MarkerOptions().position(lim14).title("V94 V2VW"));
        LatLng lim15= new LatLng(52.668753, -8.653878);
        mMap.addMarker(new MarkerOptions().position(lim15).title("V94 VYX9"));
        LatLng lim16= new LatLng(52.668679, -8.653949);
        mMap.addMarker(new MarkerOptions().position(lim16).title("V94 VYX9"));
        LatLng lim17= new LatLng(52.6559815, -8.6521503);
        mMap.addMarker(new MarkerOptions().position(lim17).title("V94 XE81"));
        LatLng lim18= new LatLng(52.59921, -8.705552);
        mMap.addMarker(new MarkerOptions().position(lim18).title("V94 EH51"));
        LatLng lim19= new LatLng(52.545658, -8.605729);
        mMap.addMarker(new MarkerOptions().position(lim19).title("Fedamore Church"));
        LatLng lim20= new LatLng(52.511366, -8.616468);
        mMap.addMarker(new MarkerOptions().position(lim20).title("Camogue Rovers GAA Club"));
        LatLng lim21= new LatLng(52.520843, -8.713801);
        mMap.addMarker(new MarkerOptions().position(lim21).title("Colaiste Chiarain"));
        LatLng lim22= new LatLng(52.467248, -8.786335);
        mMap.addMarker(new MarkerOptions().position(lim22).title("Gormans Shop"));
        LatLng lim23= new LatLng(52.459812, -8.765026);
        mMap.addMarker(new MarkerOptions().position(lim23).title("Granagh Community Centre"));
        LatLng lim24= new LatLng(52.536942, -8.329949);
        mMap.addMarker(new MarkerOptions().position(lim24).title("V94 K5P6"));
        LatLng lim25= new LatLng(52.612389, -8.458778);
        mMap.addMarker(new MarkerOptions().position(lim25).title("Pa McGrath's"));
        LatLng lim26= new LatLng(52.651389, -8.398991);
        mMap.addMarker(new MarkerOptions().position(lim26).title("Murroe Boher GAA"));


       /* for(int i=0 ; i < limerick.size(); i++) {
            mMap.addMarker(new MarkerOptions().position(limerick.get(i)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lim));
        }*/

        LatLng lastLocationPlaced = lim;

        for(Location location: savedLocations){
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Lat:" + location.getLatitude()+" Lon:" + location.getLongitude());
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            lastLocationPlaced = latLng;
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLocationPlaced,12.0f));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick( Marker marker) {

                Toast.makeText(MapsActivity.this,marker.getTitle() + " was selected",Toast.LENGTH_LONG).show();


                return false;
            }
        });

    }
}