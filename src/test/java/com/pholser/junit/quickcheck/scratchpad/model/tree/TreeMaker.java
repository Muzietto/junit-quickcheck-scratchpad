package com.pholser.junit.quickcheck.scratchpad.model.tree;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.internal.Ranges;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import static com.pholser.junit.quickcheck.internal.Ranges.*;
import static com.pholser.junit.quickcheck.scratchpad.model.tree.TreeKeys.*;

/**
 -- TreeMaker can be configured with an annotation @Depth that is itself
 marked as @GeneratorConfiguration. When TreeMaker is called upon to
 produce a value for a parameter of type Tree that is also marked with
 @Depth, TreeMaker will have its configure(Depth) method called so that
 the attributes of @Depth can be available during generation.
 */
public class TreeMaker extends Generator<Tree> {
    private Depth depthRange;

    public TreeMaker() {
        super(Tree.class);
    }

    public void configure(Depth depthRange) {
        if (depthRange != null)
            checkRange(Ranges.Type.INTEGRAL, depthRange.min(), depthRange.max());

        this.depthRange = depthRange;
    }

    /**
     *
     -- TreeMaker.generate(...) decides what depth the generated tree should
     have. If @Depth is not specified, it will be status.size() div 2; otherwise,
     depth will be randomly chosen from the interval [Depth.min, Depth.max].
     A depth of 0 produces an Empty; a depth of 1 produces a Leaf; any other
     depth set depth's value onto status and asks to produce a Node.

     * @param random source of randomness to be used when generating the value
     * @param status an object that can be used to influence the generated
     * value. For example, generating lists can use the {@link
     * GenerationStatus#size() size} method to generate lists with a given
     * number of elements.
     * @return
     */
    @Override public Tree generate(SourceOfRandomness random, GenerationStatus status) {
        int depth = depthRange == null
            ? status.size() / 2
            : random.nextInt(depthRange.min(), depthRange.max());

        switch (depth) {
            case 0:
                return gen().type(Empty.class).generate(random, status);
            case 1:
                return gen().type(Leaf.class).generate(random, status);
            default:
                status.setValue(DEPTH, depth);
                return gen().type(Node.class).generate(random, status);
        }
    }

    @Override public boolean canRegisterAsType(Class<?> type) {
        return false;
    }
}
