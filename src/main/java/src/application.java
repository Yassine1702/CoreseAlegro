package src;

import com.franz.agraph.repository.AGCatalog;
import com.franz.agraph.repository.AGRepository;
import com.franz.agraph.repository.AGRepositoryConnection;
import com.franz.agraph.repository.AGServer;
import src.Model.Alegro;



public class application {
    public static void main(String[] args) {

        // AGServer server = new AGServer(Alegro.SERVER_URL, Alegro.SUPER_USERNAME, Alegro.SUPER_PASSWORD);
        AGServer server = new AGServer(Alegro.SERVER_URL, Alegro.USERNAME, Alegro.PASSWORD);
        //System.out.println(server.listCatalogs());
        AGCatalog catalog = server.getRootCatalog();
        // System.out.println(catalog.getCatalogName());
        //  System.out.println(catalog.listRepositories());
        AGRepository repo =  catalog.createRepository("testRepo");
        repo.initialize();
        System.out.println(repo.isWritable());

        AGRepositoryConnection conn = repo.getConnection();
        //closeBeforeExit(conn);
        System.out.println("My repository : "+ repo.getRepositoryID()+ ", it constains : "+conn.size() );

    }
}
