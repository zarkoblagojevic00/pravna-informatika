package pravnainformatika.service;

import es.ucm.fdi.gaia.jcolibri.casebase.LinealCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.*;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import org.springframework.stereotype.Service;
import pravnainformatika.dto.CaseDTO;
import pravnainformatika.model.CaseDescription;
import pravnainformatika.service.interfaces.CaseBasedReasoningService;
import pravnainformatika.utils.CsvConnector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CaseBasedReasoningServiceImpl implements CaseBasedReasoningService, StandardCBRApplication {

    Connector _connector;  /** Connector object */
    CBRCaseBase _caseBase;  /** CaseBase object */
    NNConfig simConfig;  /** KNN configuration */

    @Override
    public List<String> start(CaseDescription caseDescription) {
        List<String> similarCases = new ArrayList<>();
        try {
            configure();

            preCycle();

            CBRQuery query = new CBRQuery();

            query.setDescription(caseDescription);

            similarCases = getSimilarCases(query);

            postCycle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return similarCases;
    }

    @Override
    public void configure() throws ExecutionException {
        _connector =  new CsvConnector();

        _caseBase = new LinealCaseBase();  // Create a Lineal case base for in-memory organization

        simConfig = new NNConfig(); // KNN configuration
        simConfig.setDescriptionSimFunction(new Average());  // global similarity function = average

        simConfig.addMapping(new Attribute("krivicnoDelo", CaseDescription.class), new Equal());
        simConfig.addMapping(new Attribute("nasilno", CaseDescription.class), new Equal());
        simConfig.addMapping(new Attribute("umisljaj", CaseDescription.class), new Equal());
        simConfig.addMapping(new Attribute("nepogoda", CaseDescription.class), new Equal());
        simConfig.addMapping(new Attribute("vrednost", CaseDescription.class), new Interval(5000));

        // Equal - returns 1 if both individuals are equal, otherwise returns 0
        // Interval - returns the similarity of two number inside an interval: sim(x,y) = 1-(|x-y|/interval)
        // Threshold - returns 1 if the difference between two numbers is less than a threshold, 0 in the other case
        // EqualsStringIgnoreCase - returns 1 if both String are the same despite case letters, 0 in the other case
        // MaxString - returns a similarity value depending of the biggest substring that belong to both strings
        // EnumDistance - returns the similarity of two enum values as the their distance: sim(x,y) = |ord(x) - ord(y)|
        // EnumCyclicDistance - computes the similarity between two enum values as their cyclic distance
        // Table - uses a table to obtain the similarity between two values. Allowed values are Strings or Enums. The table is read from a text file.
        // TabularSimilarity - calculates similarity between two strings or two lists of strings on the basis of tabular similarities
    }

    @Override
    public CBRCaseBase preCycle() throws ExecutionException {
        _caseBase.init(_connector);
        java.util.Collection<CBRCase> cases = _caseBase.getCases();
//		for (CBRCase c: cases)
//			System.out.println(c.getDescription());
        return _caseBase;
    }

    @Override
    public void cycle(CBRQuery query) throws ExecutionException {
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
        eval = SelectCases.selectTopKRR(eval, 5);
        System.out.println("Retrieved cases:");
        for (RetrievalResult nse : eval)
            System.out.println(nse.get_case().getDescription() + " -> " + nse.getEval());
    }

    public List<String> getSimilarCases(CBRQuery query) throws ExecutionException {
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
        eval = SelectCases.selectTopKRR(eval, 5);
        List<String> similarCases = new ArrayList<>();
        for (RetrievalResult nse : eval)
            similarCases.add(nse.get_case().getDescription() + " -> " + nse.getEval());
        return similarCases;
    }

    @Override
    public void postCycle() throws ExecutionException {

    }
}
