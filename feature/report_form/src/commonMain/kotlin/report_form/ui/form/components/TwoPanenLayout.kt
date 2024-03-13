package report_form.ui.form.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

/*
It it is not depends on external files ,so can be paste direcly
 */

/**
 * Its allow us show the content in pane mode in expanded and the medium screen.
 * in the Compact screen it will show the pane2 top of pane1 as by hiding the pane1
 * * It does not hold the scaffold because scaffold is a compose Layout as  a result making it parent or caller composable
 * as scrollable or how some use sub compose can causes error.
 * there is a another version of that with scaffold
 *
 * @param modifier it has no default value,to avoid side effect,make sure you set the necessary value
 * such as fill-maxSize or fill-maxWidth when use it
 * @param showBothPanes ,will used to hide or show the pane2
 * @param primaryPane
 * @param secondaryPane ,will be the top of pane1 in compact mode and in medium and expanded mode
 * pane2 will be side of pane1 as siblings of Row
 */


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun TwoPaneLayout(
    modifier: Modifier=Modifier.fillMaxWidth(),
    props: TwoPaneProps = TwoPaneProps(),
    showBothPanes: Boolean,
    alignment: Alignment = Alignment.Center,
    primaryPane: @Composable () -> Unit,
    secondaryPane: @Composable () -> Unit,
) {

    val windowSize = calculateWindowSizeClass()
    val windowWidth = windowSize.widthSizeClass
    when (windowWidth) {
        WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> {
            _CompactModeLayout(
                modifier=modifier,
                showTopPane = showBothPanes,
                enter = props.topPaneAnimation.enter,
                exit = props.topPaneAnimation.exit,
                pane1 = primaryPane,
                topPane = secondaryPane
            )
        }

        WindowWidthSizeClass.Expanded -> {
            _NonCompactModeLayout(
                modifier = modifier,
                props = props,
                showTopOrRightPane = showBothPanes,
                alignment = alignment,
                leftPane = primaryPane,
                topOrRightPane = secondaryPane
            )
        }
    }


}


/**
 * Creating it own  Window size decorator so that this file does not depends on another file
 * as a result it can be direcly copy-paste
 *  @param modifier it has no default value,to avoid side effect,make sure you set the necessary value
 *  such as fill-maxSize or fill-maxWidth when use it
 */

@Composable
private fun _CompactModeLayout(
    modifier: Modifier,
    showTopPane: Boolean,
    enter: EnterTransition = fadeIn() + expandIn(),
    exit: ExitTransition = shrinkOut() + fadeOut(),
    pane1: @Composable () -> Unit,
    topPane: @Composable () -> Unit,
) {
    if (!showTopPane) {
        pane1()
    }
    AnimatedVisibility(
        modifier = modifier,
        enter = enter,
        exit = exit, //TODO: fix the animation transition later
        visible = showTopPane
    ) {
        topPane()
    }
}
/**
 * Creating it own  Window size decorator so that this file does not depends on another file
 * as a result it can be direcly copy-paste
 *  @param modifier it has no default value,to avoid side effect,make sure you set the necessary value
 *   such as fill-maxSize or fill-maxWidth when use it
 */
@Composable
private fun _NonCompactModeLayout(
    modifier: Modifier,
    props: TwoPaneProps = TwoPaneProps(),
    showTopOrRightPane: Boolean,
    alignment: Alignment = Alignment.Center,
    leftPane: @Composable () -> Unit,
    topOrRightPane: @Composable () -> Unit,
) {
    Row(modifier = modifier) {
        if (showTopOrRightPane) {
            Box(Modifier.weight(1f)) {
                leftPane()
            }
            Spacer(Modifier.width(props.horizontalSpace))
            Box(Modifier.weight(1f)) {
                topOrRightPane()
            }
        } else {
            Box(Modifier.fillMaxSize(), contentAlignment = alignment) {
                leftPane()
            }

        }
    }

}
data class CompactModeTopPaneAnimation(
    val enter: EnterTransition = slideIn(
        animationSpec = tween(durationMillis = 300, easing = EaseIn),
        initialOffset = { IntOffset(it.width, 0) }
    ),
    val exit: ExitTransition =  slideOut(
        animationSpec = tween(durationMillis = 300, easing = EaseIn),
        targetOffset ={ IntOffset(0, 0) }
    )
)

data class TwoPaneProps(
    val horizontalSpace: Dp = 12.dp,
    val pane1FillMaxWidth: Boolean = false,
    val pane1MaxWidthPortion: Float = 0.5f,
    val topPaneAnimation: CompactModeTopPaneAnimation = CompactModeTopPaneAnimation()
)