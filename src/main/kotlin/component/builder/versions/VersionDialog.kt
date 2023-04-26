package component.builder.versions

import model.VersionRef
import mui.material.*
import mui.system.responsive
import provider.BuilderContext
import react.*
import react.dom.events.ChangeEvent
import react.dom.onChange
import web.html.HTMLInputElement

external interface VersionDialogProps : Props {
    var isOpen: Boolean
    var onClose: () -> Unit
}

data class VersionDialogState(
    val alias: String = "",
    val version: String = ""
)

val VersionDialog = FC<VersionDialogProps> { props ->
    val builderContext = useContext(BuilderContext)
    val (versionDialogState, setVersionDialogState) = useState(VersionDialogState())

    Dialog {
        open = props.isOpen
        onClose = { _, _ ->
            props.onClose()
        }
        DialogTitle {
            +"Add artifactId"
        }
        DialogContent {
            Grid {
                container = true
                spacing = responsive(2)
                Grid {
                    item = true
                    asDynamic().xs = 6
                    TextField {
                        size = Size.small
                        fullWidth = true
                        variant = FormControlVariant.outlined
                        label = Fragment.create {
                            +"Alias"
                        }
                        onChange = { event ->
                            val value = (event as ChangeEvent<HTMLInputElement>).target.value
                            setVersionDialogState { it.copy(alias = value) }
                        }
                    }
                }
                Grid {
                    item = true
                    asDynamic().xs = 6
                    TextField {
                        size = Size.small
                        fullWidth = true
                        variant = FormControlVariant.outlined
                        label = Fragment.create {
                            +"Version number"
                        }
                        onChange = { event ->
                            val value = (event as ChangeEvent<HTMLInputElement>).target.value
                            setVersionDialogState { it.copy(version = value) }
                        }
                    }
                }
            }
        }
        DialogActions {
            Button {
                onClick = { props.onClose() }
                +"Cancel"
            }
            Button {
                onClick = {
                    builderContext?.addVersion?.invoke(
                        VersionRef(
                            alias = versionDialogState.alias,
                            number = versionDialogState.version
                        )
                    )
                    props.onClose()
                }
                +"Submit"
            }
        }
    }
}