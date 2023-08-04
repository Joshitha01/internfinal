import {Component, OnDestroy, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ResponseMessageDTO} from "../../dto/ResponseMessageDTO";
import {UserService} from "../../service/user/UserService";
import {RoutePath} from "../../RoutePath";
import {UserDTO} from "../../dto/UserDTO";

/**
 * @author Timur Berezhnoi.
 */
@Component({
    selector: "login-component",
    templateUrl: "./login.html",
    styleUrls: ["./login.css"]
})
export class LoginComponent implements OnInit, OnDestroy {

    signInForm: FormGroup;
    errorMessage: ResponseMessageDTO = null;

    constructor(private router: Router,
                private formBuilder: FormBuilder,
                private userService: UserService) {}


    ngOnInit(): void {
        document.body.classList.add("back-ground-image");

        this.signInForm = this.formBuilder.group({
            email: [null, [Validators.required, Validators.minLength(6), Validators.maxLength(40),
                Validators.pattern("^[a-z0-9-\\+]+(\\.[a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9]+)$")]],
            password: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(20)]]
        });
    }

    signIn(): void {
        if(this.signInForm.valid) {
            const model = this.signInForm.value;
            this.userService.signIn(new UserDTO(model.email, model.password)).subscribe(
                () => {
                    localStorage.setItem("logged_in_user", model.email);
                    this.router.navigate([RoutePath.USERS]).then(/* Do nothing */);
                },
                (error: any) => {
                    this.errorMessage = new ResponseMessageDTO(error.error.message);
                });
        }
    }

    ngOnDestroy(): void {
        document.body.classList.remove("back-ground-image");
    }

    get formControls() {
        return this.signInForm.controls;
    }
}
