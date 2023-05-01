package component.code

import VersionCatalogs
import component.CodeBlock
import component.CodeBlockLanguage
import generateTOML
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import mui.icons.material.CheckRounded
import mui.material.Box
import mui.material.Button
import mui.material.ButtonVariant
import mui.system.sx
import provider.BuilderContext
import react.*
import web.buffer.Blob
import web.cssom.Display
import web.cssom.JustifyContent
import web.cssom.rem
import web.dom.document
import web.html.HTMLAnchorElement
import web.navigator.navigator
import web.timers.clearTimeout
import web.timers.setTimeout
import web.url.URL
import kotlin.time.DurationUnit
import kotlin.time.toDuration

val scope = MainScope()

val Code = FC<Props> {
    val (isCopySuccessSnackbarOpen, setIsCopySuccessSnackbarOpen) = useState(false)
    val builderContext = useContext(BuilderContext)
    val versionCatalogs = builderContext?.let {
        VersionCatalogs.create(
            versions = builderContext.versions,
            libraries = builderContext.libraries,
            plugins = builderContext.plugins,
            bundles = builderContext.bundles
        )
    } ?: VersionCatalogs.EMPTY

    useEffect(isCopySuccessSnackbarOpen) {
        val timer = setTimeout(3.toDuration(DurationUnit.SECONDS)) {
            setIsCopySuccessSnackbarOpen { false }
        }
        cleanup {
            clearTimeout(timer)
        }
    }

    CodeBlock {
        language = CodeBlockLanguage.INI
        code = versionCatalogs.generateTOML()
    }
    Box {
        sx {
            display = Display.flex
            justifyContent = JustifyContent.flexEnd
            gap = 1.rem
            marginTop = 1.rem
        }
        Button {
            disabled = versionCatalogs == VersionCatalogs.EMPTY
            variant = ButtonVariant.contained
            onClick = {
                val blob = Blob(arrayOf(versionCatalogs.generateTOML()))
                val url = URL.createObjectURL(blob)
                val link = document.createElement("a") as HTMLAnchorElement
                link.download = "libs.versions.toml"
                link.href = url
                link.click()
                URL.revokeObjectURL(url)
            }
            +"Download"
        }
        Button {
            disabled = versionCatalogs == VersionCatalogs.EMPTY
            variant = ButtonVariant.outlined
            onClick = {
                scope.launch {
                    try {
                        navigator.clipboard.writeText(versionCatalogs.generateTOML()).await()
                        setIsCopySuccessSnackbarOpen { true }
                    } catch (_: Exception) {
                    }
                }
            }
            endIcon = if (isCopySuccessSnackbarOpen) CheckRounded.create() else null
            if (isCopySuccessSnackbarOpen) {
                +"Copied"
            } else {
                +"Copy"
            }
        }
    }
}