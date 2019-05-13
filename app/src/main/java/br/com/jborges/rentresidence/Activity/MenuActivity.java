package br.com.jborges.rentresidence.Activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import br.com.jborges.rentresidence.Entidade.Imovel;
import br.com.jborges.rentresidence.Fragments.AboutFragment;
import br.com.jborges.rentresidence.Fragments.AddPlaceFragment;
import br.com.jborges.rentresidence.Fragments.MapFragment;
import br.com.jborges.rentresidence.Fragments.PlaceListingFragment;
import br.com.jborges.rentresidence.R;

/**
 * Jefferson Borges - 2019
 */

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new PlaceListingFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        getSupportActionBar().setTitle(getString(R.string.listagem_imoveis));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = new AboutFragment();
        fragmentManager = getSupportFragmentManager(); // For AppCompat use getSupportFragmentManager

        if (id == R.id.nav_home) {
            getSupportActionBar().setTitle(getString(R.string.listagem_imoveis));
            fragment = new PlaceListingFragment();
        } else if (id == R.id.nav_cadastro) {
            fragment = new AddPlaceFragment();
        } else if (id == R.id.nav_onde_encontrar) {
            getSupportActionBar().setTitle(getString(R.string.menu_mapa));
            //startActivity(new Intent(getBaseContext(), MapsActivity.class));
            //finish();
            fragment = new MapFragment();
        } else if (id == R.id.nav_sobre) {
            getSupportActionBar().setTitle(getString(R.string.menu_sobre));
            fragment = new AboutFragment();
        } else if (id == R.id.nav_sair) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getBaseContext(), getString(R.string.tv_user_logout) + "!", Toast.LENGTH_LONG).show();
            finish();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openPlaceDetailsFragment(Imovel imovel, int position) {
        Fragment fragment = new AddPlaceFragment();
        Bundle bundle = new Bundle();

        bundle.putInt("POSICAO", position);
        bundle.putLong("ID", imovel.id);
        bundle.putString("operacao", "alterar");

        fragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void openPlaceListingFragment() {
        Fragment fragment = new PlaceListingFragment();
        Bundle bundle = new Bundle();

        fragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        getSupportActionBar().setTitle(getString(R.string.listagem_imoveis));
    }
}
