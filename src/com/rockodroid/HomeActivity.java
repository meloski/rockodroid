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

import com.rockodroid.view.AlbumListActivity;
import com.rockodroid.view.ArtistaListActivity;
import com.rockodroid.view.AudioListActivity;
import com.rockodroid.view.PlaylistListActivity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
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
        	mSpec = mTabHost.newTabSpec("artista").setIndicator("Artista" /*,
        			mResources.getDrawable(R.drawable.) */).setContent(intent);
        	mTabHost.addTab(mSpec);
        	
        	intent = new Intent(this, AlbumListActivity.class);
        	mSpec = mTabHost.newTabSpec("album").setIndicator("Álbum",
        			mResources.getDrawable(R.drawable.ic_disco)).setContent(intent);
        	mTabHost.addTab(mSpec);
        	
        	intent = new Intent(this, AudioListActivity.class);
        	mSpec = mTabHost.newTabSpec("archivos").setIndicator("Archivos" /*,
        			mResources.getDrawable(R.drawable.) */).setContent(intent);
        	mTabHost.addTab(mSpec);
        	
        	intent = new Intent(this, PlaylistListActivity.class);
        	mSpec = mTabHost.newTabSpec("playlist").setIndicator("Playlist",
        			mResources.getDrawable(R.drawable.ic_lista)).setContent(intent);
        	mTabHost.addTab(mSpec);
        	
        	mTabHost.setCurrentTab(0);        	
        }catch(Exception e){
        	Log.e("CREACION TAB", e.toString());
        }
    }
}