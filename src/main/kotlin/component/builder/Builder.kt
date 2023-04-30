package component.builder

import component.SectionCard
import component.builder.bundles.BundleSectionContent
import component.builder.libraries.LibraryDialog
import component.builder.libraries.LibrarySectionContent
import component.builder.plugins.PluginDialog
import component.builder.plugins.PluginSectionContent
import component.builder.versions.VersionDialog
import component.builder.versions.VersionSectionContent
import mui.material.*
import mui.system.responsive
import react.FC
import react.Props
import react.useState

data class DialogState(
    val isVersionDialogOpen: Boolean = false,
    val isLibraryDialogOpen: Boolean = false,
    val isPluginDialogOpen: Boolean = false,
    val isBundleDialogOpen: Boolean = false,
)

val SectionBuilder = FC<Props> {
    val (dialogState, setDialogState) = useState(DialogState())

    Stack {
        spacing = responsive(2)
        SectionCard {
            sectionName = "Versions"
            onActionClick = {
                setDialogState { it.copy(isVersionDialogOpen = true) }
            }
            VersionSectionContent()
            VersionDialog {
                isOpen = dialogState.isVersionDialogOpen
                onClose = {
                    setDialogState { it.copy(isVersionDialogOpen = false) }
                }
            }
        }
        SectionCard {
            sectionName = "Libraries"
            onActionClick = {
                setDialogState { it.copy(isLibraryDialogOpen = true) }
            }
            LibrarySectionContent()
            LibraryDialog {
                isOpen = dialogState.isLibraryDialogOpen
                onClose = {
                    setDialogState { it.copy(isLibraryDialogOpen = false) }
                }
            }
        }
        SectionCard {
            sectionName = "Plugins"
            onActionClick = {
                setDialogState { it.copy(isPluginDialogOpen = true) }
            }
            PluginSectionContent()
            PluginDialog {
                isOpen = dialogState.isPluginDialogOpen
                onClose = {
                    setDialogState { it.copy(isPluginDialogOpen = false) }
                }
            }
        }
        SectionCard {
            sectionName = "Bundles"
            onActionClick = {
                setDialogState { it.copy(isBundleDialogOpen = true) }
            }
            BundleSectionContent()
        }
    }
}