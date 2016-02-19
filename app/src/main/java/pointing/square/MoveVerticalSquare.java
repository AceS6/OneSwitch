package pointing.square;

import pointing.Pause;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import data.Globale;

public class MoveVerticalSquare extends AsyncTask<Void,Void,Void> implements Pause, Runnable {

	private View square;
	private int i;
	private int height;
	private int coef;
	private MoveHorizontalSquare hmove;
	private RelativeLayout.LayoutParams params;
    private Handler handler = new Handler();
	
	public MoveVerticalSquare(View square, MoveHorizontalSquare hmove, int coef){
		this.square=square;
		this.hmove=hmove;
		this.coef=coef;
		params = (android.widget.RelativeLayout.LayoutParams) square.getLayoutParams();
		this.height=Globale.engine.getHeight();
		i=params.topMargin;
		Log.d("move vertical", "margin : i="+i);
	}
	
	public int getHeight(){
		return height;
	}

    public int getTop(){
        Log.d(params.topMargin+"", "topMargin(moveVertical)");
        return params.topMargin;
    }

    public int getBottom(){
        Log.d(params.bottomMargin+"", "bottomMargin(moveVertical)");
        return params.bottomMargin;
    }
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
        scroll();
        return null;
	}
	
	private void scroll(){
		int j=0;
		while(j<height/2 && !this.isCancelled()){
				this.publishProgress();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
                    Log.d("stop", "MoveVerticalSquare");
					e.printStackTrace();
                    return;
				}
            j++;
			}

		
	}
	
	public void pause(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onProgressUpdate(Void...result){
		//line.setBottom(i);
		if(coef==1){
			params.topMargin=params.topMargin+1;
            params.bottomMargin=params.bottomMargin-1;
			i = params.topMargin;
		}
		else{
            params.bottomMargin=params.bottomMargin+1;
            params.topMargin=params.topMargin-1;
			i = params.bottomMargin;
		}
		square.setLayoutParams(params);
	}

    @Override
    public void onPostExecute(Void result){
        if(!this.isCancelled()) {
            Log.d((20 - (Globale.engine.getProfil().getScrollSpeed())) * 50 + "", "robin");
            handler.postDelayed(this, (20 - (Globale.engine.getProfil().getScrollSpeed())) * 50);
        }
    }

    @Override
    public void run() {
        if(coef!=-1 && !this.isCancelled()){
            int hmove_height=hmove.getWidth();
            hmove = new MoveHorizontalSquare(square,this, -coef);
            hmove.execute();
        }
    }
}
