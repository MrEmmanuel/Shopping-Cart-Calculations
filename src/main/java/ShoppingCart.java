import DataModel.ShoppingBasket;
import com.google.gson.Gson;
import org.json.simple.JSONArray;

import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCart {

    public enum Status {DELIVERED, PAID, OPEN };
    ArrayList<ShoppingBasket> baskets = new ArrayList<ShoppingBasket>();

    public ShoppingCart(){
        this.baskets = JSONReadFromFile();
    }

    public List<ShoppingBasket> getCustomerBaskets(String email){
        List<ShoppingBasket> customerBaskets = this.baskets.stream()
                .filter(basket -> basket.getEmail().equals(email))
                .collect(Collectors.toList());
        return customerBaskets;
    }

    public ArrayList<ShoppingBasket> JSONReadFromFile(){
        try {
            ArrayList<ShoppingBasket> allBaskets = new ArrayList<ShoppingBasket>();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("src/data.json"));
            JSONArray jsonObject = (JSONArray) obj;
            for (Object json : jsonObject) {
                ShoppingBasket response = new Gson().fromJson(json.toString(), ShoppingBasket.class);
                allBaskets.add(response);
            }

            return allBaskets;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getAllCustomers(){
        ArrayList<String> customersList = new ArrayList<String>();
        for(int i=0; i< baskets.size(); i++){
            if(!customersList.contains(baskets.get(i).getEmail())){
                customersList.add(baskets.get(i).getEmail());
            }
        }
        return customersList;
    }

    public ArrayList<String> requiredStock(){
        return null;
    }

}
