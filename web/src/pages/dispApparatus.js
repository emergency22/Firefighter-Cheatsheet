import FirefighterCheatsheetClient from '../api/firefighterCheatsheetClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";

/**
 * Logic needed for the view apparatus page of the website.
 */
class DispApparatus extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount'], this);
        this.header = new Header();
    }

    /**
     * Add the header to the page and load the FirefighterCheatsheetClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new FirefighterCheatsheetClient();
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const dispApparatus = new DispApparatus();
    dispApparatus.mount();
};

window.addEventListener('DOMContentLoaded', main);
