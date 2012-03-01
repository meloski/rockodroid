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

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**Presenta una interfaz para la comunicación con el servicio por medio del binder.
 * @author Juan C. Orozco
 */
public class ServiceBinderHelper {

	private Context context;
	private PlayerBinder binder;
	private boolean bind;

	private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
        	ServiceBinderHelper.this.binder = (PlayerBinder) service;
        	bind = true;
        }

       public void onServiceDisconnected(ComponentName arg0) {
    	   bind = false;
       }
	};

	public ServiceBinderHelper(Context c) {
		context = c;
		bind = false;
		Intent i = new Intent(context, com.rockodroid.data.service.MediaService.class);
		context.bindService(i, mConnection, Context.BIND_AUTO_CREATE);
	}

	public void desconectar() {
		if(bind)
			context.unbindService(mConnection);
	}

	public PlayerBinder getBinder() {
		return binder;
	}
}