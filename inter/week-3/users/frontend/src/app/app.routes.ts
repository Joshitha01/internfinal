import {Routes} from "@angular/router";
import {LoginComponent} from "./component/login/LoginComponent";
import {RoutePath} from "./RoutePath";
import {UsersComponent} from "./component/user/UsersComponent";
import {UsersResolver} from "./service/user/UsersResolver";

/**
 * @author Timur Berezhnoi
 */
export const routes: Routes = [
    {
        path: "",
        redirectTo: RoutePath.USERS,
        pathMatch: "full"
    },
    {
        path: RoutePath.USERS,
        component: UsersComponent,
        resolve: {users: UsersResolver}
    },
    {
        path: RoutePath.LOGIN,
        component: LoginComponent
    },
    {
        path: "**",
        redirectTo: RoutePath.USERS
    }
];
