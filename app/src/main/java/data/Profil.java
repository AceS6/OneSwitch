package data;

import data.db.ProfilsDAO;

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
    private int squareWidth, squareHeight;
    private int squarePauseTime;
    private float squareDensity;

    public Profil(){
        squarePauseTime = 1000;
    }

    public Profil(int id, String name){
        this.id = id;
        this.name = name;
        squarePauseTime = 1000;
    }

    public Profil(int id, String name, String pointing, int lineSpeed, int lineSize, String lineColorH, String lineColorV, String squareColor, int squareWidth, int squareHeight){

        this.id = id;
        this.name = name;
        this.pointing = pointing;
        this.lineSpeed = lineSpeed;
        this.lineSize = lineSize;
        this.lineColorH = lineColorH;
        this.lineColorV = lineColorV;
        this.squareColor = squareColor;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        this.squarePauseTime = 1000;

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

    public void setSquareWidth(int width){
        this.squareWidth=width;
    }

    public void setSquareHeight(int height){
        this.squareHeight=height;
    }

    public int getSquareWidth(){
        return this.squareWidth;
    }

    public int getSquareHeight(){
        return this.squareHeight;
    }

    public void setDensity(float density){
        this.squareDensity=density;
    }

    public float getDensity(){
        return squareDensity;
    }

    public int getSquarePauseTime(){
        return squarePauseTime;
    }

    public static String getHexaColor(int color){
        return "#"+Integer.toHexString(color);
    }

    public String toString(){
        return this.name;
    }

    // ----- OTHER METHODS

}
