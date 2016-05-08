package univie.ac.at.meineinkaufswagerl.model;

import java.util.ArrayList;

/**
 * Created by Wilson on 08.05.2016.
 */
public class ProfileModel extends UserModel {

     /*
    Erstellen des Arrays um die Krankheiten zu speichern.
    Position 1: Diabetes
    Position 2: Morbus Crohn
    Position 3: Gicht
     */

    private int[] krankheiten = new int[5];

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

     private int[] unvertraeglichkeiten = new int[10] ;

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

    public int getDiabetes(){
        return krankheiten[0];
    }
    public void setDiabetes(){
        krankheiten[0]=1;
    }

    public int getMohrbus(){
        return krankheiten[1];
    }
    public void setMorbus(){
        krankheiten[1]=1;
    }

    public int getGicht(){
        return krankheiten[2];
    }
    public void setGicht(){
        krankheiten[2]=1;
    }



    public int getLactose(){
        return unvertraeglichkeiten[0];
    }
    public void setLactose(){
         unvertraeglichkeiten[0]=1;
    }

    public int getGluten(){
        return unvertraeglichkeiten[1];
    }
    public void setGluten(){
        unvertraeglichkeiten[1]=1;
    }

    public int getFructose(){
        return unvertraeglichkeiten[2];
    }
    public void setFructose(){
        unvertraeglichkeiten[2]=1;
    }

    public int getEi(){
        return unvertraeglichkeiten[3];
    }
    public void setEi(){
        unvertraeglichkeiten[3]=1;
    }

    public int getFisch(){
        return unvertraeglichkeiten[4];
    }
    public void setFisch(){
        unvertraeglichkeiten[4]=1;
    }

    public int getPhenylalanin(){
        return unvertraeglichkeiten[5];
    }
    public void setPhenylalanin(){
        unvertraeglichkeiten[5]=1;
    }

    public int getHostamin(){
        return unvertraeglichkeiten[6];
    }
    public void setHostamin(){
        unvertraeglichkeiten[6]=1;
    }

    public int getSorbin(){
        return unvertraeglichkeiten[7];
    }
    public void setSorbin(){
        unvertraeglichkeiten[7]=1;
    }

    public int getSaccharose(){
        return unvertraeglichkeiten[8];
    }
    public void setSaccharose(){
        unvertraeglichkeiten[8]=1;
    }

    public int getErdnüsse(){
        return unvertraeglichkeiten[9];
    }
    public void setErdnüsse(){
        unvertraeglichkeiten[9]=1;
    }






}
