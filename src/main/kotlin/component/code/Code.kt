package component.code

import VersionCatalogs
import component.CodeBlock
import component.CodeBlockLanguage
import generateTOML
import highlight.highlightElement
import mui.material.Typography
import provider.BuilderContext
import react.*
import react.dom.html.ReactHTML.code
import react.dom.html.ReactHTML.pre
import web.cssom.ClassName
import web.html.HTMLPreElement

val Code = FC<Props> {
    val builderContext = useContext(BuilderContext)
    val versionCatalogs = builderContext?.let {
        VersionCatalogs.create(
            versions = builderContext.versions,
            libraries = builderContext.libraries,
            plugins = builderContext.plugins,
            bundles = builderContext.bundles
        )
    } ?: VersionCatalogs.EMPTY

    CodeBlock {
        language = CodeBlockLanguage.INI
        code = versionCatalogs.generateTOML()
    }
}