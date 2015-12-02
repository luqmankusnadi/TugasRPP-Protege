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

import javax.management.Query;


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
            LoadOntology(file.getParent() + "/resources/gamedecide.owl");
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
        boolean genre;
        boolean mode;
        boolean platform;
        boolean isExit = false;
        String message = null;
        String query = null;
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
            query = "Video_Game replace";
            do {
                System.out.println("Apakah anda ingin kategori genre?(Y/N): ");
                System.out.print(">");
                message = getInput();
                if(message.toLowerCase().equals("y")){
                    System.out.println("Genre Action?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                       query = query.replace("replace","and hasGenre only Action replace");
                    }
                    System.out.println("Genre Shooter?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasGenre only Shooter replace");
                    }
                    System.out.println("Genre Action Adventure?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasGenre only Action_Adventure replace");
                    }
                    System.out.println("Genre Sports?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasGenre only Sports replace");
                    }
                    System.out.println("Genre Role Playing?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasGenre only Role_Playing replace");
                    }
                    System.out.println("Genre Simulation?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasGenre only Simulation replace");
                    }
                    System.out.println("Genre Strategy?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasGenre only Strategy replace");
                    }
                }
                System.out.println("Apakah anda ingin memilih kategori Platform?(Y/N): ");
                System.out.print(">");
                message = getInput();
                if(message.toLowerCase().equals("y")){
                    System.out.println("Platform PS2?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.equals("y")){
                        query = query.replace("replace","and hasPlatform only PS2 replace");
                    }
                    System.out.println("Platform PS3?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasPlatform only PS3 replace");
                    }
                    System.out.println("Platform PS4?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasPlatform only PS4 replace");
                    }
                    System.out.println("Platform XBOX 360?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasPlatform only XBOX_360 replace");
                    }
                    System.out.println("Platform XBOX One?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasPlatform only XBOX_One replace");
                    }
                    System.out.println("Platform Windows?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasPlatform only Microsoft_Windows replace");
                    }
                    System.out.println("Platform OS X?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasPlatform only OS_X replace");
                    }
                    System.out.println("Platform Linux?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasPlatform only Linux replace");
                    }

                }
                System.out.println("Apakah anda ingin kategori mode game?(Y/N): ");
                System.out.print(">");
                message = getInput();
                if(message.toLowerCase().equals("y")){
                    System.out.println(" Mode SinglePlayer?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasMode only SinglePlayer replace");
                    }
                    System.out.println(" Mode MultiPlayer?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasMode only MultiPlayer replace");
                    }
                    System.out.println(" Mode SinglePlayerOnline?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasMode only SinglePlayerOnline replace");
                    }
                    System.out.println(" Mode MultiPlayerOnline?(Y/N): ");
                    System.out.print(">");
                    message = getInput();
                    if(message.toLowerCase().equals("y")){
                        query = query.replace("replace","and hasMode only MultiPlayerOnline replace");
                    }
                }
                query = query.replace("replace","");
                System.out.println(query);
                vgo.Query(query);
                isExit = true;
            } while (!isExit);
        } else {
            System.out.println("masukkan perintah 'query' atau 'pilihan'!");
        }
        System.out.println("good bye!");
    }

    public static String getInput(){
        String message = null;
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            message = bufferRead.readLine();
            return message;

        } catch (IOException e) {
            e.printStackTrace();
            return message;
        }
    }
    //public static void convertToQuery()
}
