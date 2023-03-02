package kingmc.common.configure

import com.electronwill.nightconfig.core.*
import com.electronwill.nightconfig.core.file.CommentedFileConfig
import com.electronwill.nightconfig.core.file.FileConfig
import com.electronwill.nightconfig.core.file.FileConfigBuilder

typealias NightConfigFormat<C> = ConfigFormat<C>
typealias NightConfig = Config
typealias NightCommentedFileConfig = CommentedFileConfig
typealias NightCommentedConfig = CommentedConfig
typealias NightFileConfig = FileConfig
typealias NightFileConfigBuilder = FileConfigBuilder
typealias NightUnmodifiableConfig = UnmodifiableConfig
typealias NightUnmodifiableCommentedConfig = UnmodifiableCommentedConfig