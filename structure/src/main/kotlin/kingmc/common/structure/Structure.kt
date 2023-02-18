package kingmc.common.structure

import java.io.File
import kotlin.properties.Delegates

/**
 * The jar file of current code running that is
 * the source of this class
 * 
 * @author kingsthere
 * @since 0.0.1
 */
var Class<*>.sourceFile: File by Delegates.notNull()