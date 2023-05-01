package component

import emotion.react.css
import emotion.styled.styled
import highlight.highlightElement
import mui.material.Paper
import mui.material.PaperVariant
import mui.material.styles.Theme
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.code
import react.dom.html.ReactHTML.pre
import web.cssom.*
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

    Paper {
        variant = PaperVariant.outlined
        sx {
            paddingTop = 1.rem
            paddingBottom = 1.rem
            paddingLeft = 2.rem
            paddingRight = 2.rem
            overflow = Overflow.scroll
        }
        pre {
            ref = codeBlockRef
            code {
                className = ClassName("language-${props.language}")
                +props.code
            }
        }
    }
}

enum class CodeBlockLanguage(name: String) {
    INI("ini");
}