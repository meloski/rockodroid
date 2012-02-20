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
package com.rockodroid.data.service;

import com.rockodroid.data.service.MediaService.PlayerBinder;
import com.rockodroid.view.pref.PreferenceHelper;

import android.content.Context;
import android.content.Intent;

public class AudioExternoIntent extends android.content.BroadcastReceiver {

	private PlayerBinder binder;
	private PreferenceHelper prefHelper;

	public AudioExternoIntent(Context c, PlayerBinder b) {
		this.binder = b;
		prefHelper = new PreferenceHelper(c);
	}

	/**
	 * Acción que ocurre cuando los audífonos son desconectados.
	 * Dependiendo de las preferencias establecidas se detiene o no la reproducción.
	 */
	public void onReceive(Context ctx, Intent intent) {
		if(intent.getAction().equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
			/* Revisar el archivo de preferencias 
			 * Si está marcada la opción "Detener al desconectar"
			 * entonces se debe detener la reproducción */
			if(prefHelper.getDetenerAudioPref())
				binder.pause();
		}
	}
}