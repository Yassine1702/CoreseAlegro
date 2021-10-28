package src;

import com.franz.agraph.repository.*;
import com.hp.hpl.jena.sparql.resultset.XMLInput;
import net.sf.saxon.expr.flwor.Tuple;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.URI;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.query.impl.DatasetImpl;
import org.eclipse.rdf4j.query.impl.SimpleDataset;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.mapdb.Bind;
import org.springframework.validation.BindingResult;
import src.Model.Alegro;

import javax.swing.plaf.nimbus.State;
import java.io.File;

import static org.apache.solr.common.params.CoreAdminParams.DATA_DIR;


public class application {
    public static void main(String[] args) throws Exception {

        /**
                for further Documentation check the official documentation  :
                https://franz.com/agraph/support/documentation/current/java-tutorial/java-tutorial.html

         */


        RepositoryResult<Statement> statements;
    final String USERNAME  ="TestUser";
    final String PASSWORD = "TestUserPwd";
    final String URL_SERVER ="http://localhost:10035";

    String human_ns = "http://www.inria.fr/2015/humans#";
    String humanInstance_ns = "http://www.inria.fr/2015/humans-instances#";

    Alegro alegro = new Alegro();
    alegro.setSERVER_URL(URL_SERVER);
    alegro.setUSERNAME(USERNAME);
    alegro.setPASSWORD(PASSWORD);
    AGRepositoryConnection conn = alegro.init();
    AGValueFactory vf =  conn.getValueFactory();

        // PRE REQUESITS : URI's, Properties and Literals
        IRI alice_exemple = vf.createIRI(humanInstance_ns,"alice_exemple");
        IRI bob_exemple = vf.createIRI(humanInstance_ns,"bob_exemple");

        IRI name = vf.createIRI(human_ns,"name");
        IRI age = vf.createIRI(human_ns,"age");
        IRI favourit_colour = vf.createIRI(human_ns,"fav_colour");

        Literal alice_exemple_name  = vf.createLiteral("Alice_Exemple",XMLSchema.STRING);
        Literal bob_exemple_name = vf.createLiteral("Bob_Exemple",XMLSchema.STRING);

        Literal alice_exemple_age  = vf.createLiteral("22", XMLSchema.INT);
        Literal bob_exemple_age = vf.createLiteral("23", XMLSchema.INT);

        Literal red = vf.createLiteral("red",XMLSchema.STRING);
        Literal blue = vf.createLiteral("blue",XMLSchema.STRING);





                        //      POPULATING THE TRIPLE STORE

        System.out.println("before insert : "+conn.size());

        // OPTION 01:
                       //conn.add(alice_exemple,name,alice_exemple_name);
                        //  conn.add(bob_exemple,name,bob_exemple_age);
                         // even if the subject URI exists alegro will still add it ! if needed verify that the triple doesn't exist already !

        //OPTION 02 :populate via statements

                         Statement stmnt1 = vf.createStatement(alice_exemple,age,alice_exemple_age);
                         Statement stmnt2 = vf.createStatement(bob_exemple,age,bob_exemple_age);
                        // conn.add(stmnt1);
                            //  conn.add(stmnt2);

                         Statement stmnt3 = vf.createStatement(alice_exemple,favourit_colour,red);
                       Statement stmnt4 = vf.createStatement(bob_exemple,favourit_colour,blue);
                        //  conn.add(stmnt3);
                          //   conn.add(stmnt4);
                          //  System.out.println("After insert : "+conn.size());


         // OPTION 03:  populating using a File !
                        File path1 = new File("C:\\Users\\a835928\\IdeaProjects\\CoreseAlegro\\src\\main\\resources\\human1.rdf");
                         IRI context = vf.createIRI(human_ns,"local");
                     conn.add(path1, human_ns, RDFFormat.RDFXML, context);


        /**  Context :

        usefull for backups : writing a specific context into a file (RDF or NTRIPLE) --> check if different formats are available
        Query Multiple Graphs : populate different graph in different contexts !
                                 getStatement(X,Y,Z,false) --> query all contexts by default
                                 getStatement(X,Y,Z,false,context1,context2) --> query context1 and context2 garphs only ! (the null graph wont be queried)
        */







                                            // RETREIVING DATA FROM THE TRIPLE STORE

        // Retreiving information from the databases
            /*statements= conn.getStatements(null,null,null,false);
            while (statements.hasNext()){
                System.out.println(statements.next());
            }*/

        // Retreiving alice's age ! N.B : the getStatements method return a triple
           /* statements= conn.getStatements(alice_exemple,age,null,false);
            while (statements.hasNext()){
                Statement stmt = statements.next();
                System.out.println(stmt);
                // System.out.println(stmt.getSubject()+"  "+stmt.getContext());
            }
            statements.close();*/





                                                    // Query using sparql !
      /*
        String query = "SELECT DISTINCT ?s where {?s ?p ?o}";
        TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL,query);
        TupleQueryResult queryResult = tupleQuery.evaluate();
        while(queryResult.hasNext()){
            BindingSet bindingSet = queryResult.next();
            System.out.println(bindingSet.getBinding("s"));
        }
        queryResult.close();

*/


                                                          // BACKUP OPTIONS  !
     /*                                     option 01:
     String output = "/tmp/temp.rdf";
     RDFXMLWriter rdfxmlfWriter = new RDFXMLWriter(FileOutputStream(output));
     conn.export(rdfxmlfWriter, context);
     output.write('\n');

                                             option 02:

     String output = "/tmp/temp.nt";
     NTriplesWriter ntriplesWriter = new NTriplesWriter(FileOutputStream(output));
     conn.export(ntriplesWriter, context);

        */
                                                //RDF4J Datasets !  ----> try to find out the benefits of using it !
        /**
         * we can parse our SPARQL Query evaluation into an RDF4J dataset
         * N.B : to query the null context we can give the query evaluator an empty Dataset --> check exemple 01
         * */


        // querying over everything !
/*
        String q = "SELECT ?s ?p ?o  WHERE{?s ?p ?o} ";
         SimpleDataset dataset =  new SimpleDataset();
        TupleQuery tupleQuery  = conn.prepareTupleQuery(QueryLanguage.SPARQL, q);
        tupleQuery.setDataset(dataset);
        TupleQueryResult result = tupleQuery.evaluate();
        System.out.println("\nQuery over the null context.");
        while (result.hasNext()) {
            System.out.println(result.next());
        }
*/
        //querying over specific context

        /*
        String q1 = "SELECT ?s ?p ?o ?g WHERE{GRAPH ?g {?s ?p ?o}} ";
        SimpleDataset ds =  new SimpleDataset();
        ds.addNamedGraph(context);
        TupleQuery tupleQuery1  = conn.prepareTupleQuery(QueryLanguage.SPARQL, q1);
        tupleQuery1.setDataset(ds);
        TupleQueryResult result1 = tupleQuery1.evaluate();
        System.out.println("\nQuery over the specific context.");
        while (result1.hasNext()) {
            System.out.println(result1.next());
        }
*/
                                                        // PARAMETERED REQUESTS

        /*
        String queryString = "select ?s ?p ?o where { ?s ?p ?o} ";
        TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
        tupleQuery.setBinding("s", alice_exemple);
        TupleQueryResult result = tupleQuery.evaluate();
        System.out.println("\nFacts about Alice:");
        while (result.hasNext()) {
            System.out.println(result.next());
        }
        result.close();

         */
    }
}
