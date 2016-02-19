package pointing.square;


import pointing.Pause;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import data.Globale;

	public class MoveHorizontalSquare extends AsyncTask<Void,Void,Void> implements Pause, Runnable {

		private View square;
		private int i;
		private int width;
		private int coef;
		private MoveVerticalSquare vmove;
		private RelativeLayout.LayoutParams params;

        private Handler handler = new Handler();
		
		public MoveHorizontalSquare(View square, MoveVerticalSquare vmove, int coef){
			this.square=square;
			this.vmove=vmove;
			this.coef=coef;
			params = (android.widget.RelativeLayout.LayoutParams) square.getLayoutParams();
			this.width=Globale.engine.getWidth();
			i=params.leftMargin;
			Log.d("move horizontal", "margin : i="+i);
		}
		
		public int getWidth(){
			return width;
		}

        public int getLeft(){
            Log.d(params.leftMargin+"", "leftMargin(moveHorizontal)");
            return params.leftMargin;
        }

        public int getRight(){
            Log.d(params.rightMargin+"", "rightMargin(moveHorizontal)");
            return params.rightMargin;
        }
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
            scroll();
			return null;
		}
		
		private void scroll(){
			int j=0;
			while(j<width/2 && !this.isCancelled()){
					this.publishProgress();
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
                        Log.d("stop", "MoveHorizontalSquare");
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
				params.leftMargin=params.leftMargin+1;
                params.rightMargin=params.rightMargin-1;
				i = params.leftMargin;
			}
			else{
				params.rightMargin=params.rightMargin+1;
                params.leftMargin=params.leftMargin-1;
				i = params.rightMargin;
			}
			square.setLayoutParams(params);
		}
		
		@Override
		public void onPostExecute(Void result){
            if(!this.isCancelled()) {
                handler.postDelayed(this, (20 - (Globale.engine.getProfil().getScrollSpeed())) * 50);
            }

		}

        @Override
        public void run() {
            if(!this.isCancelled()){
                Log.d("horizontal move", "Move stopped at "+i);
                int vmove_height=vmove.getHeight();
                vmove = new MoveVerticalSquare(square,this, coef);
                vmove.execute();
            }
        }
    }

