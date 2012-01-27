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
import com.rockodroid.model.vo.Audio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AudioListAdapter extends ArrayAdapter<Audio> {

	private static final int LAYOUT_AUDIO = R.layout.layout_audiolist_item;

	private final LayoutInflater inflador;

	public AudioListAdapter(Context context, ArrayList<Audio> audios) {
		super(context, android.R.layout.simple_list_item_1);
		inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for(Audio a: audios) add(a);
	}

	/**
	 * Obtiene la vista para el item de Audio especificado.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflador.inflate(LAYOUT_AUDIO, null);
			holder.audioTitulo = (TextView) convertView.findViewById(R.id.audio_titulo);
			holder.audioArtista = (TextView) convertView.findViewById(R.id.audio_artista);
			holder.audioDuracion = (TextView) convertView.findViewById(R.id.audio_duracion);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		Audio currentAudio = getItem(position);
		holder.audioTitulo.setText(currentAudio.getTitulo());
		holder.audioArtista.setText(currentAudio.getArtista());
		long length = currentAudio.getDuracion() / 1000;
		int min = (int)(length / 60), seg = (int)(length % 60);
		holder.audioDuracion.setText(String.valueOf(min) + ":" + 
				((seg < 10)?"0":"") + String.valueOf(seg));
		return convertView;
	}

	/**
	 * Clase ViewHolder almacena referencias a los elementos View del
	 * layou para cada item de audio para evitar el llamado a findViewById.
	 */
	static class ViewHolder {
		TextView audioTitulo;
		TextView audioArtista;
		TextView audioDuracion;
	}
}
