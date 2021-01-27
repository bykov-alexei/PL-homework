import kotlin.math.exp

fun main() {
    val tests = listOf(::test1, ::test2, ::test3)
    for ((i, test) in tests.withIndex()) {
        val result = test()
        if (!result) {
            print("Test ${i+1} not passed")
        } else {
            println("Test ${i+1} passed")
        }
    }
}

fun test1(): Boolean {
    val map = SpecialHashMap()
    map["value1"] = 1
    map["value2"] = 2
    map["value3"] = 3
    map["1"] = 10
    map["2"] = 20
    map["3"] = 30
    map["1, 5"] = 100
    map["5, 5"] = 200
    map["10, 5"] = 300

    if (map.iloc[0] != 10) {
        return false
    }
    if (map.iloc[2] != 300) {
        return false
    }
    if (map.iloc[5] != 200) {
        return false
    }
    if (map.iloc[8] != 3) {
        return false
    }
    return true
}

fun test2(): Boolean {
    val map = SpecialHashMap()
    map["value1"] = 1
    map["value2"] = 2
    map["value3"] = 3
    map["1"] = 10
    map["2"] = 20
    map["3"] = 30
    map["(1, 5)"] = 100
    map["(5, 5)"] = 200
    map["(10, 5)"] = 300
    map["(1, 5, 3)"] = 400
    map["(5, 5, 4)"] = 500
    map["(10, 5, 5)"] = 600

    if (!checkResult(map.ploc[">=1"], mapOf("1" to 10, "2" to 20, "3" to 30))) {
        return false
    }
    if (!checkResult(map.ploc["<3"], mapOf("1" to 10, "2" to 20))) {
        return false
    }
    if (!checkResult(map.ploc[">0, >0"], mapOf("(1, 5)" to 100, "(5, 5)" to 200, "(10, 5)" to 300))) {
        return false
    }
    if (!checkResult(map.ploc[">=10, >0"], mapOf("(10, 5)" to 300))) {
        return false
    }
    if (!checkResult(map.ploc["<5, >=5, >=3"], mapOf("(1, 5, 3)" to 400))) {
        return false
    }
    return true
}

fun checkResult(result: Map<String, Int>, expected: Map<String, Int>): Boolean {
    if (result.size != expected.size) {
        return false
    }
    for ((key, value) in result) {
        if (!(key in expected && expected[key] == value)) {
            return false
        }
    }
    return true
}

fun test3(): Boolean {
    val map = SpecialHashMap()
    map["value1"] = 1
    map["value2"] = 2
    map["value3"] = 3
    map["1"] = 10
    map["2"] = 20
    map["3"] = 30
    map["1, 5"] = 100
    map["5, 5"] = 200
    map["10, 5"] = 300

    if (!checkResult(map.ploc[">0, >0, >0, >0"], emptyMap())) {
        return false
    }
    if (!checkResult(map.ploc[">5"], emptyMap())) {
        return false
    }
    try {
        map.ploc[">>2"]
        return false
    } catch (e: QueryException) {}
    try {
        map.ploc["12"]
        return false
    } catch (e: QueryException) {}
    try {
        map.ploc["2"]
        return false
    } catch (e: QueryException) {}
    try {
        map.ploc[">"]
        return false
    } catch (e: QueryException) {}
    try {
        map.ploc[""]
        return false
    } catch (e: QueryException) {}
    return true
}