# Decision Tree

The `Decision Tree` library allows you to create, modify, and use binary decision trees for various tasks. This is a toolkit for working with trees that you designed – **not a machine learning trainer**. It can be used in *Evolution Models*, *Game Development*, and *Rule-Based Systems*.

[![Java 21+](https://img.shields.io/badge/Java-21+-blue.svg)](https://adoptium.net/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

## 📦 Installing

### Maven (via GitHub Packages)
>
> ⚠️ To use GitHub Packages, [authenticate with GitHub](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry#authenticating-to-github-packages).

```xml
<dependency>
    <groupId>io.github.jinseisieko</groupId>
    <artifactId>decisiontree</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Local Development

```bash
git clone https://github.com/jinseisieko/binary-decision-tree.git
cd binary-decision-tree
mvn install
```

Then add the same dependency to your project (without GitHub auth).

## 🚀 Quick Start

```Java
// some examples
```

> 💡 Full examples in [`UseCases.java`](src/test/java/io/github/jinseisieko/decisiontree/UseCases.java).

## 📚 Key Features

- Create binary decision trees with custom conditions and outcomes
- Modify trees using the builder pattern
- Two implementations: dynamic pointer-based trees and array-based trees
- Support for copying and branching operations

## 🛠️ Current Status & To-Do List

The project is currently under development. Here are the remaining tasks to complete the implementation:

### Core Implementation

- [ ] **DynamicDecisionTree**: Implement constructor and methods (currently throws `UnsupportedOperationException`)
- [ ] **DynamicDecisionTree.Builder**: Implement all builder methods
- [ ] **ArrayDecisionTree**: Implement constructor and methods (currently throws `UnsupportedOperationException`)
- [ ] **ArrayDecisionTree.Builder**: Implement all builder methods

### Branch Functionality

- [ ] **DynamicDecisionBranch**: Implement methods for subtree operations
- [ ] **ArrayDecisionBranch**: Implement methods for subtree operations

### Infrastructure

- [ ] **Constructors**: Add proper initialization for all tree implementations
- [x] **Interface Fix**: Fixed typo in DecisionTreeBuilder interface (`asBranchoOfSubtree` should be `asBranchOfSubtree`)
- [ ] **Testing**: Write comprehensive tests for all implemented functionality

## 🤝 Contributing

Contributions are welcome! Feel free to submit a pull request or open an issue to discuss what you would like to change.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
