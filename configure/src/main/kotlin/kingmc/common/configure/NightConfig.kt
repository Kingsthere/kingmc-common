package kingmc.common.configure

import com.electronwill.nightconfig.core.*
import com.electronwill.nightconfig.core.file.CommentedFileConfig
import com.electronwill.nightconfig.core.file.FileConfig
import com.electronwill.nightconfig.core.file.FileConfigBuilder
import java.util.Properties

typealias NightConfigFormat<C> = ConfigFormat<C>
typealias NightConfig = Config
typealias NightCommentedFileConfig = CommentedFileConfig
typealias NightCommentedConfig = CommentedConfig
typealias NightFileConfig = FileConfig
typealias NightFileConfigBuilder = FileConfigBuilder
typealias NightUnmodifiableConfig = UnmodifiableConfig
typealias NightUnmodifiableCommentedConfig = UnmodifiableCommentedConfig

private fun readProperties(properties: Properties, unmodifiableConfig: NightUnmodifiableConfig) {
    unmodifiableConfig.valueMap().forEach { (key, value) ->
        if (value is NightUnmodifiableConfig) {
            readProperties(properties, unmodifiableConfig)
        } else {
            properties.setProperty(key, value.toString())
        }
    }
}

/**
 * Load properties from the given [config]
 */
fun Properties.load(config: NightUnmodifiableConfig) {
    readProperties(this, config)
}