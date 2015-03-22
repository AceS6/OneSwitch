package data;

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

    private String phoneNuber;




    // --------- CONSTRUCTOR
	
	public OneSwitchData(){
        this.setDefaultProfil();
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

    public void setDefaultProfil(){
        profil = new Profil("Default");
    }

    public String getPhoneNuber() {
        return phoneNuber;
    }

    public void setPhoneNuber(String phoneNuber) {
        this.phoneNuber = phoneNuber;
    }

}
