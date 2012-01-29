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

import android.graphics.drawable.Drawable;
import android.view.View.OnClickListener;

/**
 * Representa un item que puede ser mostrado en el QuickBar
 * @author Juan C. Orozco
 */
public class ActionItem {
	private Drawable icon;
	private String title;
	private OnClickListener listener;
	
	public ActionItem(Drawable image,String text, OnClickListener action)
	{
		this.icon = image;
		this.title = text;
		this.listener = action;
	}
	
	public Drawable getIcon(){
		return this.icon;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public OnClickListener getListener(){
		return this.listener;
	}
	
}