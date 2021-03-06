package com.example.omur.navigationavm;

import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapters.NavListAdapter;
import com.example.Database.Repositories.IRepository;
import com.example.Database.Repositories.RepositoryContainer;
import com.example.Database.Repositories.RepositoryNames;
import com.example.DrawerFragments.fragment_about;
import com.example.DrawerFragments.fragment_home;
import com.example.DrawerFragments.fragment_settings;
import com.example.models.NavItem;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity
{
    private RepositoryContainer repositoryContainer;
    DrawerLayout drawerLayout ;
    RelativeLayout drawerPane ;
    ListView lstNav ;
    IRepository repository ;
   TextView Cusername ,Cusersurname ;

    List<NavItem> listnavitem ;
    List<Fragment> listfragment ;

    ActionBarDrawerToggle actionBarDrawerToggle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MultiDex.install(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repositoryContainer = RepositoryContainer.create(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_Layout);
        drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);
        lstNav= (ListView) findViewById(R.id.nav_list);


        // drawer listesine adapter kullanarak title subtitle ve icon atamak için ;
        repository = repositoryContainer.getRepository(RepositoryNames.LOGIN) ;
        Cusername = (TextView) findViewById(R.id.connectedusername);


        listnavitem=new ArrayList<NavItem>() ;
        listnavitem.add(new NavItem("InnApp","Ana Sayfa",R.drawable.homelogo1)) ;
        listnavitem.add(new NavItem("Ayarlar","Bilgilerini güncelle",R.drawable.setting)) ;
        listnavitem.add(new NavItem("Hakkımızda","YBU-2016",R.drawable.info_icon)) ;

        NavListAdapter navListAdapter = new NavListAdapter(getApplicationContext(), R.layout.item_nav_list,listnavitem);

        lstNav.setAdapter(navListAdapter);

        // fragment işlemleri için ;

        listfragment = new ArrayList<Fragment>();

        listfragment.add(new fragment_home()) ;
        listfragment.add(new fragment_settings()) ;
        listfragment.add(new fragment_about()) ;

        // ilk fragmentı default olarak ayarlıyoruz
       final FragmentManager fragmentManager = getSupportFragmentManager() ;
        fragmentManager.beginTransaction().replace(R.id.main_content,listfragment.get(0)).commit() ;

        setTitle(listnavitem.get(0).getTitle());
        lstNav.setItemChecked(0, true);
        drawerLayout.closeDrawer(drawerPane);

        lstNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //hangi pozisyon tıklanırsa o fragment gelmesi için get(0) ları get(position) yapıp , listener içine koyuyoruz

                FragmentManager fragmentManager = getSupportFragmentManager() ;
                fragmentManager.beginTransaction().replace(R.id.main_content,listfragment.get(position)).commit() ;
                setTitle(listnavitem.get(position).getTitle());

                lstNav.setItemChecked(position, true);
                drawerLayout.closeDrawer(drawerPane);

            }
        });

        // drawer layout için listener oluşturuyoruz

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_opened,R.string.drawer_closed)
        {
            @Override
            public void onDrawerOpened(View drawerView) {

                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);


            }

            @Override
            public void onDrawerClosed(View drawerView) {

                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);

            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

       if(SigninScreen.controluser==1) {
           Intent i = getIntent();
           String Uname = i.getExtras().getString("uname");
           Cusername.setText(Uname);

       }
        else{

           Toast.makeText(MainPage.this, "Hello Guest", Toast.LENGTH_LONG).show(); }

        SigninScreen.controluser=0 ;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true ;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }
}