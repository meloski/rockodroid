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
import com.rockodroid.model.vo.PlayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author Juan C. Orozco
 * @author Roberto R. De La Parrra
 */
public class PlayListListAdapter extends ArrayAdapter<PlayList> {

	private static final int LAYOUT_PLAYLIST = R.layout.layout_playlist_item;
	
	private final LayoutInflater inflador;
	
	public PlayListListAdapter(Context context, ArrayList<PlayList> pl) {
		super(context, android.R.layout.simple_list_item_1);
		inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for(PlayList p: pl) add(p);
	}

	/**
	 * Obtiene la instancia de View para la playlist solicitada.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflador.inflate(LAYOUT_PLAYLIST, null);
			holder.plName = (TextView) convertView.findViewById(R.id.playlist_name);
			holder.plName.setPadding(28, 8, 2, 8);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.plName.setText(getItem(position).getNombre());
		return convertView;
	}
	
	static class ViewHolder {
		TextView plName;
	}
}