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
                key = "${version.alias}-${version.number}"
                secondaryAction = IconButton.create {
                    edge = IconButtonEdge.end
                    onClick = { builderContext.removeVersion(version) }
                    CancelRounded()
                }
                ListItemText {
                    primary = Fragment.create {
                        +version.alias
                    }
                    secondary = Fragment.create {
                        +version.number
                    }
                }
            }
        }
    }
}