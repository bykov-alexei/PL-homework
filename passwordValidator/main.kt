import java.lang.Exception

fun checkTest(answers: List<String>, expected: List<String>): Boolean {
    if (answers.size == expected.size) {
        for (answer in answers) {
            if (answer !in expected) {
                return false
            }
        }
        return true
    } else {
        return false
    }
}

fun main() {
    val tests = listOf(::test1, ::test2, ::test3, ::test4, ::test5)
    val answers = listOf(
        listOf(UpperLowerCaseRule(), SpecialCharactersRule()).map { it.errorMessage },
        listOf(LengthRule(50), UpperLowerCaseRule(), SpecialCharactersRule(), FrequentWordsRule() ).map { it.errorMessage },
        listOf(),
        listOf(),
        listOf(EntropyRule(0.5)).map { it.errorMessage }
    )
    for ((i, test) in tests.withIndex()) {
        try {
            val answer = test()
            if (checkTest(answer, answers[i])) {
                println("Test ${i+1} passed")
            } else {
                println("Test ${i+1} not passed")
            }
        } catch (e: Exception) {
            print(e.message)
            println("Test ${i+1} not passed")
        }
    }
}

fun test1(): List<String> {
    val password = "123456789"
    val validator = Validator()
    validator.addRule(LengthRule(8))
    validator.addRule(UpperLowerCaseRule())
    try {
        validator.addRule(LengthRule(10))
    } catch (e: SuchRuleExists) {}
    validator.addRule(SpecialCharactersRule())
    validator.validate(password)
    return validator.errors
}

fun test2(): List<String> {
    val password = "228shadow228"
    val validator = Validator()
    validator.addRule(LengthRule(50))
    validator.addRule(UpperLowerCaseRule())
    validator.addRule(SpecialCharactersRule())
    validator.addRule(FrequentWordsRule())
    validator.addRule(EntropyRule(1.5))
    validator.validate(password)
    return validator.errors
}

fun test3(): List<String> {
    val password = "123"
    val validator = Validator()
    try {
        validator.validate(password)
    } catch (e: NoRulesException) {}
    return validator.errors
}

fun test4(): List<String> {
    val password = "Aaa321+-="
    val validator = Validator()
    validator.addRule(UpperLowerCaseRule())
    validator.addRule(SpecialCharactersRule())
    validator.validate(password)
    return validator.errors
}

fun test5(): List<String> {
    val password = "11111111"
    val validator = Validator()
    validator.addRule(EntropyRule(0.5))
    validator.validate(password)
    return validator.errors
}