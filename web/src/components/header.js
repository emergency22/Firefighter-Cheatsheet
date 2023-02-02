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
            'createLoginButton', 'createLoginButton', 'createLogoutButton', 'createUserInterface'
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
        this.displayApparatusOnLogin();
    }

    async displayApparatusOnLogin() {
//        const currentUser = await this.client.getIdentity();
//        const currentUserName = currentUser.name;
//        console.log("CurrentUserName: " + currentUserName);
        const apparatusList = await this.client.getApparatus();     //may want to set apparatusList in the datastore later. dunno.
        if (apparatusList == null) {
            console.log("no wait, I don't have it");
        }
        if (apparatusList.length == 0) {
            document.getElementById('theDisplayArea').innerHTML = "No apparatus exist for this account. Try adding some below."
        }
        
        for (var i=0; i < apparatusList.length; i++) {
            var currentApparatus = apparatusList[i];
            console.log("yes" + currentApparatus);

            if (currentApparatus.fireDept != null) {
                document.getElementById('theDisplayArea').innerHTML += "<br>"+ currentApparatus.fireDept + " " + currentApparatus.apparatusTypeAndNumber + "<br>";
            }

        }


        console.log("apparatusList: " + apparatusList);
        const displayArea = document.getElementById('theDisplayArea');
        //displayArea.innerHTML = `"${apparatusList}"`;
        //input apparatusList into html somehow
    }
}
