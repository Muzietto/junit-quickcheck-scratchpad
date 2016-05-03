package com.pholser.junit.quickcheck.scratchpad.model.tree;

import java.util.AbstractMap.SimpleImmutableEntry;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.pholser.junit.quickcheck.scratchpad.model.tree.visitor.TreeDeepestLeafVisitor;
import com.pholser.junit.quickcheck.scratchpad.model.tree.visitor.TreeDepthVisitor;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(JUnitQuickcheck.class)
public class TreePropertyTest {
    @Property
    public void deepestLeafConstrained(@From(TreeMaker.class) @Depth(min = 8, max = 10) Tree t) {

        TreeDeepestLeafVisitor visitor = new TreeDeepestLeafVisitor();

        @SuppressWarnings("unchecked")
        SimpleImmutableEntry<String, Integer> result = (SimpleImmutableEntry<String, Integer>) t.accept(visitor);

        assertThat(result.getValue(), lessThanOrEqualTo(10));
    }

    @Property
    public void depthConstrained(@From(TreeMaker.class) @Depth(min = 6, max = 10) Tree t) {

        TreeDepthVisitor visitor = new TreeDepthVisitor();

        int result = (int) t.accept(visitor);

        assertThat(result, lessThanOrEqualTo(10));
    }
}
