package pointing.square;

import data.Globale;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

	public class MoveVerticalSquareQuarter extends AsyncTask<Void,Void,Void>{
		private View square;
		private int i;
		private int height;
		private int coef;
		private MoveHorizontalSquareQuarter hmove;
		private RelativeLayout.LayoutParams params ;
		
		public MoveVerticalSquareQuarter(View square, MoveHorizontalSquareQuarter hmove, int coef){
			this.square=square;
			this.hmove=hmove;
			this.coef=coef;
			params = (android.widget.RelativeLayout.LayoutParams) square.getLayoutParams();
			this.height=params.height;
			i=params.topMargin;
			Log.d("move horizontal", "margin : i="+i);
		}
		
		public int getHeight(){
			return height;
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
			while(j<height && !this.isCancelled()){
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
				params.topMargin=params.topMargin+1;
			}
			else{
				params.topMargin=params.topMargin-1;
			}		

			
			square.setLayoutParams(params);
		}
		
		@Override
		public void onPostExecute(Void result){
				Log.d("horizontal move", "Move stopped at "+i);
				if(coef!=-1 &&!this.isCancelled()){
					hmove = new MoveHorizontalSquareQuarter(square,this, -coef);
					hmove.execute();
				}
				else{
					this.cancel(true);
				}
		}
}
