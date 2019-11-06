package no.kristiania.webshop;

import no.kristiania.http.HttpServer;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class WebShopServer {

    private no.kristiania.http.HttpServer server;

    public WebShopServer(int port) throws IOException {
        Properties properties = new Properties();
       try (FileReader fileReader = new FileReader("task-manager.properties")){
           properties.load((fileReader));
       }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.username"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));

        Flyway.configure().dataSource(dataSource).load().migrate();

        server = new HttpServer(port);
        server.setAssertRoot("src/main/resources/webshop");
        server.addController("/api/products", new ProductController(new ProductDao(dataSource)));
    }

    /***********************************************************************************************
    ************************************* main(String[] args ***************************************
    ***********************************************************************************************/

    public static void main(String[] args) throws IOException, SQLException {
        new WebShopServer(8080).start();
    } // end main args

    private void start(){
        server.start();
    }
}// end WebShopServer
