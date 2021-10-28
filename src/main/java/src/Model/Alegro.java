package src.Model;

import com.franz.agraph.repository.AGCatalog;
import com.franz.agraph.repository.AGRepository;
import com.franz.agraph.repository.AGRepositoryConnection;
import com.franz.agraph.repository.AGServer;

public class Alegro {
    //static public final String SUPER_USERNAME = "YassineAtos";
    //static public final String SUPER_PASSWORD ="YassineAtosPwd";
    //public static final String SERVER_URL = "http://localhost:10035";
    //private static final String CATALOG_ID = "scratch";
    //private static final String REPOSITORY_ID = "javatutorial";
    //static public final String USERNAME = "TestUser";
    //static public final String PASSWORD = "TestUserPwd";
    private  String SERVER_URL;
    private String USERNAME;
    private String PASSWORD;
    private  String CATALOG_ID;
    private  String REPOSITORY_ID = "testRepo";
    private AGCatalog catalog;
    private AGRepository repo;
    private static AGRepositoryConnection conn;

    public AGRepositoryConnection init () throws Exception{
        if(this.conn == null ){
            if(SERVER_URL.isEmpty() || USERNAME.isEmpty() || PASSWORD.isEmpty() ||
                    SERVER_URL.isBlank() || USERNAME.isBlank() || PASSWORD.isBlank() )
                {
                throw new Exception("Non valid Connection Parameters  ! ");
                }

            AGServer server = new AGServer(this.SERVER_URL, this.USERNAME, this.PASSWORD);
            this.catalog = server.getRootCatalog();
            this.repo =  catalog.createRepository(this.REPOSITORY_ID);
            this.repo.initialize();
            this.conn = repo.getConnection();
            System.out.println("Connected succesfully, Repository : "+ repo.getRepositoryID()+ ", it constains : "+conn.size() );

        }
        return this.conn;
    }

    public void setSERVER_URL(String SERVER_URL) {
        this.SERVER_URL = SERVER_URL;
    }
    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }
    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

}
