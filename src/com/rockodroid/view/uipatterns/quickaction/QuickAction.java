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
package com.rockodroid.view.uipatterns.quickaction;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.widget.PopupWindow;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Esta clase es la encargada de mostrar la ventana emergente.
 * 
 * @author Juan Carlos Orozco
 */
public class QuickAction {

	private static final int DEFAULT_ANIMATION = android.R.style.Animation_Translucent;

	/** Quien llama al quick action */
	private final View root;
	/** La ventana que se mostrará, contenedora de los items */
	private PopupWindow window;
	private final Context context;
	/** Es la barra del quickaction, encargada de inflar los items (view)*/
	private final QuickBar quickBar;
	/** Indica si el view llamante está a la izquierda */
	private boolean izquierda = true;

	public QuickAction(View llamante){
		this.root = llamante;
		this.context = llamante.getContext();
		this.quickBar = new QuickBar(context);
		this.window = new PopupWindow(context);
		window.setTouchable(true);
		window.setFocusable(true);
		window.setOutsideTouchable(true);
		window.setContentView(quickBar.getView());
		window.setBackgroundDrawable(new BitmapDrawable()); // Elimina el background del popupWindow
		window.setAnimationStyle(DEFAULT_ANIMATION);
	}

	public void show(){
		WindowManager winMan = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int anchorScreen = winMan.getDefaultDisplay().getWidth();
		int offset = root.getWidth() / 2;

		window.setWidth(anchorScreen - offset);
		window.setHeight(root.getHeight());

		int x, y;
		if(izquierda) {
			x = root.getLeft() + offset;
		}else { 
			x = 0;
		}

		y = root.getBottom();
		window.showAtLocation(root, Gravity.NO_GRAVITY, x, y);
	}

	public void addItem(ActionItem item){
		quickBar.addItem(item);
	}

	public void dismiss(){
		window.dismiss();
	}

	public void setLado(boolean izq) {
		this.izquierda = izq;
		quickBar.confDireccion(izq);
	}
}