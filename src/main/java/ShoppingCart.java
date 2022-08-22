import DataModel.BasketItems;
import DataModel.ShoppingBasket;
import com.google.gson.Gson;
import org.json.simple.JSONArray;

import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
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

    public List<HashMap<String, Object>> requiredStock(){
        int quantity = 0;
        List<HashMap<String, Object>> requiredItems = new ArrayList<>();
        HashMap<String, Integer> products = new HashMap<>();
        for(ShoppingBasket basket: baskets){
            if(basket.getStatus().equals(Status.PAID.toString())){
                for(BasketItems item: basket.getItems()){
                    if(products.get(item.getName())==null){
                        products.put(item.getName(), item.getQuantity());
                    } else{
                        quantity = products.get(item.getName());
                        products.put(item.getName(), quantity+item.getQuantity());
                    }
                }
            }

        }
        for(String key: products.keySet()){
            HashMap<String, Object> requiredProduct = new HashMap<>();
            requiredProduct.put("name", key);
            requiredProduct.put("quantity", products.get(key));
            requiredItems.add(requiredProduct);
        }

        return requiredItems;
    }

    public int totalSpent(String email){
        List<ShoppingBasket>  customerBaskets = getCustomerBaskets(email);
        int total = 0, quantity, price;
        for(ShoppingBasket basket: customerBaskets){
            BasketItems[] items = basket.getItems();
            if((basket.getStatus().equals(Status.DELIVERED.toString())) || (basket.getStatus().equals(Status.PAID.toString()))){
                for(BasketItems item: items){
                    price = item.getPrice();
                    quantity = item.getQuantity();
                    total += quantity*price;
                }
            }
        }
        return total;
    }
}
