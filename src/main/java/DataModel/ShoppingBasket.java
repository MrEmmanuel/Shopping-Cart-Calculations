package DataModel;

import java.util.Arrays;

public class ShoppingBasket {

    private String email;
    private String status;
    private BasketItems[] items;

    ShoppingBasket(){}

    public String getEmail(){
        return this.email;
    }
    public String getStatus(){
        return this.status;
    }
    public BasketItems[] getItems(){
        return this.items;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setItems(BasketItems[] items){
        this.items=items;
    }

    @Override
    public String toString() {
        return "Shopping Basket{" +
                "email = '" + email + '\'' +
                ", status = '" + status + '\'' +
                ", items = " + Arrays.toString(items) +
                '}';
    }
}
