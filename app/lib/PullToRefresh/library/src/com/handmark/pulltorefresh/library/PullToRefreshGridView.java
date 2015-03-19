/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.handmark.pulltorefresh.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.handmark.pulltorefresh.library.internal.EmptyViewMethodAccessor;

public class PullToRefreshGridView extends PullToRefreshAdapterViewBase<GridView> {

	public PullToRefreshGridView(Context context) {
		super(context);
	}

	public PullToRefreshGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToRefreshGridView(Context context, Mode mode) {
		super(context, mode);
	}

	public PullToRefreshGridView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
	}

	@Override
	public final Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@Override
	protected final GridView createRefreshableView(Context context, AttributeSet attrs) {
		final GridView gv;
		if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			gv = new InternalGridViewSDK9(context, attrs);
		} else {
			gv = new InternalGridView(context, attrs);
		}

		// Use Generated ID (from res/values/ids.xml)
		gv.setId(R.id.gridview);
		return gv;
	}

	class InternalGridView extends GridView implements EmptyViewMethodAccessor {

		 private Bitmap background;  
		 private Context context = null;
		 
		public InternalGridView(Context context, AttributeSet attrs) {
			super(context, attrs);
			this.context = context;
			background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_bookshelf);  
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullToRefreshGridView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}
		
		protected void dispatchDraw(Canvas canvas) {  
			
		    int count = getChildCount();  
		    int top = count>0 ? getChildAt(0).getTop() : 0;  
		    int backgroundWidth = background.getWidth();  
		    int backgroundHeight = background.getHeight();  
		    int width = getWidth();  
		    int height = getHeight();  
		   
//		    int oneLineHeight = dip2px(context, 100);
//		    int bottom = count > 0 ? getChildAt(0).getBottom() : 0;	    
		    
		    View child = getChildAt(0);
//		   System.out.println("------measured state is " + state);
		    Rect rect = new Rect();
	    	getChildVisibleRect(child, rect, null);
	    	child.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED); 
	    	
	    	int childHeigth = child.getMeasuredHeight();
	    	
	    	child.requestLayout();
	    	int topValue = top + childHeigth - dip2px(context, 30) - backgroundHeight + 6;
		    for (int y = topValue; y<height; y += childHeigth){  
		        for (int x = 0; x<width; x += backgroundWidth){  
		            canvas.drawBitmap(background, x, y, null);  
		        }  
		    } 

		   
		    super.dispatchDraw(canvas);  
		}  
	}
	
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	@TargetApi(9)
	final class InternalGridViewSDK9 extends InternalGridView {

		public InternalGridViewSDK9(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
				int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

			final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
					scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

			// Does all of the hard work...
			OverscrollHelper.overScrollBy(PullToRefreshGridView.this, deltaX, scrollX, deltaY, scrollY, isTouchEvent);

			return returnValue;
		}
	}
}
