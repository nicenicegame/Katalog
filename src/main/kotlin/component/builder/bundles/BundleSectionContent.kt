package component.builder.bundles

import mui.icons.material.CancelRounded
import mui.material.*
import mui.system.responsive
import provider.BuilderContext
import react.*
import web.cssom.rem

val BundleSectionContent = FC<Props> {
    val builderContext = useContext(BuilderContext)

    List {
        builderContext?.bundles?.map { bundle ->
            ListItem {
                key = bundle.toString()
                secondaryAction = IconButton.create {
                    edge = IconButtonEdge.end
                    onClick = { builderContext.removeBundle(bundle) }
                    CancelRounded()
                }
                ListItemText {
                    primary = ReactNode(bundle.alias)
                    secondary = Stack.create {
                        direction = responsive(StackDirection.row)
                        spacing = responsive(0.5.rem)
                        bundle.group.map {
                            Chip {
                                key = it.toString()
                                label = ReactNode(it.alias)
                            }
                        }
                    }
                }
            }
        }
    }
}