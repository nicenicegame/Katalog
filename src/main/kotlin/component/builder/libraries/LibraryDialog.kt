package component.builder.libraries

import model.Library
import model.VersionRef
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
    val artifactId: String = "",
    val isUseVersionRef: Boolean = false,
    val selectedRef: String? = null
)

val LibraryDialog = FC<LibraryDialogProps> { props ->
    val builderContext = useContext(BuilderContext)
    val (libraryDialogState, setLibraryDialogState) = useState(LibraryDialogState())
    val versionRefs = useMemo(builderContext?.versions) {
        builderContext?.versions?.map { it.alias }?.toTypedArray() ?: emptyArray()
    }

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
                Grid {
                    item = true
                    asDynamic().xs = 6
                    FormControlLabel {
                        label = Fragment.create {
                            +"Use version reference"
                        }
                        control = Checkbox.create {
                            checked = libraryDialogState.isUseVersionRef
                            onChange = { _, isChecked ->
                                setLibraryDialogState { it.copy(isUseVersionRef = isChecked) }
                            }
                        }
                    }
                }
                if (libraryDialogState.isUseVersionRef) {
                    Grid {
                        item = true
                        asDynamic().xs = 6
                        @Suppress("UPPER_BOUND_VIOLATED")
                        Autocomplete<AutocompleteProps<String>> {
                            size = "small"
                            value = libraryDialogState.selectedRef
                            onChange = { _, value, _, _ ->
                                setLibraryDialogState { it.copy(selectedRef = value) }
                            }
                            options = versionRefs
                            renderInput = { params ->
                                TextField.create {
                                    label = ReactNode("Version alias")
                                    +params
                                }
                            }
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
                        if (libraryDialogState.isUseVersionRef && libraryDialogState.selectedRef != null)
                            Library.create(
                                alias = libraryDialogState.alias,
                                artifactId = libraryDialogState.artifactId,
                                ref = builderContext.versions.find { it.alias === libraryDialogState.selectedRef }
                                    ?: VersionRef("", "")
                            )
                        else
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