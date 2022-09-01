package main.rice.node;

import main.rice.obj.APyObj;
import main.rice.obj.PyFloatObj;
import main.rice.obj.PyIntObj;

import java.util.*;

public class PyIntNode extends APyNode<PyIntObj> {
    /**
     * Generates all valid PyObjs of type ObjType within the exhaustive domain.
     *
     * @return a set of PyObjs of type ObjType comprising the exhaustive domain
     */
    @Override
    public Set genExVals() {
        // getExDomain
        // create set
        Set<PyIntObj> returnSet = new HashSet<>();
        // iterate through ex domain
        for (Number num : this.getExDomain()) {
            returnSet.add(new PyIntObj(num.intValue()));

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
    public PyIntObj genRandVal() {
        int size = this.getRanDomain().size();
        Random rando = new Random();
        int index = rando.nextInt(size);
        Number elem = this.getRanDomain().get(index);
        return new PyIntObj(elem.intValue());
    }

}
