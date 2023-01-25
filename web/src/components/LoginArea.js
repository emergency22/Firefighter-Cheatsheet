import FirefighterCheatsheetClient from '../api/firefigherCheatsheetClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class LoginArea extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addLoginAreaToPage', 'createSiteTitle', 'createUserInfoForLoginArea',
            'createLoginButton', 'createLoginButton', 'createLogoutButton'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new FirefighterCheatsheetClient();
    }

    /**
     * Add the header to the page.
     */
    async addLoginAreaToPage() {
        const currentUser = await this.client.getIdentity();

        const siteTitle = this.createSiteTitle();
        const userInfo = this.createUserInfoForLoginArea(currentUser);

        const loginArea = document.getElementById('loginArea');
        loginArea.appendChild(siteTitle);
        loginArea.appendChild(userInfo);
    }

    createSiteTitle() {
        const homeButton = document.createElement('a');
        homeButton.classList.add('loginArea_home');
        homeButton.href = 'index.html';
        homeButton.innerText = 'Playlists';

        const siteTitle = document.createElement('div');
        siteTitle.classList.add('site-title');
        siteTitle.appendChild(homeButton);

        return siteTitle;
    }

    createUserInfoForHeaderLoginArea(currentUser) {
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
}
