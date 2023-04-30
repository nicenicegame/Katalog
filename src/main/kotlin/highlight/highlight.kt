@file:JsModule("highlight.js/lib/core")
@file:JsNonModule

package highlight

import web.html.HTMLElement

external fun highlightElement(element: HTMLElement)

external fun registerLanguage(languageName: String, languageFunction: dynamic)