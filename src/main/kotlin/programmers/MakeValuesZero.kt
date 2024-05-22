package programmers

import java.util.*
import kotlin.math.*

// 월간 코드 챌린지 시즌2 - 모두 0으로 만들기
class Solution {
    fun solution(a: IntArray, edges: Array<IntArray>): Long {
        var answer = 0L

        if (!isSolvable(a)) return -1L

        val nbs = getNbs(a.size, edges)

        val nodes = getNodes(a, nbs)

        val leaves = LinkedList<Node>().apply {
            addAll(nodes.filter { it.isLeaf() })
        }

        while (!leaves.isEmpty()) {
            val leaf = leaves.remove()

            answer += abs(leaf.v)

            if (leaf.isRoot()) break
            leaf.mergeToParent()

            if (leaf.parent!!.isLeaf()) {
                leaves.add(leaf.parent!!)
            }
        }

        return answer
    }

    fun isSolvable(a: IntArray): Boolean {
        var sum = 0L
        a.forEach { sum += it }
        return sum == 0L
    }

    fun getNbs(size: Int, edges: Array<IntArray>): Array<MutableList<Int>> {
        val nbs = Array(size) {
            mutableListOf<Int>()
        }

        edges.forEach {
            nbs[it[0]].add(it[1])
            nbs[it[1]].add(it[0])
        }

        return nbs
    }

    fun getNodes(a: IntArray, nbs: Array<MutableList<Int>>): Array<Node> {
        val nodes = Array(a.size) {
            Node(v = a[it].toLong(), number = it)
        }

        val root = nodes.find { nbs[it.number].size == 1 }!!

        val q = LinkedList<Node>()
        q.add(root)

        while (!q.isEmpty()) {
            val node = q.remove()
            if (node != root && nbs[node.number].size == 1) continue

            val nb = nbs[node.number]
            val children = nb.filter { it != node.parent?.number }.map { nodes[it] }

            children.forEach { child ->
                node.children.add(child)
                child.parent = node
            }

            q.addAll(children)
        }

        return nodes
    }
}

data class Node(
    var v: Long,
    var number: Int,
    var parent: Node? = null,
    val children: MutableList<Node> = mutableListOf()
) {
    private var mergeCount: Int = 0

    fun isRoot() = parent == null
    fun isLeaf() = !isRoot() && mergeCount == children.size

    fun mergeToParent() {
        parent!!.v += v
        parent!!.mergeCount++
    }
}