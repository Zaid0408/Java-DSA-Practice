For LeetCode and interview preparation, you only need to know a handful of tree types. Here's a concise cheat sheet.

| Tree Type                                        | Definition                                                                                                             |
| ------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------- |
| **Binary Tree (BT)**                             | Every node has **at most 2 children** (left and right). No ordering rules.                                             |
| **Binary Search Tree (BST)**                     | A binary tree where **left subtree < node < right subtree** for every node.                                            |
| **Full Binary Tree (Strict/Proper Binary Tree)** | Every node has **either 0 or exactly 2 children**. No node has only one child.                                         |
| **Complete Binary Tree (CBT)**                   | Every level is completely filled except possibly the last, and the last level is filled **from left to right**.        |
| **Perfect Binary Tree**                          | All internal nodes have exactly 2 children **and** all leaves are at the same level. Every level is completely filled. |
| **Balanced Binary Tree**                         | For every node, the height difference between the left and right subtrees is **at most 1**.                            |
| **Degenerate Binary Tree**                       | Every node has only one child, so the tree behaves like a linked list.                                                 |
| **Skewed Binary Tree**                           | A degenerate tree where every node has only a **left child** (left-skewed) or only a **right child** (right-skewed).   |
| **N-ary Tree**                                   | Each node can have **0 to N children** instead of only two.                                                            |
| **Forest**                                       | A collection of one or more disjoint trees. (Rare in LeetCode.)                                                        |

---

## Visual examples

### 1. Binary Tree

```text
      1
     / \
    2   3
     \
      4
```

No special property.

---

### 2. Binary Search Tree (BST)

```text
       8
      / \
     3  10
    / \   \
   1   6   14
```

Rule:

```text
left < root < right
```

---

### 3. Full Binary Tree

```text
       1
      / \
     2   3
    / \ / \
   4  5 6  7
```

Every node has:

* 0 children ✔
* or 2 children ✔

Never exactly one child.

---

### 4. Complete Binary Tree

```text
       1
      / \
     2   3
    / \  /
   4  5 6
```

Last level fills left to right.

This is complete.

But:

```text
       1
      / \
     2   3
      \  /
      5 6
```

is **not** complete because there's a gap before `5`.

---

### 5. Perfect Binary Tree

```text
        1
      /   \
     2     3
    / \   / \
   4  5  6  7
```

Every level is full.

Number of nodes:

```text
2^h - 1
```

---

### 6. Balanced Binary Tree

```text
        1
      /   \
     2     3
    / \
   4   5
```

Every node satisfies:

```text
|leftHeight - rightHeight| <= 1
```

---

### 7. Degenerate Tree

```text
1
 \
  2
   \
    3
     \
      4
```

Acts like a linked list.

---

### 8. Left Skewed

```text
      1
     /
    2
   /
  3
 /
4
```

---

### 9. Right Skewed

```text
1
 \
  2
   \
    3
     \
      4
```

---

### 10. N-ary Tree

```text
        1
     /  |  \
    2   3   4
       / \
      5   6
```

A node can have many children.

---

# Which ones appear most in LeetCode?

| Tree Type            | Frequency              |
| -------------------- | ---------------------- |
| Binary Tree          | ⭐⭐⭐⭐⭐                  |
| BST                  | ⭐⭐⭐⭐⭐                  |
| Complete Binary Tree | ⭐⭐⭐⭐                   |
| Balanced Binary Tree | ⭐⭐⭐⭐                   |
| Perfect Binary Tree  | ⭐⭐⭐                    |
| Full Binary Tree     | ⭐⭐                     |
| Skewed/Degenerate    | ⭐⭐ (mostly edge cases) |
| N-ary Tree           | ⭐⭐                     |

---

## Important relationships to remember

```text
Perfect Tree
      ↓
      is always
Complete Tree
      ↓
      is always
Full Tree
```

But the reverse is **not** true.

For example:

* Perfect ⇒ Complete ✔
* Perfect ⇒ Full ✔
* Complete ⇒ Perfect ✖
* Full ⇒ Complete ✖
* Balanced ⇒ Complete ✖
* Complete ⇒ Balanced ✔ (every complete binary tree is height-balanced)

Keeping these definitions straight will help you quickly recognize which algorithms or optimizations apply in tree problems.
