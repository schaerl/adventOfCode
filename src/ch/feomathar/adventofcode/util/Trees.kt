package ch.feomathar.adventofcode.util

fun <S> findRoots(tree: Map<S, List<S>>): List<S> {
    val toNodes = tree.values.flatten()
    return tree.keys.subtract(toNodes).toList()
}
