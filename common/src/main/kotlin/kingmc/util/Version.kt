package kingmc.util

/**
 * Thrown when trying to get [Version] but the format is incorrect
 *
 * @since 0.0.5
 * @author kingsthere
 */
class VersionFormatException(message: String?) : Exception(message)

class Version(source: String) : Comparable<Version> {
    private var version: IntArray

    init {
        val type = source.split("[- ]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val args = type[0].split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        version = when (args.size) {
            2 -> {
                intArrayOf(-1, args[0].toInt(), args[1].toInt())
            }
            3 -> {
                intArrayOf(args[0].toInt(), args[1].toInt(), args[2].toInt())
            }
            else -> {
                throw VersionFormatException("Incorrect version format $source")
            }
        }
    }

    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    override fun compareTo(other: Version): Int {
        return if (version[0] > other.version[0]) {
            1
        } else if (version[0] == other.version[0]) {
            if (version[1] > other.version[1]) {
                1
            } else if (version[1] == other.version[1]) {
                version[2].compareTo(other.version[2])
            } else {
                -1
            }
        } else {
            -1
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Version

        if (!version.contentEquals(other.version)) return false

        return true
    }

    override fun hashCode(): Int {
        return version.contentHashCode()
    }

    override fun toString(): String {
        return "Version(version=${version.contentToString()})"
    }
}