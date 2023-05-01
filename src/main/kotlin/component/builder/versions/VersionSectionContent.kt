package component.builder.versions

import mui.icons.material.CancelRounded
import mui.material.*
import provider.BuilderContext
import react.*

val VersionSectionContent = FC<Props> {
    val builderContext = useContext(BuilderContext)

    List {
        builderContext?.versions?.map { version ->
            ListItem {
                key = version.toString()
                secondaryAction = IconButton.create {
                    edge = IconButtonEdge.end
                    onClick = { builderContext.removeVersion(version) }
                    CancelRounded()
                }
                ListItemText {
                    primary = ReactNode(version.alias)
                    secondary = ReactNode(version.number)
                }
            }
        }
    }
}