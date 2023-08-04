import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserDTO} from "../../dto/UserDTO";

/**
 * @author Timur Berezhnoi.
 */
@Injectable()
export class UserService {

    private readonly SIGNIN_END_POINT: string = "/signIn";
    private readonly SIGN_OUT_END_POINT: string = "/signOut";
    private readonly USER_END_POINT: string = "/user";
    private readonly USERS_END_POINT: string = "/users";

    constructor(private httpClient: HttpClient) {}

    signIn(user: UserDTO): Observable<any> {
        let signInUrl: string = `${this.SIGNIN_END_POINT}?email=${user.email}&password=${user.password}&rememberMe=true`;
        return this.httpClient.post(signInUrl, {});
    }

    signOut(): Observable<any> {
        return this.httpClient.get(this.SIGN_OUT_END_POINT);
    }

    getUsers(): Observable<any> {
        return this.httpClient.get(this.USERS_END_POINT);
    }

    createUser(user: UserDTO): Observable<any> {
        return this.httpClient.post(this.USER_END_POINT, user);
    }

    deleteUser(email: string): Observable<any> {
        return this.httpClient.delete(`${this.USER_END_POINT}/${email}`);
    }
}
