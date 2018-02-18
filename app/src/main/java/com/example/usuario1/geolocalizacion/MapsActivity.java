package com.example.usuario1.geolocalizacion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.security.Provider;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private Location localizacionActual;
    private GoogleMap mMap;
    private double longitudInicial, latitudInicial;
    LocationListener locationListener;
    LocationManager locationManager;
    private String origenIndicado;
    private String destinoIndicado;
    private LatLng destinoLatLng;
    private LatLng origenLatLng;
    private boolean isOrigenDiferente;
    private TextView txtConsumo;
    private TextView txtDistancia;
    private double consumoCoche;
    private int numPasajeros;
    private boolean aireAcondicionado;
    private boolean equipaje;
    private boolean luces;
    private boolean idayVuelta;
    float distance;
    private double consumoTotal;
    private TextView txtcosteTotal;
    private double costeGasolina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtConsumo = findViewById(R.id.txt_consumo);
        txtDistancia = findViewById(R.id.txt_distancia);
        txtcosteTotal = findViewById(R.id.txt_coste);

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //si no lo tenemos lo pedimos
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }

        }

        Intent intent = getIntent();
        aireAcondicionado = intent.getBooleanExtra("aire", false);
        luces = intent.getBooleanExtra("luces", false);
        equipaje = intent.getBooleanExtra("equipaje", false);
        numPasajeros = intent.getIntExtra("numPasajeros", 1);
        origenIndicado = intent.getStringExtra("origen");
        consumoCoche = intent.getDoubleExtra("consumo", 5);
        costeGasolina = intent.getDoubleExtra("combustible", 1.1);
        destinoIndicado = intent.getStringExtra("destino");
        idayVuelta = intent.getBooleanExtra("idayvuelta", true);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                localizacionActual = location;
                System.out.println(location);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        /*while (localizacionActual == null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            localizacionActual = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //  ultimaLocalizacion = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (localizacionActual != null) {
                latitudInicial = localizacionActual.getLatitude();
                longitudInicial = localizacionActual.getLongitude();
            }
        }*/



    }

    //Método que pasada una dirección saca la latitud y la longitud del lugar
    private void goToPlace(String locationName){
        Geocoder geoCoder = new Geocoder(this);
        if(Geocoder.isPresent()){
            try {
                List<Address> direcciones = geoCoder.getFromLocationName(locationName, 1);
                if(direcciones.size() == 0){
                    Toast.makeText(this, "Lugar no encontrado", Toast.LENGTH_SHORT).show();
                }
                Address direccion = direcciones.get(0);
                //Aquí habría que guardar estos datos en variables
                if(isOrigenDiferente){
                    origenLatLng = new LatLng(direccion.getLatitude(), direccion.getLongitude());
                    isOrigenDiferente = false;
                }else{
                    System.out.println("Latitud: "+direccion.getLatitude()+" Longitud: "+direccion.getLongitude());
                    destinoLatLng = new LatLng(direccion.getLatitude(), direccion.getLongitude());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void getLocation() {
        try {
            locationManager = (LocationManager) this
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                boolean canGetLocation = true;
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    if (locationManager != null) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        localizacionActual = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (localizacionActual != null) {
                            latitudInicial = localizacionActual.getLatitude();
                            longitudInicial = localizacionActual.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (localizacionActual == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                0,
                                0, (LocationListener) this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            localizacionActual = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (localizacionActual != null) {
                                latitudInicial = localizacionActual.getLatitude();
                                longitudInicial = localizacionActual.getLongitude();
                            }
                        }
                    }
                }
            }
            System.out.println(latitudInicial+"   "+longitudInicial);
        } catch (Exception e) {
            e.printStackTrace();
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getLocation();

        if (origenIndicado.equals("Localizacion Actual")) {
            origenLatLng = new LatLng(localizacionActual.getLatitude(), localizacionActual.getLongitude());
        }else{
            isOrigenDiferente = true;
            goToPlace(origenIndicado);
        }

        mMap.addMarker(new MarkerOptions().position(origenLatLng).title("Origen"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(origenLatLng));

        //Obtenemos las coordenadas del destino
        goToPlace(destinoIndicado);

        MarkerOptions marcadorDestino= new MarkerOptions();
        marcadorDestino.position(destinoLatLng);
        marcadorDestino.title("Destino");
        marcadorDestino.icon(BitmapDescriptorFactory.defaultMarker()) ;
        mMap.addMarker(marcadorDestino);
        mMap.setMinZoomPreference(8);
//        float [] distancia = new float[10];
//        Location.distanceBetween(origenLatLng.latitude, origenLatLng.longitude, destinoLatLng.latitude, destinoLatLng.longitude, distancia);

        String url = obtenerDireccionesURL(origenLatLng, destinoLatLng);
        DescargaRuta downloadTask = new DescargaRuta();
        downloadTask.setMapa(mMap);
        downloadTask.execute(url);

        //Para obtener la distancia entre dos puntos
        Location locationA = new Location("punto A");
        locationA.setLatitude(origenLatLng.latitude);
        locationA.setLongitude(origenLatLng.longitude);
        Location locationB = new Location("punto B");
        locationB.setLatitude(destinoLatLng.latitude);
        locationB.setLongitude(destinoLatLng.longitude);
        distance = locationA.distanceTo(locationB);
        distance = (float) (distance + distance * 0.3)/1000;



        calcularConsumo();
    }



    private void calcularConsumo(){
        double sumatorioExtras = 0;

        if(idayVuelta){
            distance = distance * 2;
        }

        //Obtenemos el consumo a partir de la distancia y el consumo del coche a los 100km
        consumoTotal = (distance * consumoCoche)/100;
        //Si lleva aire acondicionado, equipaje o luces encendidas le sumamos precio al total
        if(aireAcondicionado){
            sumatorioExtras = sumatorioExtras + consumoTotal * 0.08;
        }
        if(equipaje){
            sumatorioExtras = sumatorioExtras + consumoTotal * 0.04;
        }
        if(luces){
            sumatorioExtras = sumatorioExtras + consumoTotal * 0.03;
        }

        //Incrementamos el precio segun el numero de pasajeros
        sumatorioExtras = sumatorioExtras + (consumoTotal * numPasajeros/100);
        consumoTotal = consumoTotal + sumatorioExtras;

        txtDistancia.setText("Distancia: "+distance+" Km");

        //Redondeamos el resultado para que no nos salga con muchos decimales
        consumoTotal = (double) Math.round(consumoTotal * 100) / 100;
        txtConsumo.setText("Consumo: "+consumoTotal +" L");

        //Para calcular el coste en €
        double costeTotal= consumoTotal * costeGasolina;
        costeTotal = (double) Math.round(costeTotal * 100) / 100;
        txtcosteTotal.setText("Coste del viaje: "+costeTotal+"€");


    }


    private String obtenerDireccionesURL(LatLng origin,LatLng dest){

        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        String sensor = "sensor=false";

        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }






}
