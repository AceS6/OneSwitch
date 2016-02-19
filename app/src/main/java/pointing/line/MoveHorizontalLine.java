package pointing.line;

import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import data.Globale;

public class MoveHorizontalLine extends AsyncTask<Void,Void,Void>{

    private View line;
    private int i;
    private boolean down;
    private int[] maxPos;

    public MoveHorizontalLine(View line, int[] maxPos){
        this.line=line;
        this.down = true;
        i=maxPos[0];
        this.maxPos = maxPos;
        Log.d("top="+maxPos[0], "MoveHorizontalLine");
        Log.d("bottom="+maxPos[1], "MoveHorizontalLine");
    }

    @Override
    protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub
        int top = maxPos[0];
        int bottom = Globale.engine.getHeight() - maxPos[1];

        while(!this.isCancelled()) {
            if (down) {
                while (i < bottom && !this.isCancelled()) {
                    this.publishProgress();
                    try {
                        Thread.sleep(20 / Globale.engine.getProfil().getLineSpeed());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        break;
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
                        break;
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

    public int getTop(){
        TypedValue tv = new TypedValue();
        if (line.getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            i+=TypedValue.complexToDimensionPixelSize(tv.data,line.getContext().getResources().getDisplayMetrics());
        }
        return i;
    }

}
