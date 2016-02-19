package data;

import android.graphics.Color;

import java.util.HashMap;

/**
 * Created by Alexis on 18/01/2015.
 */
public class Profil {

    private int id;
    private String name;
    private String pointing;
    private int lineSpeed;
    private int lineSize;
    private String lineColorH, lineColorV;
    private String squareColor;
    private int serviceOpacity;
    private String serviceColor;
    private int scrollSpeed;
    private HashMap<Integer, Contact> contacts;

    public Profil(String name){
        this.id = -1;
        this.name = name;
        this.pointing = "Line Pointing";
        this.lineSpeed = 5;
        this.lineSize = 5;
        this.lineColorH = "#000000";
        this.lineColorV = "#000000";
        this.squareColor = "#000000";
        this.scrollSpeed = 5;
        this.serviceColor = "#BBDEFB";
        this.serviceOpacity = 5;
        this.contacts = new HashMap<Integer, Contact>();
        this.contacts.put(1, new Contact("undefined", "", 1));
        this.contacts.put(2, new Contact("undefined", "", 2));
        this.contacts.put(3, new Contact("undefined", "", 3));
        this.contacts.put(4, new Contact("undefined", "", 4));
        this.contacts.put(5, new Contact("undefined", "", 5));
        this.contacts.put(6, new Contact("undefined", "", 6));
        this.contacts.put(7, new Contact("undefined", "", 7));

    }

    public Profil(int id, String name, String pointing, int lineSpeed, int lineSize, String lineColorH, String lineColorV, String squareColor, int scrollSpeed, String serviceColor, int serviceOpacity, HashMap<Integer, Contact> contacts){

        this.id = id;
        this.name = name;
        this.pointing = pointing;
        this.lineSpeed = lineSpeed;
        this.lineSize = lineSize;
        this.lineColorH = lineColorH;
        this.lineColorV = lineColorV;
        this.squareColor = squareColor;
        this.scrollSpeed = scrollSpeed;
        this.serviceColor = serviceColor;
        this.serviceOpacity = serviceOpacity;
        this.contacts = contacts;

    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setPointing(String pointing){
        this.pointing = pointing;
    }

    public String getPointing(){
        return this.pointing;
    }

    public void setColorLineH(int color){
        this.lineColorH=getHexaColor(color);
    }

    public void setColorLineV(int color){
        this.lineColorV=getHexaColor(color);
    }

    public void setColorLineH(String color){
        this.lineColorH=color;
    }

    public void setColorLineV(String color){
        this.lineColorV=color;
    }

    public void setLineSize(int size){
        this.lineSize=size;
    }

    public int getLineSize(){
        return this.lineSize;
    }

    public String getColorLineH(){
        return this.lineColorH;
    }

    public String getColorLineV(){
        return this.lineColorV;
    }

    public int getColorLineHInt(){
        return Color.parseColor(this.lineColorH);
    }
    public int getColorLineVInt(){
        return Color.parseColor(this.lineColorV);
    }

    public int getColorSquareInt(){
        return Color.parseColor(this.squareColor);
    }

    public void setLineSpeed(int speed){
        this.lineSpeed=speed;
    }

    public int getLineSpeed(){
        return this.lineSpeed;
    }

    public void setColorSquare(int color){
        this.squareColor=getHexaColor(color);
        squareColor=getSemiTransparentColor(squareColor);
    }

    public String getColorSquare(){
        return this.squareColor;
    }

    public static String getSemiTransparentColor(String color){
        return color.substring(0,1)+"50"+color.substring(3,9);
    }

    public int getServiceOpacity() {
        return serviceOpacity;
    }

    public void setServiceOpacity(int serviceOpacity) {
        this.serviceOpacity = serviceOpacity;
    }

    public float getServiceOpacityFloat() {
        return 0.1f * serviceOpacity;
    }

    public String getServiceColor() {
        return serviceColor;
    }

    public int getServiceColorInt(){
        return Color.parseColor(this.serviceColor);
    }

    public void setServiceColor(int color){
        this.serviceColor=getHexaColor(color);
    }

    public int getScrollSpeed() {
        return scrollSpeed;
    }

    public void setScrollSpeed(int scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }

    public HashMap<Integer, Contact> getContacts() {
        return contacts;
    }

    public void setContacts(HashMap<Integer, Contact> contacts) {
        this.contacts = contacts;
    }

    public static String getHexaColor(int color){
        return "#"+Integer.toHexString(color);
    }

    public String toString(){
        return this.name;
    }

    // ----- OTHER METHODS

}
