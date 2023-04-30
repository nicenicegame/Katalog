package component.builder.plugins

import model.Library
import model.Plugin
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
    val pluginId: String = ""
)

val PluginDialog = FC<PluginDialogProps> { props ->
    val builderContext = useContext(BuilderContext)
    val (pluginDialogState, setPluginDialogState) = useState(PluginDialogState())

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
                        label = Fragment.create {
                            +"Artifact ID"
                        }
                        onChange = { event ->
                            val value = (event as ChangeEvent<HTMLInputElement>).target.value
                            setPluginDialogState { it.copy(pluginId = value) }
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