import FirefighterCheatsheetClient from '../api/firefighterCheatsheetClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteTitle', 'createUserInfoForHeader',
            'createLoginButton', 'createLoginButton', 'createLogoutButton', 'createUserInterface', 
            'deleteAnApparatus'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new FirefighterCheatsheetClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const siteTitle = this.createSiteTitle();
        const userInfo = this.createUserInfoForHeader(currentUser);

        const header = document.getElementById('loginArea');
        header.appendChild(siteTitle);
        header.appendChild(userInfo);
    }

    createSiteTitle() {
        const homeButton = document.createElement('a');
        homeButton.classList.add('header_home');
        homeButton.href = 'index.html';
        homeButton.innerText = null;   //Orignally 'Playlists'

        const siteTitle = document.createElement('div');
        siteTitle.classList.add('site-title');
        siteTitle.appendChild(homeButton);

        return siteTitle;
    }

    createUserInfoForHeader(currentUser) {
        const userInfo = document.createElement('div');
        userInfo.classList.add('user');

        const childContent = currentUser
            ? this.createLogoutButton(currentUser)
            : this.createLoginButton();

        userInfo.appendChild(childContent);

        return userInfo;
    }

    createLoginButton() {
        return this.createButton('Login', this.client.login);
    }

    createLogoutButton(currentUser) {
        this.createUserInterface();
        return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
    }

    createButton(text, clickHandler) {
        const button = document.createElement('a');
        button.classList.add('button');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async () => {
            await clickHandler();
        });

        return button;
    }

    createUserInterface() {
        const interfaceArea = document.getElementById('userInterfaceArea');
        interfaceArea.classList.remove('hidden');
        const hamToggler = document.getElementById('hamToggle');
        hamToggler.classList.remove('hidden');
        this.displayApparatus();
        this.displayAddApparatusMenu();
    }

    async displayApparatus() {
        const apparatusList = await this.client.getApparatus();     //may want to set apparatusList in the datastore later. dunno.
        if (apparatusList.length == 0) {
            document.getElementById('theDisplayArea').innerHTML = "No apparatus exist for this account. Add your apparatus below."
        }

        for (var i=0; i < apparatusList.length; i++) {
            var currentApparatus = apparatusList[i];
            if (currentApparatus.fireDept != null) {
                var fireDept = currentApparatus.fireDept;
                var currentApparatus = currentApparatus.apparatusTypeAndNumber;

                document.getElementById('theDisplayArea').innerHTML += 
                "<br><li>" + fireDept + 
                " " + 
                currentApparatus + 
                "<span>" +
                (document.getElementById('deleteButton').innerHTML += " X") +
                "</span>" +
                "<div class='editHoses'>" +
                (document.getElementById('editHoses').innerHTML += " Edit Hoses for " + fireDept + " " + currentApparatus) +
                " </div></li> ";
            }
        }
  
    }

    async deleteAnApparatus() {
        const delButton = document.getElementById('deleteButton');   //get the location where the action should happen
        delButton.addEventListener('click', deleteThenRefresh(currentApparatus));  //with a click, run the client's delete apparatus function, passing in the relevant apparatus
    }

    async deleteThenRefresh(currentApparatus) {
        await this.client.deleteApparatus(currentApparatus)
        this.displayApparatus();
    }

    async displayAddApparatusMenu() {
        (document.getElementById('addApparatusForm').innerHTML += "<div class='addApp'><form>" +
            "<label for='fireDept'>Fire Department</label>" +
            "<input type='text' id='fireDept' name='fireDept'>" +
            "<label for='apparatusTypeAndNumber'>Apparatus Type and Number</label>" +
            "<input type='text' id='apparatusTypeAndNumber' name='apparatusTypeAndNumber'>" +
            "<input type='submit' value='Add Apparatus'></div>"
        );

        this.readyAddApparatus();
    }

    async readyAddApparatus() {
        const inputFireDept = document.getElementById('fireDept');
        const inputApparatusTypeAndNumber = document.getElementById('apparatusTypeAndNumber');

        if (inputFireDept === 'Fire Department' || inputApparatusTypeAndNumber === 'Apparatus Type and Number') {
            return;
        }

        if (inputFireDept && inputApparatusTypeAndNumber) {
            await this.client.addApparatus(inputFireDept, inputApparatusTypeAndNumber);
            this.displayApparatus;
        }
    }

}
