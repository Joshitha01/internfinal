/**
 * @author Timur Berezhnoi
 */
export class ResponseMessageDTO {
    constructor(private _message: string) {}

    get message(): string {
        return this._message;
    }
}
