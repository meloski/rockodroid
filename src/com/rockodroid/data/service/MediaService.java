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

import com.rockodroid.HomeActivity;
import com.rockodroid.R;
import com.rockodroid.view.notification.NotificationHelper;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.os.PowerManager;

/**
 * Servicio encargado de la reproducción de los archivos
 * de audio y video. Proporciona la capacidad de reproducir
 * en background.
 * Este es uno de los componentes principales de la aplicación.
 * 
 * @author Juan C. Orozco
 *
 */
public class MediaService extends Service implements OnPreparedListener, OnErrorListener{

	private static final int NOTIFICATION_ID = 1;
	
	private MediaPlayer mPlayer;

	private NotificationHelper notificationHelper;
	/**
	 * El sistema llama este método cuando otro componente ejecuta
	 * startService(). El servicio corre indefinidamente.
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		return START_NOT_STICKY;
	}

	/**
	 * El sistema llama este método cuando el servicio inicia por primera vez.
	 * Sirve para hacer las configuraciones iniciales.
	 */
	@Override
	public void onCreate() {
		notificationHelper = new NotificationHelper(getApplicationContext());
		
		super.onCreate();
	}

	/**
	 * Este método es llamado cuando otro componente ejecuta bindService().
	 * Este método sirve para generar una comunicacion con otros componentes
	 * de la aplicación.
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		
		return null;
	}

	/**
	 * El sistema llama este método cuando el servicio será destruido.
	 * Liberar recursos!!
	 */
	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	/**
	 * Se encarga de iniciarlizar el MediaPlayer y dejarlo
	 * listo para asignar el recurso multimedia.
	 * 
	 */
	private void iniciarPlayer() {
		if (mPlayer == null) {
			mPlayer = new MediaPlayer();
			mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
			mPlayer.setOnPreparedListener(this);
			mPlayer.setOnErrorListener(this);
		}else {
			mPlayer.reset();
		}
	}

	/**
	 * Libera los recursos del MediaPlayer según lo indicado por
	 * el parámetro total.
	 * 
	 * @param total - Indica si se liberan todos los recursos.
	 */
	private void liberarRecursos(boolean total) {
		stopForeground(true);
		if(total == true && mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}

	/**
	 * Detiene o inicia el MediaPlayer alternando su estado. 
	 */
	private void alternarReproduccion() {
		if(mPlayer != null) {
			if(mPlayer.isPlaying()) {
				liberarRecursos(false);
				mPlayer.pause();
			}else {
				startForeground(NOTIFICATION_ID, notificationHelper.crearNotificacion(
						"Rockodroid", "", "", R.drawable.ic_disco, HomeActivity.class, true));
				mPlayer.start();
			}
		}
	}

	/**
	 * Selecciona el siguiente item de la cola.
	 */
	private void avanzar() {
		
	}

	/**
	 * Selecciona el item de la cola actual que fué reproducido
	 * anteriormente.
	 */
	private void retroceder() {
		
	}

	/*** CALLBACKS ***/
	
	/**
	 * Es llamado cuando el MediaPlayer ha codificado y preparado el
	 * recurso media y está listo para comenzar su reproducción.
	 */
	public void onPrepared(MediaPlayer mp) {
		alternarReproduccion();
	}

	/**
	 * 
	 * @param mp - El MediaPlayer que generó el error.
	 * @param what - Código del error generado.
	 * @param extra - Información extra.
	 * @return boolean - true indica que el error ha sido manejado.
	 */
	public boolean onError(MediaPlayer mp, int what, int extra) {
		//El MediaPlayer está en estado de error, debería ser reseteado.
		mPlayer.reset();
		return false;
	}
}