enum Tree:
  case Node(subtrees: Tree*)
  case ID(name: String)

  override def toString: String =
    this match
      case Node(nodes*) => s"(${nodes.mkString(" ")})"
      case ID(name) => name

  override val hashCode : Int = scala.util.hashing.MurmurHash3.productHash(this)

  override def equals(obj: Any): Boolean =
    if (!obj.isInstanceOf[Tree] || hashCode != obj.hashCode) {
      return false
    }
    this match
      case Node(ourTrees*) =>
        obj match
          case Node(theirTrees*) => ourTrees == theirTrees
          case _ => false
      case ID(ourName) =>
        obj match
          case ID(theirName) => ourName == theirName
          case _ => false

object Tree:
  def parseFromString(str: String): Either[Tree, String] =
    if (str.isEmpty) {
      return Right("Unexpected EOF")
    }
    var remaining = str
    var canBeginID = true
    var brackets: Vector[Vector[Tree]] = Vector(Vector.empty)
    while (remaining.nonEmpty) {
      remaining.head match
        case '(' =>
          brackets = brackets.appended(Vector.empty)
          canBeginID = true
          remaining = remaining.tail
        case ')' =>
          if (brackets.size == 1) {
            return Right(s"Unexpected ')' at \"${remaining}\"")
          }
          val last = brackets.last
          brackets = brackets.dropRight(1)
          brackets = brackets.updated(brackets.size - 1, brackets.last.appended(Node(last *)))
          canBeginID = false
          remaining = remaining.tail
        case ' ' =>
          canBeginID = true
          remaining = remaining.tail
        case c if isAlphanumeric(c) =>
          if (!canBeginID) {
            return Right(s"Unexpected beginning of Node at \"${remaining}\"")
          }
          brackets = brackets.updated(brackets.size - 1, brackets.last.appended(ID(remaining.takeWhile(isAlphanumeric))))
          canBeginID = false
          remaining = remaining.dropWhile(isAlphanumeric)
    }
    if (brackets.size > 1 || brackets.last.size > 1) {
      return Right("Unexpected EOF: Expected ')'")
    }
    Left(brackets.head.head)

  def replace(tree: Tree, searchTree: Tree, replacement: Tree): Tree =
    if tree == searchTree then
      replacement
    else
      tree match
        case Node(trees*) => Node(trees.map(replace(_, searchTree, replacement)) *)
        case ID(_) => tree

  private val alphanumericPattern = "[a-zA-Z0-9]".r
  private def isAlphanumeric(c: Char): Boolean =
    alphanumericPattern.matches(c.toString)

end Tree