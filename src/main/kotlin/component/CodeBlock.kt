package component

import highlight.highlightElement
import react.*
import react.dom.html.ReactHTML.code
import react.dom.html.ReactHTML.pre
import web.cssom.ClassName
import web.html.HTMLPreElement

external interface CodeBlockProps : Props {
    var code: String
    var language: CodeBlockLanguage
}

val CodeBlock = FC<CodeBlockProps> { props ->
    val codeBlockRef = createRef<HTMLPreElement>()

    useEffect(props.code) {
        codeBlockRef.current?.let { element -> highlightElement(element) }
    }

    pre {
        ref = codeBlockRef
        code {
            className = ClassName("language-${props.language}")
            +props.code
        }
    }
}

enum class CodeBlockLanguage(name: String) {
    INI("ini");
}