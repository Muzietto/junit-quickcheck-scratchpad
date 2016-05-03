package com.pholser.junit.quickcheck.scratchpad.model.tree;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

/**
 *      -- NodeGenerator is the only generator available that can produce a Node.
 If when called NodeGenerator.generate(status) has status[DEPTH] == 1,
 either a Leaf or an Empty will be produced (with approximately equal
 probability). Otherwise, it will produce a Node with each of left and right
 subtrees chosen from either a Node, Leaf, or Empty (with approximately
 equal probability).

 */
public class NodeGenerator extends Generator<Node> {
    public NodeGenerator() {
        super(Node.class);
    }

    @Override public Node generate(SourceOfRandomness random, GenerationStatus status) {
        int depth = status.getValue(TreeKeys.DEPTH);

        if (depth == 1) { // close this branch
            Generator<Tree> leafOrEmpty = gen().oneOf(Leaf.class, Empty.class);
            return new Node(
                leafOrEmpty.generate(random, status),
                leafOrEmpty.generate(random, status)
            );
        }

        Generator<Tree> lsubtree = gen().oneOf(Node.class, Leaf.class, Empty.class);
        Generator<Tree> rsubtree = gen().oneOf(Node.class, Leaf.class, Empty.class);
        status.setValue(TreeKeys.DEPTH, new Integer(depth - 1));
        return new Node(
            lsubtree.generate(random, status),
            rsubtree.generate(random, status)
        );
    }

    @Override public boolean canRegisterAsType(Class<?> type) {
        return Tree.class.isAssignableFrom(type);
    }
}
