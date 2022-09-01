package main.rice.node;

import main.rice.obj.APyObj;
import main.rice.obj.PyBoolObj;
import main.rice.obj.PyFloatObj;

import java.util.*;

public class PyFloatNode extends APyNode<PyFloatObj> {

    /**
     * Generates all valid PyObjs of type ObjType within the exhaustive domain.
     *
     * @return a set of PyObjs of type ObjType comprising the exhaustive domain
     */
    @Override
    public Set genExVals() {
        // getExDomain
        // create set
        Set<PyFloatObj> returnSet = new HashSet<>();
        // iterate through ex domain
        for (Number num : this.getExDomain()) {
            returnSet.add(new PyFloatObj(num.doubleValue()));
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
    public PyFloatObj genRandVal() {
        int size = this.getRanDomain().size();
        Random rando = new Random();
        int index = rando.nextInt(size);
        Number elem = this.getRanDomain().get(index);
        return new PyFloatObj(elem.doubleValue());
    }

}
