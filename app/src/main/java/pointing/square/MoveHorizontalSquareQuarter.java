package pointing.square;


import pointing.Pause;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import data.Globale;

public class MoveHorizontalSquareQuarter extends AsyncTask<Void,Void,Void> implements Pause{

	private View square;
	private int i;
	private int width;
	private int coef;
	private MoveVerticalSquareQuarter vmove;
	private RelativeLayout.LayoutParams params ;
	
	public MoveHorizontalSquareQuarter(View square, MoveVerticalSquareQuarter vmove, int coef){
		this.square=square;
		this.vmove=vmove;
		this.coef=coef;
		params = (android.widget.RelativeLayout.LayoutParams) square.getLayoutParams();
		this.width=params.width;
		i=params.leftMargin;
		Log.d("move horizontal", "margin : i="+i);
	}
	
	public int getWidth(){
		return width;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		if(!this.isCancelled()){
			if(coef==1){
					scroll();
					pause(Globale.engine.getProfil().getSquarePauseTime());
			}
			else{
					scroll();
					pause(Globale.engine.getProfil().getSquarePauseTime());
			} 
		}
		return null;
	}
	
	private void scroll(){
		int j=0;
		while(j<width && !this.isCancelled()){
				this.publishProgress();
				try {
					Thread.sleep(200/Globale.engine.getProfil().getLineSpeed());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			params.leftMargin=params.leftMargin+1;
		}
		else{
			params.leftMargin=params.leftMargin-1;
		}		
		square.setLayoutParams(params);
	}
	
	@Override
	public void onPostExecute(Void result){
		if(!this.isCancelled()){
			Log.d("horizontal move", "Move stopped at "+i);
			vmove = new MoveVerticalSquareQuarter(square,this, coef);
			vmove.execute();
		}
		else{
			this.cancel(true);
		}
	}
}


