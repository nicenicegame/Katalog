package component.builder.libraries

import model.Library
import mui.material.*
import mui.system.responsive
import provider.BuilderContext
import react.*
import react.dom.events.ChangeEvent
import react.dom.onChange
import web.html.HTMLInputElement

external interface LibraryDialogProps : Props {
    var isOpen: Boolean
    var onClose: () -> Unit
}

data class LibraryDialogState(
    val alias: String = "",
    val artifactId: String = ""
)

val LibraryDialog = FC<LibraryDialogProps> { props ->
    val builderContext = useContext(BuilderContext)
    val (libraryDialogState, setLibraryDialogState) = useState(LibraryDialogState())

    Dialog {
        open = props.isOpen
        onClose = { _, _ ->
            props.onClose()
        }
        DialogTitle {
            +"Add library"
        }
        DialogContent {
            Grid {
                container = true
                spacing = responsive(2)
                Grid {
                    item = true
                    asDynamic().xs = 3
                    TextField {
                        size = Size.small
                        fullWidth = true
                        variant = FormControlVariant.outlined
                        label = Fragment.create {
                            +"Alias"
                        }
                        onChange = { event ->
                            val value = (event as ChangeEvent<HTMLInputElement>).target.value
                            setLibraryDialogState { it.copy(alias = value) }
                        }
                    }
                }
                Grid {
                    item = true
                    asDynamic().xs = 9
                    TextField {
                        size = Size.small
                        fullWidth = true
                        variant = FormControlVariant.outlined
                        label = Fragment.create {
                            +"Artifact ID"
                        }
                        onChange = { event ->
                            val value = (event as ChangeEvent<HTMLInputElement>).target.value
                            setLibraryDialogState { it.copy(artifactId = value) }
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
                    builderContext?.addLibrary?.invoke(
                        Library.create(
                            alias = libraryDialogState.alias,
                            artifactId = libraryDialogState.artifactId
                        )
                    )
                    props.onClose()
                }
                +"Submit"
            }
        }
    }
}