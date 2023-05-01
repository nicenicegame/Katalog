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
                key = library.toString()
                secondaryAction = IconButton.create {
                    edge = IconButtonEdge.end
                    onClick = { builderContext.removeLibrary(library) }
                    CancelRounded()
                }
                ListItemText {
                    primary = ReactNode(library.alias)
                    secondary = ReactNode(library.artifactId)
                }
            }
        }
    }
}