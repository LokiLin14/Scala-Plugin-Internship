# Scala Plugin Internship

## Part 1

Here's a recursive function definition:

- f(x) = f(x-1) + f(x-1)
- f(0) = 1

What big-O complexity is your solution? Can you do better? Why / why not / how?

The code for the f is implemented in `src/main/scala/doubling-function.scala`.

The big-O complexity is O(2^n) where n is the input to the function.
We can do better by noticing f(x) = 2^x so we can use 1 left shifted by x.
This is done in constant time because it's implemented in silicon.

## Part 2

1. Create a suitable data structure to represent the trees in Scala.

I created `Tree` to represent trees in Scala. It is an enum because by default enums implement the equality for task 2.

```scala
enum Tree:
case Node(subtrees: Tree *)
case ID(name: String)
```

2. Implement a way to check whether two trees are equal. Two trees are equal if they have
   the same identifier, or when they have the same number of children where all two
   children with the same index are equal.

We cache the hash of the enum calculated with `MurmurHash3.productHash`. If the hashes equal, we match on the type of
object. In the node case, we traverse the list to check the subtrees are equal. In the ID case we check if the strings
are equal.

3. Implement a method that returns a string by converting a tree into its representation in
   TL.

This is done by matching on the `Tree`.

4. Implement the reverse, i.e. a function, that takes a string, parses it, and returns the tree
   that was represented by the string in TL. Fail and return a message with a reason if the
   string couldn’t be parsed. Parsing a string produced by your method in 3 should return a
   tree equal to the original one.

I implemented an imperative parser because the language is quite simple. Alternatively, I could have used a parsing
combinator library, but I thought it was overkill for this language.

5. Now implement a method
   def replace(tree: Tree, searchTree: Tree, replacement: Tree): Tree
   It should return a copy of tree where all subtrees (including tree itself) that are equal to
   searchTree are replaced by replacement.

We check if our `tree` is the same as the `searchTree`. If it matches we return the `replacementTree`. Otherwise, we
match on the tree. In the Node, case we recurse on the subtrees and rebuild the Node with the resulting array. In the ID
case, we return ourselves because there's nothing to replace. 