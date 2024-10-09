package epm.xnox.fruitshop.ui.navigation

import android.net.Uri

sealed class NavRoutes(val route: String) {

    data object HomeScreen : NavRoutes("home")

    data object CartScreen : NavRoutes("cart")

    data object SearchScreen : NavRoutes("search")

    data object AccountScreen : NavRoutes("account")

    data object SignUpScreen : NavRoutes("signup")

    data object DetailScreen : NavRoutes("detail/{name}/{detail}/{image}/{price}/{rating}") {
        fun createRoute(name: String, detail: String, image: String, price: Float, rating: Float) =
            "detail/$name/$detail/${Uri.encode(image)}/$price/$rating"
    }

}
