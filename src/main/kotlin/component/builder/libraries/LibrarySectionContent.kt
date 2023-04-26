package component.builder.libraries

import model.VersionNumber
import model.VersionRef
import mui.icons.material.CancelRounded
import mui.material.*
import provider.BuilderContext
import react.*

val LibrarySectionContent = FC<Props> {
    val builderContext = useContext(BuilderContext)

    List {
        builderContext?.libraries?.map { library ->
            ListItem {
                key = when (library.version) {
                    is VersionNumber -> "${library.alias}-${library.group}-${library.name}-${library.version.value}"
                    is VersionRef -> "${library.alias}-${library.group}-${library.name}-${library.version.number}"
                }
                secondaryAction = IconButton.create {
                    edge = IconButtonEdge.end
//                    onClick = { builderContext.removeVersion(version) }
                    CancelRounded()
                }
                ListItemText {
                    primary = Fragment.create {
                        +library.alias
                    }
                    secondary = Fragment.create {
                        +library.artifactId
                    }
                }
            }
        }
    }
}