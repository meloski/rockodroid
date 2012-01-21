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

import android.content.Context;
import android.media.AudioManager;

/**
 * Ayudante para manejo del AudioFocus proporcionado por el sistema.
 * Con esta clase de le notifica al Servicio cuando debe parar la reproducción
 * y cuando puede reanudar esta.
 * 
 * @author Juan C. Orozco
 */
public class AudioFocusHelper implements AudioManager.OnAudioFocusChangeListener{

	private final AudioManager aManager;
	private AudioFocusCambiable fCambiable;

	public AudioFocusHelper(Context c, AudioFocusCambiable cambiable) {
		aManager = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
		fCambiable = cambiable;
	}

	/**
	 * Llamado cuando se requiere el AudioFocus.
	 * @return true si el AudioFocus es otorgado.
	 */
	public boolean requerirAudioFocus() {
		return (aManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, 
				AudioManager.AUDIOFOCUS_GAIN) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) ;
	}

	/**
	 * Llamado cuando se requiere soltar el AudioFocus.
	 * @return true si logra soltar el AudioFocus. 
	 */
	public boolean dejarAudioFocus() {
		return (aManager.abandonAudioFocus(this)==AudioManager.AUDIOFOCUS_REQUEST_GRANTED);
	}

	/**
	 * Callback agregado por la implentacion de la interfaz OnAudioFocusChangeListener.
	 * Es llamado cuando el AudioFocus cambia y notifica al servicio para que responda ante
	 * el evento.
	 */
	public void onAudioFocusChange(int focusChange) {
		switch(focusChange) {
		case AudioManager.AUDIOFOCUS_GAIN:
			fCambiable.alGanarFoco();
			break;
		case AudioManager.AUDIOFOCUS_LOSS:
			fCambiable.alPerderFoco();
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
			fCambiable.alPerderFocoMomentaneo(false);
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
			fCambiable.alPerderFocoMomentaneo(true);
			break;
		}
	}
}