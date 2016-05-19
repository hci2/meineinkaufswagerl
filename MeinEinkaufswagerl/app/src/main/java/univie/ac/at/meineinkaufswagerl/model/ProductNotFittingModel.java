package univie.ac.at.meineinkaufswagerl.model;

/**
 * Created by Philipp on 5/19/2016.
 */
public class ProductNotFittingModel {

    private String[][]  productUnfitList;

    public ProductNotFittingModel() {
        productUnfitList = new String[][]{
                //TODO: Überlegen zu welchen Produkten welche Unverträglichkeiten und Krankheiten sich nicht vertragen
                {"Milch", "Lactose"},
                {"Brot", "Erdnüsse"},
                {"Joghurt", "Lactose","Saccharose"},
                {"Karotten", "Histamin"},
                {"Äpfel", "Fructose"},
                {"Cola", "Saccharose","Diabetes"},
                {"Waschmittel", "Rote Kreuz"},
                {"Zahnpasta", "Care"},
                {"Duschgel", "Essen auf Rädern"},
                {"Staubsauger", "Life Call"}};

        /*
        intolerances.add("Lactose");
        intolerances.add("Gluten");
        intolerances.add("Fructose");
        intolerances.add("Ei");
        intolerances.add("Fisch");
        intolerances.add("Phenylalanin");
        intolerances.add("Histamin");
        intolerances.add("Sorbin");
        intolerances.add("Saccharose");
        intolerances.add("Erdnüsse");

        diseases.add("Diabetes");
        diseases.add("Morbus Crohn");
        diseases.add("Gicht");
         */

    }

    public String[][] getProductUnfitList() {
        return productUnfitList;
    }

    public void setProductUnfitListCharityListVienna(String[][] productUnfitList) {
        this.productUnfitList = productUnfitList;
    }

}
