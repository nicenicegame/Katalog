import highlight.registerLanguage
import kotlinext.js.require
import react.create
import react.dom.client.createRoot
import web.dom.document

fun main() {
    require("highlight.js/styles/github.css")
    registerLanguage("ini", require("highlight.js/lib/languages/ini"))
    val container = document.createElement("div")
    document.body.appendChild(container)
    createRoot(container).render(App.create())
}