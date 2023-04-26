package component

import mui.material.*
import mui.material.styles.TypographyVariant
import react.FC
import react.Props
import react.PropsWithChildren
import react.create
import react.dom.events.MouseEventHandler
import web.html.HTMLButtonElement

external interface SectionCardProps : Props, PropsWithChildren {
    var sectionName: String
    var onActionClick: MouseEventHandler<HTMLButtonElement>
}

val SectionCard = FC<SectionCardProps> { props ->
    Card {
        variant = PaperVariant.outlined
        CardHeader {
            title = Typography.create {
                variant = TypographyVariant.h6
                +props.sectionName
            }
        }
        CardContent {
            +props.children
        }
        CardActions {
            Button {
                size = Size.small
                onClick = props.onActionClick
                +"Add ${props.sectionName}"
            }
        }
    }
}