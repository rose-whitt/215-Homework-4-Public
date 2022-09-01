package main.rice.concisegen;

import main.rice.test.TestCase;
import main.rice.test.TestResults;

import java.util.*;

public class ConciseSetGenerator {

    /**
     * Finds an approximately minimal set of test cases that "cover" or "hit"
     * all of the known buggy implementations, i.e. implementations that failed
     * at least one test in the base test set.
     *
     * @param results - a TestResults object to find the minimal hitting set of
     * @return - hittingSet - the minimum hitting set;
     *          set of TestCase objects that "covers" all known buggy implementations
     */
    public static Set<TestCase> setCover(TestResults results) {
        // only one test is needed if all files failed on the given test
        // H -> 0
        Set<TestCase> hittingSet = new HashSet<>();

        // deep copy allCases
        List<TestCase> allCasesCopy = new ArrayList<>();
        int i = 0;
        while (results.getTestCase(i) != null) {
            allCasesCopy.add(results.getTestCase(i));
            i++;
        }

        // deep copy caseToFiles
        List<Set<Integer>> caseToFilesCopy = new ArrayList<>();
        for (Set<Integer> set : results.getCaseToFiles()) {
            Set<Integer> setCopy = new HashSet<>();
            for (Integer num : set) {
                setCopy.add(num);
            }
            caseToFilesCopy.add(setCopy);
        }

        // deep copy wrongSet
        Set<Integer> wrongSetCopy = new HashSet<>();
        for (Integer num : results.getWrongSet()) {
            wrongSetCopy.add(num);
        }

        // create a new TestResults object to guarantee no manipulation of input
        TestResults copyResults = new TestResults(allCasesCopy, caseToFilesCopy, wrongSetCopy);


        List<Set<Integer>> familyCaseToFiles = copyResults.getCaseToFiles();
        int index = 0;
        while (wrongSetCopy.size() != 0) {

            // heuristic to optimize
            List heuristic = heuristicSetCover(familyCaseToFiles);

            // find caseToFile set with the largest set size
            Set<Integer> biggestSet = new HashSet<>();
            biggestSet = (Set<Integer>) heuristic.get(1);

            // (1) add to hitting set
            hittingSet.add(copyResults.getTestCase(familyCaseToFiles.indexOf(biggestSet)));

            // (2) remove all files contained in this case from wrongSet
            // deep copy
            Set<Integer> wrongSetCopyTemp = new HashSet<>();
            for (Integer num : wrongSetCopy) {
                wrongSetCopyTemp.add(num);
            }
            // remove all files contained in this case from wrongSet
            for (Integer num : wrongSetCopy) {
                if (biggestSet.contains(num)) {
                    wrongSetCopyTemp.remove(num);
                }
            }

            // set wrongSetCopy to the manipulated wrongSet
            wrongSetCopy = wrongSetCopyTemp;

            // (3) remove every file from every set in caseToFiles except the ones in wrongSet
            // deep copy caseToFilesCopy
            List<Set<Integer>> caseToFilesCopyTemp = new ArrayList<>();
            for (Set<Integer> set : familyCaseToFiles) {
                Set<Integer> setCopy = new HashSet<>();
                for (Integer num : set) {
                    setCopy.add(num);
                }
                caseToFilesCopyTemp.add(setCopy);
            }
            // remove every file
            int indexSet = 0;
            for (Set<Integer> set : familyCaseToFiles) {
                for (Integer num : set) {
                    if (!wrongSetCopy.contains(num)) {
                        caseToFilesCopyTemp.get(indexSet).remove(num);
                    }
                }
                indexSet++;
            }
            // set familyCaseToFiles to manipulated deep copy
            familyCaseToFiles = caseToFilesCopyTemp;

            // increment index
            index++;

        }

        return hittingSet;
    }

    /**
     * Helper method for setCover that runs the heuristic.
     * The heuristic, in this case, is finding the largest set of Integers
     *
     * @param caseToFilesCopy - a copy of the caseToFiles List of Sets of Integers from setCover
     * @return - heuristic - a list where the first element is the case number of the largest set
     *                          and the second element is the largest set of integers
     */
    public static List heuristicSetCover(List<Set<Integer>> caseToFilesCopy) {

        int caseLargestSize = 0;
        int caseIndex = 0;
        int caseNum = 0;
        // iterate over caseToFile
        for (Set<Integer> set : caseToFilesCopy) {
            // find largest set (if equal, returns last largest)
            if (set.size() >= caseLargestSize) {
                caseLargestSize = set.size();
                caseNum = caseIndex;
            }
            caseIndex++;
        }
        // return this set and the case number (index)
        List heuristic = new ArrayList();
        heuristic.add(caseNum);
        heuristic.add(caseToFilesCopy.get(caseNum));
        return heuristic;
    }

}
