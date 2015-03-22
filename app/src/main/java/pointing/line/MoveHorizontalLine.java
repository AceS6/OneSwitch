package pointing.line;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import data.Globale;

public class MoveHorizontalLine extends AsyncTask<Void,Void,Void>{

    private View line;
    private int i;
    private boolean down;

    public MoveHorizontalLine(View line){
        this.line=line;
        this.down = true;
        i=0;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub
        int bottom = Globale.engine.getHeight();
        int top = 0;

        while(!this.isCancelled()) {
            if (down) {
                while (i < bottom && !this.isCancelled()) {
                    this.publishProgress();
                    try {
                        Thread.sleep(20 / Globale.engine.getProfil().getLineSpeed());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                down = false;
            }
            else {
                while (i > top && !this.isCancelled()) {
                    this.publishProgress();
                    try {
                        Thread.sleep(20 / Globale.engine.getProfil().getLineSpeed());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                down = true;
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
        if(down) i++;
        else i--;
        Log.d("movehorizontalline", "top margin="+i);
    }

}
