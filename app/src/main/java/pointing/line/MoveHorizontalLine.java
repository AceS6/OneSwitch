package pointing.line;

import data.Globale;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class MoveHorizontalLine extends AsyncTask<Void,Void,Void>{

	private View line;
	private int i;
	
	public MoveHorizontalLine(View line){
		this.line=line;
		i=0;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		int height = Globale.engine.getHeight();
		while(i<height && !this.isCancelled()){
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
		params.topMargin=i;
		line.setLayoutParams(params);
		i++;
		Log.d("movehorizontalline", "top margin="+i);
	}

}
