package component.builder.bundles

import provider.BuilderContext
import react.FC
import react.Props
import react.useContext

val BundleSectionContent = FC<Props> {
    val builderContext = useContext(BuilderContext)

    builderContext?.bundles?.forEach {
        +it.toString()
    }
}