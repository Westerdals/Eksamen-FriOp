package Webshop;

import com.sun.net.httpserver.HttpServer;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class WebShopServer {
    private HttpServer server;

    public WebShopServer(int port){
        Properties properties = new Properties();
        try (FileReader fileReader = new FileReader("task-manager.properties")){
            properties.load(fileReader);

            PGSimpleDataSource dataSource = new PGSimpleDataSource();
            dataSource.setUrl(properties.getProperty("dataSource.url"));
            dataSource.setUser(properties.getProperty("dataSource.username"));
            dataSource.setPassword(properties.getProperty("dataSource.password"));

            Flyway.configure().dataSource(dataSource).load().migrate();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}// end WebShopServer
