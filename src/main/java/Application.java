public class Application {

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        System.out.println(cart.getCustomerBaskets("sally@umuzi.org"));
    }

}
