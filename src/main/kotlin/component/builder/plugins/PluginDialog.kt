package component.builder.plugins

import model.Plugin
import model.VersionRef
import mui.material.*
import mui.system.responsive
import provider.BuilderContext
import react.*
import react.dom.events.ChangeEvent
import react.dom.onChange
import web.html.HTMLInputElement

external interface PluginDialogProps : Props {
    var isOpen: Boolean
    var onClose: () -> Unit
}

data class PluginDialogState(
    val alias: String = "",
    val pluginId: String = "",
    val isUseVersionRef: Boolean = false,
    val selectedRef: String? = null
)

val PluginDialog = FC<PluginDialogProps> { props ->
    val builderContext = useContext(BuilderContext)
    val (pluginDialogState, setPluginDialogState) = useState(PluginDialogState())
    val versionRefs = useMemo(builderContext?.versions) {
        builderContext?.versions?.map { it.alias }?.toTypedArray() ?: emptyArray()
    }

    Dialog {
        open = props.isOpen
        onClose = { _, _ ->
            props.onClose()
        }
        DialogTitle {
            +"Add plugin"
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
                        label = ReactNode("Alias")
                        onChange = { event ->
                            val value = (event as ChangeEvent<HTMLInputElement>).target.value
                            setPluginDialogState { it.copy(alias = value) }
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
                        label = ReactNode("Plugin ID")
                        onChange = { event ->
                            val value = (event as ChangeEvent<HTMLInputElement>).target.value
                            setPluginDialogState { it.copy(pluginId = value) }
                        }
                    }
                }
                Grid {
                    item = true
                    asDynamic().xs = 6
                    FormControlLabel {
                        label = ReactNode("Use version reference")
                        control = Checkbox.create {
                            checked = pluginDialogState.isUseVersionRef
                            onChange = { _, isChecked ->
                                setPluginDialogState { it.copy(isUseVersionRef = isChecked) }
                            }
                        }
                    }
                }
                if (pluginDialogState.isUseVersionRef) {
                    Grid {
                        item = true
                        asDynamic().xs = 6
                        @Suppress("UPPER_BOUND_VIOLATED")
                        Autocomplete<AutocompleteProps<String>> {
                            size = "small"
                            value = pluginDialogState.selectedRef
                            onChange = { _, value, _, _ ->
                                setPluginDialogState { it.copy(selectedRef = value) }
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
                    builderContext?.addPlugin?.invoke(
                        if (pluginDialogState.isUseVersionRef && pluginDialogState.selectedRef != null)
                            Plugin.create(
                                alias = pluginDialogState.alias,
                                pluginId = pluginDialogState.pluginId,
                                ref = builderContext.versions.find { it.alias === pluginDialogState.selectedRef }
                                    ?: VersionRef("", "")
                            )
                        else
                            Plugin.create(
                                alias = pluginDialogState.alias,
                                pluginId = pluginDialogState.pluginId
                            )
                    )
                    props.onClose()
                }
                +"Submit"
            }
        }
    }
}