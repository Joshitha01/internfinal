import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {Injectable} from "@angular/core";
import {catchError} from "rxjs/internal/operators";
import {Router} from "@angular/router";
import {RoutePath} from "../../RoutePath";

/**
 * @author Timur Berezhnoi.
 */
@Injectable()
export class AuthenticatedInterceptor implements HttpInterceptor {

    constructor(private router: Router) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(
            catchError(err => {
                if(err instanceof HttpErrorResponse) {
                    if(err.status === 401) {
                        this.router.navigate([RoutePath.ROOT + RoutePath.LOGIN]).then(/* DO NOTHING */);
                    }
                }

                return throwError(err);
            }))
    }
}
