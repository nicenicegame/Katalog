package component.builder.bundles

import model.Bundle
import mui.material.*
import mui.system.responsive
import provider.BuilderContext
import react.*
import react.dom.events.ChangeEvent
import react.dom.onChange
import web.html.HTMLInputElement

external interface BundleDialogProps : Props {
    var isOpen: Boolean
    var onClose: () -> Unit
}

data class BundleDialogState(
    val alias: String = "",
    val selectedRefs: List<String> = emptyList()
)

val BundleDialog = FC<BundleDialogProps> { props ->
    val builderContext = useContext(BuilderContext)
    val (bundleDialogState, setBundleDialogState) = useState(BundleDialogState())
    val libraryRefs = useMemo(builderContext?.versions) {
        builderContext?.libraries?.map { it.alias }?.toTypedArray() ?: emptyArray()
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
                            setBundleDialogState { it.copy(alias = value) }
                        }
                    }
                }
                Grid {
                    item = true
                    asDynamic().xs = 9
                    @Suppress("UPPER_BOUND_VIOLATED")
                    Autocomplete<AutocompleteProps<String>> {
                        multiple = true
                        size = "small"
                        value = bundleDialogState.selectedRefs.toTypedArray()
                        onChange = { _, value: Array<String>, _, _ ->
                            setBundleDialogState {
                                it.copy(selectedRefs = value.toList())
                            }
                        }
                        options = libraryRefs
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
        DialogActions {
            Button {
                onClick = { props.onClose() }
                +"Cancel"
            }
            Button {
                onClick = {
                    builderContext?.addBundle?.invoke(
                        Bundle(
                            alias = bundleDialogState.alias,
                            group = builderContext.libraries.filter {
                                it.alias in bundleDialogState.selectedRefs
                            }
                        )
                    )
                    props.onClose()
                }
                +"Submit"
            }
        }
    }
}