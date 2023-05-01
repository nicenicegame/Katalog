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
                key = plugin.toString()
                secondaryAction = IconButton.create {
                    edge = IconButtonEdge.end
                    onClick = { builderContext.removePlugin(plugin) }
                    CancelRounded()
                }
                ListItemText {
                    primary = ReactNode(plugin.alias)
                    secondary = ReactNode(plugin.pluginId)
                }
            }
        }
    }
}