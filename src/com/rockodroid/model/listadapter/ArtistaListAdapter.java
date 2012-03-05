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

import com.rockodroid.model.vo.Album;
import com.rockodroid.model.vo.Artista;

import com.rockodroid.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adaptador que extiende de BaseExpandibleListAdapter y que sirve de puente
 * entre el ExpandibleListView de artistas y los datos contenidos en e 
 * dispsitivo. Gestiona la creación de los View para los Artistas y sus álbumes. 
 * Está optimizado con reciclaje de vistas y ViewHolder.
 * 
 * @author Juan C. Orozco
 */
public class ArtistaListAdapter extends BaseExpandableListAdapter {

	/* Layouts usados para  inflar nuevas vistas. */
	private static final int layoutArtista = R.layout.layout_artistalist_item;
	private static final int layoutAlbumArtista = R.layout.layout_albumartistalist_item;
	private static float density;

	private final LayoutInflater inflador;
	private ArrayList<Artista> artistas;

	public ArtistaListAdapter(Context context, ArrayList<Artista> datos) {
		super();
		inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		artistas = datos;
		density = context.getResources().getDisplayMetrics().density;
	}

	public ArtistaListAdapter(Context context) {
		super();
		inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		artistas = new ArrayList<Artista>();
	}

	/**
	 * Reemplaza los elementos actuales del adapter por los elementos contenidos
	 * en arrayArtista.
	 * @param arrayArtista - ArrayList<Artista> con los nuevos elementos para el adapter.
	 */
	public void establecerElementos(ArrayList<Artista> arrayArtista) {
		artistas = arrayArtista;
	}

	/* Métodos heredados de la clase base */

	/**
	 * Retorna el Album childPosition del artista número groupPosition.
	 */
	public Object getChild(int groupPosition, int childPosition) {
		return artistas.get(groupPosition).getDiscos().get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/**
	 * Retorna la vista con los datos el álbum especificado.
	 */
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolderAlbum holder;

		if(convertView == null) {
			holder = new ViewHolderAlbum();
			convertView = inflador.inflate(layoutAlbumArtista, null);
			holder.tituloAlbum = (TextView) convertView.findViewById(R.id.album_title);
			holder.artAlbum = (ImageView) convertView.findViewById(R.id.album_art);
			holder.numCanciones = (TextView) convertView.findViewById(R.id.album_n_canciones);
			convertView.setPadding((int)(20 * density), 0, 0, 0);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolderAlbum) convertView.getTag();
		}
		Album album = artistas.get(groupPosition).getDiscos().get(childPosition);
		holder.tituloAlbum.setText(album.getTitulo());
		int num = album.getNCanciones();
		holder.numCanciones.setText(String.valueOf(num) + ((num > 1)? " canciones":" canción"));

		holder.artAlbum.setImageDrawable(album.getAlbumArt());
		return convertView;
	}

	/**
	 * Número de álbumes del artista ubicado en groupPosition
	 */
	public int getChildrenCount(int groupPosition) {
		return artistas.get(groupPosition).getDiscos().size();
	}

	/**
	 * Retorna el artista ubicado en groupPosition
	 */
	public Object getGroup(int groupPosition) {
		return artistas.get(groupPosition);
	}

	/**
	 * Número de grupos (Artista)
	 */
	public int getGroupCount() {
		return artistas.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**
	 * Retorna una vista que contiene la información del Artista ubicado
	 * en la posición groupPosition dentro del ArrayList
	 */
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewHolderArtista holder;
		
		if(convertView == null) {
			holder = new ViewHolderArtista();
			convertView = inflador.inflate(layoutArtista, null);
			holder.nombreArtista = (TextView) convertView.findViewById(R.id.artista_name);
			holder.nombreArtista.setPadding(36, 12, 0, 12);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolderArtista) convertView.getTag();
		}
		holder.nombreArtista.setText(artistas.get(groupPosition).getNombre());
		return convertView;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	/* ViewHolders */

	static class ViewHolderArtista {
		TextView nombreArtista;
	}

	static class ViewHolderAlbum {
		ImageView artAlbum;
		TextView tituloAlbum;
		TextView numCanciones;
	}
}