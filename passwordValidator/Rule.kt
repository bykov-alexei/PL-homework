import java.io.File
import kotlin.math.log2

interface Rule {
    val errorMessage: String
    fun validate(string: String): Boolean
}

class LengthRule(val length: Int): Rule {
    override val errorMessage = "Password should contain more than $length characters"

    override fun validate(string: String): Boolean {
        return string.length > length
    }
}

class UpperLowerCaseRule: Rule {
    override val errorMessage = "Password must contain characters in both cases (lower and upper)"

    override fun validate(string: String): Boolean {
        var upper = false
        var lower = false
        for (ch in string) {
            if (ch.isLowerCase()) {
                lower = true
            }
            if (ch.isUpperCase()) {
                upper = true
            }
        }
        return lower && upper
    }
}

class SpecialCharactersRule: Rule {
    override val errorMessage = "Password must contain special characters"

    override fun validate(string: String): Boolean {
        for (ch in string) {
            if (!ch.isLetterOrDigit()) {
                return true
            }
        }
        return false
    }
}

class FrequentWordsRule: Rule {
    override val errorMessage = "Password contains frequent combination"

    override fun validate(string: String): Boolean {
        val words = File("pswd-dict.txt").readLines().map {  it.trim() }
        for (word in words) {
            if (word in string) {
                return false
            }
        }
        return true
    }
}

class EntropyRule(val threshold: Double): Rule {
    override val errorMessage = "Password has low entropy"

    override fun validate(string: String): Boolean {
        val freq = HashMap<Char, Int>()
        for (ch in string) {
            if (ch in freq) {
                freq[ch] = freq[ch]!! + 1
            } else {
                freq[ch] = 1
            }
        }
        val n = freq.size
        var s = 0.0
        for ((ch, count) in freq) {
            s += (count.toDouble() / n) * log2(count.toDouble() / n)
        }
        s = -s
        return s > threshold
    }
}