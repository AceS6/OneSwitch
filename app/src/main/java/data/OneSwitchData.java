package data;

import android.view.WindowManager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class OneSwitchData {

    // ---------- ATTRIBUTES

    private Profil profil;

    private int width;
    private int height;

    protected PropertyChangeSupport propertyChangeSupport;

    private boolean serviceState;

	public final static String pointing_key="pref_pointing";



    // --------- CONSTRUCTOR
	
	public OneSwitchData(){
        profil = new Profil(-2, "default", "Line Pointing", 70, 10, "#000000", "#000000", "#000000", 80, 50);
        serviceState=false;
        propertyChangeSupport = new PropertyChangeSupport(this);
	}


    // ---------- METHODS

    // ----- GETTERS AND SETTERS

    public Profil getProfil(){
        return profil;
    }

    public void setProfil(Profil p){
        this.profil = p;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public boolean getServiceState(){ return serviceState;}

    public void setServiceState(boolean serviceState){
        this.serviceState=serviceState;
        propertyChangeSupport.firePropertyChange("serviceState",!serviceState, serviceState);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // ----- OTHER METHODS
    public static String getHexaColor(int color){
        return "#"+Integer.toHexString(color);
    }

    public static String getSemiTransparentColor(String color){
        return color.substring(0,1)+"50"+color.substring(3,9);
    }
	
}