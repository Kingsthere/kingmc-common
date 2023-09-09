package kingmc.util

/**
 * Thrown when trying to get [Version] but the format is incorrect
 *
 * @author kingsthere
 * @since 0.0.5
 */
class VersionFormatException(message: String?) : Exception(message)

/**
 * A utility class represents a version number for defining versions in specified
 * format
 *
 * @author kingsthere
 * @since 0.0.8
 */
class Version(source: CharSequence, val toString: String? = null) : Comparable<Version> {
    private var version: IntArray
    val suffix: String?

    init {
        val type = source.split("[- ]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        suffix = type.getOrNull(1)
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
     * Returns true if this version represents a snapshot version
     */
    val isSnapshot: Boolean
        get() = suffix.equals("SNAPSHOT", ignoreCase = true)

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

    companion object {
        /**
         * A common representation of `Version` to represent the latest version
         */
        val LATEST = Version("${Int.MAX_VALUE}.0.0", "LATEST")

        /**
         * A common representation of `Version` to represent the oldest version
         */
        val OLDEST = Version("0.0", "OLDEST")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Version

        return version.contentEquals(other.version)
    }

    override fun hashCode(): Int {
        return version.contentHashCode()
    }

    override fun toString(): String {
        toString?.let {
            return it
        }
        return if (suffix != null) {
            "${version.joinToString(".")}-$suffix"
        } else {
            version.joinToString(".")
        }
    }
}

/**
 * A range of values of type `Version`
 *
 * @author kingsthere
 * @since 0.0.8
 */
class VersionRange(val start: Version = Version.OLDEST, val end: Version = Version.LATEST) {
    operator fun contains(o: Version): Boolean {
        return o >= start && o <= end
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VersionRange) return false

        if (start != other.start) return false
        return end == other.end
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + end.hashCode()
        return result
    }

    override fun toString(): String {
        return "VersionRange(start=$start, end=$end)"
    }
}

operator fun Version.rangeTo(o: Version) = VersionRange(this, o)

/**
 * Convert a string to a `Version`
 *
 * @receiver the string to convert to
 * @return `Version`
 */
fun CharSequence.toVersion(): Version = Version(this)