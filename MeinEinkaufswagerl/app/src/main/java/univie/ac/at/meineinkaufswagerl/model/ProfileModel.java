package univie.ac.at.meineinkaufswagerl.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Wilson on 08.05.2016.
 */
public final class ProfileModel implements Serializable{ //extends UserModel

    private static final long serialVersionUID = 0L;

     /*
    Erstellen des Arrays um die Krankheiten zu speichern.
    Position 1: Diabetes
    Position 2: Morbus Crohn
    Position 3: Gicht
     */

    private static int[] krankheiten = {0, 0, 0}; //new int[3];
    private static ArrayList<String> krankheitenListe=new ArrayList<String>();

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

    private static int[] unvertraeglichkeiten ={0, 0, 0,0,0,0,0,0,0,0};// new int[10] ;
    private static ArrayList<String> unvertraeglichkeitenListe=new ArrayList<String>();
    private static ArrayList<String> extraListe=new ArrayList<String>();


    private ProfileModel(){}

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
*/
    public static ArrayList<String> getKrankheitenListe() {
        return krankheitenListe;
    }

    public static void addKrankheit(String speechInput) {
        ProfileModel.krankheitenListe.add(speechInput);
    }

    public static void changeKrankheit (String speechInput, int index){
        krankheitenListe.add(index,speechInput);
    }

    public static void removeKrankheit(int index){
        krankheitenListe.remove(index);
    }

    public static int getDiabetes(){
        return krankheiten[0];
    }
    public static void setDiabetes(int trigger){
        krankheiten[0]=trigger;
    }

    public static int getMohrbus(){
        return krankheiten[1];
    }
    public static void setMorbus(int trigger){
        krankheiten[1]=trigger;
    }

    public static int getGicht(){
        return krankheiten[2];
    }
    public static void setGicht(int trigger){
        krankheiten[2]=trigger;
    }

    public static ArrayList<String> getUnvertraeglichkeitenListe() {
        return unvertraeglichkeitenListe;
    }

    public static void addUnvertraeglichkeit(String speechInput) {
        ProfileModel.unvertraeglichkeitenListe.add(speechInput);
    }

    public static void changeUnvertraeglichkeit (String speechInput, int index){
        unvertraeglichkeitenListe.add(index,speechInput);
    }

    public static void removeUnvertraeglichkeit(int index){
        unvertraeglichkeitenListe.remove(index);
    }


    public static int getLactose(){
        return unvertraeglichkeiten[0];
    }
    public static void setLactose(int trigger){
         unvertraeglichkeiten[0]=trigger;
    }

    public static int getGluten(){
        return unvertraeglichkeiten[1];
    }
    public static void setGluten(int trigger){
        unvertraeglichkeiten[1]=trigger;
    }

    public static int getFructose(){
        return unvertraeglichkeiten[2];
    }
    public static void setFructose(int trigger){
        unvertraeglichkeiten[2]=trigger;
    }

    public static int getEi(){
        return unvertraeglichkeiten[3];
    }
    public static void setEi(int trigger){
        unvertraeglichkeiten[3]=trigger;
    }

    public static int getFisch(){
        return unvertraeglichkeiten[4];
    }
    public static void setFisch(int trigger){
        unvertraeglichkeiten[4]=trigger;
    }

    public static int getPhenylalanin(){
        return unvertraeglichkeiten[5];
    }
    public static void setPhenylalanin(int trigger){
        unvertraeglichkeiten[5]=trigger;
    }

    public static int getHistamin(){
        return unvertraeglichkeiten[6];
    }
    public static void setHistamin(int trigger){
        unvertraeglichkeiten[6]=trigger;
    }

    public static int getSorbin(){
        return unvertraeglichkeiten[7];
    }
    public static void setSorbin(int trigger){
        unvertraeglichkeiten[7]=trigger;
    }

    public static int getSaccharose(){
        return unvertraeglichkeiten[8];
    }
    public static void setSaccharose(int trigger){
        unvertraeglichkeiten[8]=trigger;
    }

    public static int getErdnüsse(){
        return unvertraeglichkeiten[9];
    }
    public static void setErdnüsse(int trigger){
        unvertraeglichkeiten[9]=trigger;
    }

    public static ArrayList<String> getExtraListe() {
        return extraListe;
    }

    public static void addExtra(String speechInput) {
        ProfileModel.extraListe.add(speechInput);
    }

    public static void changeExtra (String speechInput, int index){
        extraListe.add(index,speechInput);
    }

    public static void removeExtra(int index){
        extraListe.remove(index);
    }



}
