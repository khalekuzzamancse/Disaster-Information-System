package components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import routes.Destination

/*
 * Manage the own navRail so that does not need to copy-paste or implement the nav-rail file separately
 * Maintain can calculating the window size manually so that does not copy-paste or implement the window decorator  file separately
 */
/**
 * * Decorate the bottom bar
 * * It manage it own navRail version so that
 * * Manage it own Scaffold,since scaffold is sub compose layout so making it parent
 * as scrabble without defining it size can causes crash.
 * * But the [content] can be scrollable without any effect
 * * If you used it inside another sub compose layout such as Scaffold or Lazy List then
 * and make the parent scrollable then it can causes crash,so use modifier to define it size in that case
 * @param modifier the scaffold modifier,so that you can control the scaffold
 * @param selected is Nullable because it might possible that no destination is selected
 * * mandatory parameters: [destinations],[onDestinationSelected],[content]
 *
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun BottomBarToNavRailDecorator(
    modifier: Modifier = Modifier,
    destinations: List<NavigationItem>,
    onDestinationSelected: (Destination) -> Unit,
    selected: Int? = null,
    topAppbar: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val windowSize = calculateWindowSizeClass().widthSizeClass
    val compact = WindowWidthSizeClass.Compact
    val medium = WindowWidthSizeClass.Medium
    val expanded = WindowWidthSizeClass.Expanded

    AnimatedContent(windowSize) { window ->
        when (window) {
            compact -> {
                BottomBarLayout(
                    modifier = modifier,
                    destinations = destinations,
                    onItemSelected = onDestinationSelected,
                    selected = selected,
                    topAppbar = topAppbar,
                    content = content
                )
            }
            medium, expanded -> {
                NavRailLayout(
                    destinations = destinations,
                    onItemSelected = onDestinationSelected,
                    selected = selected,
                    topAppbar = topAppbar,
                    content = content
                )
            }

        }
    }


}

/*
 * Used to loose coupling,so that direcly this file can be copy -paste without the nav-rail dependency
 */
/**
 * * It does only the information that need to NavigationItem
 * * It does not hold any extra information
 * * Mandatory params : [label] , [focusedIcon]
 * Storing the destination here,to reduce the client reprehensibly to figure out
 * which destination is cliecked
 */
class NavigationItem(
    val label: String,
    val focusedIcon: ImageVector,
    val unFocusedIcon: ImageVector = focusedIcon,
    val badge: String? = null,
    val destination: Destination=Destination.None,
)


@Composable
private fun BottomBarLayout(
    modifier: Modifier = Modifier,
    destinations: List<NavigationItem>,
    onItemSelected: (Destination) -> Unit,
    selected: Int? = null,
    topAppbar: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = topAppbar,
        bottomBar = {
            _BottomNavBar(
                destinations = destinations,
                selected = selected,
                onDestinationSelected = onItemSelected
            )
        }
    ) { scaffoldPadding ->
        Box(Modifier.padding(scaffoldPadding)) {
            content()
        }

    }
}

@Composable
private fun NavRailLayout(
    modifier: Modifier = Modifier,
    destinations: List<NavigationItem>,
    onItemSelected: (Destination) -> Unit,
    selected: Int? = null,
    topAppbar: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Row(modifier = modifier) {
        _NavRail(
            destinations = destinations,
            selected = selected,
            onItemSelected = onItemSelected
        )
        Scaffold(
            modifier = Modifier,
            topBar = topAppbar,
        ) { scaffoldPadding ->
            Box(Modifier.padding(scaffoldPadding)) { content() }//takes the remaining space,after the NavRail takes place
        }

    }


}

/**
 * Used to loose coupling,so that direcly this file can be copy -paste without the nav-rail dependency
 */
@Composable
private fun _NavRail(
    modifier: Modifier = Modifier,
    destinations: List<NavigationItem>,
    onItemSelected: (Destination) -> Unit,
    selected: Int? = null,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Surface(
            modifier = Modifier.fillMaxHeight(),
            tonalElevation = 3.dp //same as bottom bar elevation
        ) {
            Column(Modifier.width(IntrinsicSize.Max)) {
                destinations.forEachIndexed { index, navigationItem ->
                    //Using Drawer item so place the icon and label side by side
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f),
                            selectedIconColor = MaterialTheme.colorScheme.secondary,
                            selectedTextColor = MaterialTheme.colorScheme.contentColorFor(
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f)
                            ),

                            unselectedIconColor = MaterialTheme.colorScheme.primary,//because they are clickable button,so high importance


                            //   se = MaterialTheme.colorScheme.onSecondary,

                        ),
                        modifier = Modifier.padding(4.dp),
                        icon = {
                            Icon(
                                navigationItem.focusedIcon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = navigationItem.label
                            )
                        },
                        selected = selected == index,
                        onClick = { onItemSelected(navigationItem.destination) },
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }
        }


    }
}

/**
 * Used to loose coupling,so that direcly this file can be copy -paste without BottomBar from another file
 */

@Composable
private fun _BottomNavBar(
    modifier: Modifier = Modifier,
    destinations: List<NavigationItem>,
    selected: Int? = null,
    onDestinationSelected: (Destination) -> Unit,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        destinations.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = selected == index,
                onClick = {
                    onDestinationSelected(destination.destination)
                },
                label = {
                    Text(text = destination.label)
                },
                alwaysShowLabel = false,
                icon = {
                    BadgedBox(
                        badge = {
                            if (destination.badge != null) {
                                Badge {
                                    Text(text = destination.badge.toString())
                                }
                            }
                        }
                    ) {
                        (if (index == selected) {
                            destination.focusedIcon
                        } else destination.unFocusedIcon).let {
                            Icon(
                                imageVector = it,
                                contentDescription = destination.label
                            )
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                    unselectedIconColor = MaterialTheme.colorScheme.primary,//because they are clickable button,so high importance

                )
            )
        }
    }


}
