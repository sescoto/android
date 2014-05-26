package com.example.android.apis.graphics;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

public class HelloOpenGLES10 extends Activity {
	  
    private GLSurfaceView mGLView;
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new HelloOpenGLES10SurfaceView(this);
        setContentView(mGLView);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }
}
  
class HelloOpenGLES10SurfaceView extends GLSurfaceView {

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private HelloOpenGLES10Renderer mRenderer;
    private float mPreviousX;
    private float mPreviousY;
	public HelloOpenGLES10SurfaceView(Context context){
        super(context);
        
        // Set the Renderer for drawing on the   GLSurfaceView
        //setRenderer(new HelloOpenGLES10Renderer());
        
        // set the mRenderer member
        mRenderer = new HelloOpenGLES10Renderer();
        setRenderer(mRenderer);
        
        // Render the view only when there is a change
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        
    }

    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();
        float y3 = getHeight() / 6;
        float x3 = getWidth() / 6;
        mRenderer.mX = 0.0f;
        mRenderer.mY = 0.5f;
        mRenderer.mZ = 1.0f;
        
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
    
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;
    
                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                  dx = dx * -1 ;
                }
                if (dy != 0){
                	if (y <= (getHeight() / 2) + y3 && y >= (getHeight() / 2) - y3 && x <= (getWidth() / 2) + x3 && x >= (getWidth() / 2) - x3) {
                		mRenderer.mX = 1.0f;
                		mRenderer.mZ = 0.0f;
                	}
                	else {
                		mRenderer.mZ = 1.0f;
                		mRenderer.mX = 0.0f;
                	}
                }
    
                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                  dy = dy * -1 ;
                }
              
//                if (dx != 0){
//                	if (x <= (getWidth() / 2) + x3 && x >= (getWidth() / 2) - x3) {
//                		mRenderer.mY = 1.0f;
//                	}
//                	else {
//                		mRenderer.mZ = 1.0f;
//                	}
//                }
    
                mRenderer.mAngle += (dx + dy) * TOUCH_SCALE_FACTOR;
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    } 
}