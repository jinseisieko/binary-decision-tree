# Usage Examples

This document provides practical examples of using the Binary Decision Tree library.

## Table of Contents

1. [Basic Usage](#basic-usage)
2. [Multi-Level Decisions](#multi-level-decisions)
3. [Function-Based Outcomes](#function-based-outcomes)
4. [Tree Modification](#tree-modification)
5. [Subtree Operations](#subtree-operations)
6. [Real-World Examples](#real-world-examples)

---

## Basic Usage

### Example 1: Simple Yes/No Decision

```java
import io.github.jinseisieko.decisiontree.*;

public class SimpleDecision {
    public static void main(String[] args) {
        // Create a tree that checks if a number is positive
        BinaryDecisionTree<Integer, String> tree = new DynamicDecisionTree<>()
            .builder()
            .insertCondition(x -> x > 0)
            .goToTrueChild()
            .insertOutcome("positive")
            .goToSibling()
            .insertOutcome("non-positive")
            .goToParent()
            .build();
        
        // Use the tree
        System.out.println(tree.decide(5));    // Output: positive
        System.out.println(tree.decide(-3));   // Output: non-positive
        System.out.println(tree.decide(0));    // Output: non-positive
    }
}
```

---

### Example 2: Number Classification

```java
public class NumberClassifier {
    public static void main(String[] args) {
        BinaryDecisionTree<Integer, String> classifier = new DynamicDecisionTree<>()
            .builder()
            // Check if positive
            .insertCondition(x -> x > 0)
            .goToTrueChild()
                // Check if large
                .insertCondition(x -> x > 100)
                .goToTrueChild()
                    .insertOutcome("large positive")
                .goToSibling()
                    .insertOutcome("small positive")
                .goToParent()
            .goToSibling()
                // Check if negative
                .insertCondition(x -> x < 0)
                .goToTrueChild()
                    .insertOutcome("negative")
                .goToSibling()
                    .insertOutcome("zero")
                .goToParent()
            .goToParent()
            .build();
        
        // Test the classifier
        System.out.println(classifier.decide(150));   // large positive
        System.out.println(classifier.decide(50));    // small positive
        System.out.println(classifier.decide(-10));   // negative
        System.out.println(classifier.decide(0));     // zero
    }
}
```

---

## Multi-Level Decisions

### Example 3: Grade Calculator

```java
public class GradeCalculator {
    public static void main(String[] args) {
        BinaryDecisionTree<Integer, String> gradeTree = new DynamicDecisionTree<>()
            .builder()
            // A grade?
            .insertCondition(score -> score >= 90)
            .goToTrueChild()
                .insertOutcome("A")
            .goToSibling()
            // B grade?
            .insertCondition(score -> score >= 80)
            .goToTrueChild()
                .insertOutcome("B")
            .goToSibling()
            // C grade?
            .insertCondition(score -> score >= 70)
            .goToTrueChild()
                .insertOutcome("C")
            .goToSibling()
            // D grade?
            .insertCondition(score -> score >= 60)
            .goToTrueChild()
                .insertOutcome("D")
            .goToSibling()
            // F grade
            .insertOutcome("F")
            .goToParent()
            .goToParent()
            .goToParent()
            .goToParent()
            .build();
        
        // Calculate grades
        int[] scores = {95, 87, 72, 65, 45};
        for (int score : scores) {
            String grade = gradeTree.decide(score);
            System.out.printf("Score: %d → Grade: %s%n", score, grade);
        }
    }
}
```

---

## Function-Based Outcomes

### Example 4: Dynamic Output

```java
public class DynamicOutput {
    public static void main(String[] args) {
        // Outcome computes result based on input
        BinaryDecisionTree<Integer, Integer> tree = new DynamicDecisionTree<>()
            .builder()
            .insertCondition(x -> x % 2 == 0)
            .goToTrueChild()
                // Even: return x * 2
                .insertOutcome(x -> x * 2)
            .goToSibling()
                // Odd: return x * 3 + 1
                .insertOutcome(x -> x * 3 + 1)
            .goToParent()
            .build();
        
        System.out.println(tree.decide(4));  // 8 (4 * 2)
        System.out.println(tree.decide(5));  // 16 (5 * 3 + 1)
        System.out.println(tree.decide(10)); // 20 (10 * 2)
    }
}
```

---

### Example 5: String Processing

```java
public class StringProcessor {
    public static void main(String[] args) {
        BinaryDecisionTree<String, String> processor = new DynamicDecisionTree<>()
            .builder()
            // Check length
            .insertCondition(s -> s.length() > 10)
            .goToTrueChild()
                // Long string: truncate
                .insertOutcome(s -> s.substring(0, 10) + "...")
            .goToSibling()
                // Short string: uppercase
                .insertOutcome(String::toUpperCase)
            .goToParent()
            .build();
        
        System.out.println(processor.decide("Hello"));           // HELLO
        System.out.println(processor.decide("Hello World!"));    // Hello Worl...
    }
}
```

---

## Tree Modification

### Example 6: Updating a Tree

```java
public class TreeModification {
    public static void main(String[] args) {
        // Original tree
        BinaryDecisionTree<Integer, String> original = new DynamicDecisionTree<>()
            .builder()
            .insertCondition(x -> x > 0)
            .goToTrueChild()
            .insertOutcome("positive")
            .goToSibling()
            .insertOutcome("negative")
            .goToParent()
            .build();
        
        // Create modified version
        BinaryDecisionTree<Integer, String> modified = original.builder()
            .goToRoot()
            .goToFalseChild()
            .clearSubtree()
            .insertCondition(x -> x < 0)
            .goToTrueChild()
            .insertOutcome("strictly negative")
            .goToSibling()
            .insertOutcome("zero")
            .goToParent()
            .build();
        
        // Original unchanged
        System.out.println(original.decide(0));  // negative
        
        // Modified has new logic
        System.out.println(modified.decide(0));  // zero
        System.out.println(modified.decide(-5)); // strictly negative
    }
}
```

---

## Subtree Operations

### Example 7: Extracting and Reusing Subtrees

```java
public class SubtreeReuse {
    public static void main(String[] args) {
        // Create a common subtree
        DecisionBranch<Integer, String> commonSubtree = new DynamicDecisionTree<>()
            .builder()
            .insertCondition(x -> x > 100)
            .goToTrueChild()
            .insertOutcome("very large")
            .goToSibling()
            .insertOutcome("moderate")
            .goToParent()
            .asBranchOfSubtree();
        
        // Use in tree 1
        BinaryDecisionTree<Integer, String> tree1 = new DynamicDecisionTree<>()
            .builder()
            .insertCondition(x -> x > 0)
            .goToTrueChild()
            .insertBranch(commonSubtree)  // Reuse subtree
            .goToSibling()
            .insertOutcome("non-positive")
            .goToParent()
            .build();
        
        // Use in tree 2
        BinaryDecisionTree<Integer, String> tree2 = new DynamicDecisionTree<>()
            .builder()
            .insertCondition(x -> x >= 0)
            .goToTrueChild()
            .insertBranch(commonSubtree)  // Same subtree
            .goToSibling()
            .insertOutcome("negative")
            .goToParent()
            .build();
        
        // Both trees share the same subtree logic
        System.out.println(tree1.decide(150));  // very large
        System.out.println(tree2.decide(150));  // very large
        System.out.println(tree1.decide(50));   // moderate
        System.out.println(tree2.decide(50));   // moderate
    }
}
```

---

### Example 8: Getting All Branches

```java
public class BranchCollection {
    public static void main(String[] args) {
        BinaryDecisionTree<Integer, String> tree = buildComplexTree();
        
        // Get all branches
        List<DecisionBranch<Integer, String>> allBranches = 
            tree.builder().getAllBranches();
        
        System.out.println("Total nodes: " + allBranches.size());
        
        // Get branches at specific depth
        List<DecisionBranch<Integer, String>> depth2Branches = 
            tree.builder().getAllBranchesWithDepth(2);
        
        System.out.println("Nodes at depth 2: " + depth2Branches.size());
        
        // Print depth of each branch
        for (DecisionBranch<Integer, String> branch : allBranches) {
            System.out.println("Branch depth: " + branch.getDepth());
        }
    }
    
    private static BinaryDecisionTree<Integer, String> buildComplexTree() {
        return new DynamicDecisionTree<>()
            .builder()
            .insertCondition(x -> x > 0)
            .goToTrueChild()
                .insertCondition(x -> x > 10)
                .goToTrueChild()
                .insertOutcome("large")
                .goToSibling()
                .insertOutcome("small")
                .goToParent()
            .goToSibling()
                .insertCondition(x -> x < 0)
                .goToTrueChild()
                .insertOutcome("negative")
                .goToSibling()
                .insertOutcome("zero")
                .goToParent()
            .goToParent()
            .build();
    }
}
```

---

## Real-World Examples

### Example 9: Loan Approval System

```java
public class LoanApproval {
    
    static class Application {
        int creditScore;
        int income;
        int debt;
        
        Application(int creditScore, int income, int debt) {
            this.creditScore = creditScore;
            this.income = income;
            this.debt = debt;
        }
    }
    
    enum Decision { APPROVE, DENY, REVIEW }
    
    public static void main(String[] args) {
        BinaryDecisionTree<Application, Decision> loanTree = new DynamicDecisionTree<>()
            .builder()
            // Check credit score
            .insertCondition(app -> app.creditScore >= 700)
            .goToTrueChild()
                // Good credit: check income
                .insertCondition(app -> app.income >= 50000)
                .goToTrueChild()
                    .insertOutcome(Decision.APPROVE)
                .goToSibling()
                    .insertOutcome(Decision.REVIEW)
                .goToParent()
            .goToSibling()
                // Poor credit: check income and debt
                .insertCondition(app -> app.income >= 100000 && app.debt < 20000)
                .goToTrueChild()
                    .insertOutcome(Decision.REVIEW)
                .goToSibling()
                    .insertOutcome(Decision.DENY)
                .goToParent()
            .goToParent()
            .build();
        
        // Test applications
        Application app1 = new Application(750, 60000, 10000);
        Application app2 = new Application(650, 120000, 15000);
        Application app3 = new Application(600, 40000, 30000);
        
        System.out.println("App1: " + loanTree.decide(app1));  // APPROVE
        System.out.println("App2: " + loanTree.decide(app2));  // REVIEW
        System.out.println("App3: " + loanTree.decide(app3));  // DENY
    }
}
```

---

### Example 10: Product Recommendation

```java
public class ProductRecommendation {
    
    static class Customer {
        int age;
        double budget;
        String category;
        
        Customer(int age, double budget, String category) {
            this.age = age;
            this.budget = budget;
            this.category = category;
        }
    }
    
    public static void main(String[] args) {
        BinaryDecisionTree<Customer, String> recommender = new DynamicDecisionTree<>()
            .builder()
            // Check category
            .insertCondition(c -> c.category.equals("electronics"))
            .goToTrueChild()
                // Electronics: check budget
                .insertCondition(c -> c.budget > 500)
                .goToTrueChild()
                    .insertOutcome("Premium Electronics")
                .goToSibling()
                    .insertOutcome("Budget Electronics")
                .goToParent()
            .goToSibling()
                // Other categories
                .insertCondition(c -> c.category.equals("clothing"))
                .goToTrueChild()
                    // Clothing: check age
                    .insertCondition(c -> c.age < 30)
                    .goToTrueChild()
                        .insertOutcome("Trendy Clothing")
                    .goToSibling()
                        .insertOutcome("Classic Clothing")
                    .goToParent()
                .goToSibling()
                    .insertOutcome("General Merchandise")
                .goToParent()
            .goToParent()
            .build();
        
        // Get recommendations
        Customer c1 = new Customer(25, 600, "electronics");
        Customer c2 = new Customer(45, 300, "clothing");
        Customer c3 = new Customer(28, 200, "clothing");
        
        System.out.println("C1: " + recommender.decide(c1));  // Premium Electronics
        System.out.println("C2: " + recommender.decide(c2));  // Classic Clothing
        System.out.println("C3: " + recommender.decide(c3));  // Trendy Clothing
    }
}
```

---

### Example 11: Game AI Decision Making

```java
public class GameAI {
    
    static class GameState {
        int health;
        int ammo;
        int enemies;
        boolean hasCover;
        
        GameState(int health, int ammo, int enemies, boolean hasCover) {
            this.health = health;
            this.ammo = ammo;
            this.enemies = enemies;
            this.hasCover = hasCover;
        }
    }
    
    enum Action { ATTACK, RETREAT, TAKE_COVER, RELOAD }
    
    public static void main(String[] args) {
        BinaryDecisionTree<GameState, Action> aiTree = new DynamicDecisionTree<>()
            .builder()
            // Check health
            .insertCondition(state -> state.health > 30)
            .goToTrueChild()
                // Healthy: check ammo
                .insertCondition(state -> state.ammo > 10)
                .goToTrueChild()
                    // Good health and ammo: check enemies
                    .insertCondition(state -> state.enemies > 2)
                    .goToTrueChild()
                        .insertOutcome(Action.ATTACK)
                    .goToSibling()
                        .insertOutcome(Action.ATTACK)
                    .goToParent()
                .goToSibling()
                    // Low ammo: reload
                    .insertOutcome(Action.RELOAD)
                .goToParent()
            .goToSibling()
                // Low health: check cover
                .insertCondition(state -> state.hasCover)
                .goToTrueChild()
                    .insertOutcome(Action.TAKE_COVER)
                .goToSibling()
                    .insertOutcome(Action.RETREAT)
                .goToParent()
            .goToParent()
            .build();
        
        // AI decisions
        GameState s1 = new GameState(80, 50, 3, true);
        GameState s2 = new GameState(20, 30, 1, false);
        GameState s3 = new GameState(50, 5, 2, true);
        
        System.out.println("S1: " + aiTree.decide(s1));  // ATTACK
        System.out.println("S2: " + aiTree.decide(s2));  // RETREAT
        System.out.println("S3: " + aiTree.decide(s3));  // RELOAD
    }
}
```

---

## See Also

- [Builder API](../api/builder-pattern.md) - Detailed builder documentation
- [Tree Operations](../api/tree-operations.md) - Tree operation guide
- [Architecture](../architecture/overview.md) - System design
