public class Application {

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
       //System.out.println(cart.getCustomerBaskets("sally@umuzi.org"));
        //System.out.println(cart.getAllCustomers());
       // System.out.println(cart.requiredStock().toString());
        System.out.println(cart.totalSpent("sally@umuzi.org"));
    }

}
