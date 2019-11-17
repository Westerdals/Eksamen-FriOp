package no.kristiania.webshop;

import no.kristiania.http.HttpServer;
import no.kristiania.webshop.AssignedProjects.AssignController;
import no.kristiania.webshop.AssignedProjects.AssignDao;
import no.kristiania.webshop.Projects.ProjectController;
import no.kristiania.webshop.Projects.ProjectDao;
import no.kristiania.webshop.members.MemberController;
import no.kristiania.webshop.members.MemberDao;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class WebShopServer {
    public static void main(String[] args) throws IOException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        Properties properties = new Properties();
        try (FileReader reader = new FileReader("task-manager.properties")) {
            properties.load(reader);
        }
        dataSource.setUrl(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.username"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));

        //Flyway.configure().dataSource(dataSource).load().clean();
        Flyway.configure().dataSource(dataSource).load().migrate();

        HttpServer server = new HttpServer(8080);
        server.setAssertRoot("src/main/resources/webshop/");
        server.addController("/api/members", new MemberController(new MemberDao(dataSource)));
        server.addController("/api/projects", new ProjectController(new ProjectDao(dataSource)));
        server.addController("/api/jointables", new AssignController(new AssignDao(dataSource)));
        server.start();
    }
}// end WebShopServer
