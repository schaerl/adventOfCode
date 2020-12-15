package ch.feomathar.adventofcode.util

fun <T> permute(list: List<T>): List<List<T>> {
    if (list.size <= 1) {
        return arrayListOf(list)
    } else {
        val allResults = ArrayList<List<T>>()
        for (i in list.indices) {
            val without = ArrayList(list)
            without.removeAt(i)
            val permuts = permute(without)
            for (res in permuts) {
                val complete = arrayListOf(list[i])
                complete.addAll(res)
                allResults.add(complete)
            }
        }
        return allResults
    }
}
