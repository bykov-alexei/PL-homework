import java.util.*

fun isNumber(num: String): Boolean {
    for (ch in num) {
        if (ch.isDigit()) {
            return true
        }
    }
    return false
}

fun main() {
    val ops = arrayOf("+", "-", "*", "/")
    val parts = readLine()?.trim()?.split(" ")
    val stack = Stack<String>()
    if (parts != null) {
        for (part in parts.reversed()) {
            if (isNumber(part)) {
                stack.push(part);
            } else {
                if (part in ops) {
                    if (stack.size >= 2) {
                        val op1 = stack.pop()
                        val op2 = stack.pop()
                        val res = "($op1 $part $op2)"
                        stack.push(res)
                    } else {
                        print("Not enough numbers in expression :(")
                        return
                    }
                } else {
                    println("Hmm. Something's wrong with expression. (Wrong operation)")
                    return
                }
            }
        }
    } else {
        println("Missing expression")
    }
    if (stack.empty()) {
        println("Impossible. Resulting stack appears to be empty")
    } else if (stack.size > 1) {
        println("Hm. It seems you're messing with input. Expression parsed, but it's wrong anyway. Resulting stack contains more than 1 element")
    } else {
        println(stack.pop())
    }
}