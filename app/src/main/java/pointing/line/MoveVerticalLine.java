package pointing.line;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import data.Globale;

public class MoveVerticalLine extends AsyncTask<Void,Void,Void>{

    private View line;
    private int i;
    private boolean toRight;

    public MoveVerticalLine(View line){
        Log.d("", "Constructor vertical line");
        this.line=line;
        this.toRight=true;
        i=0;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub
        Log.d("", "inbackground");
        int right = Globale.engine.getWidth();
        int left = 0;
        while(!this.isCancelled()) {
            if (toRight) {
                while (i < right && !this.isCancelled()) {
                    this.publishProgress();
                    try {
                        Thread.sleep(20 / Globale.engine.getProfil().getLineSpeed());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                toRight = false;
            }
            else {
                while (i > left && !this.isCancelled()) {
                    this.publishProgress();
                    try {
                        Thread.sleep(20 / Globale.engine.getProfil().getLineSpeed());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                toRight = true;
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
        if(toRight) i++;
        else i--;
        Log.d("moveverticalline", "left margin="+i);
    }
}