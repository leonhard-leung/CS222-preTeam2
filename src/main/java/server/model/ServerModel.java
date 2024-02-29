package server.model;

import server.controller.ServerController;
import server.model.listeners.ClientObserver;
import shared.*;
import util.XMLUtility;
import util.exception.AccountAlreadyLoggedIn;
import util.exception.AccountExistsException;
import util.exception.InvalidCredentialsException;
import util.exception.OutOfStockException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**Server Model class holds the data that will eventually be accessed by all the clients.
 * The idea is, when a client places an order,it will update the menu of this server model (e.g. by decrementing it)
 * and that the updated menu will be visible to other clients.
 * <p>
 * There is a predefined menu products for food, beverage and orders which will be loaded from xml files when the server model is initialized.
 */
public class ServerModel {
    private final List<ServerController> serverControllers = new ArrayList<>();
    private final List<ClientObserver> observers = new ArrayList<>();
    private HashMap<String, Food> foodMenu; //Hashmap for faster searching
    private HashMap<String, Beverage> beverageMenu;
    private final List<Customer> customerAccountList;
    private List<Order> orderList; //List for listing only orders
    private final List<String> userLoggedIn;

    public void addObserver(ClientObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        System.out.println("Notifying Controllers");
        System.out.println(observers.size());
        for (ClientObserver observer : observers) {
            observer.onDataChanged();
        }
    }

    public void registerServerController(ServerController controller) {
        serverControllers.add(controller);
    }

    public List<ServerController> getActiveServerControllers() {
        return serverControllers;
    }

    /**The constructor of the server model should load the predefined menu products*/
    public ServerModel() {
        foodMenu = (HashMap<String, Food>) XMLUtility.loadXMLData(new File("src/main/resources/data/food_menu.xml"));
        beverageMenu = (HashMap<String, Beverage>) XMLUtility.loadXMLData(new File("src/main/resources/data/beverage_menu.xml"));
        customerAccountList = (List<Customer>) XMLUtility.loadXMLData(new File("src/main/resources/data/customer_account_list.xml"));
        orderList = (List<Order>) XMLUtility.loadXMLData(new File("src/main/resources/data/order_list.xml"));

        //set up
        userLoggedIn = new ArrayList<>();
    }

    /**Process client orders and updates the food menu if necessary
     * Algorithm
     * 1. From the list of products from order, check if there are available for it.
     * 2. If there are non, throw an exception
     * 3. If there are available products, then decrement it from the product quantity
     * 4. Save the order to orders list
     * 5. Return the successfulOrder
     * 2. */
    public Order processOrder(Order order) throws Exception {
        checkAvailability(order); //order that is not successful
        updateMenu(order);

        Order successfulOrder = new Order(order); //if it reaches here, means the order is successful

        // therefore add to orderList for the admin
        orderList.add(successfulOrder);

        //then add the order in the orderList of the customer in the customerList
        for (Customer customer: customerAccountList) {
            if (customer.getUsername().equals(order.getCustomer().getUsername())){
                customer.getOrderHistory().add(order); //add the successful order to the customerOrderHistory
            }
        }

        System.out.println(foodMenu);
        return successfulOrder;
    }

    /**This method update the product in the menu there are available products. Synchronization is handled here already.
     * @throws Exception if the product in the menu is out of stock */
    private void updateMenu(Order order) throws Exception {
        for (Product product: order.getOrders()) {
            if (product instanceof Food food){
                //cast it
                int orderQuantity = food.getQuantity();

                Food productListed = foodMenu.get(food.getName());

                //updates the menu
                productListed.updateQuantity(orderQuantity); // this throws an exception
            }else if (product instanceof Beverage beverage){

                Beverage productListed = beverageMenu.get(beverage.getName());

                //for each variation of the beverage order
                //small = 10
                //medium = 2
                // large = 3
                for (String variation: beverage.getSizeQuantity().keySet()) {
                    productListed.updateQuantity(variation, beverage.getSizeQuantity().get(variation));
                }
            }
        }
    }

    /**This method checks the availability of products in the menu based on the customer's order.
     * @param order, client of the order
     * @throws Exception if the product menu is out of stock. */
    private synchronized void checkAvailability(Order order) throws Exception{
        for (Product product: order.getOrders()) {
            if (product instanceof Food food){ //check first what type of product
                //cast it

                if (food.getQuantity() > foodMenu.get(food.getName()).getQuantity()){
                    //checks if the order food quantity is greater than what is on the menu
                    throw new OutOfStockException("Out of stock");
                }
            }else if (product instanceof Beverage beverage){

                //check if all variation
                //small = 10
                //medium = 2
                // large = 3
                for (String variation: beverage.getSizeQuantity().keySet()) {
                    int variationQuantity = beverage.getVariationQuantity(variation); //small = 10;
                    int variationAvailableOnMenu = beverageMenu.get(beverage.getName()).getVariationQuantity(variation);
                    if (variationQuantity > variationAvailableOnMenu){
                        throw new OutOfStockException("Out of stock");
                    }
                }
            }
        }
    }

    /**This method handles all the signup processes from the client.
     * @param customerSignup new account that tries to create an account.
     *
     * Algorithm:
     * 1. Check the list of customers if the customer that that tries to sign up already exists.
     * 2. If exists, throws an exception  "Account exists"
     * 3. If not, create a new Customer out of a customer and add it to the list*/
    public synchronized boolean processSignUp(Customer customerSignup) throws Exception{
        String signUpUsername = customerSignup.getUsername();

        for (Customer customerAccount: customerAccountList) {
            if (customerAccount.getUsername().equals(signUpUsername)){
                throw new AccountExistsException("Account exists");
            }
        }

        Customer customer =  new Customer(customerSignup); //this creates a customer that has orderHistory in it
        customerAccountList.add(customer);
        return true;
    }

    /**This method handles all the login processes from the client.
     * @param username username
     * @param password password
     * @return the objects to be loaded in client side
     * @throws Exception when login credentials is invalid
     *
     * Algorithm:
     * 1. Check all customer in customerAccountList and see if it matches username and password.
     * 2. If not, thrown an exception called "invalid credentials"
     * 3. if account exist, get the accounts from the account list, get the foodMenu and beverageMenu
     * return foodMenu, beverageMenu and customer
     */
    public Object[] processLogin(String username, String password) throws Exception{
        for (Customer customerAccount: customerAccountList) {
            //account
            if (customerAccount.getUsername().equals(username) && customerAccount.getPassword().equals(password)){
                if (!userLoggedIn.contains(String.valueOf(username.hashCode()))){
                    //adds the value to the user logged in
                    userLoggedIn.add(String.valueOf(username.hashCode()));

                    HashMap<String, Food> clientFoodMenuToLoad = new HashMap<>(foodMenu);
                    HashMap<String, Beverage> clientBeverageMenuToLoad = new HashMap<>(beverageMenu);

                    //return to controller
                    return new Object[]{customerAccount, clientFoodMenuToLoad, clientBeverageMenuToLoad};
                }else {
                    throw new AccountAlreadyLoggedIn("Account already logged in");
                }
            }
        }
        throw new InvalidCredentialsException("Invalid credentials");
    }

    /**This removes the client from the server if the client logged out already*/
    public void processLogout(String clientID){
        this.userLoggedIn.remove(clientID);
    }

    /**This method updates the reviews for the products*/
    public void processReview(List<Product> ratedProducts){
        for (Product product : ratedProducts) {
            String productName = product.getName();
            double review = product.getReview();
            if (product instanceof Food){
                foodMenu.get(productName).updateReview(review);
            }else{
                beverageMenu.get(productName).updateReview(review);
            }
        }
    }

    public void setFoodMenu(HashMap<String, Food> foodMenu) {
        this.foodMenu = foodMenu;
    }

    public void setBeverageMenu(HashMap<String, Beverage> beverageMenu) {
        this.beverageMenu = beverageMenu;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public HashMap<String, Food> getFoodMenu() {
        return foodMenu;
    }

    public HashMap<String, Beverage> getBeverageMenu() {
        return beverageMenu;
    }

    public List<Customer> getCustomerAccountList() {
        return customerAccountList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }
}