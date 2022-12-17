package ru.netology.nmedia.helpers

object HelperNumbers {
    fun compactNumber(value: Long): String {
        var int: Long = value
        var frac: Long = 0
        val suffix = when {
            value / 1_000_000 > 0 -> {
                int = value / 1_000_000
                frac = value % 1_000_000 / 100_000
                "M"
            }
            value / 1_000 > 0 -> {
                int = value / 1_000
                if (int < 10) frac = value % 1_000 / 100
                "K"
            }
            else -> ""
        }
        val sb: StringBuilder = java.lang.StringBuilder()
        sb.append(int)
        if (frac > 0)
            sb.append(".$frac")
        sb.append(suffix)
        return sb.toString()
    }
}
