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

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Servicio encargado de la reproducción de los archivos
 * de audio y video. Proporciona la capacidad de reproducir
 * en background.
 * Este es uno de los componentes principales de la aplicación.
 * 
 * @author Juan C. Orozco
 *
 */
public class MediaService extends Service {

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

}
