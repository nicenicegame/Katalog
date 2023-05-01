package component.builder.plugins

import model.VersionNumber
import model.VersionRef
import mui.icons.material.CancelRounded
import mui.material.*
import provider.BuilderContext
import react.*

val PluginSectionContent = FC<Props> {
    val builderContext = useContext(BuilderContext)

    List {
        builderContext?.plugins?.map { plugin ->
            ListItem {
                key = when (plugin.version) {
                    is VersionNumber -> "${plugin.alias}-${plugin.id}-${plugin.version.value}"
                    is VersionRef -> "${plugin.alias}-${plugin.id}-${plugin.version.number}"
                }
                secondaryAction = IconButton.create {
                    edge = IconButtonEdge.end
                    onClick = { builderContext.removePlugin(plugin) }
                    CancelRounded()
                }
                ListItemText {
                    primary = Fragment.create {
                        +plugin.alias
                    }
                    secondary = Fragment.create {
                        +plugin.pluginId
                    }
                }
            }
        }
    }
}