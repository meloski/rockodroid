/*   
    Rockodroid: Music Player for android
    Copyright (C) 2012  Laura K. Salazar, Roberto R. De la Parra, Juan C. Orozco.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.rockodroid;

import java.util.ArrayList;

import com.rockodroid.data.media.MediaStore;
import com.rockodroid.model.queue.ModoAleatorio;
import com.rockodroid.model.queue.Queue;
import com.rockodroid.model.vo.MediaItem;
import com.rockodroid.view.AlbumListActivity;
import com.rockodroid.view.ArtistaListActivity;
import com.rockodroid.view.AudioListActivity;
import com.rockodroid.view.PlayerActivity;
import com.rockodroid.view.PlaylistListActivity;
import com.rockodroid.view.QueueActivity;
import com.rockodroid.view.pref.PreferenciasActivity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

/**
 * Esta clase permite seleccionar la categoría por la cual buscar el
 * contenido multimedia del dispositivo. Cada Tab lanza una actividad
 * distinta dependiendo de la categoría, mostrando un ListView con los
 * ítems.
 * 
 * @author Juan C. Orozco
 *
 */
public class HomeActivity extends ActivityGroup {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        
        TabHost mTabHost;
        TabHost.TabSpec mSpec;
        Intent intent;
        Resources mResources = getResources();

    	//Configuración del TabHost
    	mTabHost = (TabHost)findViewById(R.id.tabhost);
    	mTabHost.setup(this.getLocalActivityManager());

        //Creación y configuración de cada Tab
        try{
        	intent = new Intent(this, ArtistaListActivity.class);
        	mSpec = mTabHost.newTabSpec("artista").setIndicator("Artista",
        			mResources.getDrawable(R.drawable.ic_tab_artista)).setContent(intent);
        	mTabHost.addTab(mSpec);
        	
        	intent = new Intent(this, AlbumListActivity.class);
        	mSpec = mTabHost.newTabSpec("album").setIndicator("Álbum",
        			mResources.getDrawable(R.drawable.ic_tab_disco)).setContent(intent);
        	mTabHost.addTab(mSpec);
        	
        	intent = new Intent(this, AudioListActivity.class);
        	mSpec = mTabHost.newTabSpec("archivos").setIndicator("Archivos",
        			mResources.getDrawable(R.drawable.ic_tab_nota)).setContent(intent);
        	mTabHost.addTab(mSpec);
        	
        	intent = new Intent(this, PlaylistListActivity.class);
        	mSpec = mTabHost.newTabSpec("playlist").setIndicator("Playlist",
        			mResources.getDrawable(R.drawable.ic_tab_lista)).setContent(intent);
        	mTabHost.addTab(mSpec);
        	
        	mTabHost.setCurrentTab(0);        	
        }catch(Exception e){
        	Log.e("CREACION TAB", e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflador = getMenuInflater();
    	inflador.inflate(R.menu.menu_home, menu);
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.menu_home_shuffle_all:
    		Queue cola= Queue.getCola();
    		cola.limpiar();
    		MediaStore store = new MediaStore(getApplicationContext());
    		for(MediaItem m: store.buscarAudio()) cola.agregar(m);
    		cola.setAleatorio(true);
    	case R.id.menu_home_player:
    		startActivity(new Intent(this, PlayerActivity.class));
    		return true;
    	case R.id.menu_home_ver_cola:
    		startActivity(new Intent(this, QueueActivity.class));
    		return true;
    	case R.id.menu_home_conf:
    		startActivity(new Intent(this, PreferenciasActivity.class));
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
}