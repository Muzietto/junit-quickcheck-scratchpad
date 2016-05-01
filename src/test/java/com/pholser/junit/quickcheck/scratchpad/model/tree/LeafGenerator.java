package com.pholser.junit.quickcheck.scratchpad.model.tree;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class LeafGenerator extends Generator<Leaf> {
    public LeafGenerator() {
        super(Leaf.class);
    }

    @Override public Leaf generate(SourceOfRandomness random, GenerationStatus status) {
        return new Leaf("random"
            /*gen().type(String.class).generate(random, status)*/);
    }

    @Override public boolean canRegisterAsType(Class<?> type) {
        return Tree.class.isAssignableFrom(type);
    }
}
