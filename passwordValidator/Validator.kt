import java.lang.Exception

class NoRulesException: Exception()
class SuchRuleExists: Exception()

class Validator {
    val rules = mutableListOf<Rule>()
    var errors = mutableListOf<String>()

    fun addRule(newRule: Rule) {
        for (rule in rules) {
            if (rule::class == newRule::class) {
                throw SuchRuleExists()
            }
        }
        rules.add(newRule)
    }

    fun validate(string: String): Boolean {
        if (rules.isEmpty()) {
            throw NoRulesException()
        }

        errors = mutableListOf<String>()
        for (rule in rules) {
            if (!rule.validate(string)) {
                errors.add(rule.errorMessage)
            }
        }
        return errors.isEmpty()
    }
}