package src;

import com.franz.agraph.repository.*;
import com.hp.hpl.jena.sparql.resultset.XMLInput;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import src.Model.Alegro;



public class application {
    public static void main(String[] args) throws Exception {
    final String USERNAME  ="TestUser";
    final String PASSWORD = "TestUserPwd";
    final String URL_SERVER ="http://localhost:10035";
    String human_ns = "http://www.inria.fr/2015/humans#";
    String humanInstance_ns = human_ns+"-instances# ";

    Alegro alegro = new Alegro();
    alegro.setSERVER_URL(URL_SERVER);
    alegro.setUSERNAME(USERNAME);
    alegro.setPASSWORD(PASSWORD);
    AGRepositoryConnection conn = alegro.init();
    AGValueFactory vf =  conn.getValueFactory();

    // inserting individuals
        IRI alice_exemple = vf.createIRI(humanInstance_ns,"alice_exemple");
        IRI bob_exemple = vf.createIRI(humanInstance_ns,"bob_exemple");
        IRI name = vf.createIRI(human_ns,"name");
        IRI age = vf.createIRI(human_ns,"age");

        Literal alice_exemple_name  = vf.createLiteral("Alice_Exemple");
        Literal bob_exemple_name = vf.createLiteral("Bob_Exemple");

        Literal alice_exemple_age  = vf.createLiteral("22", XMLSchema.INT);
        Literal bob_exemple_age = vf.createLiteral("23", XMLSchema.INT);

        System.out.println("before insert : "+conn.size());
        // even if the subject URI exists alegro will still add it ! beware if needed verify that the triple doesn't exists already !
        conn.add(alice_exemple,name,alice_exemple_name);
        conn.add(bob_exemple,name,bob_exemple_age);

        //2nd way to populate via statements
        Statement stmnt1 = vf.createStatement(alice_exemple,age,alice_exemple_age);
        Statement stmnt2 = vf.createStatement(bob_exemple,age,bob_exemple_age);
        conn.add(stmnt1);
        conn.add(stmnt2);
        System.out.println("After insert : "+conn.size());
        conn.close();

    }
}
