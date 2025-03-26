package cp2024.tests;

import cp2024.circuit.CircuitNode;
import cp2024.circuit.NodeType;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tests_2 {
    private static final Random random = new Random();

    public enum Mode {
        BALANCED, UNBALANCED
    }

    public static CircuitNode generateTree(int n, Mode mode) {
        return generateNode(n, mode, 0);
    }

    private static CircuitNode generateNode(int remainingNodes, Mode mode, int depth) {
        if (remainingNodes <= 1) {
            // Leaf node: choose true or false, optionally with a duration
            return random.nextBoolean()
                    ? CircuitNode.mk(random.nextBoolean(), Duration.ofSeconds(random.nextInt(5)))
                    : CircuitNode.mk(random.nextBoolean());
        }

        NodeType type = getRandomNodeType();
        List<CircuitNode> children = new ArrayList<>();
        int requiredChildren = getRequiredChildrenCount(type);

        // Adjust children count based on mode and depth
        int nodesPerChild = (mode == Mode.BALANCED)
                ? Math.max(2, remainingNodes / (depth + 2))
                : Math.max(1, remainingNodes / (depth + 3));

        for (int i = 0; i < requiredChildren; i++) {
            int childNodes = Math.max(1, nodesPerChild);
            children.add(generateNode(childNodes, mode, depth + 1));
        }

        // Special case handling for GT and LT, adding a parameter in range (1, children count)
        if (type == NodeType.GT || type == NodeType.LT) {
            int threshold = random.nextInt(requiredChildren) + 1;
            return CircuitNode.mk(type, threshold, children.toArray(new CircuitNode[0]));
        }

        // Construct the CircuitNode based on type and requirements
        switch (type) {
            case OR:
            case AND:
                return CircuitNode.mk(type, children.toArray(new CircuitNode[0]));
            case NOT:
                return CircuitNode.mk(type, children.get(0));
            case IF:
                return CircuitNode.mk(type, children.get(0), children.get(1), children.get(2));
            default:
                return CircuitNode.mk(random.nextBoolean());
        }
    }

    private static int getRequiredChildrenCount(NodeType type) {
        switch (type) {
            case OR:
            case AND:
                return 2 + random.nextInt(3); // 2 to 4 children
            case NOT:
                return 1;
            case GT:
            case LT:
                return 2 + random.nextInt(3); // 2 to 4 children
            case IF:
                return 3;
            default:
                return 0; // Leaf node case
        }
    }

    private static NodeType getRandomNodeType() {
        NodeType[] types = NodeType.values();
        return types[random.nextInt(types.length)];
    }

    public static void main(String[] args) {
        int n = 100; // target nodes
        Mode mode = Mode.BALANCED;

        CircuitNode root = generateTree(n, mode);
        System.out.println("Generated Circuit Tree in " + mode + " mode: " + root);
    }
}
