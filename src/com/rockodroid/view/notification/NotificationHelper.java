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
package com.rockodroid.view.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Clase que proporciona una interfaz rápida para la administración
 * de notificaciones en la barra de estado.
 * 
 * @author Juan C. Orozco
 */
public class NotificationHelper {

	private final Context mContext;
	private final NotificationManager nManager;

	public NotificationHelper(Context c) {
		this.mContext = c;
		nManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	/**
	 * Lanza una notificación con la información dada.
	 *
	 * @param ticket - Texto inicial de la notificación.
	 * @param titulo - Título de la notificación.
	 * @param contenido - Texto que explica el porque de la notificación.
	 * @param icono - Icono que representa la notificación.
	 * @param calbackClass - Clase que será llamada cuando se pulse sobre la notificación.
	 * @param id - El identificador de la notificación.
	 */
	public void lanzarNotificacion(String ticket, String titulo, String contenido,
			int icono, Class calbackClass, int id) {
		nManager.notify(id, crearNotificacion(ticket, titulo, contenido, icono, calbackClass, false));
	}

	/**
	 * Crear una notificación con la información enviada.
	 * 
	 * @param ticket - Texto inicial de la notificación.
	 * @param titulo - Título de la notificación.
	 * @param contenido - Texto que explica el porque de la notificación.
	 * @param icono - Icono que representa la notificación.
	 * @param calbackClass - Clase que será llamada cuando se pulse sobre la notificación.
	 * @param onGoing - Indica si la notificación es OnGoing.
	 * @return Notification - La notificación creada.
	 */
	public Notification crearNotificacion(String ticket, String titulo, String contenido,
			int icono, Class calbackClass, boolean onGoing) {
		Notification notificacion;
		long when = System.currentTimeMillis();
		Intent i = new Intent(mContext, calbackClass);
		PendingIntent pending = PendingIntent.getActivity(mContext, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

		notificacion = new Notification(icono, ticket, when);
		notificacion.flags |= (onGoing)?Notification.FLAG_ONGOING_EVENT:Notification.DEFAULT_ALL;
		notificacion.setLatestEventInfo(mContext, titulo, contenido, pending);

		return notificacion;
	}
}