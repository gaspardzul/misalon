package com.claudia.misalon.views;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.claudia.misalon.Application.MyApp;
import com.claudia.misalon.BO.Cliente;
import com.claudia.misalon.BO.Producto;
import com.claudia.misalon.BO.Servicio;
import com.claudia.misalon.R;
import com.claudia.misalon.fragments.CitasFragment;
import com.claudia.misalon.fragments.ClienteFragment;
import com.claudia.misalon.fragments.DashboardFragment;
import com.claudia.misalon.fragments.ProductoServiciosFragment;
import com.claudia.misalon.fragments.ServicioFragment;
import com.parse.Parse;
import com.parse.ParseObject;


public class MainActivity extends ActionBarActivity {

    private DrawerLayout drawerLayout;
    private String drawerTitle;
    String strDashboard;
    String strProductos;
    String strCitas;
    String strCliente;
    String strServicios;
    private String srtTitle;
    private Menu menu=null;

    private FragmentTransaction fragmentTransaction;

    private Fragment[] fragmentos = new Fragment[]{
            new DashboardFragment(),
            new ProductoServiciosFragment(),
            new CitasFragment(),
            new ClienteFragment(),
            new ServicioFragment()
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();


        FragmentManager manager = getFragmentManager();
        fragmentTransaction = manager.beginTransaction();

        for(Fragment fragment:fragmentos){
            fragmentTransaction.add(R.id.main_content,fragment).hide(fragment);
        }
        fragmentTransaction.show(fragmentos[0]).commit();


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        drawerTitle = getResources().getString(R.string.dashboard);
        if (savedInstanceState == null) {
            selectItem(drawerTitle);
        }

        strCitas = getResources().getString(R.string.citas);
        strDashboard = getResources().getString(R.string.dashboard);
        strProductos = getResources().getString(R.string.products);
        strCliente = getResources().getString(R.string.clientes);
        strServicios = getResources().getString(R.string.servicios);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();

        //se asigna la accion de la opcion siguiente
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (srtTitle.equals(strProductos)) {
                    Intent intent = new Intent(getApplicationContext(), ProductoAgregarEditar.class);
                    startActivity(intent);
                } else if (srtTitle.equals(strCliente)) {
                    Intent intent = new Intent(getApplicationContext(), ClienteAgregarEditar.class);
                    startActivity(intent);
                } else if (srtTitle.equals(strServicios)) {
                    Intent intent = new Intent(getApplicationContext(), ServicioNuevoActivity.class);
                    startActivity(intent);
                } else if(strCitas.equals(strCitas)){
                    Intent intent = new Intent(getApplicationContext(), CitaActivity.class);
                    startActivity(intent);
                }
                return true;

            }
        });
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    //nos sirve para mostrar u ocultar el menu agregar
    public void showOverflowMenu(boolean showMenu){
        if(menu == null)
            return;
        menu.setGroupVisible(R.id.main_menu_group, showMenu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        showOverflowMenu(false);
        return true;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        menuItem.setChecked(true);
                        // Crear nuevo fragmento
                        String title = menuItem.getTitle().toString();
                        selectItem(title);
                        return true;
                    }
                }
        );
    }


    private void selectItem(String title) {
        srtTitle = title;
        FragmentManager fragmentManager = getFragmentManager();
        int positionFragment = 0;

        if(drawerTitle.equals(title)){
            drawerLayout.closeDrawers();
        }else if(title.equals(strDashboard)){
            positionFragment=0;
            showOverflowMenu(false);
        }else if(title.equals(strProductos)){
            positionFragment=1;
            showOverflowMenu(true);
        }else if(title.equals((strCitas))){
            positionFragment=2;
            showOverflowMenu(true);
        }else if(title.equals(strCliente)){
            positionFragment=3;
            showOverflowMenu(true);
        }else if(title.equals(strServicios)){
            positionFragment=4;
            showOverflowMenu(true);
        }

        if(!drawerTitle.equals(title)){
            fragmentTransaction = fragmentManager.beginTransaction();

            for (Fragment fragment : fragmentos) {
                fragmentTransaction.hide(fragment);
            }

            fragmentTransaction.show(fragmentos[positionFragment]).commit();

            drawerLayout.closeDrawers();

            drawerTitle = title;
            setTitle(title); // Setear título actual
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
