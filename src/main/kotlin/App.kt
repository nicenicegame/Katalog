import component.builder.SectionBuilder
import component.code.Code
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import provider.BuilderProvider
import react.*
import web.cssom.Auto
import web.cssom.px
import web.cssom.rem

val App = FC<Props> {
    val (selectedTabValue, setSelectedTabValue) = useState(0)

    BuilderProvider {
        Container {
            maxWidth = "sm"
            Typography {
                variant = TypographyVariant.h4
                +"Katalog"
            }
            Box {
                sx {
                    marginBottom = 1.rem
                }
                Tabs {
                    value = selectedTabValue
                    onChange = { _, value: Int ->
                        setSelectedTabValue { value }
                    }
                    Tab {
                        label = Fragment.create {
                            +"Builder"
                        }
                    }
                    Tab {
                        label = Fragment.create {
                            +"Code"
                        }
                    }
                }
                Divider()
            }
            when (selectedTabValue) {
                0 -> SectionBuilder()
                1 -> Code()
            }
        }
    }
}

