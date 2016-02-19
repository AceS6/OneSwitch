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
    private int[] maxPos;

    public MoveVerticalLine(View line, int[] maxPos){
        Log.d("", "Constructor vertical line");
        this.line=line;
        this.toRight=true;
        i=maxPos[0];
        this.maxPos = maxPos;
        Log.d("left="+maxPos[0], "MoveVerticalLine");
        Log.d("right="+maxPos[1], "MoveVerticalLine");
    }

    @Override
    protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub
        Log.d("", "inbackground");
        int left = maxPos[0];
        int right = Globale.engine.getWidth() - maxPos[1];
        while(!this.isCancelled()) {
            if (toRight) {
                while (i < right && !this.isCancelled()) {
                    this.publishProgress();
                    try {
                        Thread.sleep(20 / Globale.engine.getProfil().getLineSpeed());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        break;
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
                        break;
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

    public int getLeft(){
        return i;
    }
}