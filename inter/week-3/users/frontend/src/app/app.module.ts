import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NgModule} from "@angular/core";
import {HashLocationStrategy, LocationStrategy} from "@angular/common";
import {Router, RouterModule} from "@angular/router";
import {ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";

import {UserManagementApplication} from "./component/UserManagementApplication";
import {LoginComponent} from "./component/login/LoginComponent";
import {UsersComponent} from "./component/user/UsersComponent";

import {UserService} from "./service/user/UserService";

import {routes} from "./app.routes";
import {AuthenticatedInterceptor} from "./service/http-interceptor/AuthenticatedInterceptor";
import {MatAutocompleteModule, MatButtonModule, MatButtonToggleModule, MatCardModule, MatCheckboxModule, MatChipsModule, MatDatepickerModule, MatDialogModule, MatDividerModule, MatExpansionModule, MatGridListModule, MatIconModule, MatInputModule, MatListModule, MatMenuModule, MatNativeDateModule, MatPaginatorIntl, MatPaginatorModule, MatProgressBarModule, MatProgressSpinnerModule, MatRadioModule, MatRippleModule, MatSelectModule, MatSidenavModule, MatSliderModule, MatSlideToggleModule, MatSnackBarModule, MatSortModule, MatStepperModule, MatTableModule, MatTabsModule, MatToolbarModule, MatTooltipModule} from "@angular/material";
import {UsersResolver} from "./service/user/UsersResolver";

@NgModule({
    declarations: [
        UserManagementApplication,
        LoginComponent,
        UsersComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        MatAutocompleteModule,
        MatButtonModule,
        MatButtonToggleModule,
        MatCardModule,
        MatCheckboxModule,
        MatChipsModule,
        MatDatepickerModule,
        MatDialogModule,
        MatDividerModule,
        MatExpansionModule,
        MatGridListModule,
        MatIconModule,
        MatInputModule,
        MatListModule,
        MatMenuModule,
        MatNativeDateModule,
        MatPaginatorModule,
        MatProgressBarModule,
        MatProgressSpinnerModule,
        MatRadioModule,
        MatRippleModule,
        MatSelectModule,
        MatSidenavModule,
        MatSliderModule,
        MatSlideToggleModule,
        MatSnackBarModule,
        MatSortModule,
        MatStepperModule,
        MatTableModule,
        MatTabsModule,
        MatToolbarModule,
        MatTooltipModule,
        ReactiveFormsModule,
        HttpClientModule,
        RouterModule.forRoot(routes),
    ],
    providers: [
        UserService,
        UsersResolver,
        {
            provide: LocationStrategy,
            useClass: HashLocationStrategy
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthenticatedInterceptor,
            multi: true,
            deps: [Router]
        }
    ],
    bootstrap: [UserManagementApplication]
})
export class AppModule {
}
