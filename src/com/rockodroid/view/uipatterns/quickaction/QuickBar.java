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


import com.rockodroid.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Representa la barra deslizable en la cual estarán los items
 * del quickaction, por lo tanto presenta independencia entre 
 * la ventana que los muestra y el diseño de los mismos.
 * 
 * @author Juan Carlos Orozco
 */
public class QuickBar {

	/** View que representa el ViewGroup raíz del QuickAction */
	private View viewRoot;
	/** El contenedor View que se mostrará */
	private ViewGroup viewBar;
	private LayoutInflater inflador;

	/* BitMap vacío para establecer fondo transparente */
	private final static Drawable emptyBitm = new BitmapDrawable();

	/** Recursos para establecer el la dirección del quickaction */
	private final static int izqArrow = R.drawable.arrow_left;
	private final static int derArrow = R.drawable.arrow_right;

	private static ImageView izqImage;
	private static ImageView derImage;

	public QuickBar(Context context) {
		inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		viewRoot = inflador.inflate(R.layout.layout_quickbar, null);
		izqImage = (ImageView) viewRoot.findViewById(R.id.quick_iv_left);
		derImage = (ImageView) viewRoot.findViewById(R.id.quick_iv_right);
		viewBar = (ViewGroup) viewRoot.findViewById(R.id.quick_contenedor);
		confDireccion(true);
	}

	/**
	 * Agrega un item al ViewGroup contenedor (viewBar).
	 * @param item - ActionItem que será mostrado.
	 */
	public void addItem(ActionItem item){
		if (item != null){
			View itemView = inflador.inflate(R.layout.layout_quick_item,null); 
			itemView.setClickable(true);
			itemView.setFocusable(true);

			TextView title = (TextView)itemView.findViewById(R.id.quickitem_text_item);
			title.setText(item.getTitle());

			ImageView icon = (ImageView)itemView.findViewById(R.id.quickitem_icon_item);
			icon.setImageDrawable(item.getIcon());

			itemView.setOnClickListener(item.getListener());

			viewBar.addView(itemView);
		}
	}

	/**
	 * Retorna el View raíz de la barra del quickaction.
	 * @return View - La raíz del View
	 */
	public View getView(){
		return this.viewRoot;
	}

	/**
	 * Indica si la flecha se pone a la izquierda o a la derecha.
	 * @param izquierda
	 */
	public void confDireccion(boolean izquierda) {
		if(izquierda) {
			izqImage.setBackgroundResource(izqArrow);
			derImage.setBackgroundDrawable(emptyBitm);
		}else {
			derImage.setBackgroundResource(derArrow);
			izqImage.setBackgroundDrawable(emptyBitm);
		}
	}
}