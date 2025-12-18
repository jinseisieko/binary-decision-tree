/**
 * {@summary A toolkit for working with binary decision trees}
 *
 * <p>The {@code io.github.jinseisieko.decisiontree} package provides a comprehensive
 * framework for creating, modifying, and executing binary decision trees. This is
 * <strong>NOT</strong> a machine learning trainer, but rather a flexible toolkit
 * designed for:
 *
 * <ul>
 *   <li><strong>Evolution Models:</strong> Represent decision logic in genetic algorithms</li>
 *   <li><strong>Game Development:</strong> Implement NPC behavior trees and AI decision logic</li>
 *   <li><strong>Rule-Based Systems:</strong> Define complex conditional rules and outcomes</li>
 * </ul>
 *
 * <h2>Architecture</h2>
 *
 * <p>The framework consists of two main implementations:
 *
 * <ul>ext install aaron-bond.better-comments
 *   <li>{@link DynamicDecisionTree} - Node-based tree structure with flexible runtime modification</li>
 *   <li>{@link ArrayDecisionTree} - Array-backed tree structure optimized for fixed-depth trees</li>
 * </ul>
 * 
 * <h2>Core Concepts</h2>
 *
 * <p><strong>Decision Tree Structure:</strong> A binary tree where:
 * <ul>
 *   <li>{@link ConditionNode} - Internal nodes that test a condition and branch left (true) or right (false)</li>
 *   <li>{@link OutcomeNode} - Leaf nodes that return a result based on a handler function or constant value</li>
 * </ul>
 *
 * <p><strong>Building Trees:</strong> Use the {@link DecisionTreeBuilder} interface via
 * {@link BinaryDecisionTree#builder()} to fluently construct or modify trees.
 *
 * <h2>Example Usage</h2>
 *
 * <pre>{@code
 * // Create and build a simple age classifier
 * BinaryDecisionTree<Integer, String> ageTree = new DynamicDecisionTree<Integer, String>()
 *     .builder()
 *     .insertCondition(age -> age >= 65)
 *       .goToTrueChild()
 *       .insertOutcome("Senior")
 *       .goToSibling()
 *     .insertCondition(age -> age >= 18)
 *       .goToTrueChild()
 *       .insertOutcome("Adult")
 *       .goToSibling()
 *       .insertOutcome("Minor")
 *     .build();
 *
 * // Execute the tree
 * String result = ageTree.decide(30); // "Adult"
 * }</pre>
 *
 * <h2>Thread Safety</h2>
 * <p>Decision trees are not thread-safe by default. If concurrent access is required,
 * consider using {@link BinaryDecisionTree#copy()} to create independent instances
 * for each thread.
 *
 * @since 1.0
 */
package io.github.jinseisieko.decisiontree;