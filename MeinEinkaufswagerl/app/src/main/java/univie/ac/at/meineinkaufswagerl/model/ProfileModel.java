package univie.ac.at.meineinkaufswagerl.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Wilson on 08.05.2016.
 */
public class ProfileModel implements Serializable{ //extends UserModel

    private static final long serialVersionUID = 0L;

    /*
    //Singleton Construction
    private static ProfileModel ourInstance = new ProfileModel();

    public static ProfileModel getInstance() {
        return ourInstance;
    }

    private ProfileModel() {
    }
    */
     /*
    Erstellen des Arrays um die Krankheiten zu speichern.
    Position 1: Diabetes
    Position 2: Morbus Crohn
    Position 3: Gicht
     */

    private  int[] krankheiten= new int[3];// = {0, 0, 0}; //new int[3];
    private  ArrayList<String> krankheitenListe;//=new ArrayList<String>();

     /*
    Erstellen des Arrays um die Unverträglichkeiten zu speichern.
    Position 1: Lactose
    Position 2: Gluten
    Position 3: Fructose
    Position 4: Ei
    Position 5: Fisch
    Position 6: Phenylalanin
    Position 7: Histamin
    Position 8: Sorbin
    Position 9: Saccharose
    Position 10: Erdnüsse
     */

    private  int[] unvertraeglichkeiten = new int[10];// ={0, 0, 0,0,0,0,0,0,0,0};// new int[10] ;
    private  ArrayList<String> unvertraeglichkeitenListe;//=new ArrayList<String>();
    private  ArrayList<String> extraListe;//=new ArrayList<String>();


/*
    public ProfileModel(int[] unvertraeglichkeiten,int[] krankheiten ) {
        this.unvertraeglichkeiten = unvertraeglichkeiten;
        this.krankheiten = krankheiten;
        for(int i=0; i<10;i++){
            unvertraeglichkeiten[i]=0;
        }
        for(int i=0; i<3;i++){
            krankheiten[i]=0;
        }
    }
*/
    public ProfileModel() {
        this.krankheitenListe=new ArrayList<String>();
        this.unvertraeglichkeitenListe=new ArrayList<String>();
        this.extraListe=new ArrayList<String>();
        for(int i=0; i<10;i++){
            this.unvertraeglichkeiten[i]=0;
        }
        for(int i=0; i<3;i++){
            this.krankheiten[i]=0;
        }
    }

    public  ArrayList<String> getKrankheitenListe() {
        return krankheitenListe;
    }

    public  void addKrankheit(String speechInput) {
        this.krankheitenListe.add(speechInput);
    }

    public  void changeKrankheit (String speechInput, int index){
        krankheitenListe.add(index,speechInput);
    }

    public  void removeKrankheit(int index){
        krankheitenListe.remove(index);
    }

    public  int getDiabetes(){
        return krankheiten[0];
    }
    public  void setDiabetes(int trigger){
        krankheiten[0]=trigger;
    }

    public  int getMohrbus(){
        return krankheiten[1];
    }
    public  void setMorbus(int trigger){
        krankheiten[1]=trigger;
    }

    public  int getGicht(){
        return krankheiten[2];
    }
    public  void setGicht(int trigger){
        krankheiten[2]=trigger;
    }

    public  ArrayList<String> getUnvertraeglichkeitenListe() {
        return unvertraeglichkeitenListe;
    }

    public  void addUnvertraeglichkeit(String speechInput) {
        this.unvertraeglichkeitenListe.add(speechInput);
    }

    public  void changeUnvertraeglichkeit (String speechInput, int index){
        unvertraeglichkeitenListe.add(index,speechInput);
    }

    public  void removeUnvertraeglichkeit(int index){
        unvertraeglichkeitenListe.remove(index);
    }


    public  int getLactose(){
        return unvertraeglichkeiten[0];
    }
    public  void setLactose(int trigger){
         unvertraeglichkeiten[0]=trigger;
    }

    public  int getGluten(){
        return unvertraeglichkeiten[1];
    }
    public  void setGluten(int trigger){
        unvertraeglichkeiten[1]=trigger;
    }

    public  int getFructose(){
        return unvertraeglichkeiten[2];
    }
    public  void setFructose(int trigger){
        unvertraeglichkeiten[2]=trigger;
    }

    public  int getEi(){
        return unvertraeglichkeiten[3];
    }
    public  void setEi(int trigger){
        unvertraeglichkeiten[3]=trigger;
    }

    public  int getFisch(){
        return unvertraeglichkeiten[4];
    }
    public  void setFisch(int trigger){
        unvertraeglichkeiten[4]=trigger;
    }

    public  int getPhenylalanin(){
        return unvertraeglichkeiten[5];
    }
    public  void setPhenylalanin(int trigger){
        unvertraeglichkeiten[5]=trigger;
    }

    public  int getHistamin(){
        return unvertraeglichkeiten[6];
    }
    public  void setHistamin(int trigger){
        unvertraeglichkeiten[6]=trigger;
    }

    public  int getSorbin(){
        return unvertraeglichkeiten[7];
    }
    public  void setSorbin(int trigger){
        unvertraeglichkeiten[7]=trigger;
    }

    public  int getSaccharose(){
        return unvertraeglichkeiten[8];
    }
    public  void setSaccharose(int trigger){
        unvertraeglichkeiten[8]=trigger;
    }

    public  int getErdnüsse(){
        return unvertraeglichkeiten[9];
    }
    public  void setErdnüsse(int trigger){
        unvertraeglichkeiten[9]=trigger;
    }

    public  ArrayList<String> getExtraListe() {
        return extraListe;
    }

    public  void addExtra(String speechInput) {
        this.extraListe.add(speechInput);
    }

    public  void changeExtra (String speechInput, int index){
        extraListe.add(index,speechInput);
    }

    public  void removeExtra(int index){
        extraListe.remove(index);
    }



}
