package com.creativa.arcmenu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

public class ArcInitor implements OnClickListener{
	
	private Activity activity;
	private ArcMenu arcMenu;
	private FrameLayout controlLayout;
	private int arcWidth, arcHeight;
	private int controlLayoutWidth, controlLayoutHeight;
	private boolean isArcMeasureReady, isControlLayoutMeasureReady;
	private OnArcMenuItemSelectedListener listener;
	
	public ArcInitor(){
		this.isArcMeasureReady = this.isControlLayoutMeasureReady = false;
		
	}
	
	public void setOnArcMenuItemSelected(OnArcMenuItemSelectedListener listener){
		this.listener = listener;
	}
	
	public void start(Activity activity, ArcMenu menu, int[] itemDrawables){
		this.arcMenu = menu;
		this.activity = activity;
		
		final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(activity);
            item.setImageResource(itemDrawables[i]);
            item.setTag(i);
            menu.addItem(item,this);
        }
        
        this.addListeners();
	}
	
	
	
	private void addListeners(){
		isArcMeasureReady = isControlLayoutMeasureReady = false;
	    controlLayout = (FrameLayout) activity.findViewById(R.id.control_layout);
	    

	    ViewTreeObserver viewTreeObserverControlLayout = controlLayout.getViewTreeObserver();
	    if (viewTreeObserverControlLayout.isAlive()) {
	        viewTreeObserverControlLayout
	                .addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
	                    @Override
	                    public void onGlobalLayout() {
	                    	if (Build.VERSION.SDK_INT<16) {
	                            removeLayoutListenerPre16(arcMenu.getViewTreeObserver(),this);
	                        } else {
	                            removeLayoutListenerPost16(arcMenu.getViewTreeObserver(), this);
	                        }
	                        controlLayoutWidth = controlLayout.getWidth();
	                        controlLayoutHeight = controlLayout.getHeight();
	                        isControlLayoutMeasureReady = true;

	                        activity.runOnUiThread(runnable);

	                    }
	                });
	    }

	    ViewTreeObserver viewTreeObserver = arcMenu.getViewTreeObserver();
	    if (viewTreeObserver.isAlive()) {
	        viewTreeObserver
	                .addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
	                    @Override
	                    public void onGlobalLayout() {
	                    	if (Build.VERSION.SDK_INT<16) {
	                            removeLayoutListenerPre16(arcMenu.getViewTreeObserver(),this);
	                        } else {
	                            removeLayoutListenerPost16(arcMenu.getViewTreeObserver(), this);
	                        }
	                        arcWidth = arcMenu.getWidth();
	                        arcHeight = arcMenu.getHeight();
	                        isArcMeasureReady = true;

	                        activity.runOnUiThread(runnable);
	                    }
	                });
	    }
	}
	
	
	
	Runnable runnable = new Runnable() {

	    @Override
	    public void run() {
	        if (isArcMeasureReady && isControlLayoutMeasureReady) {
	            LayoutParams params = (LayoutParams) arcMenu // your container LayoutParams , mine is RelativeLayout
	                    .getLayoutParams(); 
	            int leftMargin = -((arcWidth - controlLayoutWidth) / 2) + 15;
	            int bottomMargin = -((arcHeight - controlLayoutHeight) / 2) + 15;
	            params.setMargins(leftMargin, 0, 0, bottomMargin);
	            arcMenu.setLayoutParams(params);
	        }
	    }
	};

	@Override
	public void onClick(View v) {
		listener.onArcMenuItemSelected(Integer.parseInt(v.getTag().toString()));
	}
	
	public interface OnArcMenuItemSelectedListener{
		public void onArcMenuItemSelected(int id);
	}
	
	
	
	@SuppressWarnings("deprecation")
	private void removeLayoutListenerPre16(ViewTreeObserver observer, OnGlobalLayoutListener listener){
	    observer.removeGlobalOnLayoutListener(listener);
	}

	@TargetApi(16)
	private void removeLayoutListenerPost16(ViewTreeObserver observer, OnGlobalLayoutListener listener){
	    observer.removeOnGlobalLayoutListener(listener);
	}
}
