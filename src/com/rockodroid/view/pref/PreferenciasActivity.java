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
package com.rockodroid.view.pref;

import com.rockodroid.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Clase que se encarga de mostrar, guardar y modificar las preferencias
 * de la aplicación a medida que el usuario interactúa con la interfaz.
 * Las preferencias están diseñadas vía layout xml.
 * 
 * @author Juan C. Orozco
 */
public class PreferenciasActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* Establecer estas como las preferencias por defecto */
		PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.layout_preferencias, false);
		addPreferencesFromResource(R.xml.layout_preferencias);
	}
}