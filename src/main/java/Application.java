import DataModel.ShoppingBasket;

import java.util.ArrayList;

public class Application {

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        ArrayList<ShoppingBasket> baskets =cart.JSONReadFromFile();

        System.out.println("Get Customer Baskets ");
        System.out.println(cart.getCustomerBaskets("sally@umuzi.org", baskets)  + "\n");

        System.out.println("Get All Customers");
        System.out.println(cart.getAllCustomers(baskets) + "\n");

        System.out.println("Get Required Stock");
        System.out.println(cart.requiredStock(baskets) + "\n");

        System.out.println("Get Total Spent by a Specific Customer");
        System.out.println(cart.totalSpent("sally@umuzi.org", baskets) + "\n");

        System.out.println("Get All Top Customers");
        System.out.println(cart.topCustomers(baskets) + "\n");

        System.out.println("Get Customer With Open Baskets");
        System.out.println(cart.getCustomersWithOpenBaskets(baskets) + "\n");
    }

}
