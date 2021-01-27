import java.lang.Exception

class QueryException(message: String?) : Exception(message)

class SpecialHashMap: HashMap<String, Int>() {
    var iloc = mutableListOf<Int>()
    var ploc = QueryHandler()

    override fun put(key: String, value: Int): Int? {
        val r = super.put(key, value)
        iloc = this.keys.toSortedSet().map { this[it]!! }.toMutableList()
        return r
    }

    inner class QueryHandler() {
        operator fun get(query: String): HashMap<String, Int> {
            val queries = query.split(',').map { it.trim() }
            for (query in queries) {
                for (ch in query) {
                    if (!ch.isDigit() && ch !in setOf('<', '>', '=')) {
                        throw QueryException("Unexpected character \"$ch\" in expression")
                    }
                }
            }
            val result = HashMap<String, Int>()
            for ((key, value) in this@SpecialHashMap) {
                if (compareKey(key, queries)) {
                    result[key] = value
                }
            }
            return result
        }

        private fun compareKey(key: String, queries: List<String>): Boolean {
            var key = key
            if (key[0] == '(') {
                key = key.slice(1 until key.length)
            }
            if (key[key.length-1] == ')') {
                key = key.slice(0 until key.length-1)
            }
            val parts = key.split(',').map { it.trim() }
            val values = parts.map { it.toIntOrNull() }
            for (value in values) {
                if (value == null) {
                    return false
                }
            }
            if (values.size != queries.size) {
                return false
            }
            for ((value, query) in values.zip(queries)) {
                if (!checkCondition(value!!, query)) {
                    return false
                }
            }
            return true
        }

        private fun checkCondition(value: Int, query: String): Boolean {
            val ops = setOf("<>", "<=", ">=", ">", "<", "=")
            var op: String? = null
            var rawNum: String? = null
            if (query.length < 2) {
                throw QueryException("Wrong query")
            }
            for (it in ops) {
                if (query.slice(it.indices) == it) {
                    op = query.slice(it.indices)
                    rawNum = query.slice(it.length until query.length)
                    break
                }
            }
            if (op == null) {
                throw QueryException("Missing operator")
            }
            val num = rawNum!!.toIntOrNull()
            if (num == null) {
                throw QueryException("Wrong number in query (\"$num\")")
            }
            return when (op) {
                "<>" -> num != value
                "=" -> num == value
                "<" -> value < num
                ">" -> value > num
                ">=" -> value >= num
                "<=" -> value <= num
                else -> false
            }
        }
    }
}