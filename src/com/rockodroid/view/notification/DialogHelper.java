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

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Permite la creación y manejo rápido de diálogos para
 * informar al usuario sobre eventos ocurridos.
 * 
 * @author Juan C. Orozco
 */
public class DialogHelper {

	/**
	 * Constantes de la clase para identificar un tipo de Dialog creado desde este helper
	 */
	public static final int PROGRESS_DIALOG = 0;

	public DialogHelper() {
		
	}

	/**
	 * Crea un progress dialog con los parámetros establecidos.
	 * @param context - Contexto de la aplicacion
	 * @return ProgressDialog
	 */
	public static Dialog crearProgressDialog(Context context, String titulo, String msg, boolean barraHtl) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setTitle(titulo);
		dialog.setMessage(msg);
		dialog.setCancelable(false);
		dialog.setProgressStyle((barraHtl)? ProgressDialog.STYLE_HORIZONTAL :
											ProgressDialog.STYLE_SPINNER);
		return dialog;
	}
}