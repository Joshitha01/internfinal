import {Injectable} from "@angular/core";
import {Resolve} from "@angular/router";
import {UserService} from "./UserService";
import {Observable} from "rxjs";

/**
 * @author Timur Berezhnoi.
 */
@Injectable()
export class UsersResolver implements Resolve<Observable<any>> {

    constructor(private userService: UserService) {}

    resolve(): any {
        return this.userService.getUsers();
    }
}
