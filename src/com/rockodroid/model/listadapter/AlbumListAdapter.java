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
package com.rockodroid.model.listadapter;

import java.util.ArrayList;

import com.rockodroid.R;
import com.rockodroid.model.vo.Album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adaptador para la clase AlbumListActivity.
 *  
 * @author Juan C. Orozco
 */
public class AlbumListAdapter extends ArrayAdapter<Album> {

	private final static int LAYOUT_ALBUM = R.layout.layout_albumartistalist_item;
	
	private final LayoutInflater inflador;
	
	public AlbumListAdapter(Context context, ArrayList<Album> discos) {
		super(context, android.R.layout.simple_list_item_1);
		inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for(Album a: discos) add(a);
	}

	/**
	 * Obtiene una instancia de View con los datos propios del album solicitado.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflador.inflate(LAYOUT_ALBUM, null);
			holder.tituloAlbum = (TextView) convertView.findViewById(R.id.album_title);
			holder.artAlbum = (ImageView) convertView.findViewById(R.id.album_art);
			holder.numCanciones = (TextView) convertView.findViewById(R.id.album_n_canciones);
			convertView.setPadding(12, 6, 0, 6);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		Album album = getItem(position);
		holder.tituloAlbum.setText(album.getTitulo());
		int num = album.getNCanciones();
		holder.numCanciones.setText(String.valueOf(num) + ((num > 1)? " canciones":" canción"));
		holder.artAlbum.setImageDrawable(album.getAlbumArt());
		
		return convertView;
	}

	/**
	 * Clase ViewHolder almacena referencias a los elementos View del layout de cada album.
	 * Se evita con esto el invocar el método findViewById cada que se llama a getView.
	 * Se gana en performance pues findViewById es costoso para el sistema. 
	 */
	static class ViewHolder{
		ImageView artAlbum;
		TextView tituloAlbum;
		TextView numCanciones;
	}
}
