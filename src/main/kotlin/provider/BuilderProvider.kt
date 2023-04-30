package provider

import model.Bundle
import model.Library
import model.Plugin
import model.VersionRef
import react.*

data class BuilderState(
    val versions: List<VersionRef> = emptyList(),
    val libraries: List<Library> = emptyList(),
    val plugins: List<Plugin> = emptyList(),
    val bundles: List<Bundle> = emptyList()
)

interface BuilderContextProps {
    val versions: List<VersionRef>
    val libraries: List<Library>
    val plugins: List<Plugin>
    val bundles: List<Bundle>
    val addVersion: (VersionRef) -> Unit
    val removeVersion: (VersionRef) -> Unit
    val addLibrary: (Library) -> Unit
    val removeLibrary: (Library) -> Unit
    val addPlugin: (Plugin) -> Unit
    val removePlugin: (Plugin) -> Unit
    val addBundle: (Bundle) -> Unit
    val removeBundle: (Bundle) -> Unit
}

val BuilderContext = createContext<BuilderContextProps>()

external interface BuilderProviderProps : Props, PropsWithChildren

val BuilderProvider = FC<BuilderProviderProps> { props ->
    val (builderState, setBuilderState) = useState(BuilderState())

    val addVersion: (VersionRef) -> Unit = { version ->
        setBuilderState {
            it.copy(versions = it.versions + version)
        }
    }

    val removeVersion: (VersionRef) -> Unit = { version ->
        setBuilderState {
            it.copy(versions = it.versions - version)
        }
    }

    val addLibrary: (Library) -> Unit = { library ->
        setBuilderState {
            it.copy(libraries = it.libraries + library)
        }
    }

    val removeLibrary: (Library) -> Unit = { library ->
        setBuilderState {
            it.copy(libraries = it.libraries - library)
        }
    }

    val addPlugin: (Plugin) -> Unit = { plugin ->
        setBuilderState {
            it.copy(plugins = it.plugins + plugin)
        }
    }

    val removePlugin: (Plugin) -> Unit = { plugin ->
        setBuilderState {
            it.copy(plugins = it.plugins - plugin)
        }
    }

    val addBundle: (Bundle) -> Unit = { bundle ->
        setBuilderState {
            it.copy(bundles = it.bundles + bundle)
        }
    }

    val removeBundle: (Bundle) -> Unit = { bundle ->
        setBuilderState {
            it.copy(bundles = it.bundles - bundle)
        }
    }

    BuilderContext.Provider {
        value = object : BuilderContextProps {
            override val versions = builderState.versions
            override val libraries = builderState.libraries
            override val plugins = builderState.plugins
            override val bundles = builderState.bundles
            override val addVersion = addVersion
            override val removeVersion = removeVersion
            override val addLibrary = addLibrary
            override val removeLibrary = removeLibrary
            override val addPlugin = addPlugin
            override val removePlugin = removePlugin
            override val addBundle = addBundle
            override val removeBundle = removeBundle
        }
        +props.children
    }
}