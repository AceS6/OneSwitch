package pointing.square;

import pointing.Pause;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import data.Globale;

public class MoveVerticalSquareHalf extends AsyncTask<Void,Void,Void> implements Pause{

	private View square;
	private int i;
	private int height;
	private int coef;
	private MoveHorizontalSquareHalf hmove;
	private RelativeLayout.LayoutParams params ;
	
	public MoveVerticalSquareHalf(View square, MoveHorizontalSquareHalf hmove, int coef, int width){
		this.square=square;
		this.hmove=hmove;
		this.coef=coef;
		params = (android.widget.RelativeLayout.LayoutParams) square.getLayoutParams();
		this.height=params.height;
		i=params.topMargin;
		Log.d("move vertical", "margin : i="+i);
	}
	
	public int getHeight(){
		return height;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		if(coef==1){
			while(i<Globale.engine.getProfil().getSquareHeight() && !this.isCancelled()){
				scroll();
				pause(Globale.engine.getProfil().getSquarePauseTime());
			}
		}
		else{
			while(i>0 && !this.isCancelled()){
				scroll();
				pause(Globale.engine.getProfil().getSquarePauseTime());
			}
		} 
		return null;
	}
	
	private void scroll(){
		int j=0;
		while(j<height && !this.isCancelled()){
				this.publishProgress();
				try {
					Thread.sleep(200/Globale.engine.getProfil().getLineSpeed());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			j++;
		
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
			i = params.topMargin+2*height;
		}
		else{
			params.topMargin=params.topMargin-1;
			i = params.topMargin-height;
		}		

		
		square.setLayoutParams(params);
	}
	
	@Override
	public void onPostExecute(Void result){
			Log.d("horizontal move", "Move stopped at "+i);
			if(coef!=-1 && !this.isCancelled()){
				int hmove_height=hmove.getWidth();
				hmove = new MoveHorizontalSquareHalf(square,this, -coef, hmove_height);
				hmove.execute();
			}
	}
}
