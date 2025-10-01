package com.rbrg;

import org.junit.Test;

import java.util.ArrayList;

public class ApproachOneTest {
    @Test(expected = RuntimeException.class)
    public void givenApproachOneClassWhenTryingToInstantiateWithParametersThatDontFollowTheCriteriaThenAnExceptionIsThrown(){
        ArrayList<String> values = new ArrayList<>();
        values.add("1");
        values.add("2");
        ApproachOne approachOne = new ApproachOne(values,1,2);
    }
}
