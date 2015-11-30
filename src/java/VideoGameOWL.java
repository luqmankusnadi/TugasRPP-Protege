/**
 * Created by Luqman on 11/29/2015.
 */

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.*;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
        manager = OWLManager.createOWLOntologyManager();
        LoadOntology("D:/gamedecide.owl");
        CreateReasoner(ReasonerChoice.Hermit);
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
        vgo.Query("Video_Game that hasGenre only Action");
    }
}
