package no.kristiania;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Properties;

public class WebShopServer {

    private PGSimpleDataSource dataSource;
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private ProductDao productDao;

    public WebShopServer() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("task-manager.properties"));

        dataSource = new PGSimpleDataSource();

        dataSource.setUrl(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.username"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));

        Flyway.configure().dataSource(dataSource).load().migrate();
        productDao = new ProductDao(dataSource);
    }

    /***********************************************************************************************
    ************************************* main(String[] args ***************************************
    ***********************************************************************************************/

    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("hello World From WSS");
        new WebShopServer().run();
    } // end main args


    private boolean runIsTrue = true;
    private void run() throws IOException, SQLException {

        while(runIsTrue) {
            System.out.println("\t\t\t  Choose action:" +
                    "\n\t\t 1 - Register new Product " +
                    "\n\t\t 2 - list all product's" +
                    "\n\t\t 9 - quit");
            String action = input.readLine();

            switch (action.toLowerCase()) {

                case "1":
                    case "register":
                    newProduct();
                    break;

                case "2":
                    getProducts();
                    break;

                case "9":
                    case "stop":
                    case "quit":
                    case "q":
                    runIsTrue = false;
                    break;
            }
        }
        System.out.println("\n\t\t\thave a nice day! ");
    }

    private void newProduct() throws IOException, SQLException {
        Product product = new Product();
        System.out.println("please type the name of a new product");
        product.setName(input.readLine());
        productDao.insert(product);
    }
    private void getProducts() throws SQLException {
        System.out.println(productDao.listAll() + "\n");
    }




}// end WebShopServer
