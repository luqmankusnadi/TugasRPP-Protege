/**
 * Created by Luqman on 11/29/2015.
 */

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.*;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import org.semanticweb.owlapi.*;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import uk.ac.manchester.cs.jfact.JFactFactory;


public class VideoGameOWL {
    private OWLOntologyManager manager;
    private OWLOntology ontology;
    public OWLReasoner reasoner;

    public enum ReasonerChoice {
        Hermit,
        Fact,
        Structural
    }

    public VideoGameOWL() {
        try {
            manager = OWLManager.createOWLOntologyManager();
            File file = new File(VideoGameOWL.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            LoadOntology(file.getParent()+"/gamedecide.owl");
            CreateReasoner(ReasonerChoice.Hermit);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void LoadOntology(String path) {
        File file = new File(path);
        try {
            ontology = manager.loadOntologyFromOntologyDocument(file);
            System.out.println("Ontology    : " + ontology.getOntologyID());
            System.out.println("Format      : " + manager.getOntologyFormat(ontology));
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }

    }

    public void Query(String query) {
        DLQueryPrinter printer;
        ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
        DLQueryPrinter dlQueryPrinter = new DLQueryPrinter(reasoner, shortFormProvider);
        dlQueryPrinter.askQuery(query);
        System.out.println();
    }


    public void CreateReasoner(ReasonerChoice rc) {
        OWLReasonerFactory factory;
        switch (rc) {
            case Structural:
                factory = new StructuralReasonerFactory();
                break;
            case Hermit:
                factory = new Reasoner.ReasonerFactory();
                break;
            case Fact:
                factory = new JFactFactory();
                break;
            default:
                factory = new StructuralReasonerFactory();
                break;
        }
        reasoner = factory.createReasoner(ontology);
    }


    public static void main(String [] args){
        VideoGameOWL vgo = new VideoGameOWL();
        vgo.CreateReasoner(ReasonerChoice.Fact);

        boolean isExit = false;
        String message = null;
        System.out.println("Selamat datang di Game Decide!");
        System.out.println("untuk keluar dari program masukkan perintah 'exit'.");
        System.out.println("Pilih mode input program :");
        System.out.println("Masukkan command 'query' untuk mode query");
        System.out.println("Masukkan command 'pilihan' untuk mode memilih pilihan");
        System.out.print(">");
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            message = bufferRead.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(message.equals("query")) {
            System.out.println("Masukkan langsung query anda, sebagai contoh query:");
            System.out.println("'Video_Game that hasGenre only Action'.");
            System.out.println("========================================================");
            System.out.println("");
            do {
                System.out.print(">");
                try {
                    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                    message = bufferRead.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (message.equals("exit")) {
                    isExit = true;
                } else {
                    vgo.Query(message);
                }
                //vgo.Query("Video_Game that hasGenre only Action");
            } while (!isExit);
        } else if (message.equals("pilihan")){
            do {
                System.out.print(">");
                try {
                    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                    message = bufferRead.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (message.equals("exit")) {
                    isExit = true;
                } else {
                    vgo.Query(message);
                }
            } while (!isExit);
        } else {
            System.out.println("masukkan perintah 'query' atau 'pilihan'!");
        }
        System.out.println("good bye!");
    }
}
