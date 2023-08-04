import {Component, OnInit, ViewChild} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../service/user/UserService";
import {RoutePath} from "../../RoutePath";
import {FormBuilder, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {UserDTO} from "../../dto/UserDTO";
import {MatSnackBar, MatTable} from "@angular/material";

/**
 * @author Timur Berezhnoi.
 */
@Component({
    selector: "main-component",
    templateUrl: "./users.html",
    styleUrls: ["./users.css"]
})
export class UsersComponent implements OnInit {

    tableColumns: string[] = ["positionNumber", "email", "actions"];
    createUserForm: FormGroup;
    formSubmitted: boolean;
    emails: Array<string>;

    @ViewChild(MatTable)
    private table: MatTable<UserDTO>;

    constructor(private userService: UserService,
                private router: Router,
                private route: ActivatedRoute,
                private formBuilder: FormBuilder,
                private snackBar: MatSnackBar) {
    }

    ngOnInit(): void {
        this.emails = this.route.snapshot.data.users;

        this.createUserForm = this.formBuilder.group({
            email: [null, [Validators.required, Validators.minLength(6), Validators.maxLength(40),
                Validators.pattern("^[a-z0-9-\\+]+(\\.[a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9]+)$")]],
            password: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(20)]]
        });
    }

    signOut(event: any): void {
        event.preventDefault();
        this.userService.signOut().subscribe(
            () => {
                localStorage.clear();
                this.router.navigate([RoutePath.LOGIN]).then(/* Do nothing */);
            },
            (error: any) => {
                console.log(error);
            });
    }

    createUser(userDto: UserDTO, formDirective: FormGroupDirective): void {
        if(this.createUserForm.valid) {
            this.formSubmitted = true;
            this.userService.createUser(userDto).subscribe(
                response => {
                    this.resetCreateUserForm(formDirective);
                    this.emails.push(userDto.email);
                    this.table.renderRows();
                },
                error => {
                    this.formSubmitted = false;
                    this.openSnackBar(error.error.message);
                },
                () => {
                    this.formSubmitted = false;
                }
            );
        }
    }

    deleteUser(email: string, index: number) {
        this.formSubmitted = true;
        this.userService.deleteUser(email).subscribe(
            () => {
                this.emails.splice(index, 1);
                this.table.renderRows();
            },
            () => {
                this.formSubmitted = false;
                this.openSnackBar(`Something went wrong while deleting user: ${email}`)
            },
            () => {
                this.formSubmitted = false;
            }
        );
    }

    get createUserFormControls() {
        return this.createUserForm.controls;
    }

    get loggedUser(): string {
        return localStorage.getItem("logged_in_user");
    }

    /**
     * Reset user form and more over form directive should be reset as well, because
     * for some reason it's not enough just to reset a main form.
     *
     * @param {FormGroupDirective} formDirective
     */
    private resetCreateUserForm(formDirective: FormGroupDirective): void {
        formDirective.resetForm();
        this.createUserForm.reset();
    }

    private openSnackBar(message: string) {
        this.snackBar.open(message, "", {
            duration: 5 * 1000,
            verticalPosition: "top",
            horizontalPosition: "right",
            panelClass: ["red-snackbar"]
        });
    }
}
