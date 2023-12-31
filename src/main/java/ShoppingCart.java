import DataModel.BasketItems;
import DataModel.ShoppingBasket;
import DataModel.TotalSpend;
import com.google.gson.Gson;
import org.json.simple.JSONArray;

import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class ShoppingCart {

    public enum Status {DELIVERED, PAID, OPEN };

    public List<ShoppingBasket> getCustomerBaskets(String email, ArrayList<ShoppingBasket> shoppingBaskets){

        List<ShoppingBasket> customerBaskets = shoppingBaskets.stream()
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

    public ArrayList<String> getAllCustomers(ArrayList<ShoppingBasket> shoppingBaskets){
        ArrayList<String> customersList = new ArrayList<String>();
        for(int i=0; i< shoppingBaskets.size(); i++){
            if(!customersList.contains(shoppingBaskets.get(i).getEmail())){
                customersList.add(shoppingBaskets.get(i).getEmail());
            }
        }
        return customersList;
    }

    public List<HashMap<String, Object>> requiredStock(ArrayList<ShoppingBasket> shoppingBaskets){
        int quantity = 0;
        List<HashMap<String, Object>> requiredItems = new ArrayList<>();
        HashMap<String, Integer> products = new HashMap<>();
        for(ShoppingBasket basket: shoppingBaskets){
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

    public int totalSpent(String email,ArrayList<ShoppingBasket> shoppingBaskets){
        List<ShoppingBasket>  customerBaskets = getCustomerBaskets(email, shoppingBaskets);
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

    public List<TotalSpend> topCustomers(ArrayList<ShoppingBasket> shoppingBaskets){
        List<TotalSpend> topCustomers= new ArrayList<>();
        List<String> allCustomers = getAllCustomers(shoppingBaskets);
        float totalPaid;
        for(String customer: allCustomers){
            totalPaid = totalSpent(customer, shoppingBaskets);
            TotalSpend customerSpend = new TotalSpend(customer, totalPaid);
            topCustomers.add(customerSpend);
        }
        Collections.sort(topCustomers, new Comparator<TotalSpend>() {
            @Override
            public int compare(TotalSpend first, TotalSpend second) {
                if(first.getTotal()> second.getTotal()) return -1;
                else if(first.getTotal()== second.getTotal()) return 0;
                return 1;
            }
        });
        return topCustomers;
    }

    public ArrayList<String> getCustomersWithOpenBaskets(ArrayList<ShoppingBasket> shoppingBaskets){
        ArrayList<String> openBaskets = new ArrayList<>();

        for(ShoppingBasket basket: shoppingBaskets){
            if (basket.getStatus().equals(Status.OPEN.toString()) && !openBaskets.contains(basket.getEmail())){
                openBaskets.add(basket.getEmail());
            }
        }
        return openBaskets;
    }
}
