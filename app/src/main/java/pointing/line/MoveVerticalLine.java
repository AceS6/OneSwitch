package pointing.line;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import data.Globale;

public class MoveVerticalLine extends AsyncTask<Void,Void,Void>{

	private View line;
	private int i;
	
	public MoveVerticalLine(View line){
		Log.d("", "Constructor vertical line");
		this.line=line;
		i=0;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		Log.d("", "inbackground");
		int width = Globale.engine.getWidth();
		while(i<width && !this.isCancelled()){
			this.publishProgress();
			try {
				Thread.sleep(200/Globale.engine.getProfil().getLineSpeed());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public void onProgressUpdate(Void...result){
		//line.setBottom(i);
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) line.getLayoutParams();
		params.leftMargin=i;
		line.setLayoutParams(params);
		i++;
		Log.d("moveverticalline", "left margin="+i);
	}
}