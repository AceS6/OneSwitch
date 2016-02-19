package control.listener;

import android.app.Service;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.projeta.oneswitch.R;

import data.Globale;
import data.actions.Click;
import pointing.square.MoveHorizontalSquare;
import pointing.square.MoveVerticalSquare;
import service.OneSwitchService;

public class SquareOverlayTouchListener implements OnTouchListener{

	private WindowManager windowmanager;
	private WindowManager.LayoutParams params;
	private View globalview;
    private Service s;

	private MoveVerticalSquare verticalMove;
	private MoveHorizontalSquare horizontalMove;

    private View square;

	public SquareOverlayTouchListener(WindowManager windowmanager, View globalview, WindowManager.LayoutParams params, Service s){
		this.windowmanager=windowmanager;
		this.globalview=globalview;
		this.params=params;
		this.s = s;
        windowmanager.addView(globalview, params);

        square = globalview.findViewById(R.id.square_selection);
        square.setBackgroundColor(Globale.engine.getProfil().getColorLineHInt());
        RelativeLayout.LayoutParams paramSquare = (android.widget.RelativeLayout.LayoutParams) square.getLayoutParams();
        paramSquare.topMargin=0;
        paramSquare.leftMargin=0;
        paramSquare.bottomMargin=Globale.engine.getHeight()/2;
        paramSquare.rightMargin=Globale.engine.getWidth()/2;
		verticalMove=new MoveVerticalSquare(square, horizontalMove, 1);
		horizontalMove=new MoveHorizontalSquare(square, verticalMove, 1);
		horizontalMove.execute();
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
            horizontalMove.cancel(true);
            verticalMove.cancel(true);
        OneSwitchService.startPointing(globalview, OneSwitchService.LINE_POINTING, new int[]{horizontalMove.getLeft(), horizontalMove.getRight(), verticalMove.getTop(), verticalMove.getBottom()});
        return true;
    }

}
