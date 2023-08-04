import {Component} from '@angular/core';
import {MatIconRegistry} from "@angular/material";
import {DomSanitizer} from "@angular/platform-browser";

/**
 * @author Timur Berezhnoi.
 */
@Component({
    selector: "usermanagement",
    templateUrl: "./usermanagement.html"
})
export class UserManagementApplication {

    constructor(private matIconRegistry: MatIconRegistry, private domSanitizer: DomSanitizer) {
        this.matIconRegistry.addSvgIcon("sign_out", this.domSanitizer.bypassSecurityTrustResourceUrl("../../assets/icon/sign-out-alt-solid.svg"));
    }
}
