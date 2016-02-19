package view.custom;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Color;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;

import data.Globale;
import view.custom.ColorPickerView.OnColorChangedListener;

public class ColorPickerPreferenceDialog extends DialogPreference implements OnColorChangedListener{
  private int mColor = 0;
  public ColorPickerPreferenceDialog(Context context, AttributeSet attrs) {
    super(context, attrs);
    
  }
  /**
   * Declenche des qu'on ferme la boite de dialogue
  */
  protected void onDialogClosed(boolean positiveResult) {
    // Si l'utilisateur a clique sur  OK
    if (positiveResult) {
      persistInt(mColor);
      Log.d("", "storing "+mColor);
      if(this.getKey().equals("pointingline_color_v")){
      	Globale.engine.getProfil().setColorLineV(mColor);
      }
      else if (this.getKey().equals("pointingline_color_h")){
      	Globale.engine.getProfil().setColorLineH(mColor);
      }
      else if(this.getKey().equals("pointingsquare_color")){
      	Globale.engine.getProfil().setColorSquare(mColor);
      }
      else if(this.getKey().equals("service_color")){
          Globale.engine.getProfil().setServiceColor(mColor);
      }
    // Ou getSharedPreferences().edit().putInt(getKey(), mColor).commit();
    }
    super.onDialogClosed(positiveResult);
  }
  /**
  * Pour construire la boite de dialogue
  */
  protected void onPrepareDialogBuilder(Builder builder) {
    // On recupere l'ancienne couleur ou la couleur par defaut
    int oldColor = getSharedPreferences().getInt(getKey(), Color.BLACK);
    // On insere la vue dans la boite de dialogue
    builder.setView(new ColorPickerView(getContext(), this, oldColor));
    super.onPrepareDialogBuilder(builder);
  }
  /**
  * Declenche a chaque fois que l'utilisateur choisit une couleur
  */
  public void colorChanged(int color) {
    mColor = color;
  }
}