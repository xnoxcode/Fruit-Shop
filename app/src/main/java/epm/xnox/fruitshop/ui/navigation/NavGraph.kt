package epm.xnox.fruitshop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import epm.xnox.fruitshop.presentation.account.ui.AccountScreen
import epm.xnox.fruitshop.presentation.account.ui.SignUpScreen
import epm.xnox.fruitshop.presentation.account.viewModel.AccountViewModel
import epm.xnox.fruitshop.presentation.cart.ui.CartScreen
import epm.xnox.fruitshop.presentation.cart.viewModel.CartViewModel
import epm.xnox.fruitshop.presentation.detail.ui.DetailScreen
import epm.xnox.fruitshop.presentation.detail.viewModel.DetailViewModel
import epm.xnox.fruitshop.presentation.home.ui.HomeScreen
import epm.xnox.fruitshop.presentation.home.ui.SearchScreen
import epm.xnox.fruitshop.presentation.home.viewModel.HomeViewModel

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = NavRoutes.HomeScreen.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = homeViewModel,
                onNavigateItem = { item ->
                    navController.navigate(
                        NavRoutes.DetailScreen.createRoute(
                            item.name,
                            item.detail,
                            item.image,
                            item.price,
                            item.rating
                        )
                    )
                },
                onNavigateBottomBar = { route ->
                    navController.navigate(route.route)
                }
            )
        }
        composable(
            route = NavRoutes.DetailScreen.route,
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("detail") { type = NavType.StringType },
                navArgument("image") { type = NavType.StringType },
                navArgument("price") { type = NavType.FloatType },
                navArgument("rating") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val detailViewModel = hiltViewModel<DetailViewModel>()
            DetailScreen(backStackEntry = backStackEntry, viewModel = detailViewModel) {
                navController.popBackStack()
            }
        }
        composable(route = NavRoutes.CartScreen.route) {
            val cartViewModel = hiltViewModel<CartViewModel>()
            CartScreen(viewModel = cartViewModel) {
                navController.popBackStack()
            }
        }
        composable(route = NavRoutes.SearchScreen.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            SearchScreen(
                viewModel = homeViewModel,
                onNavigate = {
                    navController.popBackStack()
                },
                onNavigateItem = { item ->
                    navController.navigate(
                        NavRoutes.DetailScreen.createRoute(
                            item.name,
                            item.detail,
                            item.image,
                            item.price,
                            item.rating
                        )
                    )
                },
            )
        }
        composable(route = NavRoutes.AccountScreen.route) {
            val accountViewModel = hiltViewModel<AccountViewModel>()
            AccountScreen(
                viewModel = accountViewModel,
                onNavigate = { navController.popBackStack() },
                onSignUp = { navController.navigate(NavRoutes.SignUpScreen.route) }
            )
        }
        composable(route = NavRoutes.SignUpScreen.route) {
            val accountViewModel = hiltViewModel<AccountViewModel>()
            SignUpScreen(viewModel = accountViewModel) {
                navController.popBackStack()
            }
        }
    }
}