package dev.definedentity.serotonin.utils

import com.google.common.base.CaseFormat
import java.util.Locale

object TextFormatting {
    /** ControllerBlock -> controller_block */
    fun toLowerCaseUnder(string: String): String {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, string)
    }

    /** controller_block.legacy -> Controller Block (Legacy) */
    fun toEnglishName(name: Any): String {
        return name.toString().lowercase(Locale.ROOT).split("_").joinToString(" ") {
            it.replaceFirstChar { char -> char.uppercase() }
        }
    }

    /** For example 20 -> XX, 18 -> XVIII */
    fun toRomanNumeral(number: Int): String {
        return "I".repeat(number)
            .replace("IIIII", "V")
            .replace("IIII", "IV")
            .replace("VV", "X")
            .replace("VIV", "IX")
            .replace("XXXXX", "L")
            .replace("XXXX", "XL")
            .replace("LL", "C")
            .replace("LXL", "XC")
            .replace("CCCCC", "D")
            .replace("CCCC", "CD")
            .replace("DD", "M")
            .replace("DCD", "CM")
    }
}
