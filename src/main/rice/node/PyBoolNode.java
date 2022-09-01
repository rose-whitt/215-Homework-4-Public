package main.rice.node;

import main.rice.obj.APyObj;
import main.rice.obj.PyBoolObj;

import java.util.*;

public class PyBoolNode extends APyNode<PyBoolObj> {
    /**
     * Generates all valid PyObjs of type ObjType within the exhaustive domain.
     *
     * @return a set of PyObjs of type ObjType comprising the exhaustive domain
     */
    @Override
    public Set genExVals() {
        // getExDomain
        // create set
        Set<PyBoolObj> returnSet = new HashSet<>();
        // iterate through ex domain
        for (Object num : this.getExDomain()) {
            if ((int) num == 0) {
                // make each number a Python object of ObjType
                // add to set
                returnSet.add(new PyBoolObj(false));
            } else {
                returnSet.add(new PyBoolObj(true));
            }
        }
        // return the set
        return returnSet;
    }

    /**
     * Generates a single valid PyObj of type ObjType within the random domain.
     *
     * @return a single PyObj of type ObjType selected from the random domain
     */
    @Override
    public PyBoolObj genRandVal() {
        if (getRanDomain().size() == 1) {
            if ((int)getRanDomain().get(0) == 0) {
                return new PyBoolObj(false);
            } else {
                return new PyBoolObj(true);
            }
        }
        Random rando = new Random();
        boolean nextBool = rando.nextBoolean();
        return new PyBoolObj(nextBool);
    }

}
