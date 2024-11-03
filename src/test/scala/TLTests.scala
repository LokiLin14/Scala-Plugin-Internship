import Tree.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

class TLTests extends AnyFunSuite:
  test("Tree.ID equals Tree.ID iff name is the same"):
    val idA = ID("A")
    val idB = ID("B")
    assert(idA == idA)
    assert(idA != idB)
  test("Tree.Node equals Tree.Node iff subtrees are the same"):
    val idA = ID("A")
    val idB = ID("B")
    val nodeA = Node(idA, idA)
    val nodeB = Node(idA, idB)
    assert(nodeA == nodeA)
    assert(nodeA != nodeB)
  test("Tree.Node not equals Tree.ID"):
    val idA = ID("A")
    val nodeA = Node(idA)
    assert(idA != nodeA)
    assert(nodeA != idA)
  test("Tree.toString works on ID"):
    assert(ID("abc").toString() === "abc")
  test("Tree.toString shows Node as brackets with space separated list of subtrees"):
    assert(Node().toString() === "()")
    assert(Node(ID("abc")).toString() === "(abc)")
    assert(Node(ID("abc"), ID("abd")).toString() === "(abc abd)")
  test("Tree.toString works on examples"):
    assert(Node().toString() === "()")
    assert(ID("a").toString() === "a")
    assert(Node(ID("a")).toString() === "(a)")
    assert(Node(Node(), ID("a")).toString() === "(() a)")
    assert(Node(Node(ID("a"), ID("bb")), ID("ccc"), ID("ddd")).toString() === "((a bb) ccc ddd)")
  test("Tree.parseFromString undoes Tree.toString"):

    def check(tl: Tree) =
      assert(Tree.parseFromString(tl.toString()) === Left(tl))

    check(Node())
    check(ID("a"))
    check(Node(ID("a")))
    check(Node(Node(), ID("a")))
    check(Node(Node(ID("a"), ID("bb")), ID("ccc"), ID("ddd")))
  test("Tree.replace works on initial tree"):
    assert(replace(ID("A"), ID("A"), ID("C")) === ID("C"))
    assert(replace(ID("A"), ID("B"), ID("C")) === ID("A"))
  test("Tree.replace works on recursive terms"):
    val nodeA = Node(Node(ID("A"), ID("B")), ID("C"))
    val nodeB = Node(Node(ID("B"), ID("B")), ID("C"))
    assert(replace(nodeA, ID("A"), ID("B")) === nodeB)
    assert(replace(nodeA, ID("D"), ID("B")) === nodeA)